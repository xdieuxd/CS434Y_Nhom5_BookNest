package com.booknest.booknest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "thu_vien_ca_nhan")
public class ThuVienCaNhan {
    @EmbeddedId
    private ThuVienCaNhanId id;

    @Column(name = "yeu_thich")
    private Boolean yeuThich;

    public ThuVienCaNhan() {}
    public ThuVienCaNhan(ThuVienCaNhanId id, Boolean yeuThich) {
        this.id = id;
        this.yeuThich = yeuThich;
    }

    public ThuVienCaNhanId getId() { return id; }
    public void setId(ThuVienCaNhanId id) { this.id = id; }

    public Boolean getYeuThich() { return yeuThich; }
    public void setYeuThich(Boolean yeuThich) { this.yeuThich = yeuThich; }
}
