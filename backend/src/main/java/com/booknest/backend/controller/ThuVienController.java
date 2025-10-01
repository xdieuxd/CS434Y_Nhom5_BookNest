package com.booknest.booknest_backend.controller;

import com.booknest.booknest_backend.model.ThuVien;
import com.booknest.booknest_backend.repository.ThuVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thu-vien")
@CrossOrigin
public class ThuVienController {

    @Autowired
    private ThuVienRepository thuVienRepository;

    @PostMapping
public ResponseEntity<?> themVaoThuVien(@RequestBody ThuVien tv) {
    try {
        if (thuVienRepository.existsByMaNguoiDungAndMaSach(tv.getMaNguoiDung(), tv.getMaSach())) {
            return ResponseEntity.badRequest().body("Đã có trong thư viện");
        }
        return ResponseEntity.ok(thuVienRepository.save(tv));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
    }
}


    @GetMapping("/{maNguoiDung}")
    public List<ThuVien> layThuVienNguoiDung(@PathVariable Integer maNguoiDung) {
        return thuVienRepository.findByMaNguoiDung(maNguoiDung);
    }

    @DeleteMapping("/{maNguoiDung}/{maSach}")
public ResponseEntity<?> xoaKhoiThuVien(@PathVariable int maNguoiDung, @PathVariable int maSach) {
    if (thuVienRepository.existsByMaNguoiDungAndMaSach(maNguoiDung, maSach)) {
        thuVienRepository.deleteByMaNguoiDungAndMaSach(maNguoiDung, maSach);
        return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
}
}
