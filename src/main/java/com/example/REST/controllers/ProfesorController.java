package com.example.REST.controllers;

import com.example.REST.models.Profesor;
import com.example.REST.repositories.ProfesorRepository;
import com.example.REST.services.ProfesorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {
    private final ProfesorService profesorService;

    @Autowired
    ProfesorRepository profesorRepository;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @GetMapping
    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable int id) {
        return profesorRepository.findById(id).map(profesor -> new ResponseEntity<>(profesor, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Profesor> addProfesor(@RequestBody Profesor profesor) {
        Profesor newProfesor = profesorRepository.saveAndFlush(profesor);
        return new ResponseEntity<>(newProfesor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> updateProfesor(@PathVariable int id, @RequestBody Profesor profesor) {
        profesor.setId(id);
        profesorRepository.saveAndFlush(profesor);
        return new ResponseEntity<>(profesor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable int id) {

        if (profesorRepository.existsById(id)) {
            profesorRepository.deleteById(id);
            profesorRepository.flush();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


