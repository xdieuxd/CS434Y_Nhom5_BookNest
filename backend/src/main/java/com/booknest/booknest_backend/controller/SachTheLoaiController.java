package main.java.com.booknest.booknest_backend.controller;

import com.booknest.booknest_backend.model.SachTheLoai;
import com.booknest.booknest_backend.model.SachTheLoaiId;
import com.booknest.booknest_backend.repository.SachTheLoaiRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sach-the-loai")   

public class SachTheLoaiController {
    private final SachTheLoaiRepository repo;

    public SachTheLoaiController(SachTheLoaiRepository repo) {
        this.repo = repo;
    }

    @GetMapping
public List<SachTheLoai> getByMaSach(@RequestParam("maSach") Integer maSach) {
    return repo.findById_MaSach(maSach);
}
@GetMapping("/theloai")
public List<SachTheLoai> getByMaTheLoai(@RequestParam("maTheLoai") Integer maTheLoai) {
    return repo.findById_MaTheLoai(maTheLoai);
}


    @PostMapping
    public SachTheLoai create(@RequestBody SachTheLoai stl) {
        return repo.save(stl);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer maSach, @RequestParam Integer maTheLoai) {
        repo.deleteById(new SachTheLoaiId(maSach, maTheLoai));
    }
}
