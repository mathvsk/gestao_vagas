# Gestor de Vagas - Backend

## Descrição
Este é o repositório do projeto de backend para um Gestor de Vagas desenvolvido em 
Java com Spring Boot.

## Funcionalidades
-  Cadastro de empresas
-  Cadastro de vagas
-  Cadastro de candidatos
-  Autenticação de usuários/empresas

## Tecnologias
- Java 17
- Spring Boot 3.2.0
- PostgreSQL com docker

## Endpoints da API
- POST /candidate/: Cria um candidato.
- POST /company/: Cria uma empresa.
- POST /company/job/: Cria uma nova vaga.
- POST /company/auth: Recupera o token da empresa.
- POST /candidate/auth: Recupera o token do candidato.
- GET /candidate/: Recupera as informações do candidato.