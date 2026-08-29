# Configuração do Banco de Dados PostgreSQL

Links oficiais de download:

- Windows: https://www.postgresql.org/download/windows/
- Linux: https://www.postgresql.org/download/linux/

## Visão geral

Este documento descreve como instalar (links acima), criar e configurar o banco de dados PostgreSQL usado pelo projeto SmartRoute. Inclui passos para Windows e Linux, criação de usuário/ banco, permissões básicas e comandos úteis (psql, createdb, pg_restore).

> Versão recomendada: PostgreSQL 18 ou superior.

## Passos comuns

- Certifique-se que o serviço do PostgreSQL está rodando.
- Tenha acesso a um usuário com permissões para criar bancos (ex.: postgres).
- Ajuste variáveis de ambiente / strings de conexão no arquivo de configuração do projeto (ex.: .env).

## Configuração no Windows

1. Instale o PostgreSQL pelo instalador do link acima.
2. Abra o "SQL Shell (psql)" ou um CMD/PowerShell.
3. Se necessário, navegue até a pasta bin (ex.: C:\Program Files\PostgreSQL\14\bin).
4. Criar banco de dados (como usuário postgres):

    createdb -U postgres smart_route

    - Se pedir senha, informe a senha do usuário postgres.

5. Criar usuário (opcional):

    psql -U postgres
    CREATE USER seu_usuario WITH PASSWORD 'sua_senha';
    ALTER ROLE seu_usuario CREATEDB;
    \q

6. Conectar com o novo usuário e criar o banco (se preferir):

    createdb -U seu_usuario -W smart_route

7. Verificar conexão:

    psql -U seu_usuario -d smart_route -h localhost

## Configuração no Linux

As instruções abaixo cobrem distribuições baseadas em Debian/Ubuntu e RHEL/CentOS (com pequenos ajustes).

1. Instale o PostgreSQL via gerenciador de pacotes:

    - Ubuntu/Debian: sudo apt update && sudo apt install postgresql postgresql-contrib
    - CentOS/RHEL: sudo dnf install postgresql-server postgresql-contrib

2. Inicialize e inicie o serviço (se necessário):

    - Ubuntu/Debian (systemd normalmente já inicia): sudo systemctl start postgresql
    - CentOS/RHEL: sudo postgresql-setup --initdb && sudo systemctl start postgresql

3. Troque para o usuário postgres e crie o banco:

    sudo -i -u postgres
    createdb smart_route
    exit

4. Criar usuário e senha (opcional):

    sudo -u postgres psql -c "CREATE USER seu_usuario WITH PASSWORD 'sua_senha';"
    sudo -u postgres psql -c "ALTER ROLE seu_usuario CREATEDB;"

5. Criar o banco com o usuário criado (alternativa):

    createdb -U seu_usuario -W smart_route

6. Verificar conexão:

    psql -U seu_usuario -d smart_route -h localhost

## Arquivos de configuração úteis

- pg_hba.conf: controla autenticação; por padrão está em /etc/postgresql/<versão>/main/ (Debian/Ubuntu) ou /var/lib/pgsql/data (CentOS). Ajuste método (md5/peer/trust) conforme necessidade.
- postgresql.conf: ajuste listen_addresses para permitir conexões remotas (ex.: listen_addresses = '*').

Após alterações, reinicie o serviço: sudo systemctl restart postgresql

## Restaurar dump ou popular banco

- Restaurar arquivo .sql: psql -U seu_usuario -d smart_route -f dump.sql
- Restaurar arquivo .dump criado por pg_dump (formato custom): pg_restore -U seu_usuario -d smart_route dump.dump

## String de conexão (exemplo)

POSTGRES_URL=postgresql://seu_usuario:sua_senha@localhost:5432/smart_route

## Permissões e segurança

- Evite usar o usuário postgres nas aplicações em produção; crie um usuário com permissões mínimas.
- Mantenha backups regulares (pg_dump, pg_basebackup) e restrinja acesso a arquivos de configuração.

## Comandos úteis rápidos

- Listar bancos: sudo -u postgres psql -c "\l"
- Listar usuários: sudo -u postgres psql -c "\du"
- Conectar ao psql: sudo -u postgres psql

---