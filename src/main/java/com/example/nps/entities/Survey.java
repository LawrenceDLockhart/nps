package com.example.nps.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "survey")
    private List<Question> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
