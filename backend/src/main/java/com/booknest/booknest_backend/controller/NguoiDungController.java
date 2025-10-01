package main.java.com.booknest.booknest_backend.controller;

import com.booknest.booknest_backend.model.NguoiDung;
import com.booknest.booknest_backend.repository.NguoiDungRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/nguoi-dung")
public class NguoiDungController {

    private final NguoiDungRepository nguoiDungRepository;

    public NguoiDungController(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @GetMapping
    public List<NguoiDung> getAll() {
        return nguoiDungRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NguoiDung> getById(@PathVariable Integer id) {
        return nguoiDungRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/thanh-vien")
public List<NguoiDung> getThanhVien() {
    return nguoiDungRepository.findByMaVaiTro(1); 
}
    @PostMapping
    public ResponseEntity<NguoiDung> create(@RequestBody NguoiDung n) {
        n.setMaNguoiDung(null); 
        if (n.getMaVaiTro() == null) {
            n.setMaVaiTro(1); 
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nguoiDungRepository.save(n));
    }

    @PutMapping("/{id}")
public ResponseEntity<NguoiDung> update(@PathVariable Integer id, @RequestBody NguoiDung n) {
    Optional<NguoiDung> existingOpt = nguoiDungRepository.findById(id);
    if (existingOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    NguoiDung existing = existingOpt.get();
    existing.setHoTen(n.getHoTen());
    existing.setEmail(n.getEmail());
    existing.setSdt(n.getSdt());
    existing.setDiaChi(n.getDiaChi());
    existing.setMaVaiTro(n.getMaVaiTro()); 

    return ResponseEntity.ok(nguoiDungRepository.save(existing));
}

    @PutMapping("/{id}/doi-mat-khau")
public ResponseEntity<?> doiMatKhau(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    Optional<NguoiDung> opt = nguoiDungRepository.findById(id);
    if (opt.isEmpty()) return ResponseEntity.notFound().build();

    NguoiDung nd = opt.get();
    String cu = body.get("matKhauCu");
    String moi = body.get("matKhauMoi");

    if (!nd.getMatKhau().equals(cu)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu cũ không đúng!");
    }

    nd.setMatKhau(moi);
    nguoiDungRepository.save(nd);
    return ResponseEntity.ok("Đổi mật khẩu thành công!");
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!nguoiDungRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        nguoiDungRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String matKhau = request.get("matKhau");

        Optional<NguoiDung> userOpt = nguoiDungRepository.findByEmailAndMatKhau(email, matKhau);
        if (userOpt.isPresent()) {
            NguoiDung user = userOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("maNguoiDung", user.getMaNguoiDung());
            response.put("email", user.getEmail());
            response.put("maVaiTro", user.getMaVaiTro());
            response.put("hoTen", user.getHoTen());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Email hoặc mật khẩu không đúng"));
        }
    }
}
