# BookNest – Xây dựng Website Bán và Đọc Sách Trực Tuyến

## Giới thiệu
**BookNest** là hệ thống bán và đọc sách trực tuyến, cho phép:
- Người dùng tìm kiếm, xem chi tiết và mua sách (giấy/PDF).
- Thanh toán trực tuyến hoặc khi nhận hàng (COD).
- Lưu trữ và đọc sách PDF trong thư viện cá nhân.
- Quản lý giỏ hàng, đơn hàng, đánh giá và bình luận.
- Nhân viên và quản trị viên có thể quản lý danh mục sách, đơn hàng, thống kê doanh thu.

Mục tiêu: cung cấp trải nghiệm đọc sách thuận tiện mọi lúc, mọi nơi và hỗ trợ kinh doanh hiệu quả.

## Công nghệ sử dụng
- **Backend:** Spring Boot (Java, RESTful API, xử lý nghiệp vụ)
- **Frontend:** HTML5, CSS3, JavaScript
- **Database:** MySQL
- **Khác:** Maven, Bootstrap (CSS), Git/GitHub

## Vai trò hệ thống
- **Khách vãng lai:** Đăng ký, đăng nhập, xem sách, đọc sách miễn phí, tìm kiếm.  
- **Thành viên:** Quản lý tài khoản, giỏ hàng, thanh toán, thư viện cá nhân, đánh giá, bình luận.  
- **Nhân viên:** Quản lý đơn hàng, danh mục sách, tạo đơn hàng trực tiếp.  
- **Admin:** Quản lý người dùng, danh mục sách, đơn hàng, thống kê.

##  Hướng dẫn cài đặt và chạy

### 1. Yêu cầu môi trường
- JDK 21+
- MySQL 8.x
- Maven 3.9+
- Git

### 2. Clone dự án
```bash
git clone https://github.com/xdieuxd/CS434Y_Nhom5_BookNest.git
cd CS434Y_Nhom5_BookNest
