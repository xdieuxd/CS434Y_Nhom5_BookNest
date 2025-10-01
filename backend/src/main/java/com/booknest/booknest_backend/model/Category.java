package main.java.com.booknest.booknest_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "the_loai")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_the_loai")
    private Long id;

    @Column(name = "ten_the_loai")
    private String name;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
