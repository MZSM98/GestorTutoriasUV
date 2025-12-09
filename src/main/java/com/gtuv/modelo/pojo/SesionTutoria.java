package com.gtuv.modelo.pojo;


public class SesionTutoria {
    
    private int idSesion;
    private int idPeriodo;
    private int idProgramaEducativo;
    private int numeroSesion;
    private String fechaSesion;
    private String estado;

    public SesionTutoria() {
    }

    public SesionTutoria(int idSesion, int idPeriodo, int idProgramaEducativo, int numeroSesion, String fechaSesion, String estado) {
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
    
    public String getFechaSesion() { 
        return fechaSesion; 
    }
    
    public void setFechaSesion(String fechaSesion) { 
        this.fechaSesion = fechaSesion; 
    }
    
    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }
    
    @Override
    public String toString(){
        return "Sesion " + numeroSesion;
    }
    
}