package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormularioTutoradoController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Label lblErrorMatricula;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<?> cmbProgramaEducativo;
    @FXML
    private TextField txtApPaterno;
    @FXML
    private TextField txtApMaterno;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Label lblErrorNombre;
    @FXML
    private Label lblErrorApPaterno;
    @FXML
    private Label lblErrorApMaterno;
    @FXML
    private Label lblErrorCorreo;
    @FXML
    private Label lblErrorProgramaEducativo;
    @FXML
    private ComboBox<?> cmbSemestre;
    @FXML
    private Label lblErrorSemestre;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}