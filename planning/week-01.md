# 📅 Week 1 Planning

**Duration:** 22/06/2026 – 28/06/2026

## 🎯 Weekly Goal

Build the project's foundation by implementing the core domain models, Generic Repository, CSV file persistence, and basic console interaction while following **Test-Driven Development (TDD)** principles.

---

## ⏰ Daily Schedule

Each day consists of **4 Pomodoro sessions**.

| Session       | Time          |
| ------------- | ------------- |
| 🍅 Pomodoro 1 | 09:00 – 09:25 |
| 🍅 Pomodoro 2 | 09:30 – 09:55 |
| 🍅 Pomodoro 3 | 10:00 – 10:25 |
| 🍅 Pomodoro 4 | 10:30 – 10:55 |

---

# 📍 Monday (22/06/2026)

## 🍅 Pomodoro 1 — Initialize Project

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create Maven project
* [ ] Organize package structure (`model`, `repository`, `service`, `ui`, `util`)
* [ ] Configure JUnit 5
* [ ] Verify project builds successfully

---

## 🍅 Pomodoro 2 — Create Domain Models

**Time:** 09:30 – 09:55

### Checklist

* [ ] Implement `Movie`
* [ ] Add constructors
* [ ] Add getters
* [ ] Override `toString()`

---

## 🍅 Pomodoro 3 — Unit Test: Movie

**Time:** 10:00 – 10:25

### Checklist

* [ ] Write `MovieTest`
* [ ] Verify constructors
* [ ] Verify getters
* [ ] Run tests

---

## 🍅 Pomodoro 4 — Create Seat & Booking Models

**Time:** 10:30 – 10:55

### Checklist

* [ ] Create `Seat`
* [ ] Create `Booking`
* [ ] Write corresponding tests

---

# 📍 Tuesday (23/06/2026)

## 🍅 Pomodoro 1 — FileStorage (Read)

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create `FileStorage`
* [ ] Implement `readLines()`
* [ ] Handle `IOException`

---

## 🍅 Pomodoro 2 — FileStorage (Write)

**Time:** 09:30 – 09:55

### Checklist

* [ ] Implement `writeLines()`
* [ ] Create directories automatically
* [ ] Verify overwrite behavior

---

## 🍅 Pomodoro 3 — Unit Test: FileStorage

**Time:** 10:00 – 10:25

### Checklist

* [ ] Create temporary test files
* [ ] Test reading
* [ ] Test writing

---

## 🍅 Pomodoro 4 — Sample Data

**Time:** 10:30 – 10:55

### Checklist

* [ ] Generate sample `movies.csv`
* [ ] Load sample data
* [ ] Verify parsing

---

# 📍 Wednesday (24/06/2026)

## 🍅 Pomodoro 1 — IRepository

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create `IRepository<T>`
* [ ] Define CRUD operations

---

## 🍅 Pomodoro 2 — BaseRepository

**Time:** 09:30 – 09:55

### Checklist

* [ ] Create `BaseRepository<T>`
* [ ] Implement generic CRUD
* [ ] Store entities in memory

---

## 🍅 Pomodoro 3 — Unit Test: BaseRepository

**Time:** 10:00 – 10:25

### Checklist

* [ ] Create fake entity
* [ ] Test CRUD operations
* [ ] Verify generic behavior

---

## 🍅 Pomodoro 4 — Refactoring

**Time:** 10:30 – 10:55

### Checklist

* [ ] Improve code quality
* [ ] Ensure all tests pass
* [ ] Commit changes

---

# 📍 Thursday (25/06/2026)

## 🍅 Pomodoro 1 — MovieRepository

**Time:** 09:00 – 09:25

### Checklist

* [ ] Extend `BaseRepository<Movie>`
* [ ] Initialize repository

---

## 🍅 Pomodoro 2 — Load Movies

**Time:** 09:30 – 09:55

### Checklist

* [ ] Read `movies.csv`
* [ ] Parse records
* [ ] Handle missing files

---

## 🍅 Pomodoro 3 — Save Movies

**Time:** 10:00 – 10:25

### Checklist

* [ ] Implement `saveToFile()`
* [ ] Overwrite CSV correctly

---

## 🍅 Pomodoro 4 — Unit Test: MovieRepository

**Time:** 10:30 – 10:55

### Checklist

* [ ] Test `findAll()`
* [ ] Test `save()`
* [ ] Test `delete()`

---

# 📍 Friday (26/06/2026)

## 🍅 Pomodoro 1 — BookingRepository

**Time:** 09:00 – 09:25

### Checklist

* [ ] Extend `BaseRepository<Booking>`

---

## 🍅 Pomodoro 2 — CSV Persistence

**Time:** 09:30 – 09:55

### Checklist

* [ ] Read `bookings.csv`
* [ ] Save `bookings.csv`

---

## 🍅 Pomodoro 3 — Seat Availability

**Time:** 10:00 – 10:25

### Checklist

* [ ] Implement `isSeatBooked()`
* [ ] Use Java Stream API

---

## 🍅 Pomodoro 4 — Unit Test: BookingRepository

**Time:** 10:30 – 10:55

### Checklist

* [ ] Test file persistence
* [ ] Test seat lookup

---

# 📍 Saturday (27/06/2026)

## 🍅 Pomodoro 1 — BookingService

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create `BookingService`
* [ ] Implement `bookSeat()`

---

## 🍅 Pomodoro 2 — Unit Test: BookingService

**Time:** 09:30 – 09:55

### Checklist

* [ ] Successful booking
* [ ] Duplicate booking
* [ ] Repository integration

---

## 🍅 Pomodoro 3 — Console Menu

**Time:** 10:00 – 10:25

### Checklist

* [ ] Create `Main`
* [ ] Display movie list
* [ ] Build navigation menu

---

## 🍅 Pomodoro 4 — Ticket Booking UI

**Time:** 10:30 – 10:55

### Checklist

* [ ] Read user input
* [ ] Call service layer
* [ ] Display booking result
* [ ] Handle simple input errors

---

# 📍 Sunday (28/06/2026)

## 🍅 Pomodoro 1 — Run All Tests

**Time:** 09:00 – 09:25

### Checklist

* [ ] Execute full test suite
* [ ] Fix failing tests

---

## 🍅 Pomodoro 2 — Refactoring

**Time:** 09:30 – 09:55

### Checklist

* [ ] Improve naming
* [ ] Extract reusable methods
* [ ] Replace magic strings with constants

---

## 🍅 Pomodoro 3 — Booking Flow Design

**Time:** 10:00 – 10:25

### Checklist

* [ ] Design booking workflow
* [ ] Prepare validation flow for Week 2

---

## 🍅 Pomodoro 4 — Prepare Week 2

**Time:** 10:30 – 10:55

### Checklist

* [ ] Create `TODO.md`
* [ ] Plan validation tasks
* [ ] Plan custom exceptions
* [ ] Commit and push to GitHub

---

# 📌 Development Guidelines

* Follow **Test-Driven Development (TDD)** whenever possible (**Red → Green → Refactor**).
* Make small, meaningful commits with descriptive commit messages.
* Keep business logic inside the Service layer.
* Refactor continuously to maintain Clean Code principles.
* Take a **5-minute break** after each Pomodoro session.
* Push completed work to GitHub at the end of each day.
