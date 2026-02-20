# Task Management Demo

Spring Boot sample application for managing tasks.

## Overview
- Simple REST API to create, list, update, soft-delete and toggle tasks.
- Uses JPA with Joda-Time `DateTime` and a custom attribute converter.

## Build

Use Maven to build:

```bash
./mvnw clean package
```

## Run

Run the application with:

```bash
./mvnw spring-boot:run
```

or run the generated jar:

```bash
java -jar target/demo-*.jar
```

## API

Base path: `/api/v1/tasks`

- `GET /api/v1/tasks` - list tasks (supports pagination and filtering)
- `GET /api/v1/tasks/{id}` - get task by id
- `POST /api/v1/tasks` - create task
- `PUT /api/v1/tasks/{id}` - update task
- `DELETE /api/v1/tasks/{id}` - soft delete task
- `PATCH /api/v1/tasks/{id}/toggle` - toggle completion

## Notes

- Validation is performed on required fields.
- Soft-deleted tasks are excluded from `list` and `get` operations.
