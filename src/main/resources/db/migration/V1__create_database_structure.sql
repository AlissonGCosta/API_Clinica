CREATE TABLE  pacientes(
    id CHAR(36) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    email VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    data_criacao DATE NOT NULL ,
    data_atualizacao DATE NOT NULL,
    senha VARCHAR(255) NOT NULL,

    CONSTRAINT pk_paciente_entity PRIMARY KEY (id),
    CONSTRAINT uk_paciente_cpf UNIQUE (cpf),
    CONSTRAINT uk_paciente_email UNIQUE (email),
    CONSTRAINT ck_paciente_entity_estado CHECK ( estado IN ('ATIVO', 'INATIVO'))

);

CREATE TABLE  medicos (
    id CHAR (36) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    crm VARCHAR(30) NOT NULL,
    estado VARCHAR(20)NOT NULL,
    senha VARCHAR(255)NOT NULL,
    data_criacao DATE NOT NULL,
    data_atualizacao DATE NOT NULL,
    especialidade_id CHAR(36),

    CONSTRAINT  pk_medicos PRIMARY KEY (id),
    CONSTRAINT uk_medico_crm UNIQUE (crm),
    CONSTRAINT ck_medicos_estado CHECK (estado IN ('ATIVO', 'INATIVO')),
    CONSTRAINT fk_medicos_especialidade
                      FOREIGN KEY (especialidade_id) REFERENCES especialidade(id)

);

CREATE TABLE consultas(
    id CHAR(36) NOT NULL,
    medico_id CHAR(36) NOT NULL,
    paciente_id CHAR(36) NOT NULL,

    CONSTRAINT  pk_consultas PRIMARY KEY (id),
    CONSTRAINT fk_consultas_medico
                      FOREIGN KEY  (medico_id) REFERENCES medicos(id),
    CONSTRAINT fk_consultas_paciente
                      FOREIGN KEY (paciente_id) REFERENCES  pacientes(id)
);

CREATE TABLE especialidade(
    id CHAR(36) NOT NULL,
    nome VARCHAR(150) NOT NULL,

    CONSTRAINT pk_especialidade PRIMARY KEY (id),
    CONSTRAINT uk_especialidade_nome UNIQUE (nome)
);

CREATE TABLE prontuario (
    id CHAR(36) NOT NULL,
    diagnostico VARCHAR(500) NOT NULL,
    prescricao VARCHAR(500) NOT NULL,
    observacao TEXT,
    datacriacao DATE NOT NULL,
    consulta_id CHAR(36) NOT NULL,

    CONSTRAINT pk_prontuario PRIMARY KEY (id),
    CONSTRAINT  uk_prontuario_consulta UNIQUE  (consulta_id),
    CONSTRAINT fk_prontuario_consulta
                        FOREIGN KEY (consulta_id) references consultas(id)

);

CREATE TABLE pagamento (

    id CHAR(36) NOT NULL,

)




