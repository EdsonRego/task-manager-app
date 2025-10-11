# Postman Collection - Task Manager Backend

Esta pasta contém a collection do **Postman** para testar o backend da aplicação **Task Manager**.  
Ela inclui todas as rotas disponíveis para **Tasks** e **Users**, permitindo testes de CRUD e filtragens.

## Estrutura

postman/
├── TaskManager.postman_collection.json # Collection com todas as requests
├── README.md # Este arquivo


## Requisitos

- [Postman](https://www.postman.com/downloads/)
- Backend rodando localmente (por padrão em `http://localhost:8080`)

## Como usar

1. Abra o Postman.
2. Importe a collection:
    - Vá em **File → Import** ou clique no botão **Import**.
    - Selecione o arquivo `TaskManager.postman_collection.json`.
3. Certifique-se de que o backend está rodando (`./mvnw spring-boot:run` ou via IDE).
4. Ajuste a variável de ambiente `baseUrl` caso o backend esteja em outra porta.
5. Execute as requests:
    - **Tasks**
        - `GET /api/tasks` - Retorna todas as tasks
        - `GET /api/tasks/completed` - Retorna tasks concluídas
        - `GET /api/tasks/pending` - Retorna tasks pendentes
        - `GET /api/tasks/overdue` - Retorna tasks atrasadas
        - `GET /api/tasks/search?keyword=` - Pesquisa tasks por palavra-chave
        - `GET /api/tasks/due-soon?days=` - Tasks com prazo próximo
        - `POST /api/tasks` - Cria uma nova task
    - **Users**
        - (Se houver endpoints de usuário na API)

## Observações

- A collection já inclui **autenticação básica** (caso a API tenha segurança habilitada).
- Para rodar em paralelo com outros projetos, ajuste a porta no arquivo `application.properties` ou em uma variável de ambiente.  

