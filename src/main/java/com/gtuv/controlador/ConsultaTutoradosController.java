package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ConsultaTutoradosController implements Initializable {

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
    private TableColumn<?, ?> colSemestre;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtBuscarTutorado;
    @FXML
    private ComboBox<?> cmbTutor;
    @FXML
    private Label lblErrorTutor;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}