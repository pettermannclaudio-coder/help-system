# Help System

Sistema interno de perguntas e respostas desenvolvido em Java.

## Objetivo

Permitir que colaboradores criem solicitações, respondam dúvidas e acompanhem o status das solicitações.

---

## Tecnologias

- Java
- JDBC
- MySQL
- Java Swing
- Git
- GitHub

## Banco de dados

O MySQL é a opção principal quando `DB_URL`, `DB_USER` e `DB_PASSWORD`
estão preenchidos no arquivo `.env`.

Se alguma dessas variáveis estiver ausente ou vazia, o projeto usa SQLite
automaticamente, sem exigir instalação de um servidor:

```text
jdbc:sqlite:database/helpdesk.db
```

O arquivo e as tabelas são criados automaticamente ao iniciar a aplicação.

Copie `.env.example` para `.env` e configure o MySQL:

```text
DB_TYPE=MYSQL
DB_URL=jdbc:mysql://localhost:3306/helpdesk
DB_USER=root
DB_PASSWORD=sua_senha
```

No Windows, execute o Maven Wrapper com `mvnw.cmd`. No macOS ou Linux,
execute com `./mvnw`.

---

## Funcionalidades

- Cadastro de usuários
- Login
- Cadastro de departamentos
- Criar solicitações
- Responder solicitações
- Marcar como resolvida
- Listagem de solicitações

---

## Organização

src/
database/
docs/
test/

---

## Integrantes

- Claudio Ciasca Junior (Scrum Master)
- Nome Integrante 2
- Nome Integrante 3
