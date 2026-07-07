# 👨‍💻 Developer Guide

This document provides an overview of the project architecture, design decisions, testing strategy, and extension guidelines for developers who want to understand or contribute to the Cinema Booking System.

---

# 🏗️ Project Structure

```text
src/main/java/com/cinema/
├── context/          # Application context and dependency management
├── exception/        # Custom exceptions
├── factory/          # Booking factories
├── model/            # Domain entities
├── pricing/          # Pricing strategies
├── repository/       # Data access layer (Generic Repository)
├── service/          # Business logic
├── simulation/       # Concurrent booking simulation
├── ui/               # Console user interface
└── util/             # Utilities (Logger, FileStorage, Validator)
```

---

# 🏛️ Layered Architecture

The application follows a layered architecture to separate responsibilities.

```text
Console UI
     │
     ▼
Business Service
     │
     ▼
Repository
     │
     ▼
CSV File Storage
```

### UI Layer

Responsible for:

* Displaying menus
* Reading user input
* Showing results
* Delegating business logic to services

Example:

* Main
* BookingUI
* TheaterUI
* ShowtimeUI

---

### Service Layer

Contains all business logic.

Responsibilities include:

* Booking validation
* Seat availability checking
* Ticket pricing
* Revenue calculation
* Business rule enforcement

Example:

* BookingService

---

### Repository Layer

Responsible for data persistence.

Each repository manages one entity and inherits common CRUD operations from `BaseRepository<T>`.

Repositories include:

* MovieRepository
* TheaterRepository
* ShowtimeRepository
* SeatRepository
* BookingRepository

---

### Model Layer

Represents domain entities.

* Movie
* Theater
* Showtime
* Seat
* Booking

These classes contain only data and basic behaviors.

---

### Utility Layer

Shared helper classes.

Examples:

* FileStorage
* AppLogger
* Validator

---

# 🎯 Design Patterns

## Singleton Pattern

Ensures only one instance exists throughout the application.

Used by:

* AppContext
* FileStorage
* AppLogger

Benefits:

* Centralized resource management
* Reduced object creation
* Easier dependency sharing

---

## Factory Pattern

Encapsulates booking object creation.

Current implementations include:

* RegularBookingFactory
* VIPBookingFactory
* ComboBookingFactory

Benefits:

* Eliminates complex object creation logic
* Easy to introduce new booking types
* Follows the Open/Closed Principle

---

## Strategy Pattern

Encapsulates ticket pricing algorithms.

Examples:

* NormalPricingStrategy
* GoldHourPricingStrategy
* WeekendPricingStrategy

Benefits:

* Pricing logic is interchangeable
* No modification required in BookingService when adding new pricing rules

---

## Generic Repository Pattern

Provides reusable CRUD functionality.

```text
BaseRepository<T>
        ▲
        │
MovieRepository
BookingRepository
SeatRepository
ShowtimeRepository
TheaterRepository
```

Benefits:

* Eliminates duplicated CRUD code
* Easier maintenance
* Consistent repository behavior

---

# 🔄 Dependency Management

Dependencies are managed manually through `AppContext`.

AppContext creates and shares:

* Repositories
* Services
* Factories
* Pricing strategies

This acts as a lightweight Dependency Injection container without requiring Spring.

---

# ⚙️ Thread Safety

Concurrent booking is implemented to ensure that only one user can reserve a seat at a time.

Technologies used:

* synchronized
* ExecutorService
* Thread-safe collections

The critical booking section is synchronized to prevent:

* Race conditions
* Duplicate bookings
* Data inconsistency

---

# 🧪 Testing

The project includes both unit tests and integration tests.

Run all tests:

```bash
mvn test
```

Run a specific integration test:

```bash
mvn test -Dtest=CinemaIntegrationTest
```

Generate a JaCoCo coverage report (if configured):

```bash
mvn jacoco:report
```

Current test coverage includes:

* Validators
* Repositories
* Services
* Booking workflow
* Concurrent booking simulation

---

# ➕ Extending the Project

## Add a New Booking Type

1. Create a new factory implementing the booking creation interface.
2. Register the factory in the provider or application context.
3. Add corresponding unit tests.

---

## Add a New Pricing Strategy

1. Implement the `PricingStrategy` interface.
2. Register the strategy in `AppContext`.
3. Update the booking workflow if necessary.
4. Write unit tests.

---

## Add a New Entity

Example: `Ticket`

Steps:

1. Create the model class.
2. Create a repository extending `BaseRepository<T>`.
3. Register the repository in `AppContext`.
4. Implement service logic.
5. Add UI menus if required.
6. Write unit and integration tests.

---

# 📝 Coding Guidelines

* Follow Clean Code principles.
* Keep business logic inside the Service layer.
* Avoid direct repository access from the UI layer.
* Validate user input before processing.
* Write unit tests for every new feature.
* Reuse existing repositories and utilities whenever possible.

---

# 🚀 Future Improvements

Potential enhancements include:

* Spring Boot REST API
* Database support (MySQL/PostgreSQL)
* Authentication and authorization
* Online payment integration
* GUI or Web interface
* Docker deployment
* CI/CD pipeline with GitHub Actions
