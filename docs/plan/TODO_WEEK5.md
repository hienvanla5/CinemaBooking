# Kế hoạch Tuần 5 (20/07 – 26/07) – Design Pattern & Refactor

## Mục tiêu

Áp dụng các Design Pattern để cải thiện cấu trúc code:

- Singleton: Quản lý repository và service toàn cục.
- Factory: Tạo Booking với các loại khác nhau (VIP, Regular, Combo).
- Strategy: Tính giá vé theo giờ chiếu (giờ vàng, ngày thường, cuối tuần).

## Công việc chi tiết

### Thứ Hai, 20/07 – Singleton Pattern

- [ ] Tạo `AppContext` (Singleton) quản lý tất cả repository và service.
- [ ] Sửa các class để lấy instance từ AppContext thay vì `new`.
- [ ] Viết test cho Singleton.

### Thứ Ba, 21/07 – Factory Pattern (Booking)

- [ ] Tạo interface `BookingFactory`.
- [ ] Tạo `RegularBookingFactory`, `VIPBookingFactory`.
- [ ] Tích hợp vào `BookingService`.
- [ ] Viết test.

### Thứ Tư, 22/07 – Strategy Pattern (Pricing)

- [ ] Tạo interface `PricingStrategy`.
- [ ] Tạo `NormalPricingStrategy`, `GoldHourPricingStrategy`, `WeekendPricingStrategy`.
- [ ] Tích hợp vào `BookingService` (thêm field `price` vào Booking).
- [ ] Viết test.

### Thứ Năm, 23/07 – Refactor Service Layer (SRP)

- [ ] Tách `BookingService` thành: `BookingValidator`, `BookingManager`, `BookingPricingService`.
- [ ] Áp dụng Dependency Injection.
- [ ] Cập nhật test.

### Thứ Sáu, 24/07 – Clean Code toàn diện

- [ ] Kiểm tra magic numbers, tên biến, độ dài method.
- [ ] Thêm JavaDoc.
- [ ] Format code.

### Thứ Bảy, 25/07 – Logging

- [ ] Tạo `AppLogger` (Singleton) sử dụng `java.util.logging`.
- [ ] Thay `System.out.println` bằng Logger.
- [ ] Ghi log ra file `app.log`.

### Chủ Nhật, 26/07 – Tổng kết

- [ ] Vẽ Class Diagram UML.
- [ ] Cập nhật README.
- [ ] Chuẩn bị cho Tuần 6 (Tích hợp & Hoàn thiện).