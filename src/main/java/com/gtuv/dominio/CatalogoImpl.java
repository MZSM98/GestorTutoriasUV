package com.gtuv.dominio;

import com.gtuv.modelo.ConexionBD;
import com.gtuv.modelo.dao.CatalogoDAO;
import com.gtuv.modelo.pojo.ExperienciaEducativa;
import com.gtuv.modelo.pojo.PeriodoEscolar;
import com.gtuv.modelo.pojo.Profesor;
import com.gtuv.modelo.pojo.Semestre;
import com.gtuv.utlidad.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CatalogoImpl {
    
    private CatalogoImpl(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    public static HashMap<String, Object> obtenerPeriodoActual(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            PeriodoEscolar periodo = CatalogoDAO.obtenerPeriodoActual(ConexionBD.abrirConexion());
            if(periodo != null){
                respuesta.put("error", false);
                respuesta.put("periodo", periodo);
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se encontró un periodo escolar vigente en el sistema.");
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerSemestres(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = CatalogoDAO.obtenerSemestres(ConexionBD.abrirConexion());
            ArrayList<Semestre> lista = new ArrayList<>();
            while(resultado.next()){
                Semestre semestre = new Semestre();
                semestre.setIdSemestre(resultado.getInt("idSemestre"));
                semestre.setNombreSemestre(resultado.getString("nombre"));
                lista.add(semestre);
            }
            respuesta.put("error", false);
            respuesta.put("semestres", lista);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerExperienciasEducativas(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = CatalogoDAO.obtenerExperienciasEducativas(ConexionBD.abrirConexion());
            ArrayList<ExperienciaEducativa> lista = new ArrayList<>();
            while(resultado.next()){
                ExperienciaEducativa ee = new ExperienciaEducativa();
                // OJO: En base de datos es idCatalogoEE, en el POJO suele ser idExperienciaEducativa
                ee.setIdExperienciaEducativa(resultado.getInt("idCatalogoEE")); 
                ee.setNombre(resultado.getString("nombre"));
                lista.add(ee);
            }
            respuesta.put("error", false);
            respuesta.put("experiencias", lista);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerProfesoresPorExperiencia(int idExperienciaEducativa){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            ResultSet resultado = CatalogoDAO.obtenerProfesoresPorExperiencia(ConexionBD.abrirConexion(), idExperienciaEducativa);
            ArrayList<Profesor> lista = new ArrayList<>();
            while(resultado.next()){
                Profesor profesor = new Profesor();
                profesor.setIdCatalogoProfesor(resultado.getInt("idCatalogoProfesor"));
                profesor.setNombreCompleto(resultado.getString("nombreCompleto"));
                lista.add(profesor);
            }
            respuesta.put("error", false);
            respuesta.put("profesores", lista);
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }finally{
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
}