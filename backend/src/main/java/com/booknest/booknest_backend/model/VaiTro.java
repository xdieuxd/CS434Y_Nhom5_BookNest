package main.java.com.booknest.booknest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vai_tro")
public class VaiTro {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_vai_tro")
    private Integer maVaiTro;

    @Column(name = "ten_vai_tro")
    private String tenVaiTro;

    public Integer getMaVaiTro() { return maVaiTro; }
    public void setMaVaiTro(Integer maVaiTro) { this.maVaiTro = maVaiTro; }
    public String getTenVaiTro() { return tenVaiTro; }
    public void setTenVaiTro(String tenVaiTro) { this.tenVaiTro = tenVaiTro; }
}
