package com.gtuv.controlador;

import com.gtuv.dominio.CatalogoImpl;
import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.ReporteGeneralImpl;
import com.gtuv.dominio.SesionTutoriaImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.PeriodoEscolar;
import com.gtuv.modelo.pojo.ProblematicaAcademica;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.ReporteGeneral;
import com.gtuv.modelo.pojo.SesionTutoria;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.GeneracionPDF;
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.stage.FileChooser;
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
    private Button btnGenerar;
    @FXML
    private Button btnExportar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEnviar;
    
    private ObservableList<ReporteGeneral> listaReportes;
    private boolean esJefeCarreraView = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarReportes();
    }    
    
    public void configurarVistaJefeCarrera() {
        this.esJefeCarreraView = true;
        
        btnEnviar.setVisible(false);
        btnEditar.setVisible(false);
        btnExportar.setVisible(false);
        
        btnGenerar.setText("Responder");
        btnGenerar.setOnAction(this::clicResponderReporte);
        
        cargarReportes();
    }
    
    private void clicResponderReporte(ActionEvent event) {
        ReporteGeneral reporteSeleccionado = tblReportesGenerales.getSelectionModel().getSelectedItem();
        
        if (reporteSeleccionado != null) {
            try {
                FXMLLoader loader = Utilidades.obtenerVistaMemoria("/com/gtuv/vista/FXMLResponderReporteGeneral.fxml");
                Parent root = loader.load();
                
                ResponderReporteGeneralController controller = loader.getController();
                controller.inicializarDatos(reporteSeleccionado, this);
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Responder Reporte General");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Utilidades.mostrarAlerta("No podemos navegar", Utilidades.ERROR_ABRIR_VENTANA, Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar un reporte de la lista para responder.", Alert.AlertType.WARNING);
        }
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
        Usuario usuario = Sesion.getUsuario();
        if(usuario == null) return;

        HashMap<String, Object> respuesta;

        if (esJefeCarreraView) {
            ProgramaEducativo programa = obtenerProgramaUsuario(usuario.getIdUsuario());
            if (programa != null) {
                respuesta = ReporteGeneralImpl.obtenerReportesEnviadosPorPrograma(programa.getIdProgramaEducativo());
            } else {
                Utilidades.mostrarAlerta("Error", "No se pudo identificar el programa educativo del Jefe de Carrera.", Alert.AlertType.ERROR);
                return;
            }
        } else {
            respuesta = ReporteGeneralImpl.obtenerReportesPorCoordinador(usuario.getIdUsuario());
        }
        
        if(!(boolean)respuesta.get("error")){
            ArrayList<ReporteGeneral> reportesBD = (ArrayList<ReporteGeneral>) respuesta.get("reportes");
            listaReportes = FXCollections.observableArrayList();
            listaReportes.addAll(reportesBD);
            tblReportesGenerales.setItems(listaReportes);
        }else{
            Utilidades.mostrarAlerta("Error de conexión", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private ProgramaEducativo obtenerProgramaUsuario(int idUsuario) {
        HashMap<String, Object> respuesta = ProgramaEducativoImpl.obtenerProgramaPorUsuario(idUsuario);
        if(!(boolean)respuesta.get("error")){
            return (ProgramaEducativo) respuesta.get("programa");
        }
        return null;
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
        ReporteGeneral reporte = tblReportesGenerales.getSelectionModel().getSelectedItem();

        if (reporte == null) {
            Utilidades.mostrarAlerta("Selección requerida", "Seleccione el reporte que desea exportar a PDF.", Alert.AlertType.WARNING);
            return;
        }
        if(reporte.getEstatus().equalsIgnoreCase("BORRADOR")){
            Utilidades.mostrarAlerta("No se puede exportar", "Debe enviar primero el reporte antes de poderlo exportar", Alert.AlertType.WARNING);
            return;
        }

        File archivo = seleccionarArchivoDestino(reporte);

        if (archivo != null) {
            exportarReporteAPdf(reporte, archivo);
        }
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
            if("REVISADO".equals(reporteSeleccionado.getEstatus())){
                Utilidades.mostrarAlerta("Edición no permitida", 
                        "El reporte seleccionado ya ha sido REVISADO y no puede ser modificado.", 
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
    
    private File seleccionarArchivoDestino(ReporteGeneral reporte) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte General");
        fileChooser.setInitialFileName("Reporte_Sesion" + reporte.getNumeroSesion() + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        
        return fileChooser.showSaveDialog(tblReportesGenerales.getScene().getWindow());
    }
    
    private void exportarReporteAPdf(ReporteGeneral reporte, File archivo) {
        try {
            Map<String, String> encabezado = prepararEncabezado(reporte);
            List<List<String>> datosRiesgo = obtenerDatosRiesgo(reporte);
            List<List<String>> datosProblematicas = obtenerDatosProblematicas(reporte);

            GeneracionPDF.generarReporteGeneral(
                    archivo,
                    "Reporte General de Tutoría Académica",
                    encabezado,
                    Arrays.asList("Matrícula", "Nombre", "Semestre"), datosRiesgo,
                    Arrays.asList("Tipo", "Docente", "Descripción"), datosProblematicas,
                    reporte.getComentariosGenerales()
            );

            Utilidades.mostrarAlerta("Exportación exitosa", "El reporte se ha guardado correctamente.", Alert.AlertType.INFORMATION);

        } catch (DocumentException e) {
            Utilidades.mostrarAlerta("Error", "No se pudo generar el archivo PDF:" , Alert.AlertType.ERROR);
        } catch (IOException ioe){
            Utilidades.mostrarAlerta("Error", "No se pudo generar el archivo PDF", Alert.AlertType.ERROR);
        }
    }
    
    private Map<String, String> prepararEncabezado(ReporteGeneral reporte) {
        Map<String, String> encabezado = new HashMap<>();
        encabezado.put("Programa Educativo", reporte.getNombreProgramaEducativo());
        encabezado.put("Número de Sesión", String.valueOf(reporte.getNumeroSesion()));
        encabezado.put("Fecha de Elaboración", reporte.getFechaElaboracion());
        encabezado.put("Total Asistentes", String.valueOf(reporte.getTotalAsistentes()));
        encabezado.put("Total en Riesgo", String.valueOf(reporte.getTotalEnRiesgo()));
        return encabezado;
    }

    private List<List<String>> obtenerDatosRiesgo(ReporteGeneral reporte) {
        HashMap<String, Object> respRiesgo = ReporteGeneralImpl.obtenerTutoradosEnRiesgoPorSesion(
                reporte.getIdSesion(), reporte.getIdProgramaEducativo());
        
        List<List<String>> datosRiesgo = new ArrayList<>();
        if (!(boolean) respRiesgo.get("error")) {
            ArrayList<Tutorado> tutorados = (ArrayList<Tutorado>) respRiesgo.get("tutorados");
            for (Tutorado t : tutorados) {
                datosRiesgo.add(Arrays.asList(
                    t.getMatricula(), 
                    t.getNombreCompleto(), 
                    t.getNombreSemestre()
                ));
            }
        }
        return datosRiesgo;
    }

    private List<List<String>> obtenerDatosProblematicas(ReporteGeneral reporte) {
        HashMap<String, Object> respProb = ReporteGeneralImpl.obtenerProblematicasPorReporte(reporte.getIdReporteGeneral());
        
        List<List<String>> datosProblemas = new ArrayList<>();
        if (!(boolean) respProb.get("error")) {
            ArrayList<ProblematicaAcademica> problemas = (ArrayList<ProblematicaAcademica>) respProb.get("problematicas");
            for (ProblematicaAcademica p : problemas) {
                datosProblemas.add(Arrays.asList(
                    p.getTipo(), 
                    p.getNombreProfesor() != null ? p.getNombreProfesor() : "N/A", 
                    p.getDescripcion()
                ));
            }
        }
        return datosProblemas;
    }
}