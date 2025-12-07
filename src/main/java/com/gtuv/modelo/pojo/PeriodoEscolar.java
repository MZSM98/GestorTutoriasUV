package com.gtuv.modelo.pojo;

import java.sql.Date;

public class PeriodoEscolar {
    
    private int idPeriodo;
    private Date fechaInicio;
    private Date fechaFin;
    private String nombre;

    public PeriodoEscolar() {
    
    }

    public PeriodoEscolar(int idPeriodo, Date fechaInicio, Date fechaFin, String nombre) {
        this.idPeriodo = idPeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombre = nombre;
    }

    public int getIdPeriodo() { 
        return idPeriodo; 
    }
    
    public void setIdPeriodo(int idPeriodo) { 
        this.idPeriodo = idPeriodo; 
    }
    
    public Date getFechaInicio() { 
        return fechaInicio; 
    }
    
    public void setFechaInicio(Date fechaInicio) { 
        this.fechaInicio = fechaInicio; 
    }
    
    public Date getFechaFin() { 
        return fechaFin; 
    }
    
    public void setFechaFin(Date fechaFin) { 
        this.fechaFin = fechaFin; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
}