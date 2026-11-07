CREATE TABLE IF NOT EXISTS drivers (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    driver_licence_level VARCHAR(2) NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id),
    CONSTRAINT fk_driver_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT check_driver_licence_level CHECK (driver_license_level IN ('A', 'B', 'C', 'AB', 'BC', 'ABC'))
);

ALTER TABLE IF EXISTS ambulance
ADD COLUMN IF NOT EXISTS driver_id UUID NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_placa' ) THEN
        ALTER TABLE IF EXISTS ambulance
        ADD CONSTRAINT fk_ambulance_driver FOREIGN KEY (driver_id) REFERENCES drivers (id);
    END IF;
END;
$$