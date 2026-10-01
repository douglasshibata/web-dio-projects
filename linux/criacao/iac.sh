#!/bin/bash
set -euo pipefail

## Infraestrutura como Código - Refactored and Secured

# Security Fix: Support secure SHA-512 crypt hashing (-6) instead of legacy DES (-crypt)
DEFAULT_PASS="${DEFAULT_USER_PASSWORD:-Senha123}"
PASS_HASH=$(openssl passwd -6 "$DEFAULT_PASS")

echo "Criando diretórios..."
for dir in /publico /adm /ven /sec; do
    mkdir -p "$dir"
done

echo "Criando grupos de usuários..."
for group in GRP_ADM GRP_VEN GRP_SEC; do
    if ! getent group "$group" &>/dev/null; then
        groupadd "$group"
    fi
done

echo "Criando usuários..."
declare -A user_groups=(
    ["carlos"]="GRP_ADM"
    ["maria"]="GRP_ADM"
    ["joao"]="GRP_ADM"
    ["debora"]="GRP_VEN"
    ["sebastiana"]="GRP_VEN"
    ["roberto"]="GRP_VEN"
    ["josefina"]="GRP_SEC"
    ["amanda"]="GRP_SEC"
    ["rogerio"]="GRP_SEC"
)

for user in "${!user_groups[@]}"; do
    group="${user_groups[$user]}"
    if ! id "$user" &>/dev/null; then
        useradd "$user" -m -s /bin/bash -p "$PASS_HASH" -G "$group"
        echo "Usuário $user criado no grupo $group."
    fi
done

echo "Especificando permissões dos diretórios..."

chown root:GRP_ADM /adm
chown root:GRP_VEN /ven
chown root:GRP_SEC /sec

chmod 770 /adm
chmod 770 /ven
chmod 770 /sec

# Security Fix: Use sticky bit 1777 (or 775) to prevent non-owners from deleting other users' files in public directory
chmod 1777 /publico

echo "Configuração concluída com sucesso!"
