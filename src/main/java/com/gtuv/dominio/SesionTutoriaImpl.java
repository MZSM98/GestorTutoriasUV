/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.SesionTutoriaDAO;
import com.gtuv.modelo.pojo.SesionTutoria;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author gurov
 */


public class SesionTutoriaImpl {

    public static HashMap<String, Object> obtenerSesionesPorPrograma(int idPrograma, int idTutor) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                
                ArrayList<SesionTutoria> sesiones = SesionTutoriaDAO.obtenerSesionesPorPrograma(conexionBD, idPrograma, idTutor);
                
                respuesta.put("error", false);
                respuesta.put("sesiones", sesiones);
                
            } catch (SQLException e) {
                respuesta.put("mensaje", "Error al consultar las sesiones: " + e.getMessage());
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        } else {
            respuesta.put("mensaje", "No hay conexión con la base de datos.");
        }

        return respuesta;
    }
}