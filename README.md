# Gamesys Registration Service

[![Build Status](https://travis-ci.com/prasadus92/registration-service.svg?branch=master)](https://travis-ci.com/prasadus92/registration-service)

## Problem Statement

The task is to write a Java based web service that provides a simple endpoint for user registration.

### Requirements
1. Must use Java.
2. Should use a build & dependency management tool e.g. Maven or Gradle.
3. We should be able to compile and run your tests e.g. mvn clean verify
4. Please include instructions for running the application.

#### Required data & validation

- Username - alphanumeric, no spaces
- Password – min length 4, at least one upper case letter & number
- DoB (Date of Birth) - ISO 8601 format
- Payment Card Number – between 15 and 19 digits

#### Expected responses

- If the request body fails to conform to any of the basic validation checks return HTTP status code: 400
- Reject registrations if the user is under the age of 18 and return HTTP Status code: 403
- If the username has already been used reject the request and return HTTP Status code: 409
- A successful registration should return HTTP Status code: 201

#### Example request

```bash
     curl -X POST \
       http://127.0.0.1:8080/api/v1/user/registration \
       -H 'Content-Type: application/json' \
       -d '{
         "username": "BruceWayne",
         "password": "Password1",
         "dob": "1980-02-21",
         "paymentCardNumber": "349293081054422"
     }'
```

#### Optional extra

On start-up, allow a list of blocked payment issuer identification numbers to be provided. The
issuer identification number (IIN) is the first 6 digits of the payment card’s number. If the IIN
is blocked registration should fail returning HTTP status code 406.

## Solution Overview

- The service is written using Spring Boot `v2.1.6 RELEASE`.
- MongoDB is used as the underlying database and hence Spring Data MongoDB is included as a dependency to the project.
- Input validation is done via specifically written custom validators implementing the `ConstraintValidator` along with Spring's `@Valid`.
- The blocked Issued Identification Number list is provided as a file (`blocked_iin.json`) from the resources and the service uses this while booting up and dumps it into a database document. The service uses this table during the registration to check for already blocked Issuer Identification Numbers and throw errors accordingly.
- The service runs on the port `8080`.

## Building the Project

Maven is used as the build tool. Use the below command to build the project -

```bash
mvn clean install
```

`Dockerfile` is included in the project and hence to build the Docker image, just run -

```bash
mvn dockerfile:build
```

The image will be built & tagged as `gamesys/registration-service:latest`.


## Running the Project

### Using Maven

It's very easy with Maven here as well, just run the below command -

```bash
mvn spring-boot:run
```

### Using Docker Compose

If you have built the Docker image already, use the Docker Compose to run the application. Here is the command to use -

```bash
docker-compose up
```

This will also run MongoDB v3.6 container locally and the `registration-service` will use the same. The Spring profile used here is `docker`.

## Running Tests

In case if you like to run the tests alone, use the below Maven command -

```bash
mvn clean test
```

## API Documentation

Swagger is included in the project and hence to access the API Documentation, open http://localhost:8080/swagger-ui.html from the browser.
