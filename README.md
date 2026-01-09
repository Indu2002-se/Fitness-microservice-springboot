# Fitness Microservices Application

## Overview

This is a microservices-based Fitness Application designed to track user activities and provide personalized AI-driven recommendations using Google's Gemini API. The system is built with Spring Boot and utilizes an event-driven architecture for seamless communication between services.

## Architecture

The system consists of the following microservices:

1.  **Eureka Server** (`eureka`):
    -   **Port**: 8761
    -   **Role**: Service Registry and Discovery. All other services register themselves here.

2.  **User Service** (`userservice`):
    -   **Port**: 8080
    -   **Role**: Manages user accounts and authentication.
    -   **Key Endpoints**:
        -   `POST /user/register`: Register a new user.
        -   `GET /user/{userId}/validate`: Validate if a user exists.
    -   **Database**: MySQL (`FitnesUser_db`)

3.  **Activity Service** (`activity-service`):
    -   **Port**: 8082
    -   **Role**: Records and manages fitness activities (running, cycling, etc.).
    -   **Key Functionality**:
        -   Validates user existence via User Service.
        -   Publishes new activity events to RabbitMQ (`fitness.exchange` -> `activity.queue`).
    -   **Key Endpoints**:
        -   `POST /activity`: Log a new activity.
        -   `GET /activity`: Get all activities for a user (Requires `userId` header).
        -   `GET /activity/{activityId}`: Get details of a specific activity.
    -   **Database**: MySQL (`FitnesUser_db`)

4.  **AI Service** (`ai-service`):
    -   **Port**: 8083
    -   **Role**: Provides intelligent fitness recommendations.
    -   **Key Functionality**:
        -   Listens to `activity.queue` for new activities.
        -   Uses **Google Gemini API** (`gemini-2.5-flash`) to generate personalized advice based on the activity data.
    -   **Key Endpoints**:
        -   `GET /recomen/user/{userId}`: Get recommendations for a user.
        -   `GET /recomen/activity/{activityId}`: Get specific recommendation for an activity.
    -   **Database**: MySQL (`FitnesUser_db`)

## Technology Stack

-   **Language**: Java 21
-   **Framework**: Spring Boot 3.x
-   **Service Discovery**: Spring Cloud Netflix Eureka
-   **Messaging**: RabbitMQ
-   **Database**: MySQL
-   **AI Integration**: Google Gemini API
-   **Build Tool**: Maven

## System Flow

1.  A user registers via the **User Service**.
2.  The user logs an activity (e.g., "30 min running") via the **Activity Service**.
3.  **Activity Service** saves the record and asynchronously publishes an event to **RabbitMQ**.
4.  **AI Service** consumes the event, sends the activity details to **Gemini API**, and processes the generated recommendation.
5.  Users can retrieve their AI-generated insights via the **AI Service** endpoints.

## Prerequisites

Ensure you have the following installed and running:

-   Java 21 JDK
-   Maven
-   MySQL Server (Port 3306)
-   RabbitMQ Server (Port 5672)

## Configuration

### Database
The services are configured to use a MySQL database named `FitnesUser_db`.
Credentials used (default):
-   **Username**: `root`
-   **Password**: `Ihara12#`

*Update `application.yaml` in each service if your credentials differ.*

### RabbitMQ
-   **Host**: `localhost` (5672)
-   **Username/Password**: `guest`/`guest`

### Google Gemini API
The `ai-service` requires a valid API key.
-   Key is configured in `ai-service/src/main/resources/application.yaml`.

## Running the Application

1.  **Start Eureka Server**:
    ```bash
    cd eureka
    mvn spring-boot:run
    ```

2.  **Start User Service**:
    ```bash
    cd userservice
    mvn spring-boot:run
    ```

3.  **Start Activity Service**:
    ```bash
    cd activity-service
    mvn spring-boot:run
    ```

4.  **Start AI Service**:
    ```bash
    cd ai-service
    mvn spring-boot:run
    ```

## API Usage

### 1. Register User
```http
POST http://localhost:8080/user/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123"
}
```

### 2. Log Activity
```http
POST http://localhost:8082/activity
Content-Type: application/json

{
    "userId": 1,
    "activityType": "Running",
    "duration": 30,
    "calories": 300
}
```

### 3. Get Recommendations
```http
GET http://localhost:8083/recomen/user/1
```
