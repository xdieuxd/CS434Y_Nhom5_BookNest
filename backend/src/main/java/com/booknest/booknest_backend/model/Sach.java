package main.java.com.booknest.booknest_backend.model;

import java.math.BigDecimal;

import jakarta.persistence.* ;
import java.sql.Date;

@Entity
@Table(name = "sach")
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_sach")
    private Integer maSach;

    @Column(name = "ten_sach")
    private String tenSach;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "gia")
    private BigDecimal gia;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "ten_file_pdf")
    private String tenFilePdf;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "ngay_phat_hanh")
    private Date ngayPhatHanh;

    @Column(name = "mien_phi")
    private Boolean mienPhi;

    @Column(name = "ma_tac_gia")
    private Integer maTacGia;

    public Integer getMaSach() { return maSach; }
    public void setMaSach(Integer maSach) { this.maSach = maSach; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }

    public String getTenFilePdf() { return tenFilePdf; }
    public void setTenFilePdf(String tenFilePdf) { this.tenFilePdf = tenFilePdf; }

    public Integer getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(Integer soLuongTon) { this.soLuongTon = soLuongTon; }

    public Date getNgayPhatHanh() { return ngayPhatHanh; }
    public void setNgayPhatHanh(Date ngayPhatHanh) { this.ngayPhatHanh = ngayPhatHanh; }

    public Boolean getMienPhi() { return mienPhi; }
    public void setMienPhi(Boolean mienPhi) { this.mienPhi = mienPhi; }

    public Integer getMaTacGia() { return maTacGia; }
    public void setMaTacGia(Integer maTacGia) { this.maTacGia = maTacGia; }
}
