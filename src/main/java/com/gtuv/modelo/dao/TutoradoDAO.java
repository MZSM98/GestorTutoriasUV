
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.modelo.dao;





import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;


/**
 *
 * @author gurov
 */

public class TutoradoDAO {

    public static List<Tutorado> obtenerTutoradosPorPrograma(int idProgramaEducativo) throws SQLException {
        List<Tutorado> tutorados = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            try {
                
                String consulta = "SELECT t.*, s.nombre AS nombreSemestre " +
                                  "FROM tutorado t " +
                                  "INNER JOIN semestre s ON t.idSemestre = s.idSemestre " +
                                  "WHERE t.idProgramaEducativo = ? AND t.activo = 1 " +
                                  "ORDER BY t.apellidoPaterno ASC";

                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idProgramaEducativo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Tutorado tutorado = new Tutorado();
                    tutorado.setIdTutorado(rs.getInt("idTutorado"));
                    tutorado.setMatricula(rs.getString("matricula"));
                    tutorado.setNombre(rs.getString("nombre"));
                    tutorado.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    tutorado.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    tutorado.setCorreo(rs.getString("correo"));
                    
                    tutorado.setNombreSemestre(rs.getString("nombreSemestre")); 
                    
                    tutorados.add(tutorado);
                }
            } catch (SQLException e) {
                System.err.println("Error al consultar tutorados: " + e.getMessage());
                throw e;
            } finally {
                ConexionBD.cerrarConexionBD(); 
            }
        }
        return tutorados;


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

    public static ResultSet obtenerTutorados(Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT t.*, pe.nombre as nombreProgramaEducativo " +
                              "FROM tutorado t " +
                              "INNER JOIN programa_educativo pe ON t.idProgramaEducativo = pe.idProgramaEducativo " +
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
}