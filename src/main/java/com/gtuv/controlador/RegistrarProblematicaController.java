/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gtuv.controlador;

import com.gtuv.dominio.ProblematicaAcademicaImpl;
import com.gtuv.dominio.TutoradoImpl;
import com.gtuv.modelo.pojo.ProblematicaAcademica;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 
 * * @author gurov
 */
public class RegistrarProblematicaController implements Initializable {

    @FXML private ComboBox<Tutorado> cmbAlumno;
    @FXML private ComboBox<String> cmbTipoProblematica;
    @FXML private CheckBox chkEnRiesgo; 
    @FXML private TextArea txaDescripcion;

    private ObservableList<Tutorado> listaTutorados;
    private ObservableList<String> listaTipos;
    
    private int idReporteTutoria;
    private ProblematicaAcademica problematicaEdicion;
    private boolean esEdicion = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTiposProblematica();
        cargarAlumnos(); 
    }
    
 
    public void inicializarValores(int idReporte, ProblematicaAcademica problematica) {
        this.idReporteTutoria = idReporte;
        this.problematicaEdicion = problematica;
        
        if (problematica != null) {
            esEdicion = true;
            cargarDatosEdicion();
        }
    }

    private void cargarTiposProblematica() {
        listaTipos = FXCollections.observableArrayList();
     
        listaTipos.addAll("ACADEMICA", "PERSONAL"); 
        cmbTipoProblematica.setItems(listaTipos);
    }
    
    private void cargarAlumnos() {
        listaTutorados = FXCollections.observableArrayList();
      
        HashMap<String, Object> respuesta = TutoradoImpl.obtenerTutorados(); 
        
        if (!(boolean) respuesta.get("error")) {
            ArrayList<Tutorado> lista = (ArrayList<Tutorado>) respuesta.get("tutorados");
            listaTutorados.addAll(lista);
            cmbAlumno.setItems(listaTutorados);
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosEdicion() {
        txaDescripcion.setText(problematicaEdicion.getDescripcion());
        cmbTipoProblematica.setValue(problematicaEdicion.getTipo());
        
        
        for (Tutorado t : cmbAlumno.getItems()) {
            if (t.getIdTutorado() == problematicaEdicion.getIdTutorado()) {
                cmbAlumno.setValue(t);
                cmbAlumno.setDisable(true); 
                
                
                break;
            }
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (validarCampos()) {
            ProblematicaAcademica problematica = new ProblematicaAcademica();
            
          
            problematica.setIdReporteTutoria(this.idReporteTutoria);
            problematica.setTipo(cmbTipoProblematica.getValue());
            problematica.setDescripcion(txaDescripcion.getText());
          
            
            problematica.setExperienciaEducativa("N/A"); 
            problematica.setNombreProfesor("N/A"); 

            if (esEdicion) {
                problematica.setIdProblematica(problematicaEdicion.getIdProblematica());
              
                problematica.setIdTutorado(problematicaEdicion.getIdTutorado());
                
                HashMap<String, Object> resp = ProblematicaAcademicaImpl.editarProblematica(problematica);
                procesarRespuesta(resp);
            } else {
                // En registro nuevo tomamos el ID del combo
                problematica.setIdTutorado(cmbAlumno.getValue().getIdTutorado());
                
                HashMap<String, Object> resp = ProblematicaAcademicaImpl.registrarProblematica(problematica);
                procesarRespuesta(resp);
            }
        }
    }
    
    private void procesarRespuesta(HashMap<String, Object> resp) {
        if (!(boolean) resp.get("error")) {
            Utilidades.mostrarAlerta("Éxito", (String) resp.get("mensaje"), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlerta("Error", (String) resp.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        String error = "";
        if (cmbAlumno.getValue() == null) error += "Selecciona un alumno.\n";
        if (cmbTipoProblematica.getValue() == null) error += "Selecciona el tipo de problemática.\n";
        if (txaDescripcion.getText().isEmpty()) error += "Ingresa una descripción.\n";
        
        if (!error.isEmpty()) {
            Utilidades.mostrarAlerta("Campos vacíos", error, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) txaDescripcion.getScene().getWindow();
        stage.close();
    }
}