package com.gtuv.modelo.dao;

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
}