package main.java.com.booknest.booknest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sach_the_loai")
public class SachTheLoai {
    @EmbeddedId
    private SachTheLoaiId id;

    public SachTheLoai() {}
    public SachTheLoai(SachTheLoaiId id) { this.id = id; }

    public SachTheLoaiId getId() { return id; }
    public void setId(SachTheLoaiId id) { this.id = id; }
}
