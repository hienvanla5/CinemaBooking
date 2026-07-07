# 📅 Week 4 Planning

**Duration:** 13/07/2026 – 19/07/2026

## 🎯 Weekly Goal

Introduce **multi-threading** into the Cinema Booking System to ensure thread-safe ticket booking. Implement concurrent booking simulation, protect critical sections, optimize file persistence, and verify correctness through concurrency testing.

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

# 📍 Monday (13/07/2026)

## 🍅 Pomodoro 1 — Concurrency Fundamentals

**Time:** 09:00 – 09:25

### Checklist

* [ ] Review Race Condition and Critical Section
* [ ] Identify thread-sensitive code in `BookingService`
* [ ] Plan synchronization strategy

---

## 🍅 Pomodoro 2 — Synchronize Booking Logic

**Time:** 09:30 – 09:55

### Checklist

* [ ] Add synchronization to `bookSeat()`
* [ ] Protect booking workflow
* [ ] Preserve exception handling

---

## 🍅 Pomodoro 3 — Critical Section Review

**Time:** 10:00 – 10:25

### Checklist

* [ ] Keep validation inside synchronized block
* [ ] Protect repository updates
* [ ] Verify data consistency

---

## 🍅 Pomodoro 4 — Regression Testing

**Time:** 10:30 – 10:55

### Checklist

* [ ] Run existing unit tests
* [ ] Verify single-thread behavior
* [ ] Commit synchronization changes

---

# 📍 Tuesday (14/07/2026)

## 🍅 Pomodoro 1 — Booking Simulation

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create `BookingSimulation`
* [ ] Configure `ExecutorService`
* [ ] Create worker threads

---

## 🍅 Pomodoro 2 — Concurrent Booking

**Time:** 09:30 – 09:55

### Checklist

* [ ] Implement concurrent simulation
* [ ] Submit booking tasks
* [ ] Wait for completion

---

## 🍅 Pomodoro 3 — Prepare Test Data

**Time:** 10:00 – 10:25

### Checklist

* [ ] Create sample movie
* [ ] Create theater
* [ ] Create showtime
* [ ] Prepare seats

---

## 🍅 Pomodoro 4 — Initial Simulation

**Time:** 10:30 – 10:55

### Checklist

* [ ] Simulate multiple users booking one seat
* [ ] Verify only one booking succeeds
* [ ] Commit implementation

---

# 📍 Wednesday (15/07/2026)

## 🍅 Pomodoro 1 — Concurrent Test Setup

**Time:** 09:00 – 09:25

### Checklist

* [ ] Create `ConcurrentBookingTest`
* [ ] Reset test data before each run

---

## 🍅 Pomodoro 2 — Same Seat Test

**Time:** 09:30 – 09:55

### Checklist

* [ ] Use `CountDownLatch`
* [ ] Use `AtomicInteger`
* [ ] Verify only one successful booking

---

## 🍅 Pomodoro 3 — Different Seats Test

**Time:** 10:00 – 10:25

### Checklist

* [ ] Simulate multiple seat bookings
* [ ] Verify all bookings succeed

---

## 🍅 Pomodoro 4 — Stability Testing

**Time:** 10:30 – 10:55

### Checklist

* [ ] Execute tests repeatedly
* [ ] Eliminate race conditions
* [ ] Commit test suite

---

# 📍 Thursday (16/07/2026)

## 🍅 Pomodoro 1 — Performance Analysis

**Time:** 09:00 – 09:25

### Checklist

* [ ] Identify file I/O bottlenecks
* [ ] Design batch persistence strategy

---

## 🍅 Pomodoro 2 — Batch Repository

**Time:** 09:30 – 09:55

### Checklist

* [ ] Implement `saveAll()`
* [ ] Batch-write bookings
* [ ] Maintain compatibility

---

## 🍅 Pomodoro 3 — In-Memory Queue

**Time:** 10:00 – 10:25

### Checklist

* [ ] Add thread-safe pending bookings
* [ ] Implement `flushBookings()`
* [ ] Separate persistence from booking logic

