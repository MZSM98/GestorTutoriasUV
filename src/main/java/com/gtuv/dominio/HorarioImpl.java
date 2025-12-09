/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.HorarioDAO;
import com.gtuv.modelo.pojo.HorarioTutor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author gurov
 */

public class HorarioImpl {
    
    public static HashMap<String, Object> registrarHorario(HorarioTutor horario) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                // Llamamos al DAO para insertar
                boolean exito = HorarioDAO.registrarHorario(conexionBD, horario);
                
                if (exito) {
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
            respuesta.put("mensaje", "No hay conexión con la base de datos.");
        }
        return respuesta;
    }

    public static HashMap<String, Object> actualizarHorario(HorarioTutor horario)  {
        
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        Connection conexionBD = ConexionBD.abrirConexion();
   if (conexionBD != null) {
            try {
               
                boolean exito = HorarioDAO.actualizarHorario(conexionBD, horario);
                
                if (exito) {
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
            respuesta.put("mensaje", "No hay conexión con la base de datos.");
        }
        return respuesta;
    }
}