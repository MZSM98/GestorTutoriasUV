/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.modelo.pojo;

import java.sql.Time; 

/**
 *
 * @author gurov
 */
public class HorarioTutor {
    
 private int idHorario;
    private int idTutor;
    private int idSesion;
    private Time horaInicio;

    public HorarioTutor() {
    }

    public HorarioTutor(int idHorario, int idTutor, int idSesion, Time horaInicio) {
        this.idHorario = idHorario;
        this.idTutor = idTutor;
        this.idSesion = idSesion;
        this.horaInicio = horaInicio;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
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

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }   
    
}
