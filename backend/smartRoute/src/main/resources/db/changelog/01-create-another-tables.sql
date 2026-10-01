--liquibase formatted sql

--changeset app:001-create-territorio
CREATE TABLE territorio (
    id_territorio UUID PRIMARY KEY,
    nome_territorio VARCHAR(60) NOT NULL,
    tipo_territorio VARCHAR(50) NOT NULL
);

--changeset app:002-create-address
CREATE TABLE address (
    id_address UUID PRIMARY KEY,
    rua VARCHAR(50) NOT NULL,
    numero INT NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL
);

--changeset app:003-create-assisted-person
CREATE TABLE assisted_person (
    id_aperson UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone CHAR(11) NOT NULL
);

--changeset app:004-create-vulnerability-type
CREATE TABLE vulnerability_type (
    id_vulnerability UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(50) NOT NULL
);

--changeset app:005-create-health-unit
CREATE TABLE health_unit (
    id_unit UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL
);

--changeset app:006-create-health-service
CREATE TABLE health_service (
    id_service UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(50) NOT NULL
);

--changeset app:007-create-ambulance
CREATE TABLE ambulance (
    id_ambulance UUID PRIMARY KEY,
    placa VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL
);

--changeset app:008-create-health-agent
CREATE TABLE health_agent (
    id_agent UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    telefone CHAR(11) NOT NULL
);

--changeset app:009-create-route-plan
CREATE TABLE route_plan (
    id_route UUID PRIMARY KEY,
    data DATE NOT NULL,
    status VARCHAR(50)
);

--changeset app:010-create-field-visit
CREATE TABLE field_visit (
    id_field UUID PRIMARY KEY,
    data_visita DATE NOT NULL,
    observacao VARCHAR(50) NOT NULL
);
