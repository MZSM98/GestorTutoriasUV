package com.gtuv.controlador;

import com.gtuv.dominio.ReporteGeneralImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.ReporteGeneral;
import com.gtuv.utlidad.RestriccionCampos;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResponderReporteGeneralController implements Initializable {

    @FXML
    private TextArea txaObservaciones;
    @FXML
    private Label lblCaracteresMaximos;
    @FXML
    private TextField textTutor;
    
    private ReporteGeneral reporteSeleccionado;
    private static final int LIMITE_OBSERVACIONES = 300;
    private IObservador observador;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarContadorCaracteres();
        RestriccionCampos.limitarLongitud(txaObservaciones, LIMITE_OBSERVACIONES);
    }    
    
    public void inicializarDatos(ReporteGeneral reporte, IObservador observador){
        this.reporteSeleccionado = reporte;
        this.observador = observador;
        cargarDatosReporte();
    }
    
    private void cargarDatosReporte(){
        if(reporteSeleccionado != null){
            if(reporteSeleccionado.getNombreCoordinador() != null) {
                textTutor.setText(reporteSeleccionado.getNombreCoordinador());
            } else {
                textTutor.setText("Coordinador (ID: " + reporteSeleccionado.getIdCoordinador() + ")");
            }
            textTutor.setEditable(false);
        }
    }

    @FXML
    private void clicResponder(ActionEvent event) {
        String observaciones = txaObservaciones.getText();
        
        if(observaciones == null || observaciones.trim().isEmpty()){
            Utilidades.mostrarAlerta("Campos vacíos", "Debe ingresar una observación para enviar la respuesta.", Alert.AlertType.WARNING);
            return;
        }
        
        HashMap<String, Object> respuesta = ReporteGeneralImpl.actualizarEstatus(reporteSeleccionado.getIdReporteGeneral(), "REVISADO");
        
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Éxito", "Respuesta enviada exitosamente", Alert.AlertType.INFORMATION);
            if(observador != null){
                observador.notificarOperacionExitosa("Respuesta", "Reporte General");
            }
            clicRegresar(null);
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) txaObservaciones.getScene().getWindow()).close();
    }
    
    private void configurarContadorCaracteres() {
        lblCaracteresMaximos.setText("0/" + LIMITE_OBSERVACIONES);
        txaObservaciones.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    lblCaracteresMaximos.setText(newValue.length() + "/" + LIMITE_OBSERVACIONES);
                } else {
                    lblCaracteresMaximos.setText("0/" + LIMITE_OBSERVACIONES);
                }
            }
        });
    }
}
