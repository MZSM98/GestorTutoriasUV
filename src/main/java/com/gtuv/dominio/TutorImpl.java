package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.TutorDAO;
import com.gtuv.modelo.pojo.Tutor;
import com.gtuv.utlidad.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TutorImpl {
    
    private TutorImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }

    public static HashMap<String, Object> obtenerTutores() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try {
            ResultSet resultado = TutorDAO.obtenerTutores(ConexionBD.abrirConexion());
            ArrayList<Tutor> tutores = new ArrayList<>();
            
            while (resultado.next()) {
                Tutor tutor = new Tutor();
                
                tutor.setIdUsuario(resultado.getInt("idUsuario"));
                tutor.setNoTrabajador(resultado.getString("noTrabajador"));
                tutor.setNombre(resultado.getString("nombre"));
                tutor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                tutor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                tutor.setCorreo(resultado.getString("correo"));
                
                tutor.setNoTutorados(resultado.getInt("totalTutorados"));
                
                tutores.add(tutor);
            }
            respuesta.put("error", false);
            respuesta.put("tutores", tutores);
        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> asignarTutorado(int idTutor, int idTutorado){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            int resultado = TutorDAO.asignarTutorado(ConexionBD.abrirConexion(), idTutor, idTutorado);
            if(resultado > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "Tutorado asignado correctamente.");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo asignar el tutorado.");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> desasignarTutorado(int idTutor, int idTutorado){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            int resultado = TutorDAO.desasignarTutorado(ConexionBD.abrirConexion(), idTutor, idTutorado);
            if(resultado > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "Tutorado desasignado correctamente.");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo desasignar el tutorado.");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerTutoresPorProgramaEducativo(int idProgramaEducativo) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try {
            ResultSet resultado = TutorDAO.obtenerTutoresPorProgramaEducativo(ConexionBD.abrirConexion(), idProgramaEducativo);
            ArrayList<Tutor> tutores = new ArrayList<>();
            
            while (resultado.next()) {
                Tutor tutor = new Tutor();
                tutor.setIdUsuario(resultado.getInt("idUsuario"));
                tutor.setNoTrabajador(resultado.getString("noTrabajador"));
                tutor.setNombre(resultado.getString("nombre"));
                tutor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                tutor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                tutor.setCorreo(resultado.getString("correo"));
                
                tutores.add(tutor);
            }
            respuesta.put("error", false);
            respuesta.put("tutores", tutores);
        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
}