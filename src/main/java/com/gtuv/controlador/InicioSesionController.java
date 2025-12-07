package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InicioSesionController implements Initializable {

    @FXML
    private Label lblErrorNoTrabajador;
    @FXML
    private Label lblErrorContrasenia;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private TextField txtNoTrabajador;
    @FXML
    private TextField txtContrasenia;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}