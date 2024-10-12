# Final Project Booking Room API

### Description

Application that purpose for experimentation in limit request per second with redis 

### Features

- Login & Register As User
- CRUD Vendors

### Documentation

To view the full API documentation, navigate to the following endpoint after starting the application: 
http://localhost:8081/swagger-ui.html

### Instalation Guide

1. Ensure you have installed at least Java Development Kit (JDK) version 17 and Maven on your computer.
2. Clone this repository to your local machine:
``` bash
git clone https://github.com/razahginanjar/Booking-room.git
```
3. Open a terminal or command prompt and navigate to the project directory where you saved the files.
4. Resolve all dependencies:
```bash
mvn dependency:resolve
```
5. Update and install:
```bash
mvn clean install
```
6. If you wanna run with docker 
first, is:
```bash
docker build -t login-redis-api/login-redis-api:1.0.0 .
```
and after that :
```bash
docker compose up -d
```
### INSTRUCTIONS
Before you run the app, you should make sure to create database with name of "login_redis" first.

# System Requirements

## Java
#### Java Version: 18
The project requires min. JDK 18 for compiling and running.
## Maven
#### Maven Compiler Plugin
The configuration specifies maven.compiler.source and maven.compiler.target both set to 18, indicating that Maven 3.x is suitable for building the project.
## Dependencies
#### Spring Boot 3.3.3
#### PostgreSQL 14 
#### Springdoc OpenAPI
#### Redis
#### Lombok
#### Spring Boot DevTools
#### Build Tools
#### Spring Boot Maven Plugin

# Recommended System Setup
- JDK 18: Install JDK 18 to compile and run the application.
- Maven 3.x: Ensure Maven is installed and configured properly.
- PostgreSQL 14 Database: Set up PostgreSQL if the application requires a database.
- Internet Access: Required to download dependencies from repositories.
- IDE: It’s beneficial to use an IDE like IntelliJ IDEA, Eclipse, or VSCode that supports Java and Maven.
- **Ensure you have correctly configured the `application.properties` file.**
