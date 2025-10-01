package main.java.com.booknest.booknest_backend.controller;

import com.booknest.booknest_backend.model.*;
import com.booknest.booknest_backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sach")
@CrossOrigin
public class SachController {

    private final SachRepository sachRepository;
    private final SachTheLoaiRepository sachTheLoaiRepository;

    @Autowired
    private TacGiaRepository tacGiaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public SachController(SachRepository sachRepository, SachTheLoaiRepository sachTheLoaiRepository) {
        this.sachRepository = sachRepository;
        this.sachTheLoaiRepository = sachTheLoaiRepository;
    }

    @GetMapping
    public List<Sach> getAll() {
        return sachRepository.findAll();
    }

    @GetMapping("/{id}")
    public Sach getById(@PathVariable Integer id) {
        return sachRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Sach> createSach(@RequestBody Sach sach,
                                           @RequestParam List<Integer> maTheLoai) {
        Sach savedSach = sachRepository.save(sach);

        for (Integer maTL : maTheLoai) {
            SachTheLoaiId id = new SachTheLoaiId(savedSach.getMaSach(), maTL);
            sachTheLoaiRepository.save(new SachTheLoai(id));
        }

        return ResponseEntity.ok(savedSach);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sach> updateSach(@PathVariable Integer id,
                                           @RequestBody Sach sach,
                                           @RequestParam List<Integer> maTheLoai) {
        sach.setMaSach(id);
        Sach updated = sachRepository.save(sach);

        // Xóa thể loại cũ
        sachTheLoaiRepository.findAll().stream()
                .filter(stl -> stl.getId().getMaSach().equals(id))
                .forEach(sachTheLoaiRepository::delete);

        // Thêm thể loại mới
        for (Integer maTL : maTheLoai) {
            SachTheLoaiId newId = new SachTheLoaiId(id, maTL);
            sachTheLoaiRepository.save(new SachTheLoai(newId));
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        sachRepository.deleteById(id);
    }

    @GetMapping("/dto")
    public List<SachResponseDTO> getAllSachDTO() {
        List<Sach> danhSach = sachRepository.findAll();
        List<SachResponseDTO> result = new ArrayList<>();

        for (Sach sach : danhSach) {
            // Lấy tên tác giả
            String tenTacGia = tacGiaRepository.findById(sach.getMaTacGia())
                    .map(TacGia::getTenTacGia)
                    .orElse("Không rõ");

            // Lấy danh sách tên thể loại
            List<String> tenTheLoai = new ArrayList<>();
            List<SachTheLoai> danhSachTheLoai = sachTheLoaiRepository.findById_MaSach(sach.getMaSach());

            for (SachTheLoai stl : danhSachTheLoai) {
                Long maTL = stl.getId().getMaTheLoai().longValue();
                categoryRepository.findById(maTL).ifPresentOrElse(
                        cat -> tenTheLoai.add(cat.getName()),
                        () -> tenTheLoai.add("Không rõ")
                );
            }

            result.add(convertToDTO(sach, tenTacGia, tenTheLoai));
        }

        return result;
    }

    private SachResponseDTO convertToDTO(Sach sach, String tenTacGia, List<String> tenTheLoai) {
        SachResponseDTO dto = new SachResponseDTO();
        dto.setMaSach(sach.getMaSach());
        dto.setTenSach(sach.getTenSach());
        dto.setMoTa(sach.getMoTa());
        dto.setGia(sach.getGia());
        dto.setHinhAnh(sach.getHinhAnh());
        dto.setMienPhi(sach.getMienPhi());
        dto.setNgayPhatHanh(sach.getNgayPhatHanh());
        dto.setTenTacGia(tenTacGia);
        dto.setTenTheLoai(tenTheLoai);
        dto.setSoLuongTon(sach.getSoLuongTon());
        return dto;
    }
}
