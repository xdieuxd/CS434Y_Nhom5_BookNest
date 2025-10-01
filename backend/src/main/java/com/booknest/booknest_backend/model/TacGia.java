package main.java.com.booknest.booknest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tac_gia")
public class TacGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_tac_gia")
    private Integer maTacGia;

    @Column(name = "ten_tac_gia")
    private String tenTacGia;

    public Integer getMaTacGia() { return maTacGia; }
    public void setMaTacGia(Integer maTacGia) { this.maTacGia = maTacGia; }
    public String getTenTacGia() { return tenTacGia; }
    public void setTenTacGia(String tenTacGia) { this.tenTacGia = tenTacGia; }
}
