package com.example.REST.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="sesiones-alumnos")
public class SesionesAlumnos {
    private String id;
    private Long fecha;
    private Integer alumnoId;
    private Boolean active;
    private String sessionString;

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName="fecha")
    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    @DynamoDBAttribute(attributeName="alumnoId")
    public Integer getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Integer alumnoId) {
        this.alumnoId = alumnoId;
    }

    @DynamoDBAttribute(attributeName="active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSessionString() {
        return sessionString;
    }

    @DynamoDBAttribute(attributeName="sessionString")
    public void setSessionString(String sessionString) {
        this.sessionString = sessionString;
    }

}
