# 📅 Week 5 Planning (20/07 – 26/07/2026)

## 🎯 Goal

Refactor the application architecture by applying common object-oriented design patterns and Clean Code principles. Improve maintainability, extensibility, and code readability while ensuring all existing functionality and tests continue to work correctly.

---

## 📚 Weekly Focus

- Apply **Singleton Pattern** for shared resources.
- Apply **Factory Pattern** for booking creation.
- Apply **Strategy Pattern** for ticket pricing.
- Refactor the Service layer following the **Single Responsibility Principle (SRP)**.
- Introduce lightweight dependency management.
- Improve logging and exception handling.
- Increase code quality through documentation and formatting.

---

## ⏰ Daily Schedule

**Pomodoro duration:** 25 minutes (+5 minute break)

| Session | Time |
|---------|------|
| P1 | 09:00 – 09:25 |
| P2 | 09:30 – 09:55 |
| P3 | 10:00 – 10:25 |
| P4 | 10:30 – 10:55 |

---

# 📍 Monday (20/07) – Singleton Pattern

## P1 — Analyze Singleton Candidates
**Time:** 09:00 – 09:25

### Checklist

- [ ] Review Singleton Pattern
- [ ] Identify globally shared components
- [ ] Decide eager vs lazy initialization
- [ ] Plan refactoring strategy

---

## P2 — Refactor FileStorage

**Time:** 09:30 – 09:55

### Checklist

- [ ] Convert `FileStorage` into Singleton
- [ ] Make constructor private
- [ ] Add `getInstance()`
- [ ] Remove unnecessary static methods

---

## P3 — Update Project References

**Time:** 10:00 – 10:25

### Checklist

- [ ] Update repositories to use Singleton
- [ ] Remove old static access
- [ ] Verify compilation

---

## P4 — Testing & Commit

**Time:** 10:30 – 10:55

### Checklist

- [ ] Run all unit tests
- [ ] Verify no regression
- [ ] Commit changes

```
refactor: apply Singleton pattern
```

---

# 📍 Tuesday (21/07) – Factory Pattern

## P1 — Study Factory Method

**Time:** 09:00 – 09:25

### Checklist

- [ ] Review Factory Method Pattern
- [ ] Determine booking creation responsibilities

---

## P2 — Create Booking Factory

**Time:** 09:30 – 09:55

### Checklist

- [ ] Create `BookingFactory`
- [ ] Define creation interface
- [ ] Prepare extension points

---

## P3 — Implement Concrete Factories

**Time:** 10:00 – 10:25

### Checklist

- [ ] RegularBookingFactory
- [ ] VIPBookingFactory
- [ ] BookingFactoryProvider

---

## P4 — Integrate Factory

**Time:** 10:30 – 10:55

### Checklist

- [ ] Remove direct constructor calls
- [ ] Inject BookingFactory
- [ ] Update tests

```
feat: implement Factory pattern
```

---

# 📍 Wednesday (22/07) – Strategy Pattern

## P1 — Analyze Pricing Rules

**Time:** 09:00 – 09:25

### Checklist

- [ ] Define pricing scenarios
- [ ] Identify interchangeable algorithms

---

## P2 — Create Pricing Strategy

**Time:** 09:30 – 09:55

### Checklist

- [ ] Create `PricingStrategy`
- [ ] Define calculation interface

---

## P3 — Implement Strategies

**Time:** 10:00 – 10:25

### Checklist

- [ ] Normal pricing
- [ ] Gold Hour pricing
- [ ] Weekend pricing
- [ ] Create PriceCalculator

---

## P4 — Integrate Pricing

**Time:** 10:30 – 10:55

### Checklist

- [ ] Calculate ticket price
- [ ] Store booking price
- [ ] Update unit tests

```
feat: implement Strategy pattern
```

---

# 📍 Thursday (23/07) – Service Layer Refactoring

## P1 — Split Responsibilities

