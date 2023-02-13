# Drone Service API

The drone service manages business rules for medication deliveries via drones

The following components and tools are used in this API:

- [Java](https://www.java.com/)
- [Spring Boot](https://spring.io/projects/spring-boot/)
- [Gradle](https://gradle.org/)
- [H2](https://www.h2database.com/html/main.html)

## Pre-requisites

- Java SE Development Kit (JDK)

## Start up

### Run on localhost (default)

```shell
# To start the service
./gradlew bootRun
```

### Run via docker compose

```shell
docker-compose up
```

### API documentation

```shell
http://localhost:8080/swagger-ui.html
```

## Run tests
```shell
./gradlew test
```
