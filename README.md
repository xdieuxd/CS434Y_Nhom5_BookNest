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
- **Khác:** Maven, Bootstrap (CSS), Git/GitHub, Trello để quản lý công việc

## Chức năng chính
### Người dùng:
- Đăng ký, đăng nhập, quản lý tài khoản cá nhân.
- Tìm kiếm và lọc sách theo thể loại, tác giả, giá.
- Quản lý giỏ hàng, đặt mua sách.
- Thanh toán (COD hoặc online mô phỏng).
- Quản lý thư viện cá nhân, đọc sách PDF trực tuyến.
- Đánh giá và bình luận sách.

### Nhân viên:
- Quản lý đơn đặt hàng (cập nhật trạng thái, xử lý giao hàng).
- Quản lý danh mục sách (thêm, sửa, xóa, cập nhật kho).
- Tạo đơn hàng trực tiếp cho khách.
- Quản lý tài khoản khách hàng.

### Quản trị viên:
- Quản lý tất cả tài khoản người dùng.
- Quản lý sách và đơn hàng
- Thống kê doanh thu, số lượng người dùng, sách bán chạy


## Cơ sở dữ liệu
### Một số bảng chính trong hệ thống:
- nguoi_dung: lưu thông tin người dùng, vai trò.
- sach, the_loai, tac_gia: lưu kho sách và thông tin liên quan.
- gio_hang, chi_tiet_gio_hang: quản lý giỏ hàng.
- don_hang, chi_tiet_don_hang: lưu đơn hàng và chi tiết sách.
- thu_vien: lưu sách đã mua của người dùng.
- binh_luan, danh_gia: lưu phản hồi và đánh giá của người dùng

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
