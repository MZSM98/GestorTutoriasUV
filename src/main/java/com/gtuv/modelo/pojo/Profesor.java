package com.gtuv.modelo.pojo;

public class Profesor {
    private int idCatalogoProfesor;
    private String nombreCompleto;

    public Profesor() {
    }

    public Profesor(int idCatalogoProfesor, String nombreCompleto) {
        this.idCatalogoProfesor = idCatalogoProfesor;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdCatalogoProfesor() { 
        return idCatalogoProfesor; 
    }
    
    public void setIdCatalogoProfesor(int idCatalogoProfesor) { 
        this.idCatalogoProfesor = idCatalogoProfesor; 
    }
    
    public String getNombreCompleto() {
        return nombreCompleto; 
    }
    
    public void setNombreCompleto(String nombreCompleto) { 
        this.nombreCompleto = nombreCompleto; 
    }
    
    @Override
    public String toString(){
        return nombreCompleto;
    }
}