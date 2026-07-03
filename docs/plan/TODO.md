# Kế hoạch Tuần 3 (06/07 – 12/07)

## Mục tiêu
Thêm các thực thể Theater và Showtime, quản lý ghế theo phòng chiếu.

## Danh sách công việc chi tiết

### Ngày 1 – Thứ Hai, 06/07
- [ ] Tạo class `Theater` (id, name, totalRows, totalColumns)
- [ ] Tạo class `Showtime` (id, movieId, theaterId, startTime)
- [ ] Tạo `TheaterRepository` (đọc/ghi file theaters.csv)
- [ ] Tạo `ShowtimeRepository` (đọc/ghi file showtimes.csv)

### Ngày 2 – Thứ Ba, 07/07
- [ ] Refactor `Seat`: thêm theaterId, row, column (xóa boolean booked)
- [ ] Tạo `SeatRepository` (đọc/ghi file seats.csv)
- [ ] Tạo method khởi tạo ghế khi thêm Theater mới
- [ ] Viết test cho SeatRepository và TheaterRepository

### Ngày 3 – Thứ Tư, 08/07
- [ ] Refactor `Booking`: thay movieId → showtimeId
- [ ] Cập nhật `BookingRepository` (định dạng file mới)
- [ ] Sửa test cũ cho phù hợp

### Ngày 4 – Thứ Năm, 09/07
- [ ] Cập nhật `Validator`: validateSeatInTheater(), validateShowtime()
- [ ] Cập nhật `BookingService.bookSeat()`: nhận showtimeId thay vì movieId
- [ ] Thêm method `getAvailableSeats(showtimeId)`

### Ngày 5 – Thứ Sáu, 10/07
- [ ] Cập nhật Menu: thêm quản lý Theater, Showtime (CRUD)
- [ ] Cập nhật luồng đặt vé theo suất chiếu

### Ngày 6 – Thứ Bảy, 11/07
- [ ] Viết integration test cho toàn bộ luồng mới
- [ ] Coverage check
- [ ] Sửa lỗi nếu có

### Ngày 7 – Chủ Nhật, 12/07
- [ ] Review Clean Code
- [ ] Cập nhật README.md
- [ ] Vẽ sơ đồ quan hệ các entity
- [ ] Chuẩn bị cho Tuần 4 (Threading)

# Kế hoạch Tuần 4 (13/07 – 19/07): Xử lý đa luồng

## Mục tiêu
Đảm bảo hệ thống đặt vé an toàn khi nhiều người dùng cùng đặt cùng một ghế/suất chiếu.

## Danh sách công việc chi tiết

### Ngày 1 – Thứ Hai, 13/07
- [ ] Thêm `synchronized` vào `BookingService.bookSeat()` (dùng lock object theo showtimeId).
- [ ] Viết test đơn luồng để đảm bảo không break.

### Ngày 2 – Thứ Ba, 14/07
- [ ] Tạo `BookingSimulation` class để mô phỏng nhiều thread.
- [ ] Viết test với `ExecutorService` và 10-100 thread.

### Ngày 3 – Thứ Tư, 15/07
- [ ] Viết test đa luồng dùng `CountDownLatch` để đồng bộ các thread.
- [ ] Kiểm tra tính nhất quán: chỉ 1 thread đặt được cho mỗi ghế.

### Ngày 4 – Thứ Năm, 16/07
- [ ] Tối ưu hiệu suất: batch save thay vì ghi file mỗi lần.
- [ ] Sử dụng `ConcurrentHashMap` hoặc `CopyOnWriteArrayList` để quản lý booking tạm.

### Ngày 5 – Thứ Sáu, 17/07
- [ ] Viết stress test với số lượng lớn user (100+).
- [ ] Kiểm tra lỗi và log.

### Ngày 6 – Thứ Bảy, 18/07
- [ ] Tích hợp mô phỏng threading vào Menu chính.
- [ ] Hiển thị kết quả đặt vé (số thành công, thất bại).

### Ngày 7 – Chủ Nhật, 19/07
- [ ] Tổng kết, review Clean Code, cập nhật README.
- [ ] Vẽ flowchart hoàn chỉnh với threading.
- [ ] Commit và push.