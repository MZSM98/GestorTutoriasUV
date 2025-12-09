
package com.gtuv.modelo.dao;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.pojo.PeriodoEscolar;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CatalogoDAO {
    
    public static List<ProgramaEducativo> obtenerProgramasEducativos() throws SQLException {
        List<ProgramaEducativo> lista = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexion();
        
        if (conexion != null) {
            try {
                String consulta = "SELECT idProgramaEducativo, nombre FROM programa_educativo ORDER BY nombre ASC";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    ProgramaEducativo pe = new ProgramaEducativo();
                    pe.setIdProgramaEducativo(rs.getInt("idProgramaEducativo"));
                    pe.setNombre(rs.getString("nombre"));
                    lista.add(pe);
                }
            } catch (SQLException e) {
                System.err.println("Error en CatalogoDAO (Programas): " + e.getMessage());
                throw e;
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return lista;
    }
    
 
    
}
