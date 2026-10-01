#!/bin/bash
set -euo pipefail

# Security Fix: Download install script to file before execution instead of unsafe piped curl | bash
INSTALLER=$(mktemp)
trap 'rm -f "$INSTALLER"' EXIT

curl -fsSL https://get.docker.com -o "$INSTALLER"
sudo bash "$INSTALLER"

COMPOSE_VERSION="v2.24.5"
sudo curl -fsSL "https://github.com/docker/compose/releases/download/${COMPOSE_VERSION}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

if id "vagrant" &>/dev/null; then
    sudo usermod -aG docker vagrant
fi

echo "Docker e Docker Compose instalados com sucesso!"
