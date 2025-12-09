/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gtuv.modelo.dao;


import com.gtuv.modelo.pojo.HorarioTutor;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.SesionTutoria;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author gurov
 */

public class SesionTutoriaDAO {

    private SesionTutoriaDAO() {
    }
    
public static ResultSet obtenerProgramasEducativos(Connection conexionBD) throws SQLException {
    if (conexionBD != null) {
        String consulta = "SELECT idProgramaEducativo, nombre FROM programa_educativo"; // [cite: 26]
        
        PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
        
        return sentencia.executeQuery();
    }
    throw new SQLException(Utilidades.ERROR_BD); 
}

public static ResultSet obtenerSesionesPorPrograma(Connection conexionBD, int idPrograma, int idTutor) throws SQLException {
    if (conexionBD != null) {
        String consulta = "SELECT s.*, h.idHorario, h.horaInicio " +
                          "FROM sesion_tutoria s " +
                          "INNER JOIN periodo_escolar p ON s.idPeriodo = p.idPeriodo " +
                          "LEFT JOIN horario_tutor h ON s.idSesion = h.idSesion AND h.idTutor = ? " +
                          "WHERE s.idProgramaEducativo = ? " +
                          "AND CURDATE() BETWEEN p.fechaInicio AND p.fechaFin";
        
        PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
        sentencia.setInt(1, idTutor);
        sentencia.setInt(2, idPrograma);
        
        return sentencia.executeQuery();
    }
    throw new SQLException(Utilidades.ERROR_BD); 
}
   
}
    
