# Gestão Vagas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Static Badge](https://img.shields.io/badge/MIT-maker?style=for-the-badge&label=License&labelColor=%23303030&color=%23808080)

**Gestão Vagas** é uma aplicação web desenvolvida em **Java** com **Spring Boot**, voltada para o gerenciamento de vagas de emprego. É uma API simples que permite o cadastro de empresas, candidatos e vagas, utilizando um banco de dados **PostgreSQL** para persistência e autenticação JWT para segurança.

---

## Estrutura do Projeto

A organização do código segue uma arquitetura modular:

```
src/
├── main/
│   ├── java/
│   │   └── com.matheus.gestao_vagas/
│   │       ├── modules/
│   │       │   ├── candidate/       # Recursos relacionados a candidatos
│   │       │   ├── company/         # Recursos relacionados a empresas
│   │       │   └── jobs/            # Recursos relacionados a vagas
│   │       ├── providers/           # Provedores de serviços (JWT)
│   │       ├── security/            # Configuração de segurança
│   │       └── exceptions/          # Tratamento de exceções
│   └── resources/
│       └── application.properties   # Configurações da aplicação
└── test/
    └── java/                       # Testes unitários e de integração
```

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.5**
- **Spring Security**
- **PostgreSQL**
- **JWT (Java JWT)**
- **Lombok**
- **Maven**
- **Docker** (para o banco de dados)

---

## Configuração do Ambiente

### Pré-requisitos

- Java 17+
- Maven
- Docker (para banco de dados)

### Configuração do Banco de Dados

1. O projeto já conta com um arquivo `.env` na raiz do projeto:

    ```env
    PC_PORT=5432
    POSTGRES_PORT=5432
    POSTGRES_USER=math_user
    POSTGRES_PASSWORD=math_password
    POSTGRES_DB=gestao_vagas
    POSTGRES_URL=jdbc:postgresql://localhost:5432/gestao_vagas

    JWT_SECRET=GESTAO_VAGAS_@2358
    JWT_SECRET_CANDIDATE=GESTAO_VAGAS_CANDIDATE_@2358
    ```

2. Suba o container com Docker Compose:

    ```bash
    docker-compose up -d
    ```

---

## Execução da Aplicação

Com o banco de dados em execução, inicie a aplicação:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

---

## Endpoints da API

A API é dividida em módulos de candidatos, empresas e vagas:

### Candidatos

| Método | Rota                | Descrição                                 | Acesso         |
|--------|---------------------|-------------------------------------------|----------------|
| POST   | `/candidate/`       | Cadastra um novo candidato                | Público        |
| POST   | `/candidate/auth`   | Autentica um candidato                    | Público        |
| GET    | `/candidate/`       | Obtém perfil do candidato autenticado     | Autenticado    |

### Empresas

| Método | Rota                | Descrição                                 | Acesso         |
|--------|---------------------|-------------------------------------------|----------------|
| POST   | `/company/`         | Cadastra uma nova empresa                 | Público        |
| POST   | `/company/auth`     | Autentica uma empresa                     | Público        |
| POST   | `/company/job/`     | Cria uma nova vaga                        | Autenticado    |

### Exemplos de Requisição

#### Cadastrar Candidato (POST `/candidate/`)

```json
{
  "name": "Nome do Candidato",
  "username": "username_candidato",
  "email": "candidato@exemplo.com",
  "password": "senha_segura_123",
  "description": "Descrição profissional"
}
```

#### Autenticar Candidato (POST `/candidate/auth`)

```json
{
  "username": "username_candidato",
  "password": "senha_segura_123"
}
```

#### Cadastrar Empresa (POST `/company/`)

```json
{
  "name": "Nome da Empresa",
  "username": "username_empresa",
  "email": "empresa@exemplo.com",
  "password": "senha_segura_123",
  "description": "Descrição da empresa",
  "website": "https://empresa.com.br"
}
```

#### Criar Vaga (POST `/company/job/`) - Requer autenticação de empresa

```json
{
  "description": "Descrição da vaga",
  "benefits": "Benefícios oferecidos",
  "level": "Pleno"
}
```

---

## Segurança

A aplicação utiliza JWT (JSON Web Token) para autenticação:

- Tokens de acesso são gerados na autenticação bem-sucedida
- Rotas protegidas exigem o token no cabeçalho `Authorization: Bearer {token}`
- Dois provedores JWT separados: um para candidatos e outro para empresas

## Tratamento de Exceções

A aplicação conta com um manipulador global que padroniza respostas de erro:

| Código HTTP | Situação                             |
|-------------|-------------------------------------|
| 400         | Erros de validação                  |
| 401         | Erro de autenticação                |
| 403         | Acesso proibido                     |
| 404         | Recurso não encontrado              |
| 409         | Conflito (ex: usuário já existente) |
