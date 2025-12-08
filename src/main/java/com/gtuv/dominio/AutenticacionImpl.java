
package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.ProgramaEducativoDAO;
import com.gtuv.modelo.dao.UsuarioDAO;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Encriptacion;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class AutenticacionImpl {
    private AutenticacionImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    public static HashMap<String, Object> autenticarUsuario(String noTrabajador, String password) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("error", true);
        
        Connection conexion = ConexionBD.abrirConexion();
        try {
            Usuario usuario = UsuarioDAO.obtenerUsuarioLogin(conexion, noTrabajador);
            if (usuario == null) {
                respuesta.put("mensaje", "Credenciales incorrectas."); // Por seguridad no decimos "usuario no existe"
                return respuesta;
            }
            if (!Encriptacion.checkPassword(password, usuario.getContrasenia())) {
                respuesta.put("mensaje", "La contraseña es incorrecta.");
                return respuesta;
            }
            cargarRolesProgramaEducativo(conexion, usuario);
            respuesta.put("error", false);
            respuesta.put("mensaje", "Credenciales correctas");
            respuesta.put("usuario", usuario);

        } catch (SQLException ex) {
            respuesta.put("mensaje", "Error de base de datos: " + ex.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        
        return respuesta;
    }
    
    private static void cargarRolesProgramaEducativo(Connection conexion, Usuario usuario) throws SQLException {
        ProgramaEducativo programa = ProgramaEducativoDAO.obtenerProgramaPorUsuario(conexion, usuario.getIdUsuario());
        
        if (programa != null) {
            if (programa.getIdJefeCarrera() == usuario.getIdUsuario()) {
                usuario.setEsJefeCarrera(true);
            }
            if (programa.getIdCoordinador() == usuario.getIdUsuario()) {
                usuario.setEsCoordinador(true);
            }
        }
    }
}
