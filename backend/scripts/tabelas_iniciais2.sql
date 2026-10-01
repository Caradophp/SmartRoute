CREATE TABLE teritorio(
   id_teritorio UUID  primary kay,
   nome_teritorio varchar(60) not null,
   tipo_teritorio varchar(50)not null;

)
CREATE TABLE address(
    id_address UUID primary kay,
    teritorio_id UUID not null,7
    rua varchar(50) not null,
    numero int not null,
    bairo varchar(50) not null,
    cidade varchar(50) not null;
)
CREATE TABLE assisted_person(
    id_aPerson UUID primary kay,
    nome varchar(50) not null,
    data_nascimento date not null,
    telefone char(11) not null;
)
CREATE TABLE vulnerability_type(
    id_vulnerability UUID primary kay,
    nome varchar(50) not null,
    descricao varchar(50) not null;

)
CREATE TABLE health_unit(
    id_unit UUID primary kay,
    nome varchar(50) not null,
    tipo varchar(50) not null;
)
CREATE TABLE health_service(
    id_service UUID primary kay,
    nome varchar(50) not null,
    descricao varchar(50) not null;

)
CREATE TABLE ambulance(
    id_ambulance UUID primary kay,
    placa varchar(10) not null,
    status varchar(20) not null;

)
CREATE TABLE health_agent(
    id_agent UUID primary kay,
    nome varchar(50) not null,
    telefone char(11) not null;

)
CREATE TABLE route_plan(
    id_route UUID primary kay,
    data date not null,
    status varchar(50);
)
CREATE TABLE field_visit(
    id_field UUID primary kay,
    data_visita date not null,
    obeservacao varchar(50) not null;
)
ALTER TABLE ambulance
ADD COLUMN agent_id UUID;

ALTER TABLE ambulance
ADD CONSTRAINT fk_ambulance_agent
FOREIGN KEY(agent_id)
REFERENCES health_agent(id_agent);
ALTER TABLE health_agent
ADD COLUMN user_id UUID UNIQUE;

ALTER TABLE health_agent
ADD CONSTRAINT fk_agent_user
FOREIGN KEY(user_id)
REFERENCES app_user(user_id);
CREATE TABLE user_role(
    user_id UUID,
    role_id UUID,

    PRIMARY KEY(user_id, role_id),

    FOREIGN KEY(user_id)
        REFERENCES app_user(user_id),

    FOREIGN KEY(role_id)
        REFERENCES role(role_id)
);
ALTER TABLE field_visit
ADD COLUMN person_id UUID;

ALTER TABLE field_visit
ADD CONSTRAINT fk_visit_person
FOREIGN KEY(person_id)
REFERENCES assisted_person(id_aPerson);
ALTER TABLE field_visit
ADD COLUMN route_id UUID;

ALTER TABLE field_visit
ADD CONSTRAINT fk_visit_route
FOREIGN KEY(route_id)
REFERENCES route_plan(id_route);
ALTER TABLE route_plan
ADD COLUMN agent_id UUID;

ALTER TABLE route_plan
ADD CONSTRAINT fk_route_agent
FOREIGN KEY(agent_id)
REFERENCES health_agent(id_agent);
CREATE TABLE health_unit_service(
    unit_id UUID,
    service_id UUID,

    PRIMARY KEY(unit_id, service_id),

    FOREIGN KEY(unit_id)
        REFERENCES health_unit(id_unit),

    FOREIGN KEY(service_id)
        REFERENCES health_service(id_service)
);
CREATE TABLE person_vulnerability(
    person_id UUID,
    vulnerability_id UUID,

    PRIMARY KEY(person_id, vulnerability_id),

    FOREIGN KEY(person_id)
        REFERENCES assisted_person(id_aPerson),

    FOREIGN KEY(vulnerability_id)
        REFERENCES vulnerability_type(id_vulnerability)
);
ALTER TABLE assisted_person
ADD COLUMN address_id UUID;

ALTER TABLE assisted_person
ADD CONSTRAINT fk_person_address
FOREIGN KEY (address_id)
REFERENCES address(id_address);
ALTER TABLE address
ADD CONSTRAINT fk_address_territory
FOREIGN KEY (teritorio_id)
REFERENCES teritorio(id_teritorio);