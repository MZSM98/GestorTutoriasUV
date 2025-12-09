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
import java.sql.Statement;

/**
 *
 * @author gurov
 */

public class ReporteTutoriaDAO {

    private ReporteTutoriaDAO() {
    }

    public static ResultSet obtenerPorSesion(Connection conexionBD, int idTutor, int idSesion) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT * FROM reporte_tutoria " +
                              "WHERE idTutor = ? AND idSesion = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor);
            sentencia.setInt(2, idSesion);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

   
    public static int registrar(Connection conexionBD, ReporteTutoria reporte) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO reporte_tutoria " +
                               "(idTutor, idSesion, idProgramaEducativo, descripcionGeneral, estado, fechaEntrega) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
                               
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion, Statement.RETURN_GENERATED_KEYS);
            
            sentencia.setInt(1, reporte.getIdTutor());
            sentencia.setInt(2, reporte.getIdSesion());
            sentencia.setInt(3, reporte.getIdProgramaEducativo());
            sentencia.setString(4, reporte.getDescripcionGeneral());
            sentencia.setString(5, reporte.getEstado());
            sentencia.setTimestamp(6, reporte.getFechaEntrega());

            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet ids = sentencia.getGeneratedKeys()) {
                    if (ids.next()) {
                        return ids.getInt(1); 
                    }
                }
            }
            return 0;
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    
    public static int actualizar(Connection conexionBD, ReporteTutoria reporte) throws SQLException {
        if (conexionBD != null) {
            String actualizacion = "UPDATE reporte_tutoria SET " +
                                   "descripcionGeneral = ?, estado = ?, fechaEntrega = ? " +
                                   "WHERE idReporteTutoria = ?";
                                   
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setString(1, reporte.getDescripcionGeneral());
            sentencia.setString(2, reporte.getEstado());
            sentencia.setTimestamp(3, reporte.getFechaEntrega());
            sentencia.setInt(4, reporte.getIdReporteTutoria());

            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
       public static ResultSet obtenerTutoradosPorTutor(Connection conexionBD, int idTutor) throws SQLException {
        if (conexionBD != null) {
         
            String consulta = "SELECT t.idTutorado, t.matricula, t.nombre, " + 
                              "t.apellidoPaterno, t.apellidoMaterno, t.correo " +
                              "FROM tutorado t " +
                              "INNER JOIN asignacion_tutor at ON t.idTutorado = at.idTutorado " +
                              "WHERE at.idTutor = ? AND t.activo = 1 " +
                              "ORDER BY t.apellidoPaterno ASC";
                              
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
       
    public static ResultSet obtenerReportesPorTutorYPrograma(Connection conexionBD, int idTutor, int idProgramaEducativo) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT rt.*, st.numeroSesion, st.fechaSesion, " +
                              "(SELECT COUNT(*) FROM asistencia a WHERE a.idReporteTutoria = rt.idReporteTutoria AND a.asistio = 1) AS totalAsistencia, " +
                              "(SELECT COUNT(*) FROM asistencia a WHERE a.idReporteTutoria = rt.idReporteTutoria AND a.enRiesgo = 1) AS totalRiesgo " +
                              "FROM reporte_tutoria rt " +
                              "INNER JOIN sesion_tutoria st ON rt.idSesion = st.idSesion " +
                              "WHERE rt.idTutor = ? AND rt.idProgramaEducativo = ?";
                              
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor);
            sentencia.setInt(2, idProgramaEducativo);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    
}