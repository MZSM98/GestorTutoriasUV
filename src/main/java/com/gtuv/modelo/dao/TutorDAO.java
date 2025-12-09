package com.gtuv.modelo.dao;

import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TutorDAO {

    private TutorDAO() {
    }

    public static ResultSet obtenerTutores(Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT u.idUsuario, u.noTrabajador, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, " +
                              "(SELECT COUNT(*) FROM asignacion_tutor a WHERE a.idTutor = u.idUsuario) AS totalTutorados " +
                              "FROM usuario u " +
                              "WHERE u.activo = 1 AND u.esTutor = 1";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerTutoradosPorTutor(Connection conexionBD, int idTutor) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT t.*, s.nombre AS nombreSemestre " +
                              "FROM tutorado t " +
                              "INNER JOIN asignacion_tutor a ON t.idTutorado = a.idTutorado " +
                              "INNER JOIN semestre s ON t.idSemestre = s.idSemestre " +
                              "WHERE a.idTutor = ? AND t.activo = 1";

            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int asignarTutorado(Connection conexionBD, int idTutor, int idTutorado) throws SQLException {
        if (conexionBD != null) {
            String sentencia = "INSERT INTO asignacion_tutor (idTutor, idTutorado) VALUES (?, ?)";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, idTutor);
            prepararSentencia.setInt(2, idTutorado);
            return prepararSentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static int desasignarTutorado(Connection conexionBD, int idTutor, int idTutorado) throws SQLException {
        if (conexionBD != null) {
            String sentencia = "DELETE FROM asignacion_tutor WHERE idTutor = ? AND idTutorado = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, idTutor);
            prepararSentencia.setInt(2, idTutorado);
            return prepararSentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static ResultSet obtenerTutoresPorProgramaEducativo(Connection conexionBD, int idProgramaEducativo) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT DISTINCT u.idUsuario, u.noTrabajador, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo " +
                              "FROM usuario u " +
                              "INNER JOIN reporte_tutoria rt ON u.idUsuario = rt.idTutor " +
                              "WHERE rt.idProgramaEducativo = ? AND u.activo = 1";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}