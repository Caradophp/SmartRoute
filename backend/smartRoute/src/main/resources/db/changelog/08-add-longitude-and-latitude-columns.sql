ALTER TABLE IF EXISTS health_unit
ADD COLUMN IF NOT EXISTS longitude DECIMAL(10, 2) NOT NULL,
ADD COLUMN IF NOT EXISTS latitude DECIMAL(10, 2) NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM
        pg_constraint
        WHERE conname = 'unique_position'
    ) THEN
        ALTER TABLE IF EXISTS health_unit
        ADD CONSTRAINT unique_position UNIQUE(longitude, latitude);
    END IF;
END $$;