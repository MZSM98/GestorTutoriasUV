package com.gtuv.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GestionReporteGeneralController implements Initializable {

    @FXML
    private TableView<?> tblReportesGenerales;
    @FXML
    private TableColumn<?, ?> colNoSesion;
    @FXML
    private TableColumn<?, ?> colFechaElaboracion;
    @FXML
    private TableColumn<?, ?> colNoProblematica;
    @FXML
    private TableColumn<?, ?> colNoTutoradosRiesgo;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnExportar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnConsultar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}