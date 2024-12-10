package com.example.REST.services;

import com.example.REST.models.Profesor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    private List<Profesor> profesores = new ArrayList<>();

    public List<Profesor> getAllProfesores() {
        return profesores;
    }

    public Optional<Profesor> getProfesorById(int id) {
        return profesores.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Profesor addProfesor(Profesor profesor) {
        profesores.add(profesor);
        return profesor;
    }

    public Optional<Profesor> updateProfesor(int id, Profesor profesor) {
        return getProfesorById(id).map(p -> {
            p.setNumeroEmpleado(profesor.getNumeroEmpleado());
            p.setNombres(profesor.getNombres());
            p.setApellidos(profesor.getApellidos());
            p.setHorasClase(profesor.getHorasClase());
            return p;
        });
    }

    public boolean deleteProfesor(int id) {
        return profesores.removeIf(p -> p.getId() == id);
    }
}