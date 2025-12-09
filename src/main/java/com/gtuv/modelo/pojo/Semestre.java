package com.gtuv.modelo.pojo;

public class Semestre {
    
    private int idSemestre;
    private String nombreSemestre;

    public Semestre() {
    }

    public Semestre(int idSemestre, String nombreSemestre) {
        this.idSemestre = idSemestre;
        this.nombreSemestre = nombreSemestre;
    }
    

    public int getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
    }

    public String getNombreSemestre() {
        return nombreSemestre;
    }

    public void setNombreSemestre(String nombreSemestre) {
        this.nombreSemestre = nombreSemestre;
    }
    
    @Override
    public String toString(){
        return nombreSemestre;
    }
}
