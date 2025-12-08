package com.gtuv.modelo.pojo;

public class ProgramaEducativo {
    
    private int idProgramaEducativo;
    private String nombre;
    private Integer idJefeCarrera; 
    private String nombreJefeDeCarrera;
    private Integer idCoordinador;
    private String nombreCoordinador;

  
    public ProgramaEducativo() {
    }

   
    public ProgramaEducativo(int idProgramaEducativo, String nombre) {
        this.idProgramaEducativo = idProgramaEducativo;
        this.nombre = nombre;
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

    public Integer getIdJefeCarrera() {
        return idJefeCarrera;
    }

    public void setIdJefeCarrera(Integer idJefeCarrera) {
        this.idJefeCarrera = idJefeCarrera;
    }

    public String getNombreJefeDeCarrera() {
        return nombreJefeDeCarrera;
    }

    public void setNombreJefeDeCarrera(String nombreJefeDeCarrera) {
        this.nombreJefeDeCarrera = nombreJefeDeCarrera;
    }

    public Integer getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(Integer idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

 
    @Override
    public String toString() {
        return nombre; 
    }
}
