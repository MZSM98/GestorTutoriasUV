package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.SesionTutoria;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SesionTutoriaDAO {

    private SesionTutoriaDAO() {
    }

    public static ResultSet obtenerSesionesPorPeriodo(Connection conexionBD, int idPeriodo, int idProgramaEducativo) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT * FROM sesion_tutoria " +
                              "WHERE idPeriodo = ? AND idProgramaEducativo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idPeriodo);
            sentencia.setInt(2, idProgramaEducativo);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int registrarSesion(Connection conexionBD, SesionTutoria sesion) throws SQLException {
        if(conexionBD != null){
            String sentencia = "INSERT INTO sesion_tutoria (idPeriodo, idProgramaEducativo, numeroSesion, fechaSesion, estado) " +
                               "VALUES (?, ?, ?, ?, 'ABIERTA')";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, sesion.getIdPeriodo());
            prepararSentencia.setInt(2, sesion.getIdProgramaEducativo());
            prepararSentencia.setInt(3, sesion.getNumeroSesion());
            prepararSentencia.setString(4, sesion.getFechaSesion()); 
            
            return prepararSentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int editarSesion(Connection conexionBD, SesionTutoria sesion) throws SQLException {
        if(conexionBD != null){
            String sentencia = "UPDATE sesion_tutoria SET fechaSesion = ? WHERE idSesion = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, sesion.getFechaSesion());
            prepararSentencia.setInt(2, sesion.getIdSesion());
            
            return prepararSentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int modificarEstadoSesion(Connection conexionBD, int idSesion, String estado) throws SQLException {
        if(conexionBD != null){
            String sentencia = "UPDATE sesion_tutoria SET estado = ? WHERE idSesion = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, estado);
            prepararSentencia.setInt(2, idSesion);
            return prepararSentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}