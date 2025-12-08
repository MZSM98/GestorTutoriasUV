package com.gtuv.modelo.pojo;

public class Usuario {
    
    private int idUsuario;
    private String noTrabajador;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String contrasenia;
    private boolean esAdministrador;
    private boolean esTutor;
    private boolean activo;
    private boolean esJefeCarrera;
    private boolean esCoordinador;
    
    public Usuario() { 
    }

    public Usuario(int idUsuario, String noTrabajador, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasenia, boolean esAdministrador, boolean esTutor, boolean activo, boolean esJefeCarrera, boolean esCoordinador) {
        this.idUsuario = idUsuario;
        this.noTrabajador = noTrabajador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.esAdministrador = esAdministrador;
        this.esTutor = esTutor;
        this.activo = activo;
        this.esJefeCarrera = esJefeCarrera;
        this.esCoordinador = esCoordinador;
    }

    
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNoTrabajador() {
        return noTrabajador;
    }

    public void setNoTrabajador(String noTrabajador) {
        this.noTrabajador = noTrabajador;
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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public boolean isEsJefeCarrera() {
        return esJefeCarrera;
    }

    public void setEsJefeCarrera(boolean esJefeCarrera) {
        this.esJefeCarrera = esJefeCarrera;
    }

    public boolean isEsCoordinador() {
        return esCoordinador;
    }

    public void setEsCoordinador(boolean esCoordinador) {
        this.esCoordinador = esCoordinador;
    }
}
