# MTIT - Student Management Microservices

A microservices-based student management system built with **Spring Boot 3.2**, **Spring Cloud Gateway**, and **MongoDB Atlas**. All services are accessible through a single API Gateway on port **8080** with a unified Swagger UI.

---

## Architecture Overview

```
                         ┌──────────────────────┐
                         │    API Gateway        │
   All traffic ────────► │    (port 8080)        │
                         │  - Routes requests    │
                         │  - Unified Swagger UI │
                         └──────┬───┬───┬───┬───┘
                                │   │   │   │
              ┌─────────────────┘   │   │   └─────────────────┐
              │          ┌──────────┘   └──────────┐          │
              ▼          ▼                         ▼          ▼
     ┌──────────────┐ ┌───────────────┐ ┌──────────────┐ ┌──────────────┐
     │ Enrollment   │ │  Lecturer     │ │   Student    │ │   Course     │
     │ Service      │ │  Service      │ │   Service    │ │   Service    │
     │ (port 8081)  │ │  (port 8082)  │ │  (port 8083) │ │  (port 8084) │
     └──────┬───────┘ └──────┬────────┘ └──────┬───────┘ └──────┬───────┘
            │                │                  │                │
            └────────────────┴──────────────────┴────────────────┘
                                    │
                           ┌────────▼────────┐
                           │  MongoDB Atlas   │
                           │  (Cloud DB)      │
                           └─────────────────┘
```

## Tech Stack

| Layer        | Technology                                    |
|-------------|----------------------------------------------|
| Language     | Java 17                                       |
| Framework    | Spring Boot 3.2.x                             |
| Gateway      | Spring Cloud Gateway 2023.0.0                 |
| Database     | MongoDB Atlas (cloud-hosted)                  |
| API Docs     | SpringDoc OpenAPI (Swagger UI)                |
| Build Tool   | Apache Maven                                  |

---

## Services

### 1. Student Service (port 8083)

Manages student records.

| Field        | Type     | Description          |
|-------------|----------|----------------------|
| `id`        | String   | Auto-generated ID    |
| `name`      | String   | Student full name    |
| `email`     | String   | Email address        |
| `age`       | int      | Student age          |
| `department`| String   | Department name      |

**Endpoints:**

| Method   | Path              | Description            |
|----------|-------------------|------------------------|
| `GET`    | `/student`        | Get all students       |
| `GET`    | `/student/{id}`   | Get student by ID      |
| `POST`   | `/student`        | Create a new student   |
| `PUT`    | `/student/{id}`   | Update a student       |
| `DELETE` | `/student/{id}`   | Delete a student       |

---

### 2. Course Service (port 8084)

Manages course information.

| Field        | Type     | Description          |
|-------------|----------|----------------------|
| `id`        | String   | Auto-generated ID    |
| `courseCode` | String  | Course code (e.g. CS101) |
| `courseName`| String   | Course title         |
| `credits`   | int      | Number of credits    |

**Endpoints:**

| Method   | Path             | Description           |
|----------|------------------|-----------------------|
| `GET`    | `/course`        | Get all courses       |
| `GET`    | `/course/{id}`   | Get course by ID      |
| `POST`   | `/course`        | Create a new course   |
| `PUT`    | `/course/{id}`   | Update a course       |
| `DELETE` | `/course/{id}`   | Delete a course       |

---

### 3. Lecturer Service (port 8082)

Manages lecturer records.

| Field        | Type     | Description          |
|-------------|----------|----------------------|
| `id`        | String   | Auto-generated ID    |
| `name`      | String   | Lecturer full name   |
| `email`     | String   | Email address        |
| `department`| String   | Department name      |

**Endpoints:**

| Method   | Path              | Description            |
|----------|-------------------|------------------------|
| `GET`    | `/lecture`        | Get all lecturers      |
| `GET`    | `/lecture/{id}`   | Get lecturer by ID     |
| `POST`   | `/lecture`        | Create a new lecturer  |
| `PUT`    | `/lecture/{id}`   | Update a lecturer      |
| `DELETE` | `/lecture/{id}`   | Delete a lecturer      |

---

### 4. Enrollment Service (port 8081)

Manages student enrollments, including items, status tracking, and amounts.

| Field             | Type             | Description                                          |
|-------------------|------------------|------------------------------------------------------|
| `id`              | String           | Auto-generated ID                                    |
| `userId`          | String           | ID of the user who enrolled                          |
| `items`           | List\<EnrollmentItem\> | List of enrolled course items                  |
| `totalAmount`     | BigDecimal       | Total enrollment cost                                |
| `status`          | String           | PENDING, CONFIRMED, ACTIVE, COMPLETED, or CANCELLED  |
| `shippingAddress` | String           | Address on file                                      |
| `enrollmentDate`  | LocalDateTime    | When the enrollment was created                      |
| `updatedAt`       | LocalDateTime    | Last update timestamp                                |

Each **EnrollmentItem** contains: `productId`, `productName`, `quantity`, `unitPrice`, `subtotal`.

**Endpoints:**

| Method   | Path                          | Description                     |
|----------|-------------------------------|---------------------------------|
| `GET`    | `/enrollments`                | Get all enrollments             |
| `GET`    | `/enrollments/{id}`           | Get enrollment by ID            |
| `GET`    | `/enrollments/user/{userId}`  | Get enrollments by user ID      |
| `GET`    | `/enrollments/status/{status}`| Get enrollments by status       |
| `POST`   | `/enrollments`                | Create a new enrollment         |
| `PUT`    | `/enrollments/{id}`           | Update an enrollment            |
| `PATCH`  | `/enrollments/{id}/status`    | Update enrollment status only   |
| `DELETE` | `/enrollments/{id}`           | Delete an enrollment            |

