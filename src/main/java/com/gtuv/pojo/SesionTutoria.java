package com.gtuv.pojo;

import java.sql.Date;

public class SesionTutoria {
    
    private int idSesion;
    private int idPeriodo;
    private int idProgramaEducativo;
    private int numeroSesion;
    private Date fechaSesion;
    private String estado;

    public SesionTutoria() {
    }

    public SesionTutoria(int idSesion, int idPeriodo, int idProgramaEducativo, int numeroSesion, Date fechaSesion, String estado) {
        this.idSesion = idSesion;
        this.idPeriodo = idPeriodo;
        this.idProgramaEducativo = idProgramaEducativo;
        this.numeroSesion = numeroSesion;
        this.fechaSesion = fechaSesion;
        this.estado = estado;
    }

    public int getIdSesion() { 
        return idSesion; 
    }
    
    public void setIdSesion(int idSesion) { 
        this.idSesion = idSesion; 
    }
    
    public int getIdPeriodo() { 
        return idPeriodo; 
    }
    
    public void setIdPeriodo(int idPeriodo) { 
        this.idPeriodo = idPeriodo; 
    }
    
    public int getIdProgramaEducativo() { 
        return idProgramaEducativo; 
    }
    
    public void setIdProgramaEducativo(int idProgramaEducativo) { 
        this.idProgramaEducativo = idProgramaEducativo; 
    }
    
    public int getNumeroSesion() { 
        return numeroSesion; 
    }
    
    public void setNumeroSesion(int numeroSesion) { 
        this.numeroSesion = numeroSesion; 
    }
    
    public Date getFechaSesion() { 
        return fechaSesion; 
    }
    
    public void setFechaSesion(Date fechaSesion) { 
        this.fechaSesion = fechaSesion; 
    }
    
    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }
    
    private enum Estado {ACTIVO, INACTIVO}
}