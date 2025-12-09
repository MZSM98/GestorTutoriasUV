package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormularioFechaTutoriaController implements Initializable {

    @FXML
    private Label lblErrorMatricula;
    @FXML
    private TextField txtPeriodoEscolar;
    @FXML
    private Label lblErrorNombre;
    @FXML
    private DatePicker dpFechaProgramada;
    @FXML
    private TextField txtSesionTutoria;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}