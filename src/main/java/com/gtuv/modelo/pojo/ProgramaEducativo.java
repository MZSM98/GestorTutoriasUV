<<<<<<< HEAD

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
        
=======
package com.gtuv.modelo.pojo;

public class ProgramaEducativo {
    
    private int idProgramaEducativo;
    private String nombre;
    private int idJefeCarrera;
    private String nombreJefeDeCarrera;
    private int idCoordinador;
    private String nombreCoordinador;

    public ProgramaEducativo() {
>>>>>>> 248e58466f5a7242e18143ce944d796b8c6174a4
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

<<<<<<< HEAD
=======
    public String getNombreJefeDeCarrera() {
        return nombreJefeDeCarrera;
    }

    public void setNombreJefeDeCarrera(String nombreJefeDeCarrera) {
        this.nombreJefeDeCarrera = nombreJefeDeCarrera;
    }

>>>>>>> 248e58466f5a7242e18143ce944d796b8c6174a4
    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }
<<<<<<< HEAD
=======

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }
>>>>>>> 248e58466f5a7242e18143ce944d796b8c6174a4
}
