package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramaEducativoDAO {
    
    private ProgramaEducativoDAO(){
        
    }
    
    public static ResultSet obtenerProgramasEducativos(Connection conexionBD) throws SQLException{
        
        if(conexionBD != null){
            String consulta = "SELECT * FROM programa_educativo";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int asignarJefeCarrera(Connection conexionBD, int idProgramaEducativo, int idUsuario) throws SQLException{
        
        if(conexionBD != null){
            String actualizacion = "UPDATE programa_educativo SET idJefeCarrera = ? WHERE idProgramaEducativo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setInt(1, idUsuario);
            sentencia.setInt(2, idProgramaEducativo);
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int asignarCoordinador(Connection conexionBD, int idProgramaEducativo, int idUsuario) throws SQLException{
        
        if(conexionBD != null){
            String actualizacion = "UPDATE programa_educativo SET idCoordinador = ? WHERE idProgramaEducativo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setInt(1, idUsuario);
            sentencia.setInt(2, idProgramaEducativo);
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean hayJefeCarreraAsignado(Connection conexionBD, int idProgramaEducativo) throws SQLException{
        
        if(conexionBD != null){
            String consulta = "SELECT idJefeCarrera FROM programa_educativo WHERE idProgramaEducativo = ? AND idJefeCarrera IS NOT NULL";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            return resultado.next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean hayCoordinadorAsignado(Connection conexionBD, int idProgramaEducativo) throws SQLException{
        
        if(conexionBD != null){
            String consulta = "SELECT idCoordinador FROM programa_educativo WHERE idProgramaEducativo = ? AND idCoordinador IS NOT NULL";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            return resultado.next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    

    public static Usuario obtenerJefeCarrera(Connection conexionBD, int idProgramaEducativo) throws SQLException {
        Usuario jefe = null;
        if (conexionBD != null) {
            String consulta = "SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.noTrabajador " +
                              "FROM programa_educativo pe " +
                              "JOIN usuario u ON pe.idJefeCarrera = u.idUsuario " +
                              "WHERE pe.idProgramaEducativo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                jefe = new Usuario();
                jefe.setNombre(resultado.getString("nombre"));
                jefe.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                jefe.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                jefe.setNoTrabajador(resultado.getString("noTrabajador"));
            }
        }
        return jefe;
    }
    
    public static Usuario obtenerCoordinador(Connection conexionBD, int idProgramaEducativo) throws SQLException {
        Usuario coordinador = null;
        if (conexionBD != null) {
            String consulta = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.noTrabajador " +
                              "FROM programa_educativo pe " +
                              "JOIN usuario u ON pe.idCoordinador = u.idUsuario " +
                              "WHERE pe.idProgramaEducativo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                coordinador = new Usuario();
                coordinador.setIdUsuario(resultado.getInt("idUsuario")); // Importante para validar si es el mismo
                coordinador.setNombre(resultado.getString("nombre"));
                coordinador.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                coordinador.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                coordinador.setNoTrabajador(resultado.getString("noTrabajador"));
            }
        }
        return coordinador;
    }

    public static ProgramaEducativo obtenerProgramaPorUsuario(Connection conexionBD, int idUsuario) throws SQLException {
        ProgramaEducativo programa = null;
        if (conexionBD != null) {
            String consulta = "SELECT * FROM programa_educativo WHERE idJefeCarrera = ? OR idCoordinador = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idUsuario);
            sentencia.setInt(2, idUsuario);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                programa = new ProgramaEducativo();
                programa.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                programa.setNombre(resultado.getString("nombre"));
                programa.setIdJefeCarrera(resultado.getInt("idJefeCarrera"));
                programa.setIdCoordinador(resultado.getInt("idCoordinador"));
            }
        }
        return programa;
    }
}