package main.java.com.booknest.booknest_backend.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/uploads/pdf")
@CrossOrigin
public class PdfController {

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getPdf(@PathVariable String fileName) throws IOException {
        Resource pdfFile = new ClassPathResource("uploads/pdf/" + fileName); 

        if (!pdfFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfFile);
    }
}
