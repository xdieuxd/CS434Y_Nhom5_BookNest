CREATE DATABASE CSDL_BookNest_PL
USE CSDL_BookNest_PL;
-- 1. Vai tro nguoi dung
CREATE TABLE vai_tro (
    ma_vai_tro INT PRIMARY KEY AUTO_INCREMENT,
    ten_vai_tro ENUM('thành viên', 'nhân viên', 'admin') NOT NULL
);
-- 2. Nguoi dung
CREATE TABLE nguoi_dung (
    ma_nguoi_dung INT PRIMARY KEY AUTO_INCREMENT,
    email NVARCHAR(100) UNIQUE NOT NULL,
    mat_khau VARCHAR(255) NOT NULL, 
    ho_ten NVARCHAR(100),
    sdt NVARCHAR(20),
    dia_chi NVARCHAR(200),
    ma_vai_tro INT,
    FOREIGN KEY (ma_vai_tro) REFERENCES vai_tro(ma_vai_tro) ON DELETE SET NULL
);
-- 3. The loai sach
CREATE TABLE the_loai (
    ma_the_loai INT PRIMARY KEY AUTO_INCREMENT,
    ten_the_loai NVARCHAR(100) UNIQUE NOT NULL
);
-- 4. Tac gia
CREATE TABLE tac_gia (
    ma_tac_gia INT PRIMARY KEY AUTO_INCREMENT,
    ten_tac_gia NVARCHAR(100) NOT NULL
);
-- 5. Sach
CREATE TABLE sach (
    ma_sach INT PRIMARY KEY AUTO_INCREMENT,
    ten_sach NVARCHAR(200) NOT NULL,
    mo_ta TEXT,
    gia DECIMAL(10,2) NOT NULL,
    hinh_anh NVARCHAR(200),
    ten_file_pdf NVARCHAR(200),
    so_luong_ton INT NOT NULL DEFAULT 0,
    ngay_phat_hanh DATE,
    mien_phi BIT DEFAULT 0,
    ma_tac_gia INT,
    FOREIGN KEY (ma_tac_gia) REFERENCES tac_gia(ma_tac_gia) ON DELETE SET NULL
);
-- 6. Sach_TheLoai (bang trung gian nhieu-nhieu)
CREATE TABLE sach_the_loai (
    ma_sach INT,
    ma_the_loai INT,
    PRIMARY KEY (ma_sach, ma_the_loai),
    FOREIGN KEY (ma_sach) REFERENCES sach(ma_sach) ON DELETE CASCADE,
    FOREIGN KEY (ma_the_loai) REFERENCES the_loai(ma_the_loai) ON DELETE CASCADE
);
-- 7. Binh luan
CREATE TABLE binh_luan (
    ma_binh_luan INT PRIMARY KEY AUTO_INCREMENT,
    ma_sach INT,
    ma_nguoi_dung INT,
    noi_dung TEXT,
    ngay_binh_luan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ma_sach) REFERENCES sach(ma_sach) ON DELETE CASCADE,
    FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung) ON DELETE SET NULL
);
-- 8. Thu vien ca nhan
CREATE TABLE thu_vien_ca_nhan (
    ma_nguoi_dung INT,
    ma_sach INT,
    yeu_thich BIT DEFAULT 0,
    PRIMARY KEY (ma_nguoi_dung, ma_sach),
    FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung) ON DELETE CASCADE,
    FOREIGN KEY (ma_sach) REFERENCES sach(ma_sach) ON DELETE CASCADE
);
-- 9. Don hang
CREATE TABLE don_hang (
    ma_don_hang INT PRIMARY KEY AUTO_INCREMENT,
    ma_nguoi_dung INT NULL, -- NULL = khach vang lai
    tong_tien DECIMAL(10,2) NOT NULL,
    ngay_dat TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    han_huy TIMESTAMP, -- han huy trong 4 tieng
    trang_thai ENUM('chờ xác nhận', 'đang xử lý', 'đã đóng gói', 'đã gửi', 'đã giao', 'đã hủy') NOT NULL DEFAULT 'chờ xác nhận',
    hinh_thuc_thanh_toan ENUM('sms_banking', 'tiền mặt') NOT NULL,
    trang_thai_thanh_toan ENUM('chờ xác nhận', 'đã xác nhận', 'thất bại') NOT NULL DEFAULT 'chờ xác nhận',
    ngay_gui DATETIME,
    ma_nhan_vien INT NULL,
    FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung) ON DELETE SET NULL,
    FOREIGN KEY (ma_nhan_vien) REFERENCES nguoi_dung(ma_nguoi_dung) ON DELETE SET NULL
);
-- 10. Chi tiet don hang
CREATE TABLE chi_tiet_don_hang (
    ma_don_hang INT,
    ma_sach INT,
    so_luong INT NOT NULL,
    la_sach_dien_tu BOOLEAN NOT NULL, -- TRUE = sach online
    PRIMARY KEY (ma_don_hang, ma_sach),
    FOREIGN KEY (ma_don_hang) REFERENCES don_hang(ma_don_hang) ON DELETE CASCADE,
    FOREIGN KEY (ma_sach) REFERENCES sach(ma_sach) ON DELETE CASCADE
);
-- 11. Danh gia
CREATE TABLE danh_gia (
    ma_danh_gia INT PRIMARY KEY AUTO_INCREMENT,
    ma_don_hang INT NOT NULL,
    ma_nguoi_dung INT NULL,
    so_sao TINYINT CHECK (so_sao BETWEEN 1 AND 5),
    noi_dung TEXT,
    ngay_danh_gia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ma_don_hang) REFERENCES don_hang(ma_don_hang) ON DELETE CASCADE,
    FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung) ON DELETE SET NULL
);
-- 12. Lich su tim kiem
CREATE TABLE lich_su_tim_kiem (
    ma_tim_kiem INT PRIMARY KEY AUTO_INCREMENT,
    ma_nguoi_dung INT NULL,
    tu_khoa NVARCHAR(100) NOT NULL,
    ngay_tim TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung) ON DELETE SET NULL
);
-- 13. Doc sach online
CREATE TABLE doc_sach_online (
    ma_nguoi_dung INT,
    ma_sach INT,
    trang_danh_dau INT DEFAULT 1,
    lan_doc_cuoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ma_nguoi_dung, ma_sach),
    FOREIGN KEY (ma_nguoi_dung) REFERENCES nguoi_dung(ma_nguoi_dung) ON DELETE CASCADE,
    FOREIGN KEY (ma_sach) REFERENCES sach(ma_sach) ON DELETE CASCADE
);