/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.modelo.dao;
import com.gtuv.modelo.pojo.ReporteTutoria;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gurov
 */

public class ReporteTutoriaDAO {

    private ReporteTutoriaDAO() {
    }

    public static int registrar(Connection conexionBD, ReporteTutoria reporte) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO reporte_tutoria (idTutor, idSesion, idProgramaEducativo, descripcionGeneral, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion);
            sentencia.setInt(1, reporte.getIdTutor());
            sentencia.setInt(2, reporte.getIdSesion());
            sentencia.setInt(3, reporte.getIdProgramaEducativo());
            sentencia.setString(4, reporte.getDescripcionGeneral());
            sentencia.setString(5, reporte.getEstado());
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static int editar(Connection conexionBD, ReporteTutoria reporte) throws SQLException {
        if (conexionBD != null) {
            String actualizacion = "UPDATE reporte_tutoria SET descripcionGeneral = ?, estado = ? WHERE idReporteTutoria = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setString(1, reporte.getDescripcionGeneral());
            sentencia.setString(2, reporte.getEstado());
            sentencia.setInt(3, reporte.getIdReporteTutoria());
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static ResultSet obtenerReportesPorTutor(Connection conexionBD, int idTutor) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT * FROM reporte_tutoria WHERE idTutor = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean verificarReporteExistente(Connection conexionBD, int idTutor, int idSesion) throws SQLException{
        if(conexionBD != null){
             String consulta = "SELECT idReporteTutoria FROM reporte_tutoria WHERE idTutor = ? AND idSesion = ?";
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
             sentencia.setInt(1, idTutor);
             sentencia.setInt(2, idSesion);
             return sentencia.executeQuery().next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}