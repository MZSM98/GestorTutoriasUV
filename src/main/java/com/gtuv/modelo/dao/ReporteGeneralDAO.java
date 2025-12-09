package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.ProblematicaAcademica;
import com.gtuv.modelo.pojo.ReporteGeneral;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

public class ReporteGeneralDAO {
    
    private ReporteGeneralDAO(){
    }
    
    public static ResultSet obtenerReportesPorCoordinador(Connection conexionBD, int idCoordinador) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT rg.*, st.numeroSesion, pe.nombre AS nombrePrograma " +
                              "FROM reporte_general rg " +
                              "INNER JOIN sesion_tutoria st ON rg.idSesion = st.idSesion " +
                              "INNER JOIN programa_educativo pe ON rg.idProgramaEducativo = pe.idProgramaEducativo " +
                              "WHERE rg.idCoordinador = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idCoordinador);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static void guardarSeleccionProblematicas(Connection conexionBD, int idReporteGeneral, ObservableList<ProblematicaAcademica> problematicas) throws SQLException {
        if (conexionBD != null) {
            String delete = "DELETE FROM problematica_seleccionada WHERE idReporteGeneral = ?";
            PreparedStatement stmtDelete = conexionBD.prepareStatement(delete);
            stmtDelete.setInt(1, idReporteGeneral);
            stmtDelete.executeUpdate();

            String insert = "INSERT INTO problematica_seleccionada (idReporteGeneral, idProblematica) VALUES (?, ?)";
            PreparedStatement stmtInsert = conexionBD.prepareStatement(insert);
            
            for (ProblematicaAcademica prob : problematicas) {
                stmtInsert.setInt(1, idReporteGeneral);
                stmtInsert.setInt(2, prob.getIdProblematica());
                stmtInsert.addBatch(); 
            }
            stmtInsert.executeBatch();
        }
    }
    
    public static int registrar(Connection conexionBD, ReporteGeneral reporte) throws SQLException {
        int idGenerado = 0;
        if(conexionBD != null){
            String insercion = "INSERT INTO reporte_general (idCoordinador, idSesion, idProgramaEducativo, comentariosGenerales, totalAsistentes, totalEnRiesgo, estatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion, java.sql.Statement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, reporte.getIdCoordinador());
            sentencia.setInt(2, reporte.getIdSesion());
            sentencia.setInt(3, reporte.getIdProgramaEducativo());
            sentencia.setString(4, reporte.getComentariosGenerales());
            sentencia.setInt(5, reporte.getTotalAsistentes());
            sentencia.setInt(6, reporte.getTotalEnRiesgo());
            sentencia.setString(7, reporte.getEstatus());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = sentencia.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1);
                }
            }
        }
        return idGenerado; 
    }
    
    public static int editar(Connection conexionBD, ReporteGeneral reporte) throws SQLException {
        if(conexionBD != null){
            String actualizacion = "UPDATE reporte_general SET comentariosGenerales = ?, totalAsistentes = ?, totalEnRiesgo = ?, estatus = ? " +
                                   "WHERE idReporteGeneral = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setString(1, reporte.getComentariosGenerales());
            sentencia.setInt(2, reporte.getTotalAsistentes());
            sentencia.setInt(3, reporte.getTotalEnRiesgo());
            sentencia.setString(4, reporte.getEstatus());
            sentencia.setInt(5, reporte.getIdReporteGeneral());
            
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean existeReporte(Connection conexionBD, int idSesion, int idProgramaEducativo) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT idReporteGeneral FROM reporte_general WHERE idSesion = ? AND idProgramaEducativo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idSesion);
            sentencia.setInt(2, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            return resultado.next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ReporteGeneral obtenerReportePorId(Connection conexionBD, int idReporte) throws SQLException {
        ReporteGeneral reporte = null;
        if(conexionBD != null){
            String consulta = "SELECT rg.*, st.numeroSesion, pe.nombre AS nombrePrograma " +
                              "FROM reporte_general rg " +
                              "INNER JOIN sesion_tutoria st ON rg.idSesion = st.idSesion " +
                              "INNER JOIN programa_educativo pe ON rg.idProgramaEducativo = pe.idProgramaEducativo " +
                              "WHERE rg.idReporteGeneral = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idReporte);
            ResultSet rs = sentencia.executeQuery();
            
            if(rs.next()){
                reporte = new ReporteGeneral();
                reporte.setIdReporteGeneral(rs.getInt("idReporteGeneral"));
                reporte.setIdCoordinador(rs.getInt("idCoordinador"));
                reporte.setIdSesion(rs.getInt("idSesion"));
                reporte.setNumeroSesion(rs.getInt("numeroSesion")); 
                reporte.setIdProgramaEducativo(rs.getInt("idProgramaEducativo"));
                reporte.setNombreProgramaEducativo(rs.getString("nombrePrograma"));
                reporte.setFechaElaboracion(rs.getString("fechaElaboracion"));
                reporte.setComentariosGenerales(rs.getString("comentariosGenerales"));
                reporte.setTotalAsistentes(rs.getInt("totalAsistentes"));
                reporte.setTotalEnRiesgo(rs.getInt("totalEnRiesgo"));
                reporte.setEstatus(rs.getString("estatus"));
            }
        }
        return reporte;
    }
    
    public static ResultSet obtenerTutoradosEnRiesgoPorSesion(Connection conexionBD, int idSesion, int idProgramaEducativo) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT t.matricula, t.nombre, t.apellidoPaterno, t.apellidoMaterno, s.nombre AS nombreSemestre " +
                              "FROM asistencia a " +
                              "INNER JOIN reporte_tutoria rt ON a.idReporteTutoria = rt.idReporteTutoria " +
                              "INNER JOIN tutorado t ON a.idTutorado = t.idTutorado " +
                              "INNER JOIN semestre s ON t.idSemestre = s.idSemestre " +
                              "WHERE rt.idSesion = ? AND rt.idProgramaEducativo = ? AND a.enRiesgo = 1";
                              
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idSesion);
            sentencia.setInt(2, idProgramaEducativo);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerProblematicasPorSesion(Connection conexionBD, int idSesion, int idProgramaEducativo) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT pa.idProblematica, pa.tipo, pa.descripcion, pa.nombreProfesor AS nombreProfesor " +
                              "FROM problematica_academica pa " +
                              "INNER JOIN reporte_tutoria rt ON pa.idReporteTutoria = rt.idReporteTutoria " +
                              "WHERE rt.idSesion = ? AND rt.idProgramaEducativo = ?";
                              
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idSesion);
            sentencia.setInt(2, idProgramaEducativo);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int actualizarEstatus(Connection conexionBD, int idReporteGeneral, String nuevoEstatus) throws SQLException {
        if(conexionBD != null){
            String consulta = "UPDATE reporte_general SET estatus = ? WHERE idReporteGeneral = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, nuevoEstatus);
            sentencia.setInt(2, idReporteGeneral);
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerProblematicasPorReporte(Connection conexionBD, int idReporteGeneral) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT pa.idProblematica, pa.tipo, pa.descripcion, pa.profesor AS nombreProfesor " +
                              "FROM problematica_seleccionada ps " +
                              "INNER JOIN problematica_academica pa ON ps.idProblematica = pa.idProblematica " +
                              "WHERE ps.idReporteGeneral = ?";
                              
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idReporteGeneral);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int obtenerTotalAsistencias(Connection conexionBD, int idSesion, int idProgramaEducativo) throws SQLException {
        int total = 0;
        if (conexionBD != null) {
            String consulta = "SELECT COUNT(*) AS total " +
                              "FROM asistencia a " +
                              "INNER JOIN reporte_tutoria rt ON a.idReporteTutoria = rt.idReporteTutoria " +
                              "WHERE rt.idSesion = ? AND rt.idProgramaEducativo = ? AND a.asistio = 1";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idSesion);
            sentencia.setInt(2, idProgramaEducativo);
            
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                total = resultado.getInt("total");
            }
        }
        return total;
    }
    
    public static ResultSet obtenerReportesPorProgramaYEstado(Connection conexionBD, int idProgramaEducativo, String estatus) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT rg.*, st.numeroSesion, pe.nombre AS nombrePrograma, " +
                              "CONCAT(u.nombre, ' ', u.apellidoPaterno, ' ', u.apellidoMaterno) AS nombreCoordinador " +
                              "FROM reporte_general rg " +
                              "INNER JOIN sesion_tutoria st ON rg.idSesion = st.idSesion " +
                              "INNER JOIN programa_educativo pe ON rg.idProgramaEducativo = pe.idProgramaEducativo " +
                              "INNER JOIN usuario u ON rg.idCoordinador = u.idUsuario " +
                              "WHERE rg.idProgramaEducativo = ? AND rg.estatus = ?";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            sentencia.setString(2, estatus);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}