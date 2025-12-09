package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    private Button btnTutorias;
    @FXML
    private Button btnReportes;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
    }

    @FXML
    private void clicTutorados(ActionEvent event) {
    }

    @FXML
    private void clicTutores(ActionEvent event) {
    }

    @FXML
    private void clicTutorias(ActionEvent event) {
    }

    @FXML
    private void clicReportes(ActionEvent event) {
    }
}