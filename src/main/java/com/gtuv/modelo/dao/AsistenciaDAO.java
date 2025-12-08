package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.Asistencia;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsistenciaDAO {

    private AsistenciaDAO() {
    }

    public static int registrar(Connection conexionBD, Asistencia asistencia) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO asistencia (idReporteTutoria, idTutorado, asistio, enRiesgo) VALUES (?, ?, ?, ?)";
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion);
            sentencia.setInt(1, asistencia.getIdReporteTutoria());
            sentencia.setInt(2, asistencia.getIdTutorado());
            sentencia.setBoolean(3, asistencia.isAsistio());
            sentencia.setBoolean(4, asistencia.isEnRiesgo());
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static ResultSet obtenerPorReporte(Connection conexionBD, int idReporteTutoria) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT a.*, t.nombre, t.apellidoPaterno, t.apellidoMaterno, t.matricula " +
                              "FROM asistencia a " +
                              "INNER JOIN tutorado t ON a.idTutorado = t.idTutorado " +
                              "WHERE a.idReporteTutoria = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idReporteTutoria);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}