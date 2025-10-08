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

## How to Run Backend Locally

1. Make sure Docker is running.
2. Start PostgreSQL container:
   docker compose up -d

3. Run backend (Spring Boot):
   ./mvnw spring-boot:run

4. Access API endpoints (e.g., login) at:
   http://localhost:8081

### Next Steps / Roadmap
## Backend

- [ ] Complete Task entity, repository, and service

- [ ] Create REST controllers for Task CRUD

- [ ] Implement unit and integration tests

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


```bash

