package com.gtuv.modelo.dao;

import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    private UsuarioDAO(){
        
    }
    
    public static int registrar(Connection conexionBD, Usuario usuario) throws SQLException {
        int idGenerado = 0;
        if (conexionBD != null) {
            String insercion = "INSERT INTO usuario (noTrabajador, nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia, esAdministrador, esTutor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement sentencia = conexionBD.prepareStatement(insercion, Statement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, usuario.getNoTrabajador());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getApellidoPaterno());
            sentencia.setString(4, usuario.getApellidoMaterno());
            sentencia.setString(5, usuario.getCorreo());
            sentencia.setString(6, usuario.getContrasenia());
            sentencia.setBoolean(7, usuario.isEsAdministrador());
            sentencia.setBoolean(8, usuario.isEsTutor());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet generatedKeys = sentencia.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1);
                }
                return idGenerado; 
            }
        }
        throw new SQLException("No se pudo registrar el usuario en la base de datos.");
    }
    
    public static int editar(Connection conexionBD, Usuario usuario) throws SQLException{
        
        if(conexionBD != null){
            String actualizacion = "UPDATE usuario SET noTrabajador = ?, nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, contrasenia = ?, esAdministrador = ?, esTutor = ? WHERE idUsuario = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setString(1, usuario.getNoTrabajador());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getApellidoPaterno());
            sentencia.setString(4, usuario.getApellidoMaterno());
            sentencia.setString(5, usuario.getCorreo());
            sentencia.setString(6, usuario.getContrasenia());
            sentencia.setBoolean(7, usuario.isEsAdministrador());
            sentencia.setBoolean(8, usuario.isEsTutor());
            sentencia.setInt(9, usuario.getIdUsuario());
            
            sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static int darDeBaja(Connection conexionBD, int idUsuario) throws SQLException{
        
        if(conexionBD != null){
            String eliminacionLogica = "UPDATE usuario SET activo = 0 WHERE idUsuario = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(eliminacionLogica);
            sentencia.setInt(1, idUsuario);
            return sentencia.executeUpdate();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static ResultSet obtenerUsuarios(Connection conexionBD) throws SQLException{
        
        if(conexionBD != null){
            String consulta = "SELECT * FROM usuario where activo = 1";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean verificarCorreo(Connection conexionBD, String correo) throws SQLException{
        
        if(conexionBD != null){
            String consulta = "SELECT idUsuario FROM usuario WHERE correo = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, correo);
            return sentencia.executeQuery().next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
    public static boolean verificarNumeroTrabajador(Connection conexionBD, String numeroTrabajador) throws SQLException{
        
        if(conexionBD != null){
            String consulta = "SELECT idUsuario FROM usuario WHERE noTrabajador = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, numeroTrabajador);
            return sentencia.executeQuery().next();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
}