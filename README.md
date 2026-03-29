# Monolith-E-commerce

E-Commerce Backend project in Monolith Architecture

# Tech Stack

# Backend
  • Java 21+ - Core programming language.
  • Spring Boot 4.0 - Framework for building the RESTful APIs.
  • Spring Data JPA - For Object-Relational Mapping (ORM).

# Database
  •  PostgreSQL - Primary relational database for persistent storage.
  •  H2 Database - Used for rapid local development and unit testing.

# DevOps & Infrastructure
  •  Docker & Docker Compose - For containerizing the application and database.
  •  Maven - Dependency management and build automation.
  •  Postman - For API testing and documentation.


• Engineered a comprehensive RESTful API suite managing 4+ core relational entities (Users, Products,Cart, Orders) using Spring Boot, 
   handling complex state transitions for order processing.
   
• Designed a PostgreSQL schema with Spring Data JPA, implementing One-to-Many and Many-toMany mappings to ensure referential integrity
   across database tables.
   
• Adopted a Layered Architecture (MVC variant) to decouple business logic from data access, resulting in a modular codebase easily extensible
   for new features like payment integration.
