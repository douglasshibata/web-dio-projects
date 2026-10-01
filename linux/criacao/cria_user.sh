#!/bin/bash
set -euo pipefail

# Security Fix: Allow override via environment variable, defaulting to secure SHA-512 crypt hashing (-6) instead of deprecated legacy DES (-crypt).
DEFAULT_PASS="${USER_PASSWORD:-Senha123}"
PASSWORD_HASH=$(openssl passwd -6 "$DEFAULT_PASS")

echo "Criando usuários do sistema..."

for user in guest10 guest11 guest12 guest13; do
    if ! id "$user" &>/dev/null; then
        useradd "$user" -c "Usuário convidado" -s /bin/bash -m -p "$PASSWORD_HASH"
        passwd "$user" -e
        echo "Usuário $user criado com sucesso."
    else
        echo "Usuário $user já existe. Ignorando."
    fi
done

echo "Finalizado!!"
