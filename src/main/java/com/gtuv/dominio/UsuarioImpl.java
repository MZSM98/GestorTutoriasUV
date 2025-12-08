
package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.UsuarioDAO;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class UsuarioImpl {
    
    private UsuarioImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    public static HashMap<String, Object> registrarUsuario(Usuario usuario){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            int filasAfectadas = UsuarioDAO.registrar(ConexionBD.abrirConexion(), usuario);
            
            if (filasAfectadas > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "El registro del Usuario" + usuario.getNombre() +
                              " fue guardado de manera exitosa");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo guardar la información, inténtelo más tarde");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> editarUsuario(Usuario usuario){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            int filasAfectadas = UsuarioDAO.editar(ConexionBD.abrirConexion(), usuario);
            
            if (filasAfectadas > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "El registro del Usuario" + usuario.getNombre() +
                              " fue guardado de manera exitosa");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo guardar la información, inténtelo más tarde");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> darBajaUsuario(int idUsuario){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            int filasAfectadas = UsuarioDAO.darDeBaja(ConexionBD.abrirConexion(), idUsuario);
            
            if (filasAfectadas > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "El usuario fue dado de baja de manera exitosa");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo dar de baja al usuario, inténtelo más tarde");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;        
    }
    
    public static HashMap<String, Object> obtenerUsuarios(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            ResultSet resultado = UsuarioDAO.obtenerUsuarios(ConexionBD.abrirConexion());
            ArrayList<Usuario> usuarios = new ArrayList<>();
            
            while (resultado.next()){
                Usuario usuario = new Usuario ();
                usuario.setIdUsuario(resultado.getInt("idUsuario"));
                usuario.setNoTrabajador(resultado.getString("noTrabajador"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                usuario.setCorreo(resultado.getString("correo"));
                usuario.setContrasenia(resultado.getString("contrasenia"));
                usuario.setEsTutor(resultado.getBoolean("esTutor"));
                usuario.setEsAdministrador(resultado.getBoolean("esAdministrador"));
                usuario.setActivo(resultado.getBoolean("activo"));
                usuarios.add(usuario);
            }
            respuesta.put("error", false);
            respuesta.put("usuarios", usuarios);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> verificarNumeroTrabajador(String noTrabajador){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            boolean existe = UsuarioDAO.verificarNumeroTrabajador(ConexionBD.abrirConexion(), noTrabajador);
            
            if (!existe){
                respuesta.put("existe", false);
                respuesta.put("error", false);
                respuesta.put("etiqueta", "");
            }else{
                respuesta.put("error", false);
                respuesta.put("existe", true);
                respuesta.put("etiqueta", "Número de trabajador ya en uso");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;    
    } 
    
    public static HashMap<String, Object> verificarCorreo(String correo){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            boolean existe = UsuarioDAO.verificarCorreo(ConexionBD.abrirConexion(), correo);
            
            if (!existe){
                respuesta.put("error", false);
                respuesta.put("existe", false);
            }else{
                respuesta.put("error", false);
                respuesta.put("existe", true);
                respuesta.put("etiqueta", "El Correo ya está en uso");
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
