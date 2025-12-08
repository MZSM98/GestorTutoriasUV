package com.gtuv.modelo.dao;

import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TutorDAO {

    private TutorDAO() {
    }

    public static ResultSet obtenerTutores(Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT u.idUsuario, u.noTrabajador, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, " +
                              "(SELECT COUNT(*) FROM asignacion_tutor a WHERE a.idTutor = u.idUsuario) AS totalTutorados " +
                              "FROM usuario u " +
                              "WHERE u.activo = 1 AND u.esTutor = 1";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }
        throw new SQLException(Utilidades.ERROR_BD);
    }
    
}