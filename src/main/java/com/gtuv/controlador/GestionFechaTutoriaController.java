package com.gtuv.controlador;

import com.gtuv.dominio.CatalogoImpl;
import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.SesionTutoriaImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.PeriodoEscolar;
import com.gtuv.modelo.pojo.ProgramaEducativo;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestionFechaTutoriaController implements Initializable, IObservador {
    
    @FXML
    private TableView<SesionTutoria> tblPlanTutorias;
    @FXML
    private TableColumn<SesionTutoria, Integer> colNoSesion;
    @FXML
    private TableColumn<SesionTutoria, String> colFechaProgramada;
    @FXML
    private TableColumn<SesionTutoria, String> colEstado;
    @FXML
    private TextField txtPeriodoEscolar;
    
    private PeriodoEscolar periodoActual;
    private ProgramaEducativo programaEducativoSesion;
    private ObservableList<SesionTutoria> listaSesiones;
    private static final int MAX_SESIONES = 4;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosSesion();
    }    
    
    private void configurarTabla(){
        colNoSesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        colFechaProgramada.setCellValueFactory(new PropertyValueFactory<>("fechaSesion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        Utilidades.alinearCentro(colNoSesion, colFechaProgramada, colEstado);
    }
    
    private void cargarDatosSesion(){
        HashMap<String, Object> respuestaPeriodo = CatalogoImpl.obtenerPeriodoActual();
        if(!(boolean)respuestaPeriodo.get("error")){
            periodoActual = (PeriodoEscolar) respuestaPeriodo.get("periodo");
            if(periodoActual != null){
                txtPeriodoEscolar.setText(periodoActual.getNombre());
                txtPeriodoEscolar.setEditable(false);
            }
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuestaPeriodo.get("mensaje"), Alert.AlertType.ERROR);
        }
        
        Usuario usuario = Sesion.getUsuario();
        if(usuario != null){
            HashMap<String, Object> respuestaPrograma = ProgramaEducativoImpl.obtenerProgramaPorUsuario(usuario.getIdUsuario());
            if(!(boolean)respuestaPrograma.get("error")){
                programaEducativoSesion = (ProgramaEducativo) respuestaPrograma.get("programa");
                
                if(periodoActual != null && programaEducativoSesion != null){
                    cargarTablaSesiones();
                }
            }
        }
    }
    
    private void cargarTablaSesiones(){
        HashMap<String, Object> respuesta = SesionTutoriaImpl.obtenerSesionesPorPeriodo(
                periodoActual.getIdPeriodo(), 
                programaEducativoSesion.getIdProgramaEducativo());
        
        if(!(boolean)respuesta.get("error")){
            listaSesiones = FXCollections.observableArrayList((ArrayList<SesionTutoria>)respuesta.get("sesiones"));
            tblPlanTutorias.setItems(listaSesiones);
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) txtPeriodoEscolar.getScene().getWindow()).close();
    }

    @FXML
    private void clicAgendar(ActionEvent event) {
        if (listaSesiones != null && listaSesiones.size() >= MAX_SESIONES) {
            Utilidades.mostrarAlerta("Límite alcanzado", 
                    "Ya se han registrado las " + MAX_SESIONES + " sesiones permitidas para este periodo escolar.\n\n" +
                    "No es posible agendar nuevas fechas.", 
                    Alert.AlertType.WARNING);
            return; 
        }
        if (existeSesionAbierta()) {
            Utilidades.mostrarAlerta("No se puede agendar", 
                    "La sesión de tutoría más reciente todavía se encuentra ABIERTA.\n\n" +
                    "Debe cerrar el acta de la sesión anterior antes de poder programar la siguiente.", 
                    Alert.AlertType.WARNING);
            return; 
        }
        irFormulario(null);
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        SesionTutoria sesionSeleccionada = tblPlanTutorias.getSelectionModel().getSelectedItem();
        
        if (sesionSeleccionada != null) {
            if (!esSesionEditable(sesionSeleccionada)) {
                Utilidades.mostrarAlerta("No es posible editar", 
                        "No se puede modificar la Sesión " + sesionSeleccionada.getNumeroSesion() + 
                        " porque ya existen sesiones posteriores registradas. \n\n", 
                        Alert.AlertType.WARNING);
                return;
            }
            irFormulario(sesionSeleccionada);
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar una sesión de la lista para editarla.", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(SesionTutoria sesion) {
        try {
            FXMLLoader loader = Utilidades.obtenerVistaMemoria("/com/gtuv/vista/FXMLFormularioFechaTutoria.fxml");
            Parent root = loader.load();
            
            FormularioFechaTutoriaController controlador = loader.getController();
            
            controlador.inicializarDatos(this, sesion, periodoActual, programaEducativoSesion);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(sesion == null ? "Agendar Sesión" : "Editar Sesión");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        } catch (IOException e) {
            Utilidades.mostrarAlerta("Error", Utilidades.ERROR_ABRIR_VENTANA, Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        cargarTablaSesiones();
    }

    @FXML
    private void clicAbrirCerrarSesion(ActionEvent event) {
        SesionTutoria sesionSeleccionada = tblPlanTutorias.getSelectionModel().getSelectedItem();

        if (sesionSeleccionada != null) {
            String estadoActual = sesionSeleccionada.getEstado();
            String nuevoEstado = "ABIERTA".equals(estadoActual) ? "CERRADA" : "ABIERTA";
            
            if ("ABIERTA".equals(nuevoEstado) && !esSesionEditable(sesionSeleccionada)) {
                Utilidades.mostrarAlerta("Acción bloqueada", 
                        "No se puede reabrir la Sesión " + sesionSeleccionada.getNumeroSesion() + 
                        " porque ya existen sesiones posteriores registradas. \n\n" + 
                        "Solo se permite reabrir la última sesión agendada.", 
                        Alert.AlertType.WARNING);
                return;
            }
            String accion = "ABIERTA".equals(estadoActual) ? "cerrar" : "abrir";

            boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Confirmar cambio de estado",
                    "¿Estás seguro de " + accion + " la sesión número " + sesionSeleccionada.getNumeroSesion() + "?",
                    "El estado cambiará a: " + nuevoEstado);

            if (confirmar) {
                cambiarEstadoSesion(sesionSeleccionada.getIdSesion(), nuevoEstado);
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar una sesión de la lista para cambiar su estado.", Alert.AlertType.WARNING);
        }
    }

    private void cambiarEstadoSesion(int idSesion, String nuevoEstado) {
        HashMap<String, Object> respuesta = SesionTutoriaImpl.modificarEstadoSesion(idSesion, nuevoEstado);
        
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlerta("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            cargarTablaSesiones(); 
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private boolean esSesionEditable(SesionTutoria sesionAValidar) {
        
        for (SesionTutoria sesion : listaSesiones) {
            if (sesion.getNumeroSesion() > sesionAValidar.getNumeroSesion()) {
                return false; 
            }
        }
        return true; 
    }
    private boolean existeSesionAbierta() {
        if (listaSesiones == null || listaSesiones.isEmpty()) {
            return false; 
        }

        SesionTutoria ultimaSesion = listaSesiones.get(0);
        for (SesionTutoria s : listaSesiones) {
            if (s.getNumeroSesion() > ultimaSesion.getNumeroSesion()) {
                ultimaSesion = s;
            }
        }
        return "ABIERTA".equalsIgnoreCase(ultimaSesion.getEstado());
    }
}