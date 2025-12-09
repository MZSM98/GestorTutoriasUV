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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author gurov
 */
public class RegistroHorarioTutorController implements Initializable {

    @FXML
    private ComboBox<?> cmbProgramaEducativo;
    @FXML
    private TableView<?> tblSesiones;
    @FXML
    private TableColumn<?, ?> colNumeroSesion;
    @FXML
    private TableColumn<?, ?> colFechaSesion;
    @FXML
    private TableColumn<?, ?> colHoraInicio;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private Label lblSesionSeleccionada;
    @FXML
    private ComboBox<?> cmbHora;
    @FXML
    private ComboBox<?> cmbMinuto;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicGuardar(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
    }
    
}
