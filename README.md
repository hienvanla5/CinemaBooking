# 🎬 Cinema Booking System

A console-based **Cinema Booking System** developed in **Java**, focusing on clean architecture, object-oriented programming, design patterns, data persistence, testing, and thread-safe seat booking.

This project simulates the core workflow of a real cinema booking system, including movie management, theater scheduling, seat reservation, pricing strategies, and concurrent booking.

---

# ✨ Features

## 🎥 Movie Management

* Create, update, delete, and search movies
* Display available movie list

## 🏢 Theater Management

* Create and manage theaters
* Configure seat layout (rows & columns)

## 🕒 Showtime Management

* Schedule movies in different theaters
* Manage showtimes

## 🎟️ Seat Booking

* Reserve available seats
* Prevent duplicate bookings
* Validate customer information
* Record booking history

## 💺 Seat Map

* Display available and reserved seats
* View remaining seats for each showtime

## 💰 Ticket Pricing

Support multiple pricing strategies:

* Normal Price
* Gold Hour
* Weekend Price
* VIP Booking
* Combo Booking

## 📊 Revenue Statistics

* Calculate total revenue
* Revenue by showtime
* Booking history

## ⚡ Concurrent Booking Simulation

The system supports multiple users booking seats simultaneously.

Implemented using:

* synchronized
* ExecutorService
* Thread-safe collections

Race conditions and duplicate bookings are prevented through synchronized critical sections.

---

# 🏗️ Project Architecture

The project follows a layered architecture.

```
UI
        │
        ▼
Service
        │
        ▼
Repository
        │
        ▼
CSV Storage
```

Project packages include:

```
com.cinema
├── context
├── exception
├── factory
├── model
├── pricing
├── repository
├── service
├── ui
├── util
└── validator
```

---

# 🎯 Design Patterns

The project applies several common software design patterns.

| Pattern                       | Purpose                               |
| ----------------------------- | ------------------------------------- |
| Singleton                     | FileStorage, AppLogger, AppContext    |
| Factory                       | Booking creation                      |
| Strategy                      | Ticket pricing                        |
| Generic Repository            | Shared CRUD operations                |
| Dependency Injection (Manual) | Service initialization via AppContext |

---

# 🛠️ Technologies

* Java 11+
* Maven
* JUnit 5
* CSV File Storage
* Java Collections Framework
* Java Concurrency API
* Java Logging API

---

# 📂 Project Structure

```
src
├── main
│   ├── java
│   └── resources
│       └── data
└── test
```

---

# 🚀 Getting Started

## Prerequisites

* Java 11 or later
* Maven 3.8+

Verify installation:

```bash
java -version
mvn -version
```

---

## Clone Repository

```bash
git clone https://github.com/your-username/cinema-booking.git

cd cinema-booking
```

---

## Build

```bash
mvn clean package
```

---

## Run

```bash
mvn exec:java -Dexec.mainClass="com.cinema.ui.Main"
```

or

```bash
java -jar target/cinema-booking.jar
```

---

# 🧪 Running Tests

Run all unit and integration tests.

```bash
mvn test
```

The project includes tests for:

* Validators
* Repositories
* Services
* Booking workflow
* Concurrent booking

---

# 📊 Performance

Concurrent booking simulation results:

| Scenario             | Result                |
| -------------------- | --------------------- |
| 20 users / 1 seat    | 1 success, 19 failed  |
| 100 users / 50 seats | 50 success, 50 failed |

The system guarantees that no seat can be booked more than once.

---

# 📸 Class Diagram

![Class Diagram](docs/class_diagram.png)

---

# 📈 Future Improvements

* User authentication
* Database integration (MySQL/PostgreSQL)
* REST API with Spring Boot
* GUI/Desktop version
* Web version
* Online payment
* Email ticket confirmation

---

# 📄 License

This project is released under the MIT License.
