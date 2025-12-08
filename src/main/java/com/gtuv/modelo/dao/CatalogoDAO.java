package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.PeriodoEscolar;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogoDAO {
    
    private CatalogoDAO(){
    }
    
    public static PeriodoEscolar obtenerPeriodoActual(Connection conexionBD) throws SQLException {
        PeriodoEscolar periodo = null;
        if(conexionBD != null){
            String consulta = "SELECT * FROM periodo_escolar WHERE CURDATE() BETWEEN fechaInicio AND fechaFin";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            
            if(resultado.next()){
                periodo = new PeriodoEscolar();
                periodo.setIdPeriodo(resultado.getInt("idPeriodo"));
                periodo.setFechaInicio(resultado.getDate("fechaInicio"));
                periodo.setFechaFin(resultado.getDate("fechaFin"));
                periodo.setNombre(resultado.getString("nombre"));
            }
        }
        return periodo;
    }
    
    public static ResultSet obtenerSemestres(Connection conexionBD) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT * FROM semestre";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerExperienciasEducativas(Connection conexionBD) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT * FROM catalogo_experiencia_educativa";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerProfesoresPorExperiencia(Connection conexionBD, int idExperienciaEducativa) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT cp.* " +
                              "FROM catalogo_profesor cp " +
                              "INNER JOIN oferta_academica oa ON cp.idCatalogoProfesor = oa.idCatalogoProfesor " +
                              "WHERE oa.idCatalogoEE = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idExperienciaEducativa);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}