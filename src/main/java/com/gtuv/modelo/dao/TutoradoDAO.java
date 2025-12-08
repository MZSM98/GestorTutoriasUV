/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.modelo.dao;

import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gurov
 */

public class TutoradoDAO {

    private TutoradoDAO() {
    }

    public static ResultSet obtenerTutoradosPorPrograma(Connection conexionBD, int idProgramaEducativo) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT t.*, s.nombre AS nombreSemestre " +
                              "FROM tutorado t " +
                              "INNER JOIN semestre s ON t.idSemestre = s.idSemestre " +
                              "WHERE t.idProgramaEducativo = ? AND t.activo = 1 " +
                              "ORDER BY t.apellidoPaterno ASC";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idProgramaEducativo);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}
