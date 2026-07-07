# 📅 Week 2 Planning

**Duration:** 29/06/2026 – 05/07/2026

## 🎯 Weekly Goal

Strengthen the business logic by introducing input validation, custom exceptions, and comprehensive testing. By the end of the week, the application should reject invalid input gracefully and provide clear error messages without crashing.

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

# 📍 Monday (29/06/2026)

## 🍅 Pomodoro 1 — Custom Exception Hierarchy

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create `com.cinema.exception`
* [ ] Implement `BookingAppException`
* [ ] Implement `InvalidInputException`
* [ ] Implement `SeatUnavailableException`

---

## 🍅 Pomodoro 2 — Unit Test: Exceptions

**Time:** 09:30 – 09:55

### Checklist

* [ ] Write `ExceptionTest`
* [ ] Verify exception messages
* [ ] Test exception hierarchy

---

## 🍅 Pomodoro 3 — Validator

**Time:** 10:00 – 10:25

### Checklist

* [ ] Create `Validator`
* [ ] Implement `validateCustomerName()`
* [ ] Implement `validateSeatId()`

---

## 🍅 Pomodoro 4 — Unit Test: Validator

**Time:** 10:30 – 10:55

### Checklist

* [ ] Test valid customer names
* [ ] Test empty or null names
* [ ] Test valid seat IDs
* [ ] Test invalid seat IDs

---

# 📍 Tuesday (30/06/2026)

## 🍅 Pomodoro 1 — Integrate Validation

**Time:** 09:00 – 09:25

### Checklist

* [ ] Update `BookingService`
* [ ] Validate customer name
* [ ] Validate seat ID

---

## 🍅 Pomodoro 2 — Seat Availability

**Time:** 09:30 – 09:55

### Checklist

* [ ] Throw `SeatUnavailableException`
* [ ] Return `Booking` object instead of boolean

---

## 🍅 Pomodoro 3 — Unit Test: BookingService

**Time:** 10:00 – 10:25

### Checklist

* [ ] Test invalid customer name
* [ ] Test booked seat
* [ ] Test successful booking

---

## 🍅 Pomodoro 4 — Refactoring

**Time:** 10:30 – 10:55

### Checklist

* [ ] Fix issues
* [ ] Ensure all tests pass
* [ ] Commit completed changes

---

# 📍 Wednesday (01/07/2026)

## 🍅 Pomodoro 1 — Improve BookingService

**Time:** 09:00 – 09:25

### Checklist

* [ ] Return `Booking` after successful booking
* [ ] Improve service workflow

---

## 🍅 Pomodoro 2 — Seat Capacity

**Time:** 09:30 – 09:55

### Checklist

* [ ] Add `getMaxSeats()`
* [ ] Prepare dynamic seat validation

---

## 🍅 Pomodoro 3 — Dynamic Validation

**Time:** 10:00 – 10:25

### Checklist

* [ ] Pass seat capacity into Validator
* [ ] Update related tests

---

## 🍅 Pomodoro 4 — End-to-End Service Tests

**Time:** 10:30 – 10:55

### Checklist

* [ ] Successful booking flow
* [ ] Invalid input scenarios
* [ ] Exception handling

---

# 📍 Thursday (02/07/2026)

## 🍅 Pomodoro 1 — Improve BookingRepository

**Time:** 09:00 – 09:25

### Checklist

* [ ] Implement `findByMovieId()`
* [ ] Write unit tests

---

## 🍅 Pomodoro 2 — Booked Seats

**Time:** 09:30 – 09:55

### Checklist

* [ ] Implement `getBookedSeats()`
* [ ] Write unit tests

---

## 🍅 Pomodoro 3 — Improve MovieRepository

**Time:** 10:00 – 10:25

### Checklist

* [ ] Implement `findByName()`
* [ ] Support partial search
* [ ] Write unit tests

---

## 🍅 Pomodoro 4 — Refactoring

**Time:** 10:30 – 10:55

### Checklist

* [ ] Remove duplicated code
* [ ] Improve repository structure
* [ ] Commit completed work

---

# 📍 Friday (03/07/2026)

## 🍅 Pomodoro 1 — Update Console Menu

**Time:** 09:00 – 09:25

### Checklist

* [ ] Catch business exceptions
* [ ] Display user-friendly messages

---

## 🍅 Pomodoro 2 — Show Booked Seats

**Time:** 09:30 – 09:55

### Checklist

* [ ] Add booked seats menu
* [ ] Display reserved seat list

---

## 🍅 Pomodoro 3 — Input Validation

**Time:** 10:00 – 10:25

### Checklist

* [ ] Handle invalid numeric input
* [ ] Prevent application crashes

---

## 🍅 Pomodoro 4 — Manual Testing

**Time:** 10:30 – 10:55

### Checklist

* [ ] Empty customer name
* [ ] Invalid seat ID
* [ ] Duplicate booking
* [ ] Successful booking

---

# 📍 Saturday (04/07/2026)

## 🍅 Pomodoro 1 — Integration Testing

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create integration tests
* [ ] Test complete booking workflow

---

## 🍅 Pomodoro 2 — Edge Case Testing

**Time:** 09:30 – 09:55

### Checklist

* [ ] Duplicate booking attempts
* [ ] Customer names with spaces
* [ ] Invalid seat numbers

---

## 🍅 Pomodoro 3 — Test Coverage

**Time:** 10:00 – 10:25

### Checklist

* [ ] Generate coverage report
* [ ] Achieve at least 70% coverage

---

## 🍅 Pomodoro 4 — Refactor Test Suite

**Time:** 10:30 – 10:55

### Checklist

* [ ] Use `@BeforeEach`
* [ ] Replace manual checks with `assertThrows()`
* [ ] Improve readability

---

# 📍 Sunday (05/07/2026)

## 🍅 Pomodoro 1 — Clean Code Review

**Time:** 09:00 – 09:25

### Checklist

* [ ] Remove magic numbers
* [ ] Improve naming
* [ ] Simplify long methods

---

## 🍅 Pomodoro 2 — Update Documentation

**Time:** 09:30 – 09:55

### Checklist

* [ ] Update README
* [ ] Document Validator
* [ ] Document Custom Exceptions

---

## 🍅 Pomodoro 3 — Prepare Next Features

**Time:** 10:00 – 10:25

### Checklist

* [ ] Design booking validation flow
* [ ] Plan entity relationships for Week 3

---

## 🍅 Pomodoro 4 — Plan Week 3

**Time:** 10:30 – 10:55

### Checklist

* [ ] Create `TODO.md`
* [ ] Plan Theater & Showtime modules
* [ ] Commit and push to GitHub

---

# ✅ Week 2 Completion Criteria

* [ ] Custom exceptions implemented and used correctly.
* [ ] Validator covers all required input validation.
* [ ] `BookingService` accepts only valid bookings.
* [ ] Console UI handles errors gracefully.
* [ ] Unit and integration tests achieve at least **70% coverage**.
* [ ] Code follows Clean Code principles and is ready for Week 3.
