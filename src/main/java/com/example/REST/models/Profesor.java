package com.example.REST.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Profesor {
    @Id
    @GeneratedValue 
    private int id;
    private int numeroEmpleado;
    private String nombres;
    private String apellidos;
    private Integer horasClase;

    public int getId() { 
        return id;
    }
    public void setId(int id) { 
        if(id == 0 || id < 0){
            throw new IllegalArgumentException("El id no puede ser un número negativo");
        }
        this.id = id;
    }
    public int getNumeroEmpleado() { 
        return numeroEmpleado; 
    }
    public void setNumeroEmpleado(int numeroEmpleado) { 
        if(numeroEmpleado < 0){
            throw new IllegalArgumentException("El número del empleado no puede ser negativo");
        }
        this.numeroEmpleado = numeroEmpleado; 
    }
    public String getNombres() { 
        return nombres; 
    }
    public void setNombres(String nombres) { 
        if(nombres == null){
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombres = nombres; 
    }
    public String getApellidos() { 
        return apellidos; 
    }
    public void setApellidos(String apellidos) {
        if(apellidos == null){
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        } 
        this.apellidos = apellidos; 
    }
    public Integer getHorasClase() { 
        return horasClase; 
    }
    public void setHorasClase(Integer horasClase) { 
        if(horasClase < 0){
            throw new IllegalArgumentException("Las horas establecidas no cumple con las horas laborales");
        }
        this.horasClase = horasClase; 
    }
}