package com.example.REST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.REST.models.Alumno;


public interface AlumnoRepository extends JpaRepository<Alumno,Integer> {

    
}
