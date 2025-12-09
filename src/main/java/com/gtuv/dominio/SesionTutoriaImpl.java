package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.SesionTutoriaDAO;
import com.gtuv.modelo.pojo.SesionTutoria;
import com.gtuv.utlidad.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SesionTutoriaImpl {
    
    private SesionTutoriaImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    public static HashMap<String, Object> obtenerSesionesPorPeriodo(int idPeriodo, int idProgramaEducativo){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = SesionTutoriaDAO.obtenerSesionesPorPeriodo(ConexionBD.abrirConexion(), idPeriodo, idProgramaEducativo);
            ArrayList<SesionTutoria> sesiones = new ArrayList<>();
            
            while(resultado.next()){
                SesionTutoria sesion = new SesionTutoria();
                sesion.setIdSesion(resultado.getInt("idSesion"));
                sesion.setIdPeriodo(resultado.getInt("idPeriodo"));
                sesion.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                sesion.setNumeroSesion(resultado.getInt("numeroSesion"));
                sesion.setFechaSesion(resultado.getString("fechaSesion")); // Recuperamos como String
                sesion.setEstado(resultado.getString("estado"));
                
                sesiones.add(sesion);
            }
            
            respuesta.put("error", false);
            respuesta.put("sesiones", sesiones);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> registrarSesion(SesionTutoria sesion){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            int resultado = SesionTutoriaDAO.registrarSesion(ConexionBD.abrirConexion(), sesion);
            if(resultado > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "La sesión de tutoría se registró correctamente.");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo registrar la sesión.");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> editarSesion(SesionTutoria sesion){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            int resultado = SesionTutoriaDAO.editarSesion(ConexionBD.abrirConexion(), sesion);
            if(resultado > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "La sesión de tutoría se actualizó correctamente.");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo actualizar la sesión.");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> modificarEstadoSesion(int idSesion, String estado){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            int resultado = SesionTutoriaDAO.modificarEstadoSesion(ConexionBD.abrirConexion(), idSesion, estado);
            if(resultado > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "El estado de la sesión se actualizó correctamente.");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo actualizar el estado de la sesión.");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
}