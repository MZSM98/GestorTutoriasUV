package com.gtuv.controlador;

import com.gtuv.dominio.ReporteGeneralImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.ReporteGeneral;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestionReporteGeneralController implements Initializable, IObservador {

    @FXML
    private TableView<ReporteGeneral> tblReportesGenerales;
    @FXML
    private TableColumn<ReporteGeneral, Integer> colNoSesion;
    @FXML
    private TableColumn<ReporteGeneral, String> colFechaElaboracion;
    @FXML
    private TableColumn<ReporteGeneral, String> colEstado;
    @FXML
    private TableColumn<ReporteGeneral, Integer> colTotalAsistencia;
    @FXML
    private TableColumn<ReporteGeneral, Integer> colTotalEnRiesgo;

    
    private ObservableList<ReporteGeneral> listaReportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarReportes();
    }    
    
    private void configurarTabla(){
        // Mapeo con los atributos del POJO ReporteGeneral
        colNoSesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        colFechaElaboracion.setCellValueFactory(new PropertyValueFactory<>("fechaElaboracion"));
        colTotalAsistencia.setCellValueFactory(new PropertyValueFactory<>("totalAsistentes"));
        colTotalEnRiesgo.setCellValueFactory(new PropertyValueFactory<>("totalEnRiesgo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        
        // Alineación visual (opcional)
        Utilidades.alinearCentro(colNoSesion, colTotalAsistencia, colTotalEnRiesgo, colEstado);
    }
    
    private void cargarReportes(){
        Usuario coordinador = Sesion.getUsuario();
        if(coordinador != null){
            HashMap<String, Object> respuesta = ReporteGeneralImpl.obtenerReportesPorCoordinador(coordinador.getIdUsuario());
            
            if(!(boolean)respuesta.get("error")){
                ArrayList<ReporteGeneral> reportesBD = (ArrayList<ReporteGeneral>) respuesta.get("reportes");
                listaReportes = FXCollections.observableArrayList();
                listaReportes.addAll(reportesBD);
                tblReportesGenerales.setItems(listaReportes);
            }else{
                Utilidades.mostrarAlerta("Error de conexión", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) tblReportesGenerales.getScene().getWindow()).close();
    }

    @FXML
    private void clicGenerar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clicExportar(ActionEvent event) {
        Utilidades.mostrarAlerta("Información", "La funcionalidad de exportar a PDF está en desarrollo.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        ReporteGeneral reporteSeleccionado = tblReportesGenerales.getSelectionModel().getSelectedItem();
        
        if(reporteSeleccionado != null){
            // Validar que no esté enviado para permitir edición
            if("ENVIADO".equals(reporteSeleccionado.getEstatus())){
                Utilidades.mostrarAlerta("Edición no permitida", 
                        "El reporte seleccionado ya ha sido ENVIADO y no puede ser modificado.", 
                        Alert.AlertType.WARNING);
                return;
            }
            irFormulario(reporteSeleccionado);
        }else{
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar un reporte para editarlo.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicEnviar(ActionEvent event) {
        // Asumiendo que el botón btnConsultar en el FXML es para "Enviar"
        ReporteGeneral reporteSeleccionado = tblReportesGenerales.getSelectionModel().getSelectedItem();
        
        if(reporteSeleccionado != null){
            if("ENVIADO".equals(reporteSeleccionado.getEstatus())){
                 Utilidades.mostrarAlerta("Aviso", "El reporte ya se encuentra en estado ENVIADO.", Alert.AlertType.INFORMATION);
                 return;
            }
            
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Confirmar envío", 
                    "¿Está seguro de enviar el reporte de la Sesión " + reporteSeleccionado.getNumeroSesion() + "?", 
                    "Una vez enviado, no podrá realizar modificaciones.");
            
            if(confirmar){
                cambiarEstatusReporte(reporteSeleccionado, "ENVIADO");
            }
        }else{
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar un reporte para enviarlo.", Alert.AlertType.WARNING);
        }
    }
    
    private void cambiarEstatusReporte(ReporteGeneral reporte, String nuevoEstatus){
        reporte.setEstatus(nuevoEstatus);
        HashMap<String, Object> respuesta = ReporteGeneralImpl.editarReporte(reporte);
        
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Éxito", "El reporte ha sido enviado correctamente.", Alert.AlertType.INFORMATION);
            cargarReportes();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void irFormulario(ReporteGeneral reporte){
        try{
            FXMLLoader loader = Utilidades.obtenerVistaMemoria("/com/gtuv/vista/FXMLFormularioReporteGeneral.fxml");
            Parent root = loader.load();
            
            FormularioReporteGeneralController controlador = loader.getController();
            controlador.inicializarDatos(this, reporte); 
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(reporte == null ? "Generar Reporte General" : "Editar Reporte General");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        }catch(IOException e){
            e.printStackTrace();
            Utilidades.mostrarAlerta("Error", "No se pudo cargar el formulario.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        cargarReportes();
    }
}   