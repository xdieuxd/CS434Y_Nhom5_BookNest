package com.booknest.booknest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "thu_vien", uniqueConstraints = @UniqueConstraint(columnNames = {"ma_nguoi_dung", "ma_sach"}))
public class ThuVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_thu_vien")
    private Integer maThuVien;

    @Column(name = "ma_nguoi_dung")
    private Integer maNguoiDung;

    @Column(name = "ma_sach")
    private Integer maSach;

    public ThuVien() {}

    public ThuVien(Integer maNguoiDung, Integer maSach) {
        this.maNguoiDung = maNguoiDung;
        this.maSach = maSach;
    }

    public Integer getMaThuVien() { return maThuVien; }
    public void setMaThuVien(Integer maThuVien) { this.maThuVien = maThuVien; }

    public Integer getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(Integer maNguoiDung) { this.maNguoiDung = maNguoiDung; }

    public Integer getMaSach() { return maSach; }
    public void setMaSach(Integer maSach) { this.maSach = maSach; }
}
