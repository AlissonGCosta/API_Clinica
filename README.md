# Atividade Prática - API de Clínica (Spring Boot + MySQL)

## Visão geral
Desenvolver uma API REST para gestão de consultas médicas, com foco em regras de negócio entre múltiplas entidades e aplicação dos 3 pilares do Spring: IoC, DI e AOP.

## Objetivo
Construir uma solução com fluxo completo de agendamento, atendimento, faturamento e bloqueios por inadimplência, garantindo consistência transacional e modelagem relacional no MySQL.

---

## Tecnologias obrigatórias
- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring AOP
- MySQL
- Flyway (ou Liquibase)
- Maven

---

## Escopo funcional

### Entidades
- `Paciente`
- `Medico`
- `Especialidade`
- `Consulta`
- `Prontuario`
- `Pagamento`

### Relacionamentos esperados
- Um `Medico` pertence a uma `Especialidade`.
- Um `Paciente` possui várias `Consulta`.
- Um `Medico` possui várias `Consulta`.
- Cada `Consulta` pode gerar 1 `Prontuario`.
- Cada `Consulta` concluída gera 1 `Pagamento`.

---

## Regras de negócio (obrigatórias)

1. O paciente agenda consulta para um médico em data/hora específica.
2. O médico não pode ter duas consultas no mesmo horário.
3. O paciente não pode ter duas consultas no mesmo horário.
4. Consulta só pode ser concluída se status for `AGENDADA`.
5. Ao concluir consulta, deve ser criado automaticamente:
   - um `Prontuario`;
   - um `Pagamento` com status inicial `PENDENTE`.
6. Paciente com pagamento vencido e não pago (`PENDENTE` + `dataVencimento < hoje`) não pode agendar nova consulta.
7. Cancelamento com menos de 24h da consulta gera taxa de cancelamento:
   - criar um `Pagamento` adicional (tipo `TAXA_CANCELAMENTO`).
8. Consulta cancelada ou concluída não pode voltar para `AGENDADA`.
9. Apenas `ADMIN` pode excluir médico ou paciente.
10. Não permitir conclusão de consulta já cancelada/concluída.
11. Não permitir agendamento de consulta em data/hora no passado.
12. Não permitir agendamento para paciente ou médico inativo (`ativo = false`).
13. Ao cancelar consulta com pagamento `PAGO` do tipo `CONSULTA`, registrar crédito para reaproveitamento em próxima consulta (sem estorno automático).
14. Ao pagar um `Pagamento`, registrar obrigatoriamente `dataPagamento` e impedir pagamento duplicado.
15. Pagamentos vencidos (`status = PENDENTE` e `dataVencimento < hoje`) devem ser atualizados para `VENCIDO` antes da validação de novo agendamento.
16. Consultas de um mesmo médico devem respeitar intervalo mínimo de 30 minutos entre horários.

---

## Estados e enums sugeridos

### `ConsultaStatus`
- `AGENDADA`
- `CANCELADA`
- `CONCLUIDA`

### `PagamentoStatus`
- `PENDENTE`
- `PAGO`
- `VENCIDO` (opcional, pode ser derivado por data)

### `PagamentoTipo`
- `CONSULTA`
- `TAXA_CANCELAMENTO`

---

## Endpoints mínimos da API

### Pacientes
- `POST /pacientes`
- `GET /pacientes`
- `GET /pacientes/{id}`
- `PUT /pacientes/{id}`
- `DELETE /pacientes/{id}` (somente `ADMIN`)
- `GET /pacientes/{id}/pendencias`

### Médicos
- `POST /medicos`
- `GET /medicos`
- `GET /medicos/{id}`
- `PUT /medicos/{id}`
- `DELETE /medicos/{id}` (somente `ADMIN`)
- `GET /medicos/{id}/agenda?data=yyyy-MM-dd`

### Especialidades
- `POST /especialidades`
- `GET /especialidades`

### Consultas
- `POST /consultas/agendar`
- `PUT /consultas/{id}/cancelar`
- `PUT /consultas/{id}/concluir`
- `GET /consultas/{id}`
- `GET /consultas?status=AGENDADA&medicoId=1&pacienteId=2`

### Pagamentos
- `GET /pagamentos/{id}`
- `GET /pagamentos?pacienteId=1&status=PENDENTE`
- `PUT /pagamentos/{id}/pagar`

---

## Aplicação dos 3 pilares do Spring

### 1) Inversão de Controle (IoC)
Use estereótipos para delegar criação/gerenciamento de objetos ao container Spring:
- `@RestController`
- `@Service`
- `@Repository`
- `@Component`

### 2) Injeção de Dependência (DI)
Injete dependências por construtor em todas as camadas:
- Controllers dependem de Services.
- Services dependem de Repositories e componentes auxiliares.

### 3) AOP (Aspect-Oriented Programming)
Criar um `@Aspect` para:
- Log de operações críticas (`agendar`, `cancelar`, `concluir`, `pagar`).
- Tempo de execução dos métodos de serviço.
- (Opcional) Auditoria de usuário e timestamp da ação.

---

## Modelo de banco (resumo)

### Tabelas essenciais
- `paciente(id, nome, cpf, email, ativo, created_at, updated_at)`
- `especialidade(id, nome)`
- `medico(id, nome, crm, especialidade_id, ativo, created_at, updated_at)`
- `consulta(id, paciente_id, medico_id, data_hora, status, motivo_cancelamento, created_at, updated_at)`
- `prontuario(id, consulta_id, diagnostico, prescricao, observacoes, created_at)`
- `pagamento(id, consulta_id, paciente_id, tipo, valor, status, data_vencimento, data_pagamento, created_at)`

### Constraints importantes
- `UNIQUE (medico_id, data_hora)` em `consulta`.
- `UNIQUE (paciente_id, data_hora)` em `consulta`.
- `UNIQUE (consulta_id)` em `prontuario` (1:1).
- `NOT NULL` nos campos críticos.
- Índices para consultas por `status`, `data_hora` e `paciente_id`.

---

## Estrutura sugerida de pacotes

```text
com.seuprojeto.clinica
  ├─ config
  ├─ controller
  ├─ dto
  │   ├─ request
  │   └─ response
  ├─ entity
  ├─ enums
  ├─ exception
  ├─ repository
  ├─ service
  ├─ aspect
  └─ mapper
```

---

## Critérios de avaliação

1. Regras de negócio implementadas corretamente.
2. Uso explícito e correto de IoC, DI e AOP.
3. API consistente (status HTTP, validação e mensagens de erro).
4. Persistência relacional com integridade no MySQL.
5. Organização em camadas (controller/service/repository/dto).
6. Tratamento global de exceções com `@ControllerAdvice`.
7. Testes mínimos:
   - unitários de service (regras de negócio);
   - integração de endpoints críticos.

---

## Entregáveis esperados

1. Código fonte da API.
2. Scripts de migração (`V1__...sql`, `V2__...sql`, etc.).
3. Coleção de testes de endpoint (Postman/Insomnia ou testes automatizados).
4. README com instruções de execução local.

---

## Diferenciais (opcional)
- Segurança com Spring Security + JWT.
- Paginação e filtros avançados.
- Auditoria automática (`createdBy`, `updatedBy`).
- Docker Compose com MySQL + app.
