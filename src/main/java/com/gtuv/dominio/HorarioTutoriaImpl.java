/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.HorarioTutoriasDAO;
import com.gtuv.modelo.pojo.HorarioTutor;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author gurov
 */

public class HorarioTutoriaImpl {
    
    public static HashMap<String, Object> registrarHorario(HorarioTutor horario) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            try {
             
                int filasAfectadas = HorarioTutoriasDAO.registrarHorario(conexionBD, horario);
                
              
                if (filasAfectadas > 0) {
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Horario registrado correctamente.");
                } else {
                    respuesta.put("mensaje", "No se pudo registrar el horario. Intente nuevamente.");
                }
            } catch (SQLException e) {
                respuesta.put("mensaje", "Error en la base de datos: " + e.getMessage());
            } finally {
                
                ConexionBD.cerrarConexionBD();
            }
        } else {
            
            respuesta.put("mensaje", Utilidades.ERROR_BD);
        }
        return respuesta;
    }

    public static HashMap<String, Object> actualizarHorario(HorarioTutor horario) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            try {
                
                int filasAfectadas = HorarioTutoriasDAO.actualizarHorario(conexionBD, horario);
                
               
                if (filasAfectadas > 0) {
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Horario actualizado correctamente.");
                } else {
                    respuesta.put("mensaje", "No se pudo actualizar el horario.");
                }
            } catch (SQLException e) {
                respuesta.put("mensaje", "Error al actualizar: " + e.getMessage());
            } finally {
             
                ConexionBD.cerrarConexionBD();
            }
        } else {
            respuesta.put("mensaje", Utilidades.ERROR_BD);
        }
        return respuesta;
    }
}
