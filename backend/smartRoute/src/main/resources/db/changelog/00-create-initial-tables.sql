CREATE table role(
    role_id UUID primary key,
    nome_perfil varchar(50) not null
);

CREATE table app_user(
    user_id UUID primary key,
    nome varchar(50) not null,
    email varchar(50) not null,
    telefone char(11) not null,
    status varchar(10) not null
);

ALTER TABLE app_user ADD role_id UUID;
ALTER Table app_user ADD CONSTRAINT fk_app_user FOREIGN KEY (role_id) REFERENCES role(role_id);
