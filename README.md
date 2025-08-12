# 📚 Projeto Spring Boot - Gerenciamento de Livros e Autores (JPA, Docker, Testes)

Este projeto é um **CRUD completo de livros e autores** utilizando **Spring Boot** e **PostgreSQL**, com foco em arquitetura em camadas, boas práticas, testes automatizados e infraestrutura dockerizada.

Embora o domínio seja simples, o projeto foi estruturado para refletir um ambiente real de desenvolvimento profissional, aplicando conceitos essenciais de backend moderno.

---

## 🚀 Tecnologias & Ferramentas Utilizadas

### 🖥️ Backend

- **Spring Boot** — Framework principal.
- **Spring Data JPA (Hibernate)** — ORM robusto para persistência relacional.
- **Spring Web (Spring MVC)** — Desenvolvimento de APIs RESTful.
- **ModelMapper** — Mapeamento de Entidades ↔ DTOs de forma desacoplada.
- **Maven** — Gerenciador de dependências e build.

### 🗄️ Banco de Dados

- **PostgreSQL** — Banco relacional, rodando em container Docker.

### 🐳 Infraestrutura

- **Docker** & **Docker Compose** — Containers para PostgreSQL e configuração do ambiente de desenvolvimento.
- **Volumes e Networks dockerizados** para persistência de dados e comunicação entre serviços.

### 🧪 Testes

- **JUnit 5** — Estrutura de testes.
- **Mockito** — Mocking de dependências em testes unitários.
- **MockMvc** — Testes de integração das APIs REST.
- **Testcontainers** (opcional para expandir) — Para isolar testes de integração reais com containers.

### 🧭 Documentação da API

- **springdoc-openapi** — Gera automaticamente a especificação OpenAPI e a UI Swagger (Swagger UI).→ Endpoints gerados: /v3/api-docs (JSON) e /swagger-ui/index.html (UI).

---

## 🏗️ Arquitetura do Projeto

O projeto foi estruturado em **camadas bem definidas**, seguindo boas práticas de **Clean Architecture** e **DDD simplificado**.

### 📁 Estrutura de Pastas:

```
src/main/java/com/joljolionn/postgresjpa/
 ├── controllers/
 │    ├── docs/        → Interfaces de documentação (Swagger) (ex: BookControllerDocs.java)
 │    └── impl/        → Implementações reais dos controllers (ex: BookControllerImpl.java)
 ├── domain/
 │    ├── dtos/         → DTOs (Data Transfer Objects)
 │    └── entities/     → JPA Entities
 ├── mappers/           → Interfaces e implementações de mapeamento DTO ↔ Entity
 ├── repositories/      → Interfaces JPA Repository
 ├── services/          → Regras de negócio (Services + Impl)
 └── config/            → Configurações globais (ex: ModelMapper)
```

### 🧪 Testes

```
src/test/java/com/joljolionn/postgresjpa/
 ├── controllers/        → Testes de integração com MockMvc (API REST)
 ├── repositories/       → Testes de integração com banco de dados
 ├── TestDataUtil.java   → Classe utilitária para mocks de dados
 └── PostgresjpaApplicationTests.java
```

---

## ✨ Funcionalidades Implementadas

- CRUD de Autores e Livros.
- Associação de livros a autores (Relacionamento ManyToOne).
- Paginação e ordenação com Spring Data Pageable.
- Querys personalizadas (buscas específicas no banco).
- Mapeamento DTO ↔ Entity com **ModelMapper**.
- Testes Unitários com **Mockito**.
- Testes de Integração com **MockMvc** (simulando requisições HTTP reais).
- Configuração de ambiente com **Docker Compose**.
- Utilização de Profiles (dev/test/prod) via `application.properties` e Docker.
- Documentação automática da API com **OpenAPI / Swagger** (springdoc-openapi).

---

## 🐳 Como Rodar o Projeto com Docker

### 1️⃣ Subir o banco de dados PostgreSQL:

```bash
docker-compose up -d
```

### 2️⃣ Configure o `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Rode a aplicação:

```bash
./mvnw spring-boot:run
```

#### A API estará acessível em: `http://localhost:8080`

#### Swagger UI (web): http://localhost:8080/swagger-ui/index.html  (também pode funcionar /swagger-ui.html dependendo da versão)

#### Especificação OpenAPI (JSON): http://localhost:8080/v3/api-docs

---

## 🧪 Como Rodar os Testes

Para executar todos os testes:

```bash
./mvnw test
```

---

## 📊 Endpoints Disponíveis 

| Método | Endpoint            | Descrição                      |
| ------ | ------------------- | ------------------------------ |
| GET    | `/authors`      | Listar autores (não paginado)  |
| GET    | `/authors/{id}` | Busca autor no banco           |
| POST   | `/authors     ` | Criar novo autor               |
| PUT    | `/authors/{id}` | Atualiza um autor por completo |
| PATCH  | `/authors/{id}` | Atualiza um autor parcialmente |
| DELETE | `/authors/{id}` | Deleta um autor                |
| GET    | `/books`        | Listar livros (paginado)       |
| GET    | `/books/{isbn}` | Busca livro no banco           |
| PUT    | `/books/{isbn}` | Cria ou atualiza um livro por completo |
| PATCH  | `/books/{isbn}` | Atualiza um livro parcialmente |
| DELETE | `/books/{isbn}` | Deleta um livro                |

---

## 🧠 O que foi aprofundado neste projeto?

- **Spring Data JPA (Hibernate)**: Entendimento profundo sobre ciclo de vida das entidades, Lazy/Eager Fetch, caching e custom queries.
- **Arquitetura RESTful** com boas práticas (Controllers finos, Services robustos).
- **Separação de responsabilidades** com DTOs, Mappers e Configurations.
- **Testes Automatizados Profissionais** (MockMvc, Mockito, Repositories).
- **Infraestrutura dockerizada**, com isolamento de ambiente próximo ao de produção.
- **Documentação padronizada de APIs** para melhor mantenabilidade e facilitar visualização

---

## 🗂️ Estrutura de Arquivos (Nível Alto)

```
📦 postgresjpa
 ┣ 📂 src/
 ┃ ┣ 📂 main/
 ┃ ┃ ┣ 📂 java/com/joljolionn/postgresjpa/
 ┃ ┃ ┃ ┣ 📂 controllers/{docs, impl}/
 ┃ ┃ ┃ ┣ 📂 domain/{dtos, entities}/
 ┃ ┃ ┃ ┣ 📂 mappers/{impl}/
 ┃ ┃ ┃ ┣ 📂 repositories/
 ┃ ┃ ┃ ┣ 📂 services/{impl}/
 ┃ ┃ ┃ ┗ 📂 config/
 ┃ ┃ ┗ 📂 resources/
 ┃ ┣ 📂 test/
 ┃ ┃ ┗ 📂 java/com/joljolionn/postgresjpa/
 ┃ ┃     ┣ 📂 controllers/
 ┃ ┃     ┣ 📂 repositories/
 ┃ ┃     ┗ TestDataUtil.java
 ┣ 📄 Dockerfile
 ┣ 📄 docker-compose.yml
 ┣ 📄 pom.xml
 ┗ 📄 README.md
```
