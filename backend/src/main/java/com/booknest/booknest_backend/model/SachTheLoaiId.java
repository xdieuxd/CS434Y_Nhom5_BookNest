package main.java.com.booknest.booknest_backend.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Embeddable
public class SachTheLoaiId implements Serializable{
    @Column(name = "ma_sach")
    private Integer maSach;

    @Column(name = "ma_the_loai")
    private Integer maTheLoai;

    public SachTheLoaiId() {}
    public SachTheLoaiId(Integer maSach, Integer maTheLoai) {
        this.maSach = maSach;
        this.maTheLoai = maTheLoai;
    }

    public Integer getMaSach() { return maSach; }
    public void setMaSach(Integer maSach) { this.maSach = maSach; }
    public Integer getMaTheLoai() { return maTheLoai; }
    public void setMaTheLoai(Integer maTheLoai) { this.maTheLoai = maTheLoai; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SachTheLoaiId)) return false;
        SachTheLoaiId that = (SachTheLoaiId) o;
        return maSach.equals(that.maSach) && maTheLoai.equals(that.maTheLoai);
    }

    @Override
    public int hashCode() {
        return maSach.hashCode() + maTheLoai.hashCode();
    }
}
