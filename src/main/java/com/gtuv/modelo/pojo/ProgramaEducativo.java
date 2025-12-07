
package com.gtuv.modelo.pojo;


public class ProgramaEducativo {
    
    private int idProgramaEducativo;
    String nombre;
    int idJefeCarrera;
    int idCoordinador;

    public ProgramaEducativo(int idProgramaEducativo, String nombre, int idJefeCarrera, int idCoordinador) {
        this.idProgramaEducativo = idProgramaEducativo;
        this.nombre = nombre;
        this.idJefeCarrera = idJefeCarrera;
        this.idCoordinador = idCoordinador;
    }
    
    public ProgramaEducativo(){
        
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

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }
}
