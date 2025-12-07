package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ConsultaTutoresController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private TableColumn<?, ?> colNoTrabajador;
    @FXML
    private TableColumn<?, ?> colApPaterno;
    @FXML
    private TableColumn<?, ?> colApMaterno;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colCorreo;
    @FXML
    private Button btnAsignar;
    @FXML
    private TextField txtBuscarTutor;
    @FXML
    private TableView<?> tblTutores;
    @FXML
    private TableColumn<?, ?> colNoTutorados;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}