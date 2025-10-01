package main.java.com.booknest.booknest_backend.repository;

import main.java.com.booknest.booknest_backend.model.Category;
import main.java.org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

