package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.ProgramaEducativoDAO;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProgramaEducativoImpl {
    
    private ProgramaEducativoImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    public static HashMap<String, Object> obtenerProgramasEducativos(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            ResultSet resultado = ProgramaEducativoDAO.obtenerProgramasEducativos(ConexionBD.abrirConexion());
            ArrayList<ProgramaEducativo> programas = new ArrayList<>();
            
            while (resultado.next()){
                ProgramaEducativo programa = new ProgramaEducativo();
                programa.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                programa.setNombre(resultado.getString("nombre"));
                programa.setIdJefeCarrera(resultado.getInt("idJefeCarrera"));
                programa.setIdCoordinador(resultado.getInt("idCoordinador"));
                programas.add(programa);
            }
            respuesta.put("error", false);
            respuesta.put("programas", programas);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> asignarJefeCarrera(int idProgramaEducativo, int idUsuario){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            if(ProgramaEducativoDAO.hayJefeCarreraAsignado(ConexionBD.abrirConexion(), idProgramaEducativo)){
                respuesta.put("error", true);
                respuesta.put("mensaje", "El programa educativo seleccionado ya cuenta con un Jefe de Carrera asignado.");
            }else{
                int filasAfectadas = ProgramaEducativoDAO.asignarJefeCarrera(ConexionBD.abrirConexion(), idProgramaEducativo, idUsuario);
                if (filasAfectadas > 0){
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Jefe de Carrera asignado correctamente.");
                }else{
                    respuesta.put("error", true);
                    respuesta.put("mensaje", "No se pudo asignar el Jefe de Carrera, inténtelo más tarde.");
                }
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> asignarCoordinador(int idProgramaEducativo, int idUsuario){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        try{
            if(ProgramaEducativoDAO.hayCoordinadorAsignado(conexionBD, idProgramaEducativo)){
                respuesta.put("error", true);
                respuesta.put("mensaje", "El programa educativo seleccionado ya cuenta con un Coordinador asignado.");
            }else{
                int filasAfectadas = ProgramaEducativoDAO.asignarCoordinador(conexionBD, idProgramaEducativo, idUsuario);
                if (filasAfectadas > 0){
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Coordinador asignado correctamente.");
                }else{
                    respuesta.put("error", true);
                    respuesta.put("mensaje", "No se pudo asignar el Coordinador, inténtelo más tarde.");
                }
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