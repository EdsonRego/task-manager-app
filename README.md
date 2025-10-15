# task-manager-app

Portfolio project: **Task Manager Web App** — Java Spring Boot backend, React frontend (em desenvolvimento), PostgreSQL, Docker.

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15.14-blue)
![Docker](https://img.shields.io/badge/Docker-enabled-lightblue)
![Maven](https://img.shields.io/badge/Maven-3.9.0-red)

## Overview

Portfolio project showcasing a full-stack web application.

- **Backend**: Java Spring Boot
    - REST API for managing tasks and users
    - Spring Data JPA for persistence
    - Spring Security for authentication
    - PostgreSQL database (via Docker)
- **Frontend**: React + TypeScript (planned)
    - Login page, dashboard, task list
    - CRUD operations for tasks (planned)
- **Containerization**: Docker for backend and PostgreSQL
- **Database**: PostgreSQL (running in Docker container)

> 🚀 This project is part of my professional portfolio to demonstrate skills in full-stack development, containerization, and cloud-ready applications.

## Project Status

### Backend
- [x] Project initialized with Spring Initializr
- [x] Dependencies: Spring Web, Spring Data JPA, Spring Security, Validation, PostgreSQL Driver, Lombok
- [x] Git repository and initial commit
- [x] PostgreSQL database configured via Docker
- [x] Entities, repositories, and basic security implemented
- [x] Backend runs locally on port **8081**

### Frontend
- [ ] React frontend project not yet started

### UpWork / Portfolio
- [x] Account created
- [x] Project selected for portfolio
- [ ] Frontend development
- [ ] Video presentation
- [ ] Publish project on UpWork

### Next Steps / Roadmap
## Backend

- [x] Complete Task entity, repository, and service

- [x] Create REST controllers for Task CRUD

- [x] Implement unit and integration tests

- [ ] Add API documentation (Swagger / OpenAPI)

- [ ] Prepare database seed scripts

## Frontend

- [ ] Initialize React + TypeScript project

- [ ] Create pages: Login, Dashboard, Task List

- [ ] Connect frontend to backend API

- [ ] Implement Task CRUD operations

- [ ] Authentication handling (JWT)

- [ ] Local build and Docker container (optional)

## Portfolio / UpWork

- [ ] Create short presentation video (≤2 min)

- [ ] Add project description, screenshots, and demo link

- [ ] Publish project as portfolio showcase

- [ ] Deploy full-stack application on cloud (Heroku, AWS, or Railway)

This README reflects the current status of the project and the next steps for completing the full-stack Task Manager App.

## How to Run Backend Locally

### Perfil Teste Backend

Este subtópico descreve como executar e testar o backend do Task Manager no **perfil de teste (`test`)**, usando a base de dados H2 em memória.

#### Requisitos
- Java 17 instalado e configurado no PATH.
- Maven instalado.
- Docker Desktop instalado e em execução (para uso de containers, se necessário).
- Postman instalado para testes dos endpoints.
- Porta **8081** disponível para a aplicação.

#### Passo a Passo

1. **Verificar o Docker Desktop**
    - Abra o Docker Desktop e verifique se ele está ativo.
    - Caso queira usar PostgreSQL ou outros serviços em container, eles podem ser iniciados via `docker-compose`.

2. **Executar Docker Compose (opcional)**  
    -  for necessário subir serviços auxiliares (ex.: PostgreSQL), execute:

   docker-compose up -d

    - Verifique se o container subiu corretamente:

   docker ps

    - Confirme se as portas do container não estão em conflito com a aplicação (no perfil de teste geralmente não é necessário, pois usamos H2).

3. **Limpar build e compilar sem executar testes**

   mvn clean package -DskipTests

    - Isso garante que todas as classes e recursos estejam atualizados sem rodar os testes unitários/integrados.
   
4. **Subir a aplicação no perfil de teste**

   mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=test --server.port=8081"

    - A aplicação iniciará com o perfil de teste, utilizando a base H2 em memória.

   - O endpoint padrão /api/users deve retornar o usuário inicial de teste (admin@example.com) criado automaticamente.

5. **Carregar a collection do Postman**

    - Abra o Postman.

   - Importe o arquivo TaskManagerCollection.json (versão para perfil de teste).

   - Confirme que todos os endpoints estão presentes e apontando para http://localhost:8081/api/....

6. **Sequência de testes sugerida**

    - Recomenda-se testar os endpoints na seguinte ordem lógica:

    1. GET /api/health — verifica se a aplicação está rodando.
    
    2. GET /api/status — verifica informações de status.
    
    3. GET /api/users — lista usuários existentes.
    
    4. POST /api/users — cria um novo usuário de teste.
    
    5. GET /api/users/{id} — consulta usuário específico.
    
    6. PUT /api/users/{id} — atualiza usuário.

    7. DELETE /api/users/{id} — remove usuário.
    
    8. GET /api/tasks — lista todas as tarefas.
    
    9. POST /api/tasks — cria nova tarefa.
    
    10. GET /api/tasks/{id} — consulta tarefa específica.
    
    11. PUT /api/tasks/{id} — atualiza tarefa.
    
    12. DELETE /api/tasks/{id} — remove tarefa.
    
    - Endpoints específicos:
    
    1. GET /api/tasks/completed
    
    2. GET /api/tasks/pending
    
    3. GET /api/tasks/overdue
    
    4. GET /api/tasks/search?keyword=...

    5. GET /api/tasks/due-soon?days=...

7. **Pontos importantes**

    - No perfil de teste, a base de dados é em memória (H2), portanto todos os dados são apagados ao reiniciar a aplicação.

   - Certifique-se de que nenhuma outra aplicação esteja ocupando a porta 8081.

   - Para qualquer teste envolvendo segurança, o perfil test libera todos os endpoints sem autenticação.

   - Logs detalhados podem ser encontrados em logs/test-execution.log.

8. **Finalizando a aplicação**

    - Para parar a aplicação rodando via Maven:
    
    CTRL + C

    - Para parar containers Docker (se utilizados):

    docker-compose down

    - Seguindo essa sequência, é possível testar todos os endpoints do backend de forma confiável, garantindo que a aplicação esteja funcional no perfil de teste.

## 🧪 How to Run Backend Locally

### ⚙️ Perfil DEV Backend

Este perfil é voltado para o ambiente de desenvolvimento local do backend. Ele permite que o desenvolvedor suba a aplicação Java conectada a um banco PostgreSQL em container Docker e valide os endpoints via Postman.

#### ✅ Requisitos

Antes de iniciar, garanta que possui instalado em seu ambiente:

- **Docker Desktop**
- **Java 17 ou superior**
- **Maven 3.8+**
- **Postman**
- **Git** (para clonar o repositório, se aplicável)

#### 🚀 Passo a Passo

1. **Inicie o Docker Desktop**  
   Certifique-se de que o Docker está ativo antes de executar qualquer comando.

2. **Suba o banco de dados via Docker Compose**
   ```bash
   cd backend/
   docker-compose up -d
   ```

3. **Verifique se o container foi iniciado corretamente**
   ```bash
   docker ps
   ```
   O container `task-manager-postgres` deve aparecer com o status `Up` e a porta `5433` mapeada.

4. **Confirme se a porta está disponível (5433)**
   - Se a porta já estiver em uso, encerre o serviço em conflito ou altere a configuração no `docker-compose.yml`.
   - Caso a aplicação já esteja em execução, pare-a antes de subir novamente:
     ```bash
     mvn spring-boot:stop
     ```

5. **Execute o build da aplicação sem os testes automatizados**
   ```bash
   mvn clean install -DskipTests
   ```

6. **Inicie a aplicação no perfil DEV**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

   A aplicação deve iniciar e ficar acessível na URL:
   ```
   http://localhost:8080
   ```

7. **Verifique a conexão com o banco**
   - Confirme se o banco foi populado com as tabelas `users` e `tasks`:
     ```bash
     docker exec -it task-manager-postgres psql -U postgres -d task_manager_db
     \dt
     ```
   - O resultado deve listar:
     ```
     public | users | table | postgres
     public | tasks | table | postgres
     ```

8. **Importe a collection do Postman**
   - Abra o Postman.
   - Vá em **File → Import** e selecione o arquivo da collection (`Task Manager.postman_collection.json`).

9. **Siga a sequência lógica dos testes:**

   1. **Login e autenticação**
      - Endpoint: `POST /auth/login`
      - Retorna o token JWT para as requisições seguintes.

   2. **Listar usuários**
      - Endpoint: `GET /users`
      - Verifica se o usuário admin padrão está ativo.

   3. **Criar um novo usuário**
      - Endpoint: `POST /users`

   4. **Criar tarefas**
      - Endpoint: `POST /tasks`

   5. **Listar tarefas**
      - Endpoint: `GET /tasks`

   6. **Atualizar tarefa**
      - Endpoint: `PUT /tasks/{id}`

   7. **Excluir tarefa**
      - Endpoint: `DELETE /tasks/{id}`

10. **Verifique logs e saídas da aplicação**
   - Monitore o console Maven durante a execução.
   - Logs de conexão com o banco e endpoints devem ser exibidos.

#### 🧩 Dicas adicionais

- Caso precise limpar o ambiente de containers:
  ```bash
  docker-compose down -v
  ```
- Para resetar o banco de dados e recomeçar os testes:
  ```bash
  docker exec -it task-manager-postgres psql -U postgres -c "DROP DATABASE task_manager_db;"
  docker-compose up -d
  ```

- É possível depurar a aplicação diretamente em uma IDE como **IntelliJ** ou **VS Code**, configurando o mesmo perfil `dev`.



```bash

