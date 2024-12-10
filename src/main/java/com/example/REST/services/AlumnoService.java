package com.example.REST.services;

import com.example.REST.models.Alumno;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    private List<Alumno> alumnos = new ArrayList<>();

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public Optional<Alumno> getAlumnoById(int id) {
        return alumnos.stream().filter(a -> a.getId() == id).findFirst();
    }

    public Alumno addAlumno(Alumno alumno) {
        alumnos.add(alumno);
        return alumno;
    }

    public Optional<Alumno> updateAlumno(int id, Alumno alumno) {
        return getAlumnoById(id).map(a -> {
            a.setNombres(alumno.getNombres());
            a.setApellidos(alumno.getApellidos());
            a.setMatricula(alumno.getMatricula());
            a.setPromedio(alumno.getPromedio());
            a.setFotoPerfilUrl(alumno.getFotoPerfilUrl());
            a.setPassword(alumno.getPaswword());
            return a;
        });
    }

    public boolean deleteAlumno(int id) {
        return alumnos.removeIf(a -> a.getId() == id);
    }
}