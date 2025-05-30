# Spring MVC & Client-Server Web Application

## Overview

This project is a multi-stage web application developed as part of a coursework assignment. It evolves from a basic Spring MVC monolith to a client-server architecture, and finally into a database-integrated application with advanced features such as DTOs, exception handling, and WebSockets.

---

## Project Stages

### STAGE 1– Spring MVC Monolith
- Implemented a monolithic MVC application with:
  - Minimum 3 domain model classes
  - Entity relationships (1:n or n:m)
  - In-memory repository
  - Full CRUD operations (Create, Read, Update, Delete)
  - Controllers and Views
  - Unit Tests

### STAGE 2 – Client-Server Conversion
- Converted the monolithic application into a **client-server** architecture
- Server side:
  - Converted Controllers to `@RestController`
  - Exposed REST endpoints
  - Implemented basic login functionality
  - Added 3 new business functionalities (with edge-case handling)
  - Integrated a new Spring module (e.g., Spring Security or Spring Validation)
  - Documented endpoints with Swagger
- Client side:
  - Interacts with the server through REST APIs

### STAGE 3 – Database Integration & Advanced Features
- Integrated the application with an **SQL database**
- Server side improvements:
  - Replaced in-memory repository with **JPA Repositories**
  - Added **DTOs**, **Model-Entity conversion**, and **Mappers**
  - Included **custom exception handling** using `ResponseEntityExceptionHandler`
  - Added 2 new features, one of which uses **WebSockets**
- Optional bonus: refactor towards **microservices architecture**

---

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Web / MVC**
- **Spring Data JPA**
- **Spring Security / WebSocket** (depending on the chosen module)
- **Thymeleaf / React** (for front-end, if applicable)
- **H2 / MySQL** (SQL database)
- **Swagger / OpenAPI** (for API documentation)

