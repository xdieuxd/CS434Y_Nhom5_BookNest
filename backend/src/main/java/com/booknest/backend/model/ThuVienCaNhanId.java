package com.booknest.booknest_backend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class ThuVienCaNhanId implements Serializable {
    @Column(name = "ma_nguoi_dung")
    private Integer maNguoiDung;

    @Column(name = "ma_sach")
    private Integer maSach;

    public ThuVienCaNhanId() {}
    public ThuVienCaNhanId(Integer maNguoiDung, Integer maSach) {
        this.maNguoiDung = maNguoiDung;
        this.maSach = maSach;
    }

    public Integer getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(Integer maNguoiDung) { this.maNguoiDung = maNguoiDung; }
    public Integer getMaSach() { return maSach; }
    public void setMaSach(Integer maSach) { this.maSach = maSach; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThuVienCaNhanId)) return false;
        ThuVienCaNhanId that = (ThuVienCaNhanId) o;
        return maNguoiDung.equals(that.maNguoiDung) && maSach.equals(that.maSach);
    }

    @Override
    public int hashCode() {
        return maNguoiDung.hashCode() + maSach.hashCode();
    }
}
