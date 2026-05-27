ALTER TABLE consultas
        DROP COLUMN data_hora,
        ADD COLUMN data_consulta VARCHAR(50) NOT NULL,
        ADD COLUMN hora_consulta VARCHAR(50) NOT NULL
