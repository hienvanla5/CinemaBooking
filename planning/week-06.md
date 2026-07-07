# 📅 Week 6 Planning (27/07 – 02/08/2026)

## 🎯 Theme
**Integration & Finalization**

## 🎯 Goal
Integrate every module into a complete cinema booking application, complete end-to-end testing, package the project as an executable JAR, prepare documentation, and polish the project for the final demo.

---

## 📌 Weekly Objectives

- Integrate all modules into one application
- Complete Integration Tests
- Improve console user experience
- Package executable JAR
- Finish project documentation
- Prepare GitHub Release
- Prepare final presentation/demo

---

## ⏰ Daily Schedule

- **P1:** 09:00 – 09:25
- **P2:** 09:30 – 09:55
- **P3:** 10:00 – 10:25
- **P4:** 10:30 – 10:55

---

# 📍 Monday (27/07) — Complete Main Menu Integration

| Pomo | Time | Task | Checklist |
|------|------|------|-----------|
| P1 | 09:00-09:25 | Seat Map UI | - Create `displaySeatMap(showtimeId)`<br>- Display booked/free seats<br>- Improve console formatting |
| P2 | 09:30-09:55 | Booking History | - Add `findByCustomerName()`<br>- Display booking history<br>- Handle empty result |
| P3 | 10:00-10:25 | Revenue Statistics | - Calculate total revenue<br>- Statistics by movie/showtime<br>- Display ticket count |
| P4 | 10:30-10:55 | Main Menu Integration | - Connect every menu together<br>- Verify navigation<br>- **Commit:** `feat: integrate all features into main menu` |

---

# 📍 Tuesday (28/07) — Integration Testing

| Pomo | Time | Task | Checklist |
|------|------|------|-----------|
| P1 | 09:00-09:25 | Test Environment | - Create `CinemaIntegrationTest`<br>- Prepare sample data<br>- Configure test-data directory |
| P2 | 09:30-09:55 | Booking Success Test | - Book valid seat<br>- Verify repository<br>- Verify booked seats |
| P3 | 10:00-10:25 | Exception Integration Test | - Book duplicate seat<br>- Verify exception<br>- Validate repository consistency |
| P4 | 10:30-10:55 | Concurrent Integration Test | - Multi-thread booking test<br>- Verify file consistency<br>- **Commit:** `test: add integration tests` |

---

# 📍 Wednesday (29/07) — Performance Optimization & Bug Fixes

| Pomo | Time | Task | Checklist |
|------|------|------|-----------|
| P1 | 09:00-09:25 | Performance Analysis | - Measure execution time<br>- Profile bottlenecks<br>- Compare single vs concurrent |
| P2 | 09:30-09:55 | Batch Save Optimization | - Verify `flushBookings()`<br>- Optimize pending list<br>- Improve repository performance |
| P3 | 10:00-10:25 | UI Improvements | - Better input validation<br>- Prevent application crashes<br>- Improve messages |
| P4 | 10:30-10:55 | Full Test Run | - Execute all tests<br>- Fix discovered bugs<br>- **Commit:** `perf: optimize application and fix UI bugs` |

---

# 📍 Thursday (30/07) — Build & Packaging

| Pomo | Time | Task | Checklist |
|------|------|------|-----------|
| P1 | 09:00-09:25 | Configure Build | - Configure Maven/Gradle<br>- Set executable Main Class |
| P2 | 09:30-09:55 | Generate Executable JAR | - Run build<br>- Execute JAR<br>- Verify functionality |
| P3 | 10:00-10:25 | Startup Scripts | - Create `run.bat`<br>- Create `run.sh`<br>- Test scripts |
| P4 | 10:30-10:55 | Release Build | - Organize artifacts<br>- **Commit:** `build: add executable jar and startup scripts` |

---

# 📍 Friday (31/07) — Documentation

| Pomo | Time | Task | Checklist |
|------|------|------|-----------|
| P1 | 09:00-09:25 | README | - Features<br>- Technologies<br>- Installation<br>- Usage |
| P2 | 09:30-09:55 | User Guide | - Explain menus<br>- Booking workflow<br>- Screenshots (optional) |
| P3 | 10:00-10:25 | Developer Guide | - Architecture<br>- Design Patterns<br>- Testing<br>- Extension guide |
| P4 | 10:30-10:55 | Documentation Review | - Proofread documentation<br>- **Commit:** `docs: complete project documentation` |

---

# 📍 Saturday (01/08) — End-to-End Testing

| Pomo | Time | Task | Checklist |
|------|------|------|-----------|
| P1 | 09:00-09:25 | Real Scenario Test | - Reset data<br>- Create movies<br>- Create theaters<br>- Create showtimes |
| P2 | 09:30-09:55 | Threading Demo | - Simulate 100 users<br>- Verify only one booking succeeds |
| P3 | 10:00-10:25 | Bug Fix Session | - Record issues<br>- Fix bugs<br>- Verify fixes |
| P4 | 10:30-10:55 | Final Testing | - Run complete workflow<br>- **Commit:** `fix: final end-to-end bug fixes` |

---

# 📍 Sunday (02/08) — Release & Demo Preparation

| Pomo | Time | Task | Checklist |
|------|------|------|-----------|
| P1 | 09:00-09:25 | Project Cleanup | - Remove temporary files<br>- Organize folders<br>- Verify project structure |
| P2 | 09:30-09:55 | GitHub Release | - Create `v1.0.0`<br>- Upload JAR<br>- Upload documentation |
| P3 | 10:00-10:25 | Demo Slides | - Architecture<br>- Features<br>- Threading<br>- Design Patterns |
| P4 | 10:30-10:55 | Final Demo Rehearsal | - Run complete presentation<br>- Time presentation<br>- **Commit:** `chore: finalize project release` |

---

# ✅ Week 6 Completion Criteria

| Category | Expected Result |
|-----------|-----------------|
| Menu | All features accessible |
| Integration Tests | ≥ 5 passing integration tests |
| Seat Map | Clear seat visualization |
| Revenue Statistics | Accurate calculations |
| Executable JAR | Runs with `java -jar` |
| Documentation | README + User Guide + Developer Guide |
| Demo | Smooth end-to-end presentation |
| GitHub Release | Version `v1.0.0` published |

---

# 📊 Final Deliverables

## Source Code

- Complete Cinema Booking System
- Clean Architecture
- Thread-safe Booking
- CRUD Management
- Logging
- Exception Handling

## Testing

- Unit Tests
- Integration Tests
- Concurrent Tests
- >70% Test Coverage

## Design Patterns

- Singleton
- Factory
- Strategy
- Generic Repository

## Deliverables

- Executable JAR
- Source Code
- README
- User Guide
- Developer Guide
- UML/Class Diagram
- Flowcharts
- GitHub Release v1.0.0

---

# 🏆 Final Project Outcome

After six weeks, the project should deliver:

- 🎬 Complete Cinema Booking Console Application
- 🧵 Thread-safe concurrent booking system
- 🧪 Comprehensive Unit, Integration, and Concurrent Tests
- 🏗️ Clean Architecture with Design Patterns
- 📦 Executable JAR ready for distribution
- 📚 Professional documentation
- 🚀 GitHub portfolio project ready for internship and showcase