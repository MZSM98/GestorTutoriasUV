package com.gtuv.modelo.pojo;

public class Tutorado {
    
    private int idTutorado;
    private String matricula;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private int idProgramaEducativo;
    private String nombreProgramaEducativo;
    private int idSemestre;
    private String nombreSemestre;
    private boolean activo;
    
    public Tutorado() {
    }

    public Tutorado(int idTutorado, String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String Correo, int idProgramaEducativo, String nombreProgramaEducativo, int idSemestre, String nombreSemestre, boolean activo) {
        this.idTutorado = idTutorado;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = Correo;
        this.idProgramaEducativo = idProgramaEducativo;
        this.nombreProgramaEducativo = nombreProgramaEducativo;
        this.idSemestre = idSemestre;
        this.nombreSemestre = nombreSemestre;
        this.activo = activo;
    }

    public int getIdTutorado() {
        return idTutorado;
    }

    public void setIdTutorado(int idTutorado) {
        this.idTutorado = idTutorado;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getNombreProgramaEducativo() {
        return nombreProgramaEducativo;
    }

    public void setNombreProgramaEducativo(String nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public String getNombreCompleto() {
        if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
            return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
        } else {
            return nombre + " " + apellidoPaterno;
        }
    }
}