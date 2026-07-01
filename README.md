# Hệ thống đặt vé rạp chiếu phim – Tiến độ Tuần 2

## ✅ Đã hoàn thành trong Tuần 2 (29/06 – 05/07)

### 1. Xây dựng Custom Exception
- **BookingAppException**: lớp cha cho tất cả exception.
- **InvalidInputException**: ném khi dữ liệu đầu vào không hợp lệ.
- **SeatUnavailableException**: ném khi ghế đã được đặt.

### 2. Xây dựng Validator
- **Validator.validateCustomerName()**: kiểm tra tên không được rỗng/null.
- **Validator.validateSeatId()**: kiểm tra số ghế hợp lệ (1 <= seatId <= maxSeats).

### 3. Tích hợp vào BookingService
- `bookSeat()` trả về `Booking` object và ném exception thay vì return false.
- Tất cả logic kiểm tra dữ liệu được chuyển sang Validator.

### 4. Tối ưu Repository
- `BookingRepository.findByMovieId()`: lọc booking theo phim.
- `BookingRepository.getBookedSeats()`: lấy danh sách ghế đã đặt.
- `MovieRepository.findByName()`: tìm phim theo tên (gần đúng).

### 5. Unit Test & Integration Test
- Unit test cho Validator, Service, Repository.
- Integration test cho toàn bộ luồng đặt vé.
- Độ phủ coverage > 70%.

### 6. Cập nhật Menu
- Menu chính có các chức năng: Xem phim, Đặt vé, Xem ghế đã đặt.
- Xử lý lỗi nhập liệu và exception trong UI.

---

## 📊 Kết quả đạt được
- Code được viết theo hướng Clean Code, có phân tầng rõ ràng.
- Tất cả test đều pass.
- Sẵn sàng cho Tuần 3: Quản lý Theater và Showtime.