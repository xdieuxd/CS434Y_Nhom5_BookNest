package main.java.com.booknest.booknest_backend.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class SachDTO {
    private String tenSach;
    private String moTa;
    private BigDecimal gia;
    private String hinhAnh;
    private String tenFilePdf;
    private Integer soLuongTon;
    private Date ngayPhatHanh;
    private Boolean mienPhi;
    private Integer maTacGia;
    private List<Integer> maTheLoai;

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

    public List<Integer> getMaTheLoai() { return maTheLoai; }
    public void setMaTheLoai(List<Integer> maTheLoai) { this.maTheLoai = maTheLoai; }
}
