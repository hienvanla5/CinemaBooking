# 📖 User Guide

This guide explains how to use the **Cinema Booking System** console application.

---

# 🚀 Getting Started

Run the application using:

```bash
mvn exec:java -Dexec.mainClass="com.cinema.ui.Main"
```

After launching, the main menu will appear:

```text
===== Welcome to Cinema Booking Application =====

1. View list of movies
2. Book a ticket
3. Manage theaters
4. Manage showtimes
5. Show seating chart
6. Customer booking history
7. Revenue statistics
8. Simulate concurrent ticket booking
9. Measure performance
10. Exit

Enter your choice:
```

---

# 🎬 1. View List of Movies

Displays all movies currently available in the system.

Information shown:

* Movie ID
* Movie title
* Duration (minutes)

Example:

```text
ID | Title                       | Duration
----------------------------------------------
1  | Avengers: Endgame           | 181
2  | Interstellar                | 169
```

---

# 🎟️ 2. Book a Ticket

Reserve a seat for a specific showtime.

### Steps

1. Select **Book a Ticket**.
2. Choose a showtime.
3. Enter the seat ID.
4. Enter the customer name.
5. The system validates:

    * Showtime exists
    * Seat exists
    * Seat is available
6. If successful, the booking is saved.

If the seat has already been booked, an error message is displayed.

---

# 🏢 3. Manage Theaters

Manage cinema theaters and seating layouts.

Typical operations include:

* Add a new theater
* View all theaters
* Update theater information
* Delete a theater

Each theater stores:

* Theater name
* Number of rows
* Number of columns

---

# 🕒 4. Manage Showtimes

Manage movie schedules.

Typical operations include:

* Create a new showtime
* View showtimes
* Update showtimes
* Delete showtimes

Each showtime contains:

* Movie
* Theater
* Start time

---

# 💺 5. Show Seating Chart

Display the seating status of a selected showtime.

### Steps

1. Enter the showtime ID.
2. The system displays the theater layout.

Seat status:

* `[ ]` = Available
* `[X]` = Booked

Example:

```text
      1  2  3  4
 1   [ ][X][ ][ ]
 2   [ ][ ][X][ ]
 3   [ ][ ][ ][ ]
```

---

# 👤 6. Customer Booking History

Search booking records by customer name.

### Steps

1. Enter the customer's full name or part of the name.
2. The system displays all matching bookings.

Information displayed:

* Showtime ID
* Seat ID
* Customer name
* Ticket price
* Total bookings found

---

# 💰 7. Revenue Statistics

Display ticket sales statistics.

The report includes:

* Total tickets sold
* Total revenue
* Average ticket price
* Revenue grouped by showtime

This feature helps monitor business performance.

---

# ⚡ 8. Simulate Concurrent Ticket Booking

Simulate multiple users attempting to book the same seat simultaneously.

### Steps

1. Select a showtime.
2. Enter the seat ID.
3. Enter the number of simulated users (threads).
4. Start the simulation.

The system reports:

* Number of successful bookings
* Number of failed bookings
* Total execution time

This feature demonstrates the application's thread-safe booking mechanism.

---

# 🚀 9. Measure Performance

Run a built-in performance benchmark.

The application automatically executes a predefined concurrent booking scenario and reports:

* Successful bookings
* Failed bookings
* Total execution time
* Average execution time per user

This feature is intended for evaluating the system's concurrency performance.

---

# 🚪 10. Exit

Safely close the application.

All resources are released before the program terminates.

---

# 📌 Notes

* Enter numeric values for menu options and IDs.
* Theater and showtime data should be created before booking tickets.
* Invalid input is handled gracefully with validation messages.
* Concurrent booking is synchronized to prevent duplicate seat reservations.
