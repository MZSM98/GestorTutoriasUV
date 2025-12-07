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

    public ReporteTutoria() {
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
    
}