# ToolsChallenge - API de Pagamentos

![Badge - Java](https://img.shields.io/badge/Java-21-blue.svg?logo=openjdk&style=for-the-badge)
![Badge - Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-success.svg?logo=spring&style=for-the-badge)
![Badge - Swagger](https://img.shields.io/badge/Swagger-UI-green.svg?logo=swagger&style=for-the-badge)
![Badge - Testes](https://img.shields.io/badge/Testes-JUnit_5-green.svg?logo=junit5&style=for-the-badge)

Solução em Java/Spring Boot para o Desafio [C] da Tools, implementando uma API REST para transações de pagamento, consulta e estorno.

---
## 🚀 Sobre o Projeto (PT-BR)

O objetivo deste projeto foi implementar uma API de Pagamentos para um banco, seguindo os conceitos REST e focando na qualidade da entrega. A aplicação permite realizar pagamentos, consultá-los (individualmente ou todos) e realizar estornos de transações previamente autorizadas.

### ✨ Funcionalidades

* **Pagamento:** Recebe uma transação, valida se o `ID` é único e, se autorizado, salva no banco.
* **Consulta:** Permite a busca de uma transação específica por `ID` ou a listagem de tpdas as transações.
* **Estorno:** Permite o cancelamento de uma transação `AUTORIZADO`, mudando o seu status para `CANCELADO`.
* **Validação de Negócio:**
    * Não permite pagamentos com `ID` duplicados (retorna `HTTP 409 Conflict`).
    * Não permite estornos em transações `NEGADO` ou já `CANCELADO` (retorna `HTTP 422 Unprocessable Entity`).
    * Retorna `HTTP 404 Not Found` para consultas ou estornos de `ID` inexistentes.

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.5.7**
    * **Spring Web:** Para a criação dos endpoints REST.
    * **Spring Data JPA:** Para a persistência de dados.
    * **Spring Boot Validation:** Para a validação declarativa dos requests.
* **H2 Database:** Banco de dados em memória para facilitar a execução e os testes do projeto.
* **SpringDoc (Swagger):** Para documentação de API interativa e automática.
* **JUnit 5 e Mockito:** Para a cobertura de testes unitários da camada de serviço.
* **Lombok:** Para reduzir o boilerplate em Entidades e Injeção de Dependências.
* **Maven:** Para gerenciamento de dependências.

## 🏁 Como Executar

### Pré-requisitos

* Java (JDK) 21+
* Apache Maven 3.8+

### Passos

1. **Clone o repositório:**
    ```bash
    git clone [https://github.com/SuzanStockey/ToolsChallenge.git](https://github.com/SuzanStockey/ToolsChallenge.git)
    ```

2. **Navegue até o diretório:**
    ```bash
   cd ToolsChallenge
   ```

3. **Execute o projeto com Maven:**
   ```bash
   mvn spring-boot:run
   ```

A API estará disponível em <http://localhost:8080>.

## 🧪 Como Rodar os Testes

O projeto possui uma suíte de testes unitários para a camada de serviço, garantindo que as regras de negócio funcionam como esperado.

Para executar os testes:
```bash
mvn clean test
```

## 📄 Documentação da API (Swagger)

A API está 100% documentada usando o padrão OpenAPI (SpringDoc), o que permite a visualização e interação com todos os endpoints em tempo real.

Após executar a aplicação, a documentação interativa (Swagger UI) estará disponível em:

➡️ <http://localhost:8080/swagger-ui.html>

O JSON da especificação OpenAPI está disponível em:

➡️ <http://localhost:8080/v3/api-docs>

## 📋 Endpoints da API

A URL base para a API é <http://localhost:8080/api/pagamentos>.

| Verbo  | Endpoint        | Descrição                          |
|:-------|:----------------|:-----------------------------------|
| `POST` | `/`             | Realiza um novo pagamento.         |
| `GET`  | `/{id}`         | Consulta uma transação por ID.     |
| `GET`  | `/`             | Lista todas as transações.         |
| `POST` | `/{id}/estorno` | Realiza o estorno de um pagamento. |

<details>
<summary><b>Exemplo: Corpo da Requisição (POST <code>/</code>)</b></summary>

```json
{
    "transacao": {
        "cartao": "4444123412341234",
        "id": "100023568900001",
        "descricao": {
            "valor": "500.50",
            "datahora": "01/05/2021 18:30:00",
            "estabelecimento": "PetShop Mundo Cão"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": "1"
        }
    }
}
```
</details>

<details>

<summary><b>Exemplo: Resposta de Sucesso (POST <code>/</code>, GET <code>/{id}</code>)</b></summary>

```json
{
    "transacao": {
        "cartao": "4444123412341234",
        "id": "100023568900001",
        "descricao": {
            "valor": "500.50",
            "dataHora": "01/05/2021 18:30:00",
            "estabelecimento": "PetShop Mundo Cão",
            "nsu": "1234567890",
            "codigoAutorizacao": "147258369",
            "status": "AUTORIZADO"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": "1"
        }
    }
}
```
</details>

<details>
<summary><b>Exemplo: Resposta de Estorno (POST <code>/{id}/estorno</code>)</b></summary>

```json
{
    "transacao": {
        "cartao": "4444123412341234",
        "id": "100023568900001",
        "descricao": {
            "valor": "500.50",
            "dataHora": "01/05/2021 18:30:00",
            "estabelecimento": "PetShop Mundo Cão",
            "nsu": "1234567890",
            "codigoAutorizacao": "147258369",
            "status": "CANCELADO"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": "1"
        }
    }
}
```
</details>

<details>
<summary><b>Exemplo: Resposta de Consulta (GET <code>/</code>)</b></summary>

```json
[
  {
    "transacao": {
      "cartao": "4444123412341234",
      "id": "100023568900001",
      "descricao": {
        "valor": "500.50",
        "dataHora": "01/05/2021 18:30:00",
        "estabelecimento": "PetShop Mundo Cão",
        "nsu": "1234567890",
        "codigoAutorizacao": "147258369",
        "status": "AUTORIZADO"
      },
      "formaPagamento": {
        "tipo": "AVISTA",
        "parcelas": "1"
      }
    }
  },
  {
    "transacao": {
      "cartao": "5555876587658765",
      "id": "100023568900002",
      "descricao": {
        "valor": "250.00",
        "dataHora": "02/05/2021 10:15:00",
        "estabelecimento": "Padaria Pão Bom",
        "nsu": "9876543210",
        "codigoAutorizacao": "741852963",
        "status": "CANCELADO"
      },
      "formaPagamento": {
        "tipo": "PARCELADO LOJA",
        "parcelas": "2"
      }
    }
  }
]
```
</details>

## 🧠 Decisões de Design

* **Arquitetura em Camadas:** O projeto segue uma clara separação (Controller, Service, Repository) para manter a organização e facilitar os testes.

* **Java Records para DTOs:** Em vez de classes tradicionais, foram usados `Record`s (Java 16+) para os objetos de transferência de dados (`PagamentoRequest`, `PagamentoResponse`, etc.). Isso garante imutabilidade e reduz drasticamente o boilerplate, seguindo as práticas de "Clean Code".

* **Entidade `@Embedded`:** As classes `Descricao` e `FormaPagamento` foram modeladas como `@Embeddable` dentro da entidade `Transacao`. Isso mantém o código Java organizado (refletindo o JSON) e o banco de dados performático (uma única tabela).

* **Injeção via Construtor:** A Injeção de Dependência é feita via Construtor (com `@RequiredArgsConstructor` do Lombok) em vez de `@Autowired` em campos. Isso torna as dependências explícitas, `final`, e facilita testes unitários puros.

* **Tratamento de Exceções Global:** Um `@ControllerAdvice` centraliza o tratamento de exceções, convertendo exceções de negócio (ex: `TransacaoNaoEncontradaException`) em respostas HTTP semânticas (404, 409, 422), mantendo os Controllers limpos.

* **Banco em Memória (H2):** O uso do H2 permite que o avaliador clone o repositório e execute o projeto imediatamente, sem qualquer configuração de banco de dados.

* **Virtual Threads (Project Loom):** A aplicação está configurada com `spring.threads.virtual.enabled=true` (feature do Java 21+), otimizando-a para alta concorrência em operações de I/O (como chamadas ao banco) ao criar uma Thread Virtual leve para cada requisição.

---
---

## 🚀 About The Project (EN)

This is the solution for the Tools Java Challenge [C], implementing a REST API for payment, query, and reversal transactions.

### ✨ Features

* **Payment:** Receives a transaction, validates if the ID is unique, and saves it upon authorization.
* **Query:** Allows fetching a specific transaction by `ID` or listing all transactions.
* **Reversal (Estorno):** Allows an `AUTORIZADO`(authorized) transaction to be canceled, changing its status to `CANCELADO` (canceled).
* **Business Logic Validation:**
    * Prevents payments with duplicate IDs (returns `HTTP 409 Conflict`).
    * Prevents reversals on `NEGADO`(denied) or already `CANCELADO`(canceled) transactions (returns `HTTP 422 Unprocessable Entity`).
    * Returns `HTTP 404 Not Found` when querying or reversing non-existent IDs.

## 🛠️ Tech Stack

* **Java 21**
* **Spring Boot 3.5.7**
    * **Spring Web:** For creating REST endpoints.
    * **Spring Data JPA:** For data persistence.
    * **Spring Boot Validation:** For declarative request validation.
* **H2 Database:** In-memory database for ease of execution and testing.
* **SpringDoc (Swagger):** For automatic and interactive API documentation.
* **JUnit 5 & Mockito:** For service-layer unit testing.
* **Lombok:** To reduce boilerplate in Entities and for Dependency Injection.
* **Maven:** For dependency management.

## 🏁 How to Run

### Prerequisites

* Java (JDK) 21+
* Apache Maven 3.8+

### Steps

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/SuzanStockey/ToolsChallenge.git](https://github.com/SuzanStockey/ToolsChallenge.git)
    ```

2.  **Navigate to the directory:**
    ```bash
    cd ToolsChallenge
    ```

3.  **Run the project with Maven:**
    ```bash
    mvn spring-boot:run
    ```

The API will be available at <http://localhost:8080>.

## 🧪 How to Run Tests

The project includes a suite of unit tests for the service layer, ensuring all business rules function as expected.

To run the tests:
```bash
mvn clean test
```

## 📄 API Documentation (Swagger)

The API is fully documented using the OpenAPI standard (SpringDoc), which allows for real-time visualization and interaction with all endpoints.

After running the application, the interactive documentation (Swagger UI) will be available at:

➡️ <http://localhost:8080/swagger-ui.html>

The OpenAPI specification JSON is available at:

➡️ <http://localhost:8080/v3/api-docs>

## 📋 API Endpoints

The base URL for the API is <http://localhost:8080/api/pagamentos>.

| Verb   | Endpoint        | Description                  |
|:-------|:----------------|:-----------------------------|
| `POST` | `/`             | Performs a new payment.      |
| `GET`  | `/{id}`         | Queries a transaction by ID. |
| `GET`  | `/`             | Lists all transactions.      |
| `POST` | `/{id}/estorno` | Performs a payment reversal. |

_(See JSON examples in the Portuguese section above)_

## 🧠 Design Decisions

* **Layered Architecture:** The project follows a clear Controller-Service-Repository separation for organization and testability.

* **Java Records for DTOs:** Instead of traditional classes, `Records` (Java 16+) are used for Data Transfer Objects (`PagamentoRequest`, `PagamentoResponse`, etc.). This ensures immutability and drastically reduces boilerplate, adhering to "Clean Code" practices.

* **`@Embedded` Entities:** `Descricao` and `FormaPagamento` were modeled as `@Embeddable` within the `Transacao` entity. This keeps the Java code organized (mirroring the JSON) while maintaining a performant, single-table database design.

* **Constructor Injection:** Dependency Injection is done via Constructor (with Lombok's `@RequiredArgsConstructor`) instead of Field Injection. This makes dependencies explicit, `final`, and simplifies pure unit testing.

* **Global Exception Handling:** A `@ControllerAdvice` centralizes exception handling, translating business exceptions (e.g., `TransacaoNaoEncontradaException`) into semantic HTTP responses (404, 409, 422), keeping Controllers clean.

* **In-Memory DB (H2):** Using H2 allows the evaluator to clone and run the project immediately with zero database setup.

* **Virtual Threads (Project Loom):** The application is configured with `spring.threads.virtual.enabled=true` (a Java 21+ feature), optimizing it for high concurrency in I/O operations (like database calls) by creating a lightweight Virtual Thread for each request.
