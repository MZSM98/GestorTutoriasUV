package com.gtuv.modelo.pojo;

import javafx.scene.control.CheckBox; 

public class Asistencia {
    
    private int idAsistencia;
    private int idReporteTutoria;
    private int idTutorado;
    private boolean asistio;
    private boolean enRiesgo;
    private String matricula; 
    private String nombre;
    private String apellidoPaterno; 
    private String apellidoMaterno; 
    
      public Asistencia() {
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
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

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public boolean isEnRiesgo() {
        return enRiesgo;
    }

    public void setEnRiesgo(boolean enRiesgo) {
        this.enRiesgo = enRiesgo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    
   

  @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + (apellidoMaterno != null ? apellidoMaterno : "");
    }


    

}
