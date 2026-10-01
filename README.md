# DIO Projects Monorepo

Welcome to the refactored and audited repository for Digital Innovation One (DIO) projects. This repository contains Java console applications, Python cybersecurity challenge tools, Linux administration scripts, and Docker container configurations.

---

## Project Overview & Architecture

The repository is organized into distinct domain-specific modules:

```text
.
├── conta-banco/                # Java Banking Terminal Application
│   └── src/
│       └── ContaTerminal.java  # Interactive account creation with safe Scanner validation
├── controle-fluxo/             # Java Control Flow & Exception Handling
│   └── src/
│       ├── App.java                      # Main application counter logic
│       ├── ParametrosInvalidosException.java # Custom domain exception
│       └── AppTest.java                  # Unit test suite
├── cibersecurity/              # Cybersecurity Educational Utilities
│   └── desafio-ransomware/
│       ├── encrypted.py        # Safe file encryption utility (AES CTR mode)
│       ├── decrypted.py        # Safe file decryption utility
│       └── test_ransomware.py  # Unit test suite
├── linux/                      # Shell Automation & System Administration
│   ├── criacao/
│   │   ├── cria_user.sh        # System user creation script (SHA-512 crypt)
│   │   └── iac.sh              # Infrastructure as Code setup script
│   └── apache/
│       └── script-cria-site.sh # Apache server deployment automation
├── docker/                     # Container Orchestration
│   ├── docker-compose.yml      # Multi-container Apache setup
│   └── docker-swarm/           # Swarm provisioning scripts
└── README.md
```

---

## Setup & Installation Instructions

### Prerequisites
- **Java Development Kit (JDK):** Version 11 or higher
- **Python:** Version 3.8 or higher
- **Bash Shell / Linux Environment** (for shell automation scripts)
- **Docker & Docker Compose** (optional, for container environments)

### Dependencies Installation

1. **Python Dependencies:**
   Install required cryptographic packages:
   ```bash
   pip install pyaes
   ```

2. **Java Environment:**
   Ensure `javac` and `java` are available on your `PATH`.

---

## Environment Variables Required

| Variable Name | Module | Default Value | Description |
|---|---|---|---|
| `RANSOMWARE_KEY` | `cibersecurity` | `testeransomwares` | Secret key for AES encryption/decryption (must be 16, 24, or 32 bytes). |
| `USER_PASSWORD` | `linux/criacao` | `Senha123` | Default initial password used when provisioning system guest accounts. |
| `DEFAULT_USER_PASSWORD` | `linux/criacao` | `Senha123` | Default password used by Infrastructure as Code provisioning (`iac.sh`). |

---

## How to Run the Test Suite

### Running Python Unit Tests
Execute unit tests for the encryption/decryption utilities:
```bash
PYTHONPATH=cibersecurity/desafio-ransomware python3 -m unittest discover -s cibersecurity/desafio-ransomware -p "test_*.py"
```

### Running Java Unit Tests
Compile and run the control flow unit test suite:
```bash
javac controle-fluxo/src/*.java
java -cp controle-fluxo/src AppTest
```

### Compiling Java Applications
To verify compilation of terminal banking application:
```bash
javac conta-banco/src/ContaTerminal.java
```

---

## Security Considerations & Vulnerabilities Resolved

During the code audit and security remediation phase, the following critical issues were resolved:

1. **Hardcoded Secrets Removed:**
   - **Issue:** Encryption keys and default user passwords were hardcoded in Python and Shell source files.
   - **Remediation:** Refactored utilities to load keys and passwords securely from environment variables (`RANSOMWARE_KEY`, `USER_PASSWORD`).

2. **Safe File Operations & Data Loss Prevention:**
   - **Issue:** Original files were deleted before verifying whether encrypted or decrypted outputs were successfully written to disk.
   - **Remediation:** Wrapped file reads/writes in context managers (`with` statements) and ensured original files are removed only after successful output verification.

3. **Deprecated Password Hashing (`openssl passwd -crypt`):**
   - **Issue:** Used legacy DES-based `crypt()` algorithm which is insecure and removed in modern OpenSSL 3.0+.
   - **Remediation:** Upgraded password hashing to SHA-512 (`openssl passwd -6`).

4. **Directory Permission Hardening:**
   - **Issue:** Directories created with global `chmod 777` permissions exposed shared folders to unauthorized file deletion/modification.
   - **Remediation:** Implemented sticky bit `chmod 1777` for public folders to prevent users from deleting files owned by others.

5. **Piped Remote Execution Risks (`curl | bash`):**
   - **Issue:** Docker installation scripts executed unverified scripts directly from network endpoints into root shell.
   - **Remediation:** Downloaded scripts to temporary local files prior to execution with strict shell options (`set -euo pipefail`).

6. **Input Validation & Exception Safety in Java:**
   - **Issue:** Unhandled `InputMismatchException` crashes and unclosed Scanner resources in terminal applications.
   - **Remediation:** Applied `try-with-resources` blocks for automatic stream closing and explicit loops for validating input format.
