package com.gtuv.modelo.pojo;

public class ReporteGeneral {
    
    private int idReporteGeneral;
    private int idCoordinador;
    private int idSesion;
    private int idProgramaEducativo;
    private String fechaElaboracion;
    private String comentariosGenerales;
    private int totalAsistentes;
    private int totalEnRiesgo;
    private String estatus;
    private int numeroSesion;
    private String nombreProgramaEducativo;
    private String nombreCoordinador;

    public ReporteGeneral() {
    }

    public int getIdReporteGeneral() {
        return idReporteGeneral;
    }

    public void setIdReporteGeneral(int idReporteGeneral) {
        this.idReporteGeneral = idReporteGeneral;
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(String fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getComentariosGenerales() {
        return comentariosGenerales;
    }

    public void setComentariosGenerales(String comentariosGenerales) {
        this.comentariosGenerales = comentariosGenerales;
    }

    public int getTotalAsistentes() {
        return totalAsistentes;
    }

    public void setTotalAsistentes(int totalAsistentes) {
        this.totalAsistentes = totalAsistentes;
    }

    public int getTotalEnRiesgo() {
        return totalEnRiesgo;
    }

    public void setTotalEnRiesgo(int totalEnRiesgo) {
        this.totalEnRiesgo = totalEnRiesgo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }

    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public String getNombreProgramaEducativo() {
        return nombreProgramaEducativo;
    }

    public void setNombreProgramaEducativo(String nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

    
    @Override
    public String toString() {
        return "Reporte General - Sesión " + numeroSesion;
    }
}