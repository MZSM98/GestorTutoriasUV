package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.ReporteGeneralDAO;
import com.gtuv.modelo.pojo.ProblematicaAcademica;
import com.gtuv.modelo.pojo.ReporteGeneral;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javafx.collections.ObservableList;

public class ReporteGeneralImpl {
    
    private ReporteGeneralImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    public static HashMap<String, Object> obtenerReportesPorCoordinador(int idCoordinador){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = ReporteGeneralDAO.obtenerReportesPorCoordinador(ConexionBD.abrirConexion(), idCoordinador);
            ArrayList<ReporteGeneral> reportes = new ArrayList<>();
            
            while(resultado.next()){
                ReporteGeneral reporte = new ReporteGeneral();
                reporte.setIdReporteGeneral(resultado.getInt("idReporteGeneral"));
                reporte.setIdCoordinador(resultado.getInt("idCoordinador"));
                reporte.setIdSesion(resultado.getInt("idSesion"));
                reporte.setNumeroSesion(resultado.getInt("numeroSesion"));
                reporte.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                reporte.setNombreProgramaEducativo(resultado.getString("nombrePrograma"));
                reporte.setFechaElaboracion(resultado.getString("fechaElaboracion"));
                reporte.setComentariosGenerales(resultado.getString("comentariosGenerales"));
                reporte.setTotalAsistentes(resultado.getInt("totalAsistentes"));
                reporte.setTotalEnRiesgo(resultado.getInt("totalEnRiesgo"));
                reporte.setEstatus(resultado.getString("estatus"));
                
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
    
    public static HashMap<String, Object> registrarReporte(ReporteGeneral reporte, ObservableList<ProblematicaAcademica> listaProblematicas){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexion = ConexionBD.abrirConexion(); 
        try{
            boolean existe = ReporteGeneralDAO.existeReporte(conexion, reporte.getIdSesion(), reporte.getIdProgramaEducativo());
            
            if(existe){
                respuesta.put("error", true);
                respuesta.put("mensaje", "Ya existe un reporte para esta sesión.");
            } else {
                int idReporte = ReporteGeneralDAO.registrar(conexion, reporte);
                
                if(idReporte > 0){
                    ReporteGeneralDAO.guardarSeleccionProblematicas(conexion, idReporte, listaProblematicas);
                    
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Reporte registrado correctamente.");
                }else{
                    respuesta.put("error", true);
                    respuesta.put("mensaje", "No se pudo registrar el encabezado del reporte.");
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
    
    public static HashMap<String, Object> editarReporte(ReporteGeneral reporte, ObservableList<ProblematicaAcademica> listaProblematicas) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        Connection conexion = ConexionBD.abrirConexion();
        try {
            int resultado = ReporteGeneralDAO.editar(conexion, reporte);
            
            if (resultado > 0) {
                ReporteGeneralDAO.guardarSeleccionProblematicas(conexion, reporte.getIdReporteGeneral(), listaProblematicas);
                respuesta.put("error", false);
                respuesta.put("mensaje", "El reporte general se actualizó correctamente.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo actualizar la información del reporte.");
            }
        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerReportePorId(int idReporte) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            ResultSet resultado = ReporteGeneralDAO.obtenerReportePorId(ConexionBD.abrirConexion(), idReporte);

            if (resultado.next()) {
                ReporteGeneral reporte = new ReporteGeneral();
                reporte.setIdReporteGeneral(resultado.getInt("idReporteGeneral"));
                reporte.setIdCoordinador(resultado.getInt("idCoordinador"));
                reporte.setIdSesion(resultado.getInt("idSesion"));
                reporte.setNumeroSesion(resultado.getInt("numeroSesion"));
                reporte.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                reporte.setNombreProgramaEducativo(resultado.getString("nombrePrograma"));
                reporte.setFechaElaboracion(resultado.getString("fechaElaboracion"));
                reporte.setComentariosGenerales(resultado.getString("comentariosGenerales"));
                reporte.setTotalAsistentes(resultado.getInt("totalAsistentes"));
                reporte.setTotalEnRiesgo(resultado.getInt("totalEnRiesgo"));
                reporte.setEstatus(resultado.getString("estatus"));

                respuesta.put("error", false);
                respuesta.put("reporte", reporte);
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se encontró el reporte solicitado.");
            }
        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerTutoradosEnRiesgoPorSesion(int idSesion, int idProgramaEducativo){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = ReporteGeneralDAO.obtenerTutoradosEnRiesgoPorSesion(ConexionBD.abrirConexion(), idSesion, idProgramaEducativo);
            ArrayList<Tutorado> tutorados = new ArrayList<>();
            
            while(resultado.next()){
                Tutorado tutorado = new Tutorado();
                tutorado.setMatricula(resultado.getString("matricula"));
                tutorado.setNombre(resultado.getString("nombre"));
                tutorado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                tutorado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                tutorado.setNombreSemestre(resultado.getString("nombreSemestre"));
                
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
    
    public static HashMap<String, Object> obtenerProblematicasPorSesion(int idSesion, int idProgramaEducativo){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = ReporteGeneralDAO.obtenerProblematicasPorSesion(ConexionBD.abrirConexion(), idSesion, idProgramaEducativo);
            ArrayList<ProblematicaAcademica> problematicas = new ArrayList<>();
            
            while(resultado.next()){
                ProblematicaAcademica problematica = new ProblematicaAcademica();
                problematica.setIdProblematica(resultado.getInt("idProblematica"));
                problematica.setTipo(resultado.getString("tipo"));
                problematica.setDescripcion(resultado.getString("descripcion"));
                problematica.setNombreProfesor(resultado.getString("nombreProfesor"));
                
                problematicas.add(problematica);
            }
            respuesta.put("error", false);
            respuesta.put("problematicas", problematicas);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> actualizarEstatus(int idReporteGeneral, String nuevoEstatus){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            int resultado = ReporteGeneralDAO.actualizarEstatus(ConexionBD.abrirConexion(), idReporteGeneral, nuevoEstatus);
            if(resultado > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "Estado actualizado correctamente.");
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo actualizar el estado del reporte.");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerProblematicasPorReporte(int idReporteGeneral) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            ResultSet resultado = ReporteGeneralDAO.obtenerProblematicasPorReporte(ConexionBD.abrirConexion(), idReporteGeneral);
            ArrayList<ProblematicaAcademica> problematicas = new ArrayList<>();
            
            while (resultado.next()) {
                ProblematicaAcademica problematica = new ProblematicaAcademica();
                problematica.setIdProblematica(resultado.getInt("idProblematica"));
                problematica.setTipo(resultado.getString("tipo"));
                problematica.setDescripcion(resultado.getString("descripcion"));
                problematica.setNombreProfesor(resultado.getString("nombreProfesor"));
                
                problematicas.add(problematica);
            }
            
            respuesta.put("error", false);
            respuesta.put("problematicas", problematicas);
        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static int obtenerTotalAsistencias(int idSesion, int idProgramaEducativo) {
        int total = 0;
        try {
            total = ReporteGeneralDAO.obtenerTotalAsistencias(ConexionBD.abrirConexion(), idSesion, idProgramaEducativo);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return total;
    }
    
    public static HashMap<String, Object> obtenerReportesEnviadosPorPrograma(int idProgramaEducativo) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            // Buscamos solo los ENVIADO (Opcionalmente REVISADO también si se requiere historial)
            ResultSet resultado = ReporteGeneralDAO.obtenerReportesPorProgramaYEstado(ConexionBD.abrirConexion(), idProgramaEducativo, "ENVIADO");
            ArrayList<ReporteGeneral> reportes = new ArrayList<>();
            
            while (resultado.next()) {
                ReporteGeneral reporte = new ReporteGeneral();
                reporte.setIdReporteGeneral(resultado.getInt("idReporteGeneral"));
                reporte.setIdCoordinador(resultado.getInt("idCoordinador"));
                reporte.setIdSesion(resultado.getInt("idSesion"));
                reporte.setNumeroSesion(resultado.getInt("numeroSesion"));
                reporte.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                reporte.setNombreProgramaEducativo(resultado.getString("nombrePrograma"));
                reporte.setFechaElaboracion(resultado.getString("fechaElaboracion"));
                reporte.setComentariosGenerales(resultado.getString("comentariosGenerales"));
                reporte.setTotalAsistentes(resultado.getInt("totalAsistentes"));
                reporte.setTotalEnRiesgo(resultado.getInt("totalEnRiesgo"));
                reporte.setEstatus(resultado.getString("estatus"));
                reporte.setNombreCoordinador(resultado.getString("nombreCoordinador")); // Recuperamos el autor
                
                reportes.add(reporte);
            }
            respuesta.put("error", false);
            respuesta.put("reportes", reportes);
        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

}