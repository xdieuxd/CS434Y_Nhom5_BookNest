package main.java.com.booknest.booknest_backend.controller;

import com.booknest.booknest_backend.model.VaiTro;
import com.booknest.booknest_backend.repository.VaiTroRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vai-tro")
@CrossOrigin
public class VaiTroController {
    private final VaiTroRepository vaiTroRepository;

    public VaiTroController(VaiTroRepository vaiTroRepository) {
        this.vaiTroRepository = vaiTroRepository;
    }

    @GetMapping
    public List<VaiTro> getAll() {
        return vaiTroRepository.findAll();
    }

    @GetMapping("/{id}")
    public VaiTro getById(@PathVariable Integer id) {
        return vaiTroRepository.findById(id).orElse(null);
    }

    @PostMapping
    public VaiTro create(@RequestBody VaiTro v) {
        return vaiTroRepository.save(v);
    }

    @PutMapping("/{id}")
    public VaiTro update(@PathVariable Integer id, @RequestBody VaiTro v) {
        v.setMaVaiTro(id);
        return vaiTroRepository.save(v);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        vaiTroRepository.deleteById(id);
    }
}
