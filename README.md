# Ticketly - "API first" ticket management system

Ticketly is a RESTful API that allows users to create, retrieve, and manage tickets within an
organization. This API provides endpoints for creating new tickets, retrieving a list of tickets, and filtering tickets
based on different parameters such as status and priority.

## Requirements
- Java 21

## Build 
`./gradlew build` after build you can start application by `java -jar build/libs/ticketly-<version>-SNAPSHOT.jar`

## Run
`./gradlew bootRun`

## Test
`./gradlew test`

## Swagger
You will find Swagger ui here: `<app-url>/swagger-ui/index.html`.

### Example API Requests
Feel free to use Postman or Insomnia to send requests

- create new ticket
`POST <app-url>/api/tickets`
```json
{
  "title": "Sample Ticket",
  "description": "This is a sample ticket description",
  "priority": "HIGH"
}
```

- get all tickets `GET <app-url>/api/tickets`
