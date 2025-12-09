/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.ProblematicaAcademicaDAO;
import com.gtuv.modelo.pojo.ProblematicaAcademica;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author gurov
 */
public class ProblematicaAcademicaImpl {
    private ProblematicaAcademicaImpl() {
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }

    public static HashMap<String, Object> registrarProblematica(ProblematicaAcademica problematica) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexion = ConexionBD.abrirConexion();
        try {
            int resultado = ProblematicaAcademicaDAO.registrar(conexion, problematica);
            if (resultado > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "La problemática ha sido registrada correctamente.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo registrar la problemática.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> editarProblematica(ProblematicaAcademica problematica) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexion = ConexionBD.abrirConexion();
        try {
            int resultado = ProblematicaAcademicaDAO.editar(conexion, problematica);
            if (resultado > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "La problemática ha sido actualizada correctamente.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo actualizar la problemática.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    
    }
}
