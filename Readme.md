# ToolsChallenge - API de Pagamentos

[Badge - Java](https://img.shields.io/badge/Java-21-blue.svg?logo=openjdk&style=for-the-badge)
[Badge - Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-success.svg?logo=spring&style=for-the-badge)
[Badge - Testes](https://img.shields.io/badge/Testes-JUnit_5-green.svg?logo=junit5&style=for-the-badge)

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

* **Java 25**
* **Spring Boot 3.5.7**
    * **Spring Web:** Para a criação dos endpoints REST.
    * **Spring Data JPA:** Para a persistência de dados.
    * **Spring Boot Validation:** Para a validação declarativa dos requests.
* **H2 Database:** Banco de dados em memória para facilitar a execução e os testes do projeto.
* **JUnit 5 e Mockito:** Para a cobertura de testes unitários da camada de serviço.
* **Lombok:** Para reduzir o boilerplate em Entidades e Injeção de Dependências.
* **Maven:** Para gerenciamento de dependências.

## 🏁 Como Executar

### Pré-requisitos

* Java (JDK) 17+ (recomendado 21)
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

A API estará disponível em `http://localhost:8080`.

## 🧪 Como Rodar os Testes

O projeto possui uma suíte de testes unitários para a camada de serviço, garantindo que as regras de negócio funcionam como esperado.

Para executar os testes:
```bash
mvn clean test
```

## Endpoints da API

A URL base para a API é `http://localhost:8080/api/pagamentos`.

| Verbo | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/` | Realiza um novo pagamento. |
| `GET` | `/{id}` | Consulta uma transação por ID. |
| `GET` | `/` | Lista todas as transações. |
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