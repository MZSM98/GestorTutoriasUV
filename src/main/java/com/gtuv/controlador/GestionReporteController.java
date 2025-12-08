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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author gurov
 */
public class GestionReporteController implements Initializable {

    @FXML
    private TextField txtNoSesion;
    @FXML
    private TextField txtProgramaEducativo;
    @FXML
    private TextField txtFechaTutoria;
    @FXML
    private TableView<?> tblAsistencia;
    @FXML
    private TableColumn<?, ?> colNoLista;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colPaterno;
    @FXML
    private TableColumn<?, ?> colMaterno;
    @FXML
    private TableColumn<?, ?> colAsistencia;
    @FXML
    private TableView<?> tblProblematicas;
    @FXML
    private TableColumn<?, ?> colAlumnoProb;
    @FXML
    private TableColumn<?, ?> colTituloProb;
    @FXML
    private TableColumn<?, ?> colEnRiesgo;
    @FXML
    private Button btnNuevaProblematica;
    @FXML
    private Button btnEditarProblematica;
    @FXML
    private Button btnEliminarProblematica;
    @FXML
    private TextArea txaComentarios;
    @FXML
    private Label lblNombreArchivo;
    @FXML
    private Button btnSubirEvidencia;
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
    private void clicNuevaProblematica(ActionEvent event) {
    }

    @FXML
    private void clicEditarProblematica(ActionEvent event) {
    }

    @FXML
    private void clicEliminarProblematica(ActionEvent event) {
    }

    @FXML
    private void clicSubirEvidencia(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
    }
    
}
