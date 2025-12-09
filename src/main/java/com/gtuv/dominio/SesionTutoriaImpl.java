/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.SesionTutoriaDAO;
import com.gtuv.modelo.pojo.HorarioTutor;
import com.gtuv.modelo.pojo.SesionTutoria;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author gurov
 */


public class SesionTutoriaImpl {

    public static HashMap<String, Object> obtenerSesionesPorPrograma(int idPrograma, int idTutor) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        Connection conexionBD = ConexionBD.abrirConexion();
        ResultSet resultado = null; 

        if (conexionBD != null) {
            try {
               
                resultado = SesionTutoriaDAO.obtenerSesionesPorPrograma(conexionBD, idPrograma, idTutor);
                
                List<SesionTutoria> sesiones = new ArrayList<>();

                
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

                respuesta.put("error", false);
                respuesta.put("sesiones", sesiones);

            } catch (SQLException e) {
                respuesta.put("mensaje", "Error al consultar sesiones: " + e.getMessage());
            } finally {
               
                try {
                    if (resultado != null) resultado.close();
                    ConexionBD.cerrarConexionBD();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            respuesta.put("mensaje", "No hay conexión con la base de datos.");
        }

        return respuesta;
    }
}