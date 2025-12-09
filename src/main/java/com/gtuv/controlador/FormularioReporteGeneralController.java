package com.gtuv.controlador;

import com.gtuv.dominio.ReporteGeneralImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.ProblematicaAcademica;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.ReporteGeneral;
import com.gtuv.modelo.pojo.SesionTutoria;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.RestriccionCampos;
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FormularioReporteGeneralController implements Initializable {

    @FXML
    private TextField txtProgramaEducativo;
    @FXML
    private TextField textNumeroSesion;
    @FXML
    private TextField textFechaDeElaboracion;
    
    @FXML
    private TableView<Tutorado> tblTutoradosEnRiesgo;
    @FXML
    private TableColumn<Tutorado, String> colMatricula;
    @FXML
    private TableColumn<Tutorado, String> colApPaterno;
    @FXML
    private TableColumn<Tutorado, String> colApMaterno;
    @FXML
    private TableColumn<Tutorado, String> colNombre;
    @FXML
    private TableColumn<Tutorado, String> colSemestre; 
    
    @FXML
    private TableView<ProblematicaAcademica> tblProblematicasReportadas;
    @FXML
    private TableColumn colNoProblematicasReporatdas; 
    @FXML
    private TableColumn colDocenteInvolucrado;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private Label lblCaracteresMaximos;
    @FXML
    private TextArea txaObservaciones;
    @FXML
    private Label lblErrorObservaciones; 
    
    private static final int LIMITE_CAMPO_OBSERVACIONES = 300;
    private IObservador observador;
    private ReporteGeneral reporteEdicion;
    private SesionTutoria sesionActual;
    private ProgramaEducativo programaActual;
    private ObservableList<Tutorado> listaRiesgo;
    private ObservableList<ProblematicaAcademica> listaProblematicas;
    private Stack<ProblematicaAcademica> pilaEliminados = new Stack<>();
    @FXML
    private Button btnDeshacer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablas();
        configurarContadorCaracteres();
        RestriccionCampos.limitarLongitud(txaObservaciones, LIMITE_CAMPO_OBSERVACIONES);
        if (btnDeshacer != null) {
            btnDeshacer.setDisable(true);
        }
    }    
    
    public void inicializarDatos(IObservador observador, ReporteGeneral reporte, SesionTutoria sesion, ProgramaEducativo programa){
        this.observador = observador;
        this.reporteEdicion = reporte;
        this.sesionActual = sesion;
        this.programaActual = programa;
        
        cargarDatosEncabezado();
        cargarListasDeSesion();
        
        if(reporteEdicion != null){
            cargarDatosEdicion();
        } else {
            textFechaDeElaboracion.setText(LocalDate.now().toString());
        }
    }
    
    private void cargarDatosEncabezado(){
        if(programaActual != null) {
            txtProgramaEducativo.setText(programaActual.getNombre());
            txtProgramaEducativo.setEditable(false);
        }
        if(sesionActual != null) {
            textNumeroSesion.setText(String.valueOf(sesionActual.getNumeroSesion()));
            textNumeroSesion.setEditable(false);
        }
        textFechaDeElaboracion.setEditable(false);
    }
    
    private void configurarTablas(){
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("nombreSemestre")); 
        
        colNoProblematicasReporatdas.setText("Tipo"); 
        colNoProblematicasReporatdas.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        
        colDocenteInvolucrado.setCellValueFactory(new PropertyValueFactory<>("nombreProfesor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }
    
    private void cargarListasDeSesion(){
        if(sesionActual == null || programaActual == null) return;

        HashMap<String, Object> respuestaRiesgo = ReporteGeneralImpl.obtenerTutoradosEnRiesgoPorSesion(
                sesionActual.getIdSesion(), programaActual.getIdProgramaEducativo());
        
        if(!(boolean)respuestaRiesgo.get("error")){
            listaRiesgo = FXCollections.observableArrayList((ArrayList<Tutorado>) respuestaRiesgo.get("tutorados"));
            tblTutoradosEnRiesgo.setItems(listaRiesgo);
        } else {
            Utilidades.mostrarAlerta("Error", respuestaRiesgo.get("mensaje").toString(), Alert.AlertType.ERROR);
        }

        HashMap<String, Object> respuestaProblematicas = ReporteGeneralImpl.obtenerProblematicasPorSesion(
                sesionActual.getIdSesion(), programaActual.getIdProgramaEducativo());
        
        if(!(boolean)respuestaProblematicas.get("error")){
            listaProblematicas = FXCollections.observableArrayList((ArrayList<ProblematicaAcademica>) respuestaProblematicas.get("problematicas"));
            tblProblematicasReportadas.setItems(listaProblematicas);
        } else {
            Utilidades.mostrarAlerta("Error", respuestaProblematicas.get("mensaje").toString(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarDatosEdicion(){
        txaObservaciones.setText(reporteEdicion.getComentariosGenerales());
        textFechaDeElaboracion.setText(reporteEdicion.getFechaElaboracion());
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if(lblErrorObservaciones != null)
            lblErrorObservaciones.setText("");
        
        if(txaObservaciones.getText() == null || txaObservaciones.getText().trim().isEmpty()){
            lblErrorObservaciones.setText("Campo Obligatorio");
            Utilidades.mostrarAlerta("Campo requerido", "Las observaciones son obligatorias.", Alert.AlertType.WARNING);
            return;
        }
        
        if(confirmarGuardado()){
        
        if(reporteEdicion == null){
            registrarReporte();
        } else {
            editarReporte();
        }
        }
    }

    private void clicCancelar(ActionEvent event) {
        boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Cancelar", "¿Desea salir?", "Los cambios no guardados se perderán.");
        if(confirmar){
            cerrarVentana();
        }
    }
    
    private void registrarReporte(){
        ReporteGeneral nuevoReporte = new ReporteGeneral();
        Usuario coordinador = Sesion.getUsuario();
        
        nuevoReporte.setIdCoordinador(coordinador.getIdUsuario());
        nuevoReporte.setIdSesion(sesionActual.getIdSesion());
        nuevoReporte.setIdProgramaEducativo(programaActual.getIdProgramaEducativo());
        nuevoReporte.setComentariosGenerales(txaObservaciones.getText().trim());
        nuevoReporte.setEstatus("BORRADOR"); 
        
        nuevoReporte.setTotalEnRiesgo(listaRiesgo != null ? listaRiesgo.size() : 0);
        nuevoReporte.setTotalAsistentes(0); 
        
        HashMap<String, Object> respuesta = ReporteGeneralImpl.registrarReporte(nuevoReporte, listaProblematicas);
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Éxito", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", "Reporte General");
            cerrarVentana();
        } else {
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void editarReporte(){
        reporteEdicion.setComentariosGenerales(txaObservaciones.getText().trim());
        reporteEdicion.setTotalEnRiesgo(listaRiesgo != null ? listaRiesgo.size() : 0);
        
        HashMap<String, Object> respuesta = ReporteGeneralImpl.editarReporte(reporteEdicion, listaProblematicas);
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Éxito", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Edicion", "Reporte General");
            cerrarVentana();
        } else {
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVentana(){
        ((Stage) txtProgramaEducativo.getScene().getWindow()).close();
    }

    @FXML
    private void clicDeshacer(ActionEvent event) {
        if (!pilaEliminados.isEmpty()) {
            ProblematicaAcademica recuperado = pilaEliminados.pop();
            listaProblematicas.add(recuperado);
            if (pilaEliminados.isEmpty() && btnDeshacer != null) {
                btnDeshacer.setDisable(true);
            }
        }
    }

    @FXML
    private void clicQuitar(ActionEvent event) {
        ProblematicaAcademica seleccion = tblProblematicasReportadas.getSelectionModel().getSelectedItem();

        if (seleccion != null) {
            pilaEliminados.push(seleccion);
            listaProblematicas.remove(seleccion);
            if (btnDeshacer != null) {
                btnDeshacer.setDisable(false);
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Seleccione una problemática de la lista para quitarla.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        boolean confirmacion = Utilidades.mostrarAlertaConfirmacion("Confirmar cancelación de registro",
                "¿Está seguro de cancelar el registro del reporte general?",
                "Ninguno de los cambios realizados se guardarán");
        
        if (confirmacion){
            cerrarVentana();
        }
    }
    
    private boolean confirmarGuardado(){
        return Utilidades.mostrarAlertaConfirmacion("Confirmar Guardado",
                "¿Desea realizar el guardado?",
                "Las problemáticas eliminadas con anterioridad no se podrán recuperar");
    }
    
    private void configurarContadorCaracteres(){
        txaObservaciones.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    lblCaracteresMaximos.setText(newValue.length() + "/" + LIMITE_CAMPO_OBSERVACIONES);
                } else {
                    lblCaracteresMaximos.setText("0/" + LIMITE_CAMPO_OBSERVACIONES);
                }
            }
        });
    }
    
}