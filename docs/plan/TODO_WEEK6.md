# Kế hoạch Tuần 6 (27/07 – 02/08) – Tích hợp & Hoàn thiện

## Mục tiêu
- Tích hợp tất cả tính năng vào menu chính.
- Viết integration test toàn bộ.
- Đóng gói thành file JAR executable.
- Viết tài liệu hướng dẫn sử dụng.
- Chuẩn bị demo cuối cùng.

## Công việc chi tiết

### Thứ Hai, 27/07 – Tích hợp Menu
- [ ] Thêm menu "Hiển thị sơ đồ ghế"
- [ ] Thêm menu "Xem lịch sử đặt vé của khách"
- [ ] Thêm menu "Thống kê doanh thu"
- [ ] Tích hợp tất cả menu con vào Main.

### Thứ Ba, 28/07 – Integration Tests
- [ ] Viết `CinemaIntegrationTest` kiểm tra toàn bộ luồng.
- [ ] Kiểm tra đặt vé, xem ghế, thống kê.
- [ ] Đảm bảo test xanh.

### Thứ Tư, 29/07 – Tối ưu hiệu năng
- [ ] Đo thời gian đặt vé với 50 user.
- [ ] Tối ưu batch save (nếu cần).
- [ ] Sửa lỗi UI nhỏ.

### Thứ Năm, 30/07 – Đóng gói JAR
- [ ] Cấu hình Maven/Gradle để tạo JAR executable.
- [ ] Chạy `java -jar cinema-booking.jar` và kiểm tra.
- [ ] Tạo script run.sh / run.bat.

### Thứ Sáu, 31/07 – Viết tài liệu
- [ ] User Guide: hướng dẫn từng bước.
- [ ] Developer Guide: cấu trúc và cách mở rộng.
- [ ] Cập nhật README.md.

### Thứ Bảy, 01/08 – End-to-End Test
- [ ] Chạy thử toàn bộ tình huống thực tế.
- [ ] Mô phỏng 100 users.
- [ ] Sửa lỗi lần cuối.

### Chủ Nhật, 02/08 – Chuẩn bị Demo
- [ ] Tạo release v1.0.0 trên GitHub.
- [ ] Upload JAR và tài liệu.
- [ ] Chuẩn bị slide demo.
- [ ] Demo thử và điều chỉnh.