# Baozi Store – API REST

API REST simples para uma loja de pãozinho chinês (Baozi Store), feita em **Java 17**, **Spring Boot 3**, **Spring Data JPA** e banco relacional **H2** (MySQL opcional). Segue o padrão MVC do Spring (`model`, `repository`, `controller`).

Disciplina: Desenvolvimento Web Back-End – UNINTER
Aluno: Vinicius Marteleto – RU 5484433

## Como executar

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`. O banco H2 é criado automaticamente na pasta `data/`.
Para usar MySQL, comente o bloco H2 e descomente o bloco MySQL em `src/main/resources/application.properties`.

## Entidades

| Entidade | Campos |
|---|---|
| Produto | id (Long), nome (String), preco (BigDecimal), estoque (Boolean) |
| Cliente | id (Long), nome (String), clienteDesde (LocalDate) |
| Pedido | id (Long), clienteId (Long), produtoId (Long), quantidade (Integer) |

## Endpoints

Para `/clientes`, `/produtos` e `/pedidos`:

| Método | Caminho | Descrição |
|---|---|---|
| POST | `/{recurso}` | Cria |
| GET | `/{recurso}/{id}` | Consulta por ID (404 se não existir) |
| GET | `/{recurso}` | Lista todos |
| PUT | `/{recurso}/{id}` | Atualiza (opcional) |
| DELETE | `/{recurso}/{id}` | Apaga (204; 404 se não existir) |

Um pedido só é criado se o cliente e o produto existirem e a quantidade for maior que zero (senão retorna 400).

## Exemplo

```json
POST /clientes   { "nome": "ViniciusMarteleto5484433", "clienteDesde": "2026-10-05" }
POST /produtos   { "nome": "Baozi de Carne", "preco": 8.50, "estoque": true }
POST /pedidos    { "clienteId": 1, "produtoId": 1, "quantidade": 3 }
```

A coleção do Postman está em `postman/BaoziStore.postman_collection.json`.