---

### 5. API Gateway (port 8080)

Single entry point that routes all traffic to the correct backend service and hosts a unified Swagger UI.

| Gateway Path       | Routed To                   |
|--------------------|-----------------------------|
| `/student/**`      | Student Service (8083)      |
| `/course/**`       | Course Service (8084)       |
| `/lecture/**`      | Lecturer Service (8082)     |
| `/enrollments/**`  | Enrollment Service (8081)   |

---

## Swagger UI Links

### Unified (API Gateway) — recommended

Open **one URL** and use the dropdown to switch between all services:

| Description                | URL                                             |
|----------------------------|------------------------------------------------|
| **Unified Swagger UI**     | http://localhost:8080/swagger-ui.html           |

### Individual Services (direct access for debugging)

| Service      | Swagger UI                                       |
|-------------|--------------------------------------------------|
| Enrollment   | http://localhost:8081/swagger-ui.html             |
| Lecturer     | http://localhost:8082/swagger-ui.html             |
| Student      | http://localhost:8083/swagger-ui.html             |
| Course       | http://localhost:8084/swagger-ui.html             |

### OpenAPI JSON Specs

| Service      | Via Gateway                          | Direct                              |
|-------------|--------------------------------------|--------------------------------------|
| Student      | http://localhost:8080/v3/api-docs/student    | http://localhost:8083/v3/api-docs  |
| Enrollment   | http://localhost:8080/v3/api-docs/enrollment | http://localhost:8081/v3/api-docs  |
| Lecturer     | http://localhost:8080/v3/api-docs/lecture     | http://localhost:8082/v3/api-docs  |
| Course       | http://localhost:8080/v3/api-docs/course     | http://localhost:8084/v3/api-docs  |

---

## Prerequisites

- **Java 17** or higher
- **Apache Maven 3.8+**
- Internet connection (MongoDB Atlas is cloud-hosted)

No local MongoDB installation is required.

---

## Getting Started

Start each microservice in its own terminal (or IDE run configuration). Start the **four backends first**, then the **API Gateway** so routes are ready when the gateway listens on port 8080.

```bash
# Terminal 1 — Enrollment Service
cd enrollment-service
mvn spring-boot:run

# Terminal 2 — Lecturer Service
cd lecturer-service
mvn spring-boot:run

# Terminal 3 — Student Service
cd student-service
mvn spring-boot:run

# Terminal 4 — Course Service
cd course-service
mvn spring-boot:run

# Terminal 5 — API Gateway (start last)
cd api-gateway
mvn spring-boot:run
```

### Verify everything is running

1. Open http://localhost:8080/swagger-ui.html
2. Use the dropdown at the top to select a service
3. Click **Try it out** on any endpoint

---

## Quick Test with curl

```bash
# Get all students (via gateway)
curl http://localhost:8080/student

# Create a new course (via gateway)
curl -X POST http://localhost:8080/course \
  -H "Content-Type: application/json" \
  -d '{"courseCode":"CS101","courseName":"Intro to CS","credits":3}'

# Get all lecturers (via gateway)
curl http://localhost:8080/lecture

# Get all enrollments (via gateway)
curl http://localhost:8080/enrollments
```

---

## Project Structure

```
MTIT/
├── api-gateway/                  # Spring Cloud Gateway (port 8080)
│   ├── src/main/java/.../
│   │   └── ApiGatewayApplication.java
│   ├── src/main/resources/
│   │   └── application.yml       # Routes + SpringDoc config
│   └── pom.xml
│
├── student-service/              # Student CRUD (port 8083)
│   ├── src/main/java/.../
│   │   ├── controller/StudentController.java
│   │   ├── model/Student.java
│   │   ├── repository/StudentRepository.java
│   │   └── service/StudentService.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── course-service/               # Course CRUD (port 8084)
│   ├── src/main/java/.../
│   │   ├── controller/CourseController.java
│   │   ├── model/Course.java
│   │   ├── repository/CourseRepository.java
│   │   └── service/CourseService.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── lecturer-service/             # Lecturer CRUD (port 8082)
│   ├── src/main/java/.../
│   │   ├── controller/LecturerController.java
│   │   ├── model/Lecturer.java
│   │   ├── repository/LecturerRepository.java
│   │   └── service/LecturerService.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── enrollment-service/           # Enrollment CRUD (port 8081)
│   ├── src/main/java/.../
│   │   ├── controller/EnrollmentController.java
│   │   ├── model/Enrollment.java
│   │   ├── model/EnrollmentItem.java
│   │   ├── repository/EnrollmentRepository.java
│   │   ├── service/EnrollmentService.java
│   │   └── config/DataSeeder.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
└── README.md
```

---

## Port Summary

| Service            | Port  |
|--------------------|-------|
| API Gateway        | 8080  |
| Enrollment Service | 8081  |
| Lecturer Service   | 8082  |
| Student Service    | 8083  |
| Course Service     | 8084  |

---

## Database

All services connect to **MongoDB Atlas** (cloud). Collections are stored in the `mtit` database (student service uses `studentdb`).

| Service      | Database     | Collection     |
|-------------|-------------|----------------|
| Student      | `studentdb` | `students`     |
| Course       | `mtit`      | `courses`      |
| Lecturer     | `mtit`      | `lecturers`    |
| Enrollment   | `mtit`      | `enrollments`  |
