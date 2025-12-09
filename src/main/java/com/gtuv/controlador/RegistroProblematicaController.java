/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author gurov
 */
public class RegistroProblematicaController implements Initializable {

    @FXML
    private ComboBox<?> cmbAlumno;
    @FXML
    private ComboBox<?> cmbTipoProblematica;
    @FXML
    private CheckBox chkEnRiesgo;
    @FXML
    private TextArea txaDescripcion;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicRegresar(ActionEvent event) {
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
    }
    
}
