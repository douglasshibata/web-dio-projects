#!/bin/bash
set -euo pipefail

echo "Atualizando o servidor..."
apt-get update
apt-get upgrade -y
apt-get install apache2 unzip wget -y

echo "Baixando e copiando os arquivos da aplicação..."

TMP_DIR=$(mktemp -d)
trap 'rm -rf "$TMP_DIR"' EXIT

cd "$TMP_DIR"
wget -q https://github.com/douglasshibata/web-dio-projects/archive/refs/heads/main.zip -O main.zip
unzip -q main.zip

if [ -d "web-dio-projects-main/linux/apache/linux-site-dio" ]; then
    cp -R web-dio-projects-main/linux/apache/linux-site-dio/* /var/www/html/
elif [ -d "web-dio-projects/linux/apache/linux-site-dio" ]; then
    cp -R web-dio-projects/linux/apache/linux-site-dio/* /var/www/html/
else
    echo "Diretório de origem não encontrado no arquivo baixado!" >&2
    exit 1
fi

echo "Aplicação Apache implantada com sucesso!"