**Time:** 09:00 – 09:25

### Checklist

- [ ] Create BookingValidator
- [ ] Create BookingManager
- [ ] Create BookingPricingService

---

## P2 — Dependency Injection

**Time:** 09:30 – 09:55

### Checklist

- [ ] Constructor Injection
- [ ] Remove unnecessary object creation
- [ ] Create AppContext

---

## P3 — Update Tests

**Time:** 10:00 – 10:25

### Checklist

- [ ] Refactor test setup
- [ ] Verify all dependencies

---

## P4 — Review

**Time:** 10:30 – 10:55

### Checklist

- [ ] Run tests
- [ ] Review SRP compliance

```
refactor: improve service architecture
```

---

# 📍 Friday (24/07) – Clean Code

## P1 — Naming Review

**Time:** 09:00 – 09:25

### Checklist

- [ ] Rename unclear variables
- [ ] Improve class names
- [ ] Improve method names

---

## P2 — Constants

**Time:** 09:30 – 09:55

### Checklist

- [ ] Create `AppConstants`
- [ ] Remove magic numbers
- [ ] Remove duplicated strings

---

## P3 — Formatting

**Time:** 10:00 – 10:25

### Checklist

- [ ] Format source code
- [ ] Organize imports
- [ ] Remove dead code

---

## P4 — Documentation

**Time:** 10:30 – 10:55

### Checklist

- [ ] Add JavaDoc
- [ ] Improve comments
- [ ] Review public APIs

```
style: improve code quality
```

---

# 📍 Saturday (25/07) – Logging & Exception Handling

## P1 — Logging Infrastructure

**Time:** 09:00 – 09:25

### Checklist

- [ ] Create AppLogger
- [ ] Configure console logging
- [ ] Configure file logging

---

## P2 — Logging Integration

**Time:** 09:30 – 09:55

### Checklist

- [ ] Replace debug prints
- [ ] Log service events
- [ ] Log repository events

---

## P3 — Exception Improvements

**Time:** 10:00 – 10:25

### Checklist

- [ ] Improve exception messages
- [ ] Log stack traces
- [ ] Handle unexpected errors

---

## P4 — Validation

**Time:** 10:30 – 10:55

### Checklist

- [ ] Verify log output
- [ ] Review exception flow
- [ ] Commit changes

```
feat: add centralized logging
```

---

# 📍 Sunday (26/07) – Documentation & Review

## P1 — UML Diagram

**Time:** 09:00 – 09:25

### Checklist

- [ ] Update Class Diagram
- [ ] Show Design Patterns
- [ ] Export diagrams

---

## P2 — Documentation

**Time:** 09:30 – 09:55

### Checklist

- [ ] Update README
- [ ] Update Developer Guide
- [ ] Update Architecture documentation

---

## P3 — Final Review

**Time:** 10:00 – 10:25

### Checklist

- [ ] Search for code smells
- [ ] Review package organization
- [ ] Verify naming consistency

---

## P4 — Prepare Week 6

**Time:** 10:30 – 10:55

### Checklist

- [ ] Create Week 6 planning
- [ ] Push source code
- [ ] Tag weekly milestone

---

# ✅ Week 5 Deliverables

- [ ] Singleton Pattern applied
- [ ] Factory Pattern implemented
- [ ] Strategy Pattern implemented
- [ ] Dependency Injection introduced
- [ ] Service layer refactored
- [ ] Centralized logging system
- [ ] Improved exception handling
- [ ] JavaDoc completed
- [ ] Clean Code improvements
- [ ] All unit and integration tests passing

---

# 🚀 Expected Outcome

At the end of Week 5, the project should have a cleaner architecture with lower coupling, better extensibility, and improved maintainability. Core business logic will follow SOLID principles, Design Patterns will be integrated into appropriate layers, and the application will be well-prepared for the final integration, documentation, packaging, and release phase in Week 6.