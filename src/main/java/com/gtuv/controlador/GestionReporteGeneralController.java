package com.gtuv.controlador;

import com.gtuv.dominio.CatalogoImpl;
import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.ReporteGeneralImpl;
import com.gtuv.dominio.SesionTutoriaImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.PeriodoEscolar;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.ReporteGeneral;
import com.gtuv.modelo.pojo.SesionTutoria;
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
    
    private ObservableList<ReporteGeneral> listaReportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarReportes();
    }    
    
    private void configurarTabla(){
        colNoSesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        colFechaElaboracion.setCellValueFactory(new PropertyValueFactory<>("fechaElaboracion"));
        colTotalAsistencia.setCellValueFactory(new PropertyValueFactory<>("totalAsistentes"));
        colTotalEnRiesgo.setCellValueFactory(new PropertyValueFactory<>("totalEnRiesgo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        
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
        if (hayBorradorPendiente()) {
            return;
        }

        ProgramaEducativo programa = obtenerProgramaDelCoordinador();
        SesionTutoria sesionActualSistema = null;
        
        if (programa != null) {
            sesionActualSistema = obtenerSesionActiva(programa.getIdProgramaEducativo());
        }

        if (sesionActualSistema == null || programa == null) {
            Utilidades.mostrarAlerta("Datos insuficientes", 
                    "No se pudo identificar una sesión activa o programa educativo para generar el reporte.", 
                    Alert.AlertType.WARNING);
            return;
        }

        if (esSesionYaReportada(sesionActualSistema)) {
            return;
        }

        irFormulario(null, sesionActualSistema, programa);
    }

    @FXML
    private void clicExportar(ActionEvent event) {
        Utilidades.mostrarAlerta("Información", "La funcionalidad de exportar a PDF está en desarrollo.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        ReporteGeneral reporteSeleccionado = tblReportesGenerales.getSelectionModel().getSelectedItem();
        
        if(reporteSeleccionado != null){
            if("ENVIADO".equals(reporteSeleccionado.getEstatus())){
                Utilidades.mostrarAlerta("Edición no permitida", 
                        "El reporte seleccionado ya ha sido ENVIADO y no puede ser modificado.", 
                        Alert.AlertType.WARNING);
                return;
            }

            SesionTutoria sesion = new SesionTutoria();
            sesion.setIdSesion(reporteSeleccionado.getIdSesion());
            sesion.setNumeroSesion(reporteSeleccionado.getNumeroSesion());
            ProgramaEducativo programa = new ProgramaEducativo();
            programa.setIdProgramaEducativo(reporteSeleccionado.getIdProgramaEducativo());
            programa.setNombre(reporteSeleccionado.getNombreProgramaEducativo());

            irFormulario(reporteSeleccionado, sesion, programa);
            
        }else{
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar un reporte para editarlo.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicEnviar(ActionEvent event) {
        ReporteGeneral reporteSeleccionado = tblReportesGenerales.getSelectionModel().getSelectedItem();
        
        if(reporteSeleccionado != null){
            if("ENVIADO".equals(reporteSeleccionado.getEstatus())){
                 Utilidades.mostrarAlerta("Aviso", "El reporte ya se ha enviado previamente.", Alert.AlertType.INFORMATION);
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
        HashMap<String, Object> respuesta = ReporteGeneralImpl.actualizarEstatus(reporte.getIdReporteGeneral(), nuevoEstatus);
        
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Éxito", "El reporte ha sido enviado correctamente.", Alert.AlertType.INFORMATION);
            cargarReportes();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void irFormulario(ReporteGeneral reporte, SesionTutoria sesion, ProgramaEducativo programa){
        try{
            FXMLLoader loader = Utilidades.obtenerVistaMemoria("/com/gtuv/vista/FXMLFormularioReporteGeneral.fxml");
            Parent root = loader.load();
            
            FormularioReporteGeneralController controlador = loader.getController();
            
            controlador.inicializarDatos(this, reporte, sesion, programa); 
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(reporte == null ? "Generar Reporte General" : "Editar Reporte General");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        }catch(IOException e){
            e.printStackTrace();
            Utilidades.mostrarAlerta("Error", "No se pudo cargar la ventana del formulario.", Alert.AlertType.ERROR);
        }
    }
    
    private ProgramaEducativo obtenerProgramaDelCoordinador() {
        Usuario coordinador = Sesion.getUsuario();
        if(coordinador != null){
            HashMap<String, Object> respuesta = ProgramaEducativoImpl.obtenerProgramaPorUsuario(coordinador.getIdUsuario());
            if(!(boolean)respuesta.get("error")){
                return (ProgramaEducativo) respuesta.get("programa");
            }
        }
        return null;
    }

    private SesionTutoria obtenerSesionActiva(int idPrograma) {
        HashMap<String, Object> respPeriodo = CatalogoImpl.obtenerPeriodoActual();
        PeriodoEscolar periodo = (PeriodoEscolar) respPeriodo.get("periodo");
        
        if(periodo != null){
            HashMap<String, Object> respSesiones = SesionTutoriaImpl.obtenerSesionesPorPeriodo(periodo.getIdPeriodo(), idPrograma);
            if(!(boolean)respSesiones.get("error")){
                ArrayList<SesionTutoria> sesiones = (ArrayList<SesionTutoria>) respSesiones.get("sesiones");
                if(!sesiones.isEmpty()){
                    return sesiones.get(sesiones.size() - 1); 
                }
            }
        }
        return null;
    }

    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        cargarReportes();
    }
    
    private boolean hayBorradorPendiente() {
        if (listaReportes != null) {
            for (ReporteGeneral reporte : listaReportes) {
                if ("BORRADOR".equals(reporte.getEstatus())) {
                    Utilidades.mostrarAlerta("Acción bloqueada", 
                            "Tiene un reporte pendiente en estado BORRADOR (Sesión " + reporte.getNumeroSesion() + ").\n\n" +
                            "Debe completar y enviar ese reporte antes de poder generar uno nuevo.", 
                            Alert.AlertType.WARNING);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean esSesionYaReportada(SesionTutoria sesionActualSistema) {
        if (listaReportes == null || listaReportes.isEmpty()) {
            return false; 
        }

        int maxSesionReportada = 0;
        for (ReporteGeneral reporte : listaReportes) {
            if (reporte.getNumeroSesion() > maxSesionReportada) {
                maxSesionReportada = reporte.getNumeroSesion();
            }
        }

        if (sesionActualSistema.getNumeroSesion() <= maxSesionReportada) {
            Utilidades.mostrarAlerta("Reporte al día", 
                    "Ya ha generado el reporte para la Sesión " + maxSesionReportada + ".\n\n" +
                    "No se puede generar un nuevo reporte hasta que una nueva sesión de tutorías comience ",
                    Alert.AlertType.INFORMATION);
            return true; 
        }
        
        return false;
    }
}