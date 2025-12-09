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
}