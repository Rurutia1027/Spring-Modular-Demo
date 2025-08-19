# Medium Demo Cloud Native Project | [![Medium Demo Cloud Native Project](https://github.com/Rurutia1027/Spring-Modular-Demo/actions/workflows/ci.yml/badge.svg)](https://github.com/Rurutia1027/Spring-Modular-Demo/actions/workflows/ci.yml)

## Overview 
This repository is a **medium-scale demostration project** showcasing how to setup a **multi-module Spring Boot & Spring Cloud architecture** with the following features: 
- **Gateway**
- **Feign Client** for service-to-service communication 
- **Database interfaction** (JPA + relational DB)
- **Flyway** for SQL-based schema migration & dataset initialization
- **ShardingSphere** for database sharding / read–write splitting configuration
- **Redis** for caching/session management 
- **Consul** for service discovery 
- **Envoy** as a sidecar / API gateway experiment 
- **Jib** for building Docker images wihtout a Dockerfile 
- **BDD test execution** from a separate test repo against this envionment

## Project Structure 
```mermaid 
```

--- 

## Feature Demostrated 

### Multi-Module Setup
Parent project manages all modules consistently (dependencies, build config)

### Service Gateway 
Routes requests to different services and provides a single entry point

### Feign Clients 
Simplifies service-to-service REST communication

### Database + Redis 
Relational database for persistence, Redis for caching and fast lookup

### Migration with Flyway
- Initializes database tables and seed data on startup.
- Manages schema evolution for version upgrades (e.g., new columns, constraints).
- Handles adjustment of initial data as the project evolves.

### ShardingSphere
Provides horizontal sharding, read/write splitting, and distributed database support.

### Consul 
Service discovery and configuration management 

### Envoy 
Optional sidecar/proxy for demostrating traffic routing.

### Jib 
Containerizes modules into lightweight Docker images without custom Dockerfiles. 

### BDD Tests (External Project)
Another project/repo executes BDD scenarios (e.g., using **Cucumber** or **Karate**) against this environment. 

---

## Tech Stack 
- Java 17+
- Spring Boot 3.x 
- Spring Data JPA
- Spring Cloud OpenFeign 
- Spring Data Redis 
- HashiCorp Consul 
- Envoy Proxy 
- Maven (multi-module setup)
- Google Jib (containerization)
- Docker & Docker Compose 
- BDD Framework: Cucumber/Serenity/Karate

---

## Building Docker Images with Jib

- From the root of the project: 
```bash 
./mvnw clean compile jib:dockerBuild -Dimage=org/medium/demo/
```

No `Dockerfile` required -- Jib will build optimized container images for each module. 


---

## Running BDD Tests 
BDD tests are maintained in a **separate project** (e.g., `spring-modular-bdd-tests`).

That project spins up the environment form this repo (via `docker-compose` or `Kubernetes`) and executes feature scenarios against the running services. 

- Example flow: 

```bash 
# In this repo (build & start services)
docker-compose up -d

# In the BDD project (execute scenarios)
./mvnw verify 
```

---

## How to Run Locally 
- Start **Consul** and **Redis** (e.g., via Docker Compose)
- Build services 
```bash 
./mvnw clean install 
```
- Run modules individually or use Docker images built with Jib. 
- Access the API through the **Gateway** (default `http://localhost:8080`)

---

## Test Reports 
BDD project will generate human-readable reports (HTML/PDF) after execution. 
Reports will be available under: 

```bash 
spring-modular-bdd-tests/target/cucumber-reports
```

---
## Notes 
- This is a **demostration project only**, not intended for production use. 
- Designed for showing modular Spring setup, service discovery, contarinerization, and externalized BDD testing. 
