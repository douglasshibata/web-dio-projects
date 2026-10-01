import os
import sys
import pyaes

# Security Fix: Allow obtaining decryption key from environment variable to avoid hardcoded secrets.
DEFAULT_KEY = b"testeransomwares"

def get_decryption_key() -> bytes:
    key_env = os.getenv("RANSOMWARE_KEY")
    if key_env:
        key_bytes = key_env.encode('utf-8')
        if len(key_bytes) in (16, 24, 32):
            return key_bytes
        else:
            raise ValueError(f"Invalid key length ({len(key_bytes)} bytes). Key must be 16, 24, or 32 bytes.")
    return DEFAULT_KEY

def decrypt_file(encrypted_file_name: str = "text.txt.ransomwaretroll", output_file_name: str = "teste.txt", key: bytes = None) -> str:
    if key is None:
        key = get_decryption_key()

    if not os.path.exists(encrypted_file_name):
        raise FileNotFoundError(f"Encrypted file '{encrypted_file_name}' not found.")

    # Fix: Use context manager ('with') for safe file reading
    with open(encrypted_file_name, "rb") as file:
        file_data = file.read()

    # Initialize AES cipher in Counter mode
    aes = pyaes.AESModeOfOperationCTR(key)
    decrypt_data = aes.decrypt(file_data)

    # Fix: Write decrypted file safely using context manager before removing encrypted file
    with open(output_file_name, "wb") as new_file:
        new_file.write(decrypt_data)

    # Fix: Remove encrypted file only AFTER decrypted file is written successfully
    os.remove(encrypted_file_name)
    return output_file_name

if __name__ == "__main__":
    enc_file = sys.argv[1] if len(sys.argv) > 1 else "text.txt.ransomwaretroll"
    out_file = sys.argv[2] if len(sys.argv) > 2 else "teste.txt"
    try:
        dec_file = decrypt_file(enc_file, out_file)
        print(f"File successfully decrypted: {dec_file}")
    except Exception as e:
        print(f"Decryption failed: {e}", file=sys.stderr)
