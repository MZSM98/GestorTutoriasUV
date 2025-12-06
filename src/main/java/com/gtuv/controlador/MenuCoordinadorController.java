package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuCoordinadorController implements Initializable {

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblRol;
    @FXML
    private Button btnTutorados;
    @FXML
    private Button btnTutores;
    @FXML
    private Button btnTutorias;
    @FXML
    private Button btnReportes;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}