#!/bin/bash

set -e
set -u

echo "Creating database 'aziz'"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE aziz;
    GRANT ALL PRIVILEGES ON DATABASE aziz TO $POSTGRES_USER;
EOSQL
echo "Database 'aziz' created"