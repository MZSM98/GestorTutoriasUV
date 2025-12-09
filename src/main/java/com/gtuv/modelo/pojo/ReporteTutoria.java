package com.gtuv.modelo.pojo;

import java.sql.Timestamp;

public class ReporteTutoria {
    
    private int idReporteTutoria;
    private int idTutor;
    private int idSesion;
    private int idProgramaEducativo;
    private String descripcionGeneral;
    private String estado;
    private Timestamp fechaEntrega;
    
    private int numeroSesion;
    private String fechaSesion; 
    private int totalAsistencia;
    private int totalRiesgo;

    public ReporteTutoria() {
    }

    public ReporteTutoria(int idReporteTutoria, int idTutor, int idSesion, int idProgramaEducativo, String descripcionGeneral, String estado, Timestamp fechaEntrega, int numeroSesion, String fechaSesion, int totalAsistencia, int totalRiesgo) {
        this.idReporteTutoria = idReporteTutoria;
        this.idTutor = idTutor;
        this.idSesion = idSesion;
        this.idProgramaEducativo = idProgramaEducativo;
        this.descripcionGeneral = descripcionGeneral;
        this.estado = estado;
        this.fechaEntrega = fechaEntrega;
        this.numeroSesion = numeroSesion;
        this.fechaSesion = fechaSesion;
        this.totalAsistencia = totalAsistencia;
        this.totalRiesgo = totalRiesgo;
    }
    

    public int getIdReporteTutoria() {
        return idReporteTutoria;
    }

    public void setIdReporteTutoria(int idReporteTutoria) {
        this.idReporteTutoria = idReporteTutoria;
    }

    public int getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
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

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Timestamp fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }

    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public String getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(String fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    public int getTotalAsistencia() {
        return totalAsistencia;
    }

    public void setTotalAsistencia(int totalAsistencia) {
        this.totalAsistencia = totalAsistencia;
    }

    public int getTotalRiesgo() {
        return totalRiesgo;
    }

    public void setTotalRiesgo(int totalRiesgo) {
        this.totalRiesgo = totalRiesgo;
    }
    
    @Override
    public String toString(){
        return "Reporte Sesión " + numeroSesion;
    }
}