package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TutoradoDAO {

    private TutoradoDAO() {
    }

    public static int registrar(Connection conexionBD, Tutorado tutorado) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO tutorado (matricula, nombre, apellidoPaterno, apellidoMaterno, correo, idSemestre, idProgramaEducativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion);
            sentencia.setString(1, tutorado.getMatricula());
            sentencia.setString(2, tutorado.getNombre());
            sentencia.setString(3, tutorado.getApellidoPaterno());
            sentencia.setString(4, tutorado.getApellidoMaterno());
            sentencia.setString(5, tutorado.getCorreo());
            sentencia.setInt(6, tutorado.getIdSemestre());
            sentencia.setInt(7, tutorado.getIdProgramaEducativo());
            
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static int editar(Connection conexionBD, Tutorado tutorado) throws SQLException {
        if (conexionBD != null) {
            String actualizacion = "UPDATE tutorado SET matricula = ?, nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, idSemestre = ?, idProgramaEducativo = ? WHERE idTutorado = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setString(1, tutorado.getMatricula());
            sentencia.setString(2, tutorado.getNombre());
            sentencia.setString(3, tutorado.getApellidoPaterno());
            sentencia.setString(4, tutorado.getApellidoMaterno());
            sentencia.setString(5, tutorado.getCorreo());
            sentencia.setInt(6, tutorado.getIdSemestre());
            sentencia.setInt(7, tutorado.getIdProgramaEducativo());
            sentencia.setInt(8, tutorado.getIdTutorado());
            
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static int darDeBaja(Connection conexionBD, int idTutorado) throws SQLException {
        if (conexionBD != null) {
            String eliminacionLogica = "UPDATE tutorado SET activo = 0 WHERE idTutorado = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(eliminacionLogica);
            sentencia.setInt(1, idTutorado);
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static ResultSet obtenerTodosLosTutorados(Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT t.*, pe.nombre AS nombreProgramaEducativo, s.nombre AS nombreSemestre " +
                              "FROM tutorado t " +
                              "INNER JOIN programa_educativo pe ON t.idProgramaEducativo = pe.idProgramaEducativo " +
                              "INNER JOIN semestre s ON t.idSemestre = s.idSemestre " +
                              "WHERE t.activo = 1";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerTutoradosPorProgramaEducativo(Connection conexionBD, int idProgramaEducativo) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT * FROM tutorado WHERE idProgramaEducativo = ? AND activo = 1";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static boolean verificarMatricula(Connection conexionBD, String matricula) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT idTutorado FROM tutorado WHERE matricula = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, matricula);
            return sentencia.executeQuery().next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean verificarCorreo(Connection conexionBD, String correo) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT idTutorado FROM tutorado WHERE correo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, correo);
            return sentencia.executeQuery().next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerTutoradosDisponibles(Connection conexionBD) throws SQLException {
        if(conexionBD != null){
            String consulta = "SELECT t.*, s.nombre AS nombreSemestre " +
                              "FROM tutorado t " +
                              "INNER JOIN semestre s ON t.idSemestre = s.idSemestre " +
                              "WHERE t.idTutorado NOT IN (SELECT idTutorado FROM asignacion_tutor) " +
                              "AND t.activo = 1";

            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}