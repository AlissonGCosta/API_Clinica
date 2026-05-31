ALTER TABLE consultas
DROP CHECK ck_consultas_status;

ALTER TABLE consultas
    ADD CONSTRAINT ck_consultas_status
        CHECK ( consulta_status IN ('AGENDADA', 'CONCLUIDA', 'VENCIDO', 'CANCELADA'));