---

## 🍅 Pomodoro 4 — Performance Benchmark

**Time:** 10:30 – 10:55

### Checklist

* [ ] Compare execution time
* [ ] Validate batch saving
* [ ] Commit performance improvements

---

# 📍 Friday (17/07/2026)

## 🍅 Pomodoro 1 — Stress Testing

**Time:** 09:00 – 09:25

### Checklist

* [ ] Simulate 100 users
* [ ] Simulate 50 seats
* [ ] Verify booking consistency

---

## 🍅 Pomodoro 2 — Edge Case Testing

**Time:** 09:30 – 09:55

### Checklist

* [ ] Invalid seat
* [ ] Invalid showtime
* [ ] Mixed successful and failed requests

---

## 🍅 Pomodoro 3 — Integration Test

**Time:** 10:00 – 10:25

### Checklist

* [ ] Test with actual CSV files
* [ ] Verify final booking data

---

## 🍅 Pomodoro 4 — Optimization

**Time:** 10:30 – 10:55

### Checklist

* [ ] Evaluate `ReentrantLock`
* [ ] Improve synchronization
* [ ] Commit refinements

---

# 📍 Saturday (18/07/2026)

## 🍅 Pomodoro 1 — Simulation Menu

**Time:** 09:00 – 09:25

### Checklist

* [ ] Add simulation option
* [ ] Read simulation parameters
* [ ] Execute concurrent booking

---

## 🍅 Pomodoro 2 — Console Results

**Time:** 09:30 – 09:55

### Checklist

* [ ] Display successful bookings
* [ ] Display failed bookings
* [ ] Show execution summary

---

## 🍅 Pomodoro 3 — End-to-End Demo

**Time:** 10:00 – 10:25

### Checklist

* [ ] Create showtime
* [ ] Run 20-user simulation
* [ ] Verify expected output

---

## 🍅 Pomodoro 4 — Performance Report

**Time:** 10:30 – 10:55

### Checklist

* [ ] Record benchmark results
* [ ] Compare with single-thread execution
* [ ] Commit documentation

---

# 📍 Sunday (19/07/2026)

## 🍅 Pomodoro 1 — Threading Review

**Time:** 09:00 – 09:25

### Checklist

* [ ] Review synchronization logic
* [ ] Check for deadlocks
* [ ] Verify thread-safe collections

---

## 🍅 Pomodoro 2 — Refactoring

**Time:** 09:30 – 09:55

### Checklist

* [ ] Simplify booking workflow
* [ ] Separate persistence logic
* [ ] Improve thread naming

---

## 🍅 Pomodoro 3 — Documentation

**Time:** 10:00 – 10:25

### Checklist

* [ ] Update architecture diagrams
* [ ] Document concurrency design
* [ ] Update README

---

## 🍅 Pomodoro 4 — Plan Week 5

**Time:** 10:30 – 10:55

### Checklist

* [ ] Create `TODO.md`
* [ ] Plan Singleton implementation
* [ ] Plan Factory Pattern
* [ ] Plan Strategy Pattern
* [ ] Commit and push to GitHub

---

# ✅ Week 4 Completion Criteria

* [ ] Booking process is fully thread-safe.
* [ ] Critical sections are properly synchronized.
* [ ] Concurrent booking simulation supports 10–100 users.
* [ ] Concurrency test suite passes consistently.
* [ ] Batch persistence improves file I/O performance.
* [ ] Console application includes a booking simulation feature.
* [ ] Threading implementation is well documented and maintainable.

---

# 🚀 Expected Outcome

By the end of Week 4, the project will include:

* ✅ A thread-safe booking system resistant to race conditions.
* ✅ Concurrent simulation using `ExecutorService`.
* ✅ Comprehensive concurrency and stress tests.
* ✅ Optimized batch file persistence.
* ✅ Interactive console demonstration for concurrent booking.
* ✅ A solid foundation for introducing Design Patterns in Week 5.
