/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.modelo.dao;
import com.gtuv.modelo.pojo.ProblematicaAcademica;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gurov
 */
public class ProblematicaDAO {
    
    private ProblematicaDAO() {
    }

    public static int registrar(Connection conexionBD, ProblematicaAcademica problematica) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO problematica_academica (idReporteTutoria, idTutorado, tipo, descripcion, experienciaEducativa, nombreProfesor) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion);
            sentencia.setInt(1, problematica.getIdReporteTutoria());
            sentencia.setInt(2, problematica.getIdTutorado());
            sentencia.setString(3, problematica.getTipo());
            sentencia.setString(4, problematica.getDescripcion());
            sentencia.setString(5, problematica.getExperienciaEducativa());
            sentencia.setString(6, problematica.getNombreProfesor());
            
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static int editar(Connection conexionBD, ProblematicaAcademica problematica) throws SQLException {
        if (conexionBD != null) {
            String actualizacion = "UPDATE problematica_academica SET tipo = ?, descripcion = ?, experienciaEducativa = ?, nombreProfesor = ? WHERE idProblematica = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setString(1, problematica.getTipo());
            sentencia.setString(2, problematica.getDescripcion());
            sentencia.setString(3, problematica.getExperienciaEducativa());
            sentencia.setString(4, problematica.getNombreProfesor());
            sentencia.setInt(5, problematica.getIdProblematica());
            
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static int eliminar(Connection conexionBD, int idProblematica) throws SQLException {
        if (conexionBD != null) {
            String eliminacion = "DELETE FROM problematica_academica WHERE idProblematica = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(eliminacion);
            sentencia.setInt(1, idProblematica);
            
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static ResultSet obtenerProblematicasPorReporte(Connection conexionBD, int idReporteTutoria) throws SQLException {
        if (conexionBD != null) {
            // Se actualizó la columna a nombreProfesor en el SELECT también
            String consulta = "SELECT pa.idProblematica, pa.idReporteTutoria, pa.idTutorado, pa.tipo, " +
                              "pa.descripcion, pa.experienciaEducativa, pa.nombreProfesor, " +
                              "t.nombre AS nombreTutorado, t.apellidoPaterno, t.apellidoMaterno " +
                              "FROM problematica_academica pa " +
                              "INNER JOIN tutorado t ON pa.idTutorado = t.idTutorado " +
                              "WHERE pa.idReporteTutoria = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idReporteTutoria);
            
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
}
