package com.gtuv.modelo.pojo;

public class ProgramaEducativo {
    
    private int idProgramaEducativo;
    private String nombre;
    private int idJefeCarrera;
    private String nombreJefeDeCarrera;
    private int idCoordinador;
    private String nombreCoordinador;

    public ProgramaEducativo() {
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdJefeCarrera() {
        return idJefeCarrera;
    }

    public void setIdJefeCarrera(int idJefeCarrera) {
        this.idJefeCarrera = idJefeCarrera;
    }

    public String getNombreJefeDeCarrera() {
        return nombreJefeDeCarrera;
    }

    public void setNombreJefeDeCarrera(String nombreJefeDeCarrera) {
        this.nombreJefeDeCarrera = nombreJefeDeCarrera;
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }
}
