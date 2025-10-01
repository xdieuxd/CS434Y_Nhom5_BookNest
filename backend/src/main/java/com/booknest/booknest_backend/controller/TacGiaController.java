package main.java.com.booknest.booknest_backend.controller;

import com.booknest.booknest_backend.model.TacGia;
import com.booknest.booknest_backend.repository.TacGiaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/tac-gia")
@CrossOrigin
public class TacGiaController {
    private final TacGiaRepository tacGiaRepository;

    public TacGiaController(TacGiaRepository tacGiaRepository) {
        this.tacGiaRepository = tacGiaRepository;
    }

    @GetMapping
    public List<TacGia> getAll() {
        return tacGiaRepository.findAll();
    }

    @GetMapping("/{id}")
    public TacGia getById(@PathVariable Integer id) {
        return tacGiaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public TacGia create(@RequestBody TacGia t) {
        return tacGiaRepository.save(t);
    }

    @PutMapping("/{id}")
    public TacGia update(@PathVariable Integer id, @RequestBody TacGia t) {
        t.setMaTacGia(id);
        return tacGiaRepository.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        tacGiaRepository.deleteById(id);
    }
}
