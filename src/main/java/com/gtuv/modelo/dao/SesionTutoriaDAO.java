/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.modelo.dao;

import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 *
 * @author gurov
 */
public class SesionTutoriaDAO {
    
    private SesionTutoriaDAO() {
    }

   /* public static ResultSet obtenerSesionesPorPrograma(Connection conexionBD, int idProgramaEducativo, int idTutor) throws SQLException {
        if (conexionBD != null) {
            // Corregido: Quitamos s.idPeriodo del WHERE
            // Agregamos ORDER BY fechaSesion DESC para ver las más recientes primero
            String consulta = "SELECT s.idSesion, s.numeroSesion, s.fechaSesion, s.estado, h.horaInicio " +
                              "FROM sesion_tutoria s " +
                              "LEFT JOIN horario_tutor h ON s.idSesion = h.idSesion AND h.idTutor = ? " +
                              "WHERE s.idProgramaEducativo = ? " +
                              "ORDER BY s.fechaSesion DESC";
                              
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor); // Para el JOIN del horario
            sentencia.setInt(2, idProgramaEducativo); // Para el filtro principal
            
            return sentencia.executeQuery();
        }*/
 

    public static int registrarHorario(Connection conexionBD, int idTutor, int idSesion, Time horaInicio) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO horario_tutor (idTutor, idSesion, horaInicio) VALUES (?, ?, ?)";
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion);
            sentencia.setInt(1, idTutor);
            sentencia.setInt(2, idSesion);
            sentencia.setTime(3, horaInicio);
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static int editarHorario(Connection conexionBD, int idTutor, int idSesion, Time horaInicio) throws SQLException {
        if (conexionBD != null) {
            String actualizacion = "UPDATE horario_tutor SET horaInicio = ? WHERE idTutor = ? AND idSesion = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setTime(1, horaInicio);
            sentencia.setInt(2, idTutor);
            sentencia.setInt(3, idSesion);
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean verificarHorarioRegistrado(Connection conexionBD, int idTutor, int idSesion) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT idHorario FROM horario_tutor WHERE idTutor = ? AND idSesion = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor);
            sentencia.setInt(2, idSesion);
            return sentencia.executeQuery().next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}
    
