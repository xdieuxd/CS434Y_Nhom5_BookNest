package main.java.com.booknest.booknest_backend.repository;

import com.booknest.booknest_backend.model.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    Optional<NguoiDung> findByEmailAndMatKhau(String email, String matKhau);
    List<NguoiDung> findByMaVaiTro(Integer maVaiTro);
}
