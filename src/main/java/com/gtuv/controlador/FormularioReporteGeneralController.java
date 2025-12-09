package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormularioReporteGeneralController implements Initializable {

    @FXML
    private TextField txtProgramaEducativo;
    @FXML
    private TextField textNumeroSesion;
    @FXML
    private TableView<?> tblTutoradosEnRiesgo;
    @FXML
    private TableColumn<?, ?> colApPaterno;
    @FXML
    private TableColumn<?, ?> colApMaterno;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colSemestre;
    @FXML
    private TableView<?> tblProblematicasReportadas;
    @FXML
    private TableColumn<?, ?> colNoProblematicasReporatdas;
    @FXML
    private TableColumn<?, ?> colDocenteInvolucrado;
    @FXML
    private TextArea txaObservaciones;
    @FXML
    private TextField textFechaDeElaboracion;
    @FXML
    private TableColumn<?, ?> colMatricula;
    @FXML
    private Label lblObservaciones;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}