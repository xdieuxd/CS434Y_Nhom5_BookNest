package main.java.com.booknest.booknest_backend.repository;

import com.booknest.booknest_backend.model.SachTheLoai;
import com.booknest.booknest_backend.model.SachTheLoaiId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SachTheLoaiRepository extends JpaRepository<SachTheLoai, SachTheLoaiId> {
    List<SachTheLoai> findById_MaSach(Integer maSach);
    List<SachTheLoai> findById_MaTheLoai(Integer maTheLoai);
}
