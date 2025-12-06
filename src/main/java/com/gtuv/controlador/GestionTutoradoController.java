package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GestionTutoradoController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnRegistrar;
    @FXML
    private TableView<?> tblTutorados;
    @FXML
    private TableColumn<?, ?> colMatricula;
    @FXML
    private TableColumn<?, ?> colApPaterno;
    @FXML
    private TableColumn<?, ?> colApMaterno;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colProgramaEducativo;
    @FXML
    private TableColumn<?, ?> colAcciones;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}