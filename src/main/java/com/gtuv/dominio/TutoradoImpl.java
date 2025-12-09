package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.TutorDAO;
import com.gtuv.modelo.dao.TutoradoDAO;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TutoradoImpl {

    private TutoradoImpl() {
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }

    public static HashMap<String, Object> registrarTutorado(Tutorado tutorado) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexion = ConexionBD.abrirConexion();
        try {
            int resultado = TutoradoDAO.registrar(conexion, tutorado);
            if (resultado > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "El tutorado " + tutorado.getNombre() + " fue registrado correctamente.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo registrar el tutorado.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> editarTutorado(Tutorado tutorado) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexion = ConexionBD.abrirConexion();
        try {
            int resultado = TutoradoDAO.editar(conexion, tutorado);
            if (resultado > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "La información del tutorado " + tutorado.getNombre() + " ha sido actualizada.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo actualizar la información del tutorado.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> darBajaTutorado(int idTutorado) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexion = ConexionBD.abrirConexion();
        try {
            int resultado = TutoradoDAO.darDeBaja(conexion, idTutorado);
            if (resultado > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "El tutorado ha sido dado de baja correctamente.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo dar de baja al tutorado.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerTutorados() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            ResultSet rs = TutoradoDAO.obtenerTodosLosTutorados(ConexionBD.abrirConexion());
            ArrayList<Tutorado> listaTutorados = new ArrayList<>();
            while (rs.next()) {
                Tutorado tutorado = new Tutorado();
                tutorado.setIdTutorado(rs.getInt("idTutorado"));
                tutorado.setMatricula(rs.getString("matricula"));
                tutorado.setNombre(rs.getString("nombre"));
                tutorado.setApellidoPaterno(rs.getString("apellidoPaterno"));
                tutorado.setApellidoMaterno(rs.getString("apellidoMaterno"));
                tutorado.setCorreo(rs.getString("correo"));
                tutorado.setIdProgramaEducativo(rs.getInt("idProgramaEducativo"));
                tutorado.setNombreProgramaEducativo(rs.getString("nombreProgramaEducativo"));
                tutorado.setIdSemestre(rs.getInt("idSemestre")); 
                tutorado.setActivo(rs.getBoolean("activo"));
                
                listaTutorados.add(tutorado);
            }
            respuesta.put("error", false);
            respuesta.put("tutorados", listaTutorados);
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerTutoradosPorProgramaEducativo(int idProgramaEducativo) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            ResultSet rs = TutoradoDAO.obtenerTutoradosPorProgramaEducativo(ConexionBD.abrirConexion(), idProgramaEducativo);
            ArrayList<Tutorado> listaTutorados = new ArrayList<>();
            while (rs.next()) {
                Tutorado tutorado = new Tutorado();
                tutorado.setIdTutorado(rs.getInt("idTutorado"));
                tutorado.setMatricula(rs.getString("matricula"));
                tutorado.setNombre(rs.getString("nombre"));
                tutorado.setApellidoPaterno(rs.getString("apellidoPaterno"));
                tutorado.setApellidoMaterno(rs.getString("apellidoMaterno"));
                tutorado.setCorreo(rs.getString("correo"));
                tutorado.setIdProgramaEducativo(rs.getInt("idProgramaEducativo"));
                tutorado.setActivo(rs.getBoolean("activo"));
                
                listaTutorados.add(tutorado);
            }
            respuesta.put("error", false);
            respuesta.put("tutorados", listaTutorados);
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> verificarMatricula(String matricula) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            boolean existe = TutoradoDAO.verificarMatricula(ConexionBD.abrirConexion(), matricula);
            respuesta.put("error", false);
            respuesta.put("existe", existe);
            if (existe) {
                respuesta.put("etiqueta", "La matrícula ya está en uso.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public static HashMap<String, Object> verificarCorreo(String correo) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            boolean existe = TutoradoDAO.verificarCorreo(ConexionBD.abrirConexion(), correo);
            respuesta.put("error", false);
            respuesta.put("existe", existe);
            if (existe) {
                respuesta.put("mensaje", "El correo ya está en uso.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerTutoradosDisponibles(){
        return obtenerListaTutorados(false, 0);
    }
    
    public static HashMap<String, Object> obtenerTutoradosPorTutor(int idTutor){
        return obtenerListaTutorados(true, idTutor);
    }
    
    private static HashMap<String, Object> obtenerListaTutorados(boolean porTutor, int idTutor){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado;
            if(porTutor){
                resultado = TutorDAO.obtenerTutoradosPorTutor(ConexionBD.abrirConexion(), idTutor);
            } else {
                resultado = TutoradoDAO.obtenerTutoradosDisponibles(ConexionBD.abrirConexion());
            }
            
            ArrayList<Tutorado> tutorados = new ArrayList<>();
            while(resultado.next()){
                Tutorado tutorado = new Tutorado();
                tutorado.setIdTutorado(resultado.getInt("idTutorado"));
                tutorado.setNombre(resultado.getString("nombre"));
                tutorado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                tutorado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                tutorado.setNombreSemestre(resultado.getString("nombreSemestre"));
                tutorado.setMatricula(resultado.getString("matricula"));
                tutorados.add(tutorado);
            }
            respuesta.put("error", false);
            respuesta.put("tutorados", tutorados);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
}