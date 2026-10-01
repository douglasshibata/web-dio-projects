import os
import unittest
import tempfile
from encrypted import encrypt_file, get_encryption_key
from decrypted import decrypt_file, get_decryption_key

class TestRansomwareUtils(unittest.TestCase):

    def setUp(self):
        self.test_dir = tempfile.TemporaryDirectory()
        self.file_path = os.path.join(self.test_dir.name, "sample.txt")
        self.content = b"Hello, World! Security test content 12345."
        with open(self.file_path, "wb") as f:
            f.write(self.content)

    def tearDown(self):
        self.test_dir.cleanup()

    def test_encryption_and_decryption_cycle(self):
        # Perform encryption
        enc_file = encrypt_file(self.file_path)
        self.assertTrue(os.path.exists(enc_file))
        self.assertFalse(os.path.exists(self.file_path))

        # Perform decryption
        dec_file = os.path.join(self.test_dir.name, "restored.txt")
        decrypted_result = decrypt_file(enc_file, dec_file)
        self.assertTrue(os.path.exists(decrypted_result))
        self.assertFalse(os.path.exists(enc_file))

        # Verify decrypted content matches original
        with open(decrypted_result, "rb") as f:
            restored_content = f.read()
        self.assertEqual(restored_content, self.content)

    def test_missing_file_raises_error(self):
        non_existent = os.path.join(self.test_dir.name, "does_not_exist.txt")
        with self.assertRaises(FileNotFoundError):
            encrypt_file(non_existent)

        with self.assertRaises(FileNotFoundError):
            decrypt_file(non_existent)

    def test_custom_key_length_validation(self):
        os.environ["RANSOMWARE_KEY"] = "invalid_length_key"  # 18 chars, not 16/24/32
        with self.assertRaises(ValueError):
            get_encryption_key()

        with self.assertRaises(ValueError):
            get_decryption_key()

        # Clean environment
        del os.environ["RANSOMWARE_KEY"]

if __name__ == "__main__":
    unittest.main()
