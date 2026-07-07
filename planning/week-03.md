# 📅 Week 3 Planning

**Duration:** 06/07/2026 – 12/07/2026

## 🎯 Weekly Goal

Expand the system by introducing **Theater**, **Seat**, and **Showtime** management. Refactor the booking workflow so that tickets are booked by **showtime** instead of movie, while maintaining backward compatibility through comprehensive testing.

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

# 📍 Monday (06/07/2026)

## 🍅 Pomodoro 1 — Theater Model

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create `Theater` model
* [ ] Add constructors and getters
* [ ] Override `toString()`
* [ ] Write `TheaterTest`

---

## 🍅 Pomodoro 2 — Showtime Model

**Time:** 09:30 – 09:55

### Checklist

* [ ] Create `Showtime`
* [ ] Add constructors and getters
* [ ] Override `toString()`
* [ ] Write `ShowtimeTest`

---

## 🍅 Pomodoro 3 — TheaterRepository

**Time:** 10:00 – 10:25

### Checklist

* [ ] Create `TheaterRepository`
* [ ] Implement CSV loading
* [ ] Implement CSV saving

---

## 🍅 Pomodoro 4 — ShowtimeRepository

**Time:** 10:30 – 10:55

### Checklist

* [ ] Create `ShowtimeRepository`
* [ ] Implement CSV persistence
* [ ] Write repository tests

---

# 📍 Tuesday (07/07/2026)

## 🍅 Pomodoro 1 — Refactor Seat Model

**Time:** 09:00 – 09:25

### Checklist

* [ ] Add `theaterId`
* [ ] Add row and column
* [ ] Remove booking status field

---

## 🍅 Pomodoro 2 — SeatRepository

**Time:** 09:30 – 09:55

### Checklist

* [ ] Create `SeatRepository`
* [ ] Support CSV persistence
* [ ] Implement `findByTheaterId()`

---

## 🍅 Pomodoro 3 — Seat Initialization

**Time:** 10:00 – 10:25

### Checklist

* [ ] Automatically generate seats
* [ ] Save generated seats
* [ ] Verify seat numbering

---

## 🍅 Pomodoro 4 — Repository Testing

**Time:** 10:30 – 10:55

### Checklist

* [ ] Test `SeatRepository`
* [ ] Test automatic seat generation

---

# 📍 Wednesday (08/07/2026)

## 🍅 Pomodoro 1 — Refactor Booking

**Time:** 09:00 – 09:25

### Checklist

* [ ] Replace `movieId` with `showtimeId`
* [ ] Add booking timestamp
* [ ] Update constructors

---

## 🍅 Pomodoro 2 — BookingRepository

**Time:** 09:30 – 09:55

### Checklist

* [ ] Update CSV format
* [ ] Update seat lookup
* [ ] Maintain backward compatibility

---

## 🍅 Pomodoro 3 — Update Tests

**Time:** 10:00 – 10:25

### Checklist

* [ ] Refactor existing tests
* [ ] Update sample data
* [ ] Verify migration

---

## 🍅 Pomodoro 4 — Booked Seats by Showtime

**Time:** 10:30 – 10:55

### Checklist

* [ ] Implement `getBookedSeatsByShowtime()`
* [ ] Write unit tests

---

# 📍 Thursday (09/07/2026)

## 🍅 Pomodoro 1 — Validator Improvements

**Time:** 09:00 – 09:25

### Checklist

* [ ] Validate showtime
* [ ] Validate seat belongs to theater

---

## 🍅 Pomodoro 2 — BookingService Refactor

**Time:** 09:30 – 09:55

### Checklist

* [ ] Book tickets by showtime
* [ ] Validate theater-seat relationship
* [ ] Validate seat availability

---

## 🍅 Pomodoro 3 — Service Testing

**Time:** 10:00 – 10:25

### Checklist

* [ ] Valid booking
* [ ] Invalid theater
* [ ] Reserved seat
* [ ] Invalid showtime

---

## 🍅 Pomodoro 4 — Available Seats

**Time:** 10:30 – 10:55

### Checklist

* [ ] Implement `getAvailableSeats()`
* [ ] Write unit tests

---

# 📍 Friday (10/07/2026)

## 🍅 Pomodoro 1 — Theater Management

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create Theater menu
* [ ] Implement CRUD operations

---

## 🍅 Pomodoro 2 — Showtime Management

**Time:** 09:30 – 09:55

### Checklist

* [ ] Create Showtime menu
* [ ] Add and remove showtimes
* [ ] List showtimes

---

## 🍅 Pomodoro 3 — Update Main Menu

**Time:** 10:00 – 10:25

### Checklist

* [ ] Integrate Theater menu
* [ ] Integrate Showtime menu
* [ ] Add available seats option

---

## 🍅 Pomodoro 4 — Manual Testing

**Time:** 10:30 – 10:55

### Checklist

* [ ] Create theater
* [ ] Generate seats
* [ ] Create showtime
* [ ] Book tickets successfully

---

# 📍 Saturday (11/07/2026)

## 🍅 Pomodoro 1 — Integration Testing

**Time:** 09:00 – 09:25

### Checklist

* [ ] Theater workflow
* [ ] Showtime workflow
* [ ] Complete booking process

---

## 🍅 Pomodoro 2 — Exception Testing

**Time:** 09:30 – 09:55

### Checklist

* [ ] Invalid theater
* [ ] Invalid showtime
* [ ] Invalid seat
* [ ] Delete protected resources

---

## 🍅 Pomodoro 3 — Test Coverage

**Time:** 10:00 – 10:25

### Checklist

* [ ] Generate coverage report
* [ ] Reach at least 70% coverage

---

## 🍅 Pomodoro 4 — Refactoring

**Time:** 10:30 – 10:55

### Checklist

* [ ] Remove obsolete methods
* [ ] Improve architecture
* [ ] Commit completed work

---

# 📍 Sunday (12/07/2026)

## 🍅 Pomodoro 1 — Code Review

**Time:** 09:00 – 09:25

### Checklist

* [ ] Review naming
* [ ] Remove magic numbers
* [ ] Simplify complex methods

---

## 🍅 Pomodoro 2 — Documentation

**Time:** 09:30 – 09:55

### Checklist

* [ ] Update README
* [ ] Update Entity Relationship Diagram
* [ ] Document new modules

---

## 🍅 Pomodoro 3 — Threading Preparation

**Time:** 10:00 – 10:25

### Checklist

* [ ] Design concurrent booking flow
* [ ] Identify synchronization points
* [ ] Draft booking sequence diagram

---

## 🍅 Pomodoro 4 — Plan Week 4

**Time:** 10:30 – 10:55

### Checklist

* [ ] Create `TODO.md`
* [ ] Plan multi-threaded booking
* [ ] Plan performance testing
* [ ] Commit and push to GitHub

---

# ✅ Week 3 Completion Criteria

* [ ] `Theater`, `Seat`, and `Showtime` entities are fully implemented.
* [ ] CSV persistence supports all new entities.
* [ ] Seats are automatically generated for each theater.
* [ ] Booking is based on **showtime** instead of movie.
* [ ] Full CRUD operations are available for Theater and Showtime.
* [ ] Existing functionality remains stable after refactoring.
* [ ] Unit and integration tests pass successfully.
* [ ] Codebase is clean, maintainable, and ready for concurrent booking in Week 4.
