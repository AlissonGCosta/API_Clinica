ALTER TABLE consultas
    ADD COLUMN data_hora VARCHAR(150),
    ADD COLUMN data_criacao DATE,
    ADD COLUMN data_atualizacao DATE,
    ADD COLUMN consulta_status VARCHAR(30),
    ADD COLUMN motivo_cancelamento VARCHAR(255);

ALTER TABLE consultas
    ADD CONSTRAINT ck_consultas_status
    CHECK ( consulta_status IN ('AGENDADA', 'PAGO', 'VENCIDO'));