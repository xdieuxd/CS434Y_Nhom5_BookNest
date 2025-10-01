package com.booknest.booknest_backend.repository;

import com.booknest.booknest_backend.model.ThuVien;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ThuVienRepository extends JpaRepository<ThuVien, Integer> {
    List<ThuVien> findByMaNguoiDung(Integer maNguoiDung);
    boolean existsByMaNguoiDungAndMaSach(Integer maNguoiDung, Integer maSach);
    void deleteByMaNguoiDungAndMaSach(Integer maNguoiDung, Integer maSach);
}
