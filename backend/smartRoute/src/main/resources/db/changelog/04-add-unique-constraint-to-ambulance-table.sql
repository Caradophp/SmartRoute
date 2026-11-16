DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'unique_placa'
    ) THEN
        ALTER TABLE ambulance
        ADD CONSTRAINT unique_placa UNIQUE (placa);
    END IF;
END $$;