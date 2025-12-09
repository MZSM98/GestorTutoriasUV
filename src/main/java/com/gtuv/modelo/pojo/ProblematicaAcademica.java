package com.gtuv.modelo.pojo;

public class ProblematicaAcademica {
    
    private int idProblematica;
    private int idReporteTutoria;
    private int idTutorado;
    private String tipo;
    private String descripcion;
    private String experienciaEducativa;
    private String NombreProfesor;
    
    private String nombreAlumno; 

    public ProblematicaAcademica() {
    }

    public int getIdProblematica() {
        return idProblematica;
    }

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
    }

    public int getIdReporteTutoria() {
        return idReporteTutoria;
    }

    public void setIdReporteTutoria(int idReporteTutoria) {
        this.idReporteTutoria = idReporteTutoria;
    }

    public int getIdTutorado() {
        return idTutorado;
    }

    public void setIdTutorado(int idTutorado) {
        this.idTutorado = idTutorado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(String experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public String getNombreProfesor() {
        return NombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.NombreProfesor = nombreProfesor;
    }
    
    
    @Override
    public String toString() {
        return descripcion; // Para debugging o logs
    }
    
}
