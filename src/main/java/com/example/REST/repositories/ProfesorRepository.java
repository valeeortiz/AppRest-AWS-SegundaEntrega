package com.example.REST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.REST.models.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer>{
    
}
