package com.booknest.booknest_backend.repository;

import com.booknest.booknest_backend.model.ThuVienCaNhan;
import com.booknest.booknest_backend.model.ThuVienCaNhanId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThuVienCaNhanRepository extends JpaRepository<ThuVienCaNhan, ThuVienCaNhanId> {

}
