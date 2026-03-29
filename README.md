# E-commerce Tool

A production-ready Monolithic E-commerce backend designed with scalability and maintainability. This project implements a full suite of RESTful APIs to manage the core lifecycle of an e-commerce platform, from product discovery to order fulfillment.

## Tech Stack

### **Backend & Core**
* **Language:** Java 21 — Utilizing **Streams API** for data processing and **Optional** for null-safety.
* **Framework:** Spring Boot 4.0 — High-performance RESTful API development and dependency injection.
* **Persistence:** Spring Data JPA — Advanced ORM for seamless database abstraction and repository management.
* **Build Tool:** Maven — Lifecycle management, dependency resolution, and automated builds.

### **Data Persistence**
* **Primary Database:** PostgreSQL — Production-grade relational storage for Users, Products, and Orders.
* **Development DB:** H2 Database — Lightweight in-memory store for rapid prototyping and unit testing.

### **DevOps & Tooling**
* **Containerization:** Docker & Docker Compose — Orchestrating the application and database environment.
* **API Testing:** Postman — Comprehensive endpoint validation and request/response documentation.

---

##  Engineering Highlights

* **Functional Programming:** Leveraged **Java Streams** to perform efficient data filtering and mapping on collection sets, and utilized **Optional** to handle potential null values, significantly reducing the risk of `NullPointerException`.
* **Core Business Logic:** Developed a robust ordering workflow—managing the lifecycle of Users, Products, and Carts—including a custom service layer to handle complex state transitions (e.g., validating stock before transitioning an Order from `PENDING` to `CONFIRMED`).
* **Relational Data Modeling:** Architected a normalized **PostgreSQL** schema leveraging **Spring Data JPA**. Managed complex data relationships, including **Many-to-Many** mappings for product categories and **One-to-Many** for user orders, while ensuring strict referential integrity.
* **Separation of Concerns:** Implemented a clean, layered architecture (MVC variant) to decouple API controllers from the service logic. This approach ensured the business rules remained independent of the data access layer, making the system easier to unit test and maintain.
* **Environment Orchestration:** Standardized the development environment by containerizing the entire stack. Using **Docker Compose**, I automated the networking between the Spring Boot container and the PostgreSQL instance, ensuring a "plug-and-play" setup for any collaborator.
