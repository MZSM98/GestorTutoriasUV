package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.ReporteTutoriaDAO;
import com.gtuv.modelo.pojo.ReporteTutoria;
import com.gtuv.utlidad.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ReporteTutoriaImpl {
    
    private ReporteTutoriaImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    public static HashMap<String, Object> obtenerReportesPorTutor(int idTutor, int idProgramaEducativo){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = ReporteTutoriaDAO.obtenerReportesPorTutorYPrograma(ConexionBD.abrirConexion(), idTutor, idProgramaEducativo);
            ArrayList<ReporteTutoria> reportes = new ArrayList<>();
            
            while(resultado.next()){
                ReporteTutoria reporte = new ReporteTutoria();
                reporte.setIdReporteTutoria(resultado.getInt("idReporteTutoria"));
                reporte.setIdTutor(resultado.getInt("idTutor"));
                reporte.setIdSesion(resultado.getInt("idSesion"));
                reporte.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                reporte.setDescripcionGeneral(resultado.getString("descripcionGeneral"));
                reporte.setEstado(resultado.getString("estado"));
                reporte.setFechaEntrega(resultado.getTimestamp("fechaEntrega"));
                reporte.setNumeroSesion(resultado.getInt("numeroSesion"));
                reporte.setFechaSesion(resultado.getString("fechaSesion"));
                reporte.setTotalAsistencia(resultado.getInt("totalAsistencia"));
                reporte.setTotalRiesgo(resultado.getInt("totalRiesgo"));
                
                reportes.add(reporte);
            }
            respuesta.put("error", false);
            respuesta.put("reportes", reportes);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
}