package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
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
        Connection conexion = ConexionBD.abrirConexion();
        try {
            ResultSet rs = TutoradoDAO.obtenerTutorados(conexion);
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
                // Asegúrate de que tu consulta SQL traiga estos campos si los necesitas mostrar
                // tutorado.setIdSemestre(rs.getInt("idSemestre")); 
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
        Connection conexion = ConexionBD.abrirConexion();
        try {
            ResultSet rs = TutoradoDAO.obtenerTutoradosPorProgramaEducativo(conexion, idProgramaEducativo);
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
        Connection conexion = ConexionBD.abrirConexion();
        try {
            boolean existe = TutoradoDAO.verificarMatricula(conexion, matricula);
            respuesta.put("error", false);
            respuesta.put("existe", existe);
            if (existe) {
                respuesta.put("mensaje", "La matrícula ya se encuentra registrada.");
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
        Connection conexion = ConexionBD.abrirConexion();
        try {
            boolean existe = TutoradoDAO.verificarCorreo(conexion, correo);
            respuesta.put("error", false);
            respuesta.put("existe", existe);
            if (existe) {
                respuesta.put("mensaje", "El correo ya se encuentra registrado.");
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