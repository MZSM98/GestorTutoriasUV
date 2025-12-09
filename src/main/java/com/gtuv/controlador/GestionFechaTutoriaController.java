package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GestionFechaTutoriaController implements Initializable {

    @FXML
    private TableView<?> tblPlanTutorias;
    @FXML
    private TableColumn<?, ?> colNoSesion;
    @FXML
    private TableColumn<?, ?> colFechaProgramada;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private TextField txtPeriodoEscolar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}