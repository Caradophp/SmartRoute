CREATE TABLE IF NOT EXISTS emergencias (
    id UUID NOT NULL,
    latitude DECIMAL(10, 2) NULL,
    longitude DECIMAL(10, 2) NULL,
    id_app_user UUID NOT NULL,
    CONSTRAINT pk_emergencia PRIMARY KEY (id),
    CONSTRAINT fk_emengencia_app_user FOREIGN KEY (id_app_user) REFERENCES app_user(id)
);