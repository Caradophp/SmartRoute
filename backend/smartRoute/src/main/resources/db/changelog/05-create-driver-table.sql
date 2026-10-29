CREATE TABLE IF NOT EXISTS driver(
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    driver_licente_level VARCHAR(2) NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id),
    CONSTRAINT fk_driver_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT CHECK (driver_license_level IN ('A', 'B', 'C', 'AB', 'BC', 'ABC'))
);

ALTER TABLE IF EXISTS ambulence
ADD COLUMN IF NOT EXISTS driver_id,
ADD CONSTRAINT IF NOT EXISTS fk_ambulance_driver FOREIGN KEY (driver_id) REFERENCES drivers (id);