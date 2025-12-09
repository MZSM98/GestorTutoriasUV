/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.HorarioTutor;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author gurov
 */
public class HorarioTutoriasDAO {
    
    public static int registrarHorario(Connection conexionBD, HorarioTutor horario) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO horario_tutor (idTutor, idSesion, horaInicio) VALUES (?, ?, ?)";
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion);
            
            sentencia.setInt(1, horario.getIdTutor());
            sentencia.setInt(2, horario.getIdSesion());
            sentencia.setTime(3, horario.getHoraInicio());
            
           
            return sentencia.executeUpdate(); 
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    public static int actualizarHorario(Connection conexionBD, HorarioTutor horario) throws SQLException {
        if (conexionBD != null) {
            String actualizacion = "UPDATE horario_tutor SET horaInicio = ? WHERE idHorario = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            
            sentencia.setTime(1, horario.getHoraInicio());
            sentencia.setInt(2, horario.getIdHorario());
            
           
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
}
