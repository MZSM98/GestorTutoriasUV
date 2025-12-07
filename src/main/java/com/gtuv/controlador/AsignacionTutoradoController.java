package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AsignacionTutoradoController implements Initializable {

    @FXML
    private TextField txtBuscarTutorado;
    @FXML
    private TableView<?> tblTutoradosDisponibles;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colAsignar;
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView<?> tblTutoradosAsignados;
    @FXML
    private TableColumn<?, ?> colDesasignar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}