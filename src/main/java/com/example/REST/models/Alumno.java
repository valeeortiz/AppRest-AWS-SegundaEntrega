package com.example.REST.models;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Alumno {
    @Id
    @GeneratedValue 
    private int id;
    private String nombres;
    private String apellidos;
    private String matricula;
    private Double promedio;
    private String fotoPerfilUrl;
    private String password;

    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        if(id == 0 || id < 0){
            throw new IllegalArgumentException("El id no puede ser negativo");

        }
        this.id = id; 
    }
    public String getNombres() { 
        return nombres; 
    }
    public void setNombres(String nombres) {
        if (nombres == null) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        } 
        this.nombres = nombres; 
    }
    public String getApellidos() { 
        return apellidos; 
    }
    public void setApellidos(String apellidos) { 
        if (apellidos == null) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        this.apellidos = apellidos; 
    }
    public String getMatricula() { 
        return matricula; 
    }
    public void setMatricula(String matricula) { 
        if (matricula == null) {
            throw new IllegalArgumentException("La matrícula no puede estar vacía");
        }
        this.matricula = matricula; 
    }
    public Double getPromedio() { 
        return promedio; 
    }
    public void setPromedio(Double promedio) { 
        if (promedio == null) {
            throw new IllegalArgumentException("El promedio no puede estar vacío");
        }else{
            if(promedio < 0 || promedio > 10){
                throw new IllegalArgumentException("El promedio debe estar entre 0 y 10");
            }
        }
        this.promedio = promedio; 
    }

    public String getFotoPerfilUrl(){
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl){
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
    public String getPaswword() {
        return password;
    }
    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        this.password = password;
    }

}