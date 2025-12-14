# Cura - Healthcare Microservices Platform

Cura is a comprehensive healthcare platform built using a modern microservices architecture. It provides various services for patient management, appointments, billing, and analytics.

## Technology Stack

### Core
*   **Language**: Java 25
*   **Framework**: Spring Boot 3.5.7
*   **Build Tool**: Maven

### Infrastructure & Database
*   **Database**: PostgreSQL
*   **Cloud Emulation**: LocalStack (AWS S3, CloudFormation)
*   **Containerization**: Docker

### Monitoring & Observability
*   **Metrics**: Prometheus
*   **Visualization**: Grafana

## Architecture

The project follows a microservices architecture with an API Gateway handling external requests and routing them to appropriate internal services.

### Services

| Service Name | Port   | Description |
| :--- |:-------| :--- |
| **API Gateway** | `4004` | Entry point for all client requests. Handles routing, rate limiting, and authentication filtering. |
| **Auth Service** | `4005` | Manages user authentication and JWT token generation. |
| **Patient Service** | `4000` | Handles patient records and demographics. |
| **Appointment Service** | `4006` | Manages scheduling and appointments. |
| **Billing Service** | `4001` | Handles invoicing and payments. |
| **Analytics Service** | `4002`    | Provides data insights and reporting. |

## Getting Started

### Prerequisites
*   Java 25 SDK
*   Docker & Docker Compose
*   Maven
*   AWS CLI (configured for LocalStack)

### Local Development Setup

1.  **Start Infrastructure**
    Ensure Docker is running and start the required infrastructure (Databases, LocalStack, etc.).
    *Refer to specific service READMEs or docker-compose files for detailed startup instructions.*

2.  **Deploy to LocalStack**
    Use the provided script to set up AWS resources (S3, CloudFormation stacks) locally.
    ```bash
    ./infrastracture/localstack-deploy.sh
    ```

3.  **Build Services**
    Navigate to each service directory and build the application.
    ```bash
    mvn clean install
    ```

4.  **Run Services**
    Start the microservices individually or via your IDE.
    *   **API Gateway**: `http://localhost:4004`
    *   **Auth Service**: `http://localhost:4005`
    *   **Patient Service**: `http://localhost:4000`

## Monitoring

Metrics are exposed via Spring Boot Actuator and scraped by Prometheus.

*   **Prometheus**: Scrapes metrics from services (e.g., `patient-service`).
*   **Grafana**: Visualizes the metrics collected by Prometheus.

Configuration files for monitoring can be found in the `monitoring/` directory.
