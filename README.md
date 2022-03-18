# Blue-Harvest Task

The project is developed for blue-harvest interview backend task.

## Summary
 
The project provides to create account for customers. To make testing easy, also some extra endpoints added.

## Endpoints

There are 2 main and 3 util apis.

```
POST /account/createAccount -> creates a new account for existing customer

POST /account/createCustomer -> util function to create customer

GET /account -> get all customers

GET /account/{customerId} -> get a customer whose id is provided

GET /account/mock -> generates mock data to make testing easier
```

## Tech Stack

- Java 11
- Spring Boot
- Kafka
- Docker
- Docker compose
- React

## Prerequisites

- Maven
- Docker
- Docker compose

## Build & Run
Running docker-compose-release.yml file.