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
    
    public static ArrayList<ProgramaEducativo> obtenerProgramasEducativos(Connection conexionBD) throws SQLException {
        ArrayList<ProgramaEducativo> programas = new ArrayList<>();
        if (conexionBD != null) {
            String consulta = "SELECT idProgramaEducativo, nombre FROM programa_educativo";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                ProgramaEducativo programa = new ProgramaEducativo();
                programa.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                programa.setNombre(resultado.getString("nombre"));
                programas.add(programa);
            }
            return programas;
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }

    public static ArrayList<SesionTutoria> obtenerSesionesPorPrograma(Connection conexionBD, int idPrograma, int idTutor) throws SQLException {
        ArrayList<SesionTutoria> sesiones = new ArrayList<>();
        if (conexionBD != null) {
            // LEFT JOIN para traer el horario específico de ESTE tutor si existe
            String consulta = "SELECT s.*, h.idHorario, h.horaInicio " +
                              "FROM sesion_tutoria s " +
                              "INNER JOIN periodo_escolar p ON s.idPeriodo = p.idPeriodo " +
                              "LEFT JOIN horario_tutor h ON s.idSesion = h.idSesion AND h.idTutor = ? " +
                              "WHERE s.idProgramaEducativo = ? " +
                              "AND CURDATE() BETWEEN p.fechaInicio AND p.fechaFin";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idTutor);
            sentencia.setInt(2, idPrograma);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
               
                SesionTutoria sesion = new SesionTutoria();
                sesion.setIdSesion(resultado.getInt("idSesion"));
                sesion.setIdPeriodo(resultado.getInt("idPeriodo"));
                sesion.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                sesion.setNumeroSesion(resultado.getInt("numeroSesion"));
                sesion.setFechaSesion(resultado.getDate("fechaSesion"));
                sesion.setEstado(resultado.getString("estado"));
                
                
                int idHorario = resultado.getInt("idHorario");
                if (!resultado.wasNull() && idHorario > 0) {
                    HorarioTutor horario = new HorarioTutor();
                    horario.setIdHorario(idHorario);
                    horario.setIdSesion(sesion.getIdSesion());
                    horario.setIdTutor(idTutor);
                    horario.setHoraInicio(resultado.getTime("horaInicio"));
                    
                  
                    sesion.setHorario(horario); 
                } else {
                    sesion.setHorario(null);
                }

                sesiones.add(sesion);
            }
            return sesiones;
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
   
}
    
