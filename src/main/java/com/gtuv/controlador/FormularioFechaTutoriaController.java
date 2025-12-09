package com.gtuv.controlador;

import com.gtuv.dominio.SesionTutoriaImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.PeriodoEscolar;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.SesionTutoria;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioFechaTutoriaController implements Initializable {

    @FXML
    private Label lblErrorFecha;
    @FXML
    private TextField txtPeriodoEscolar;
    @FXML
    private DatePicker dpFechaProgramada;
    @FXML
    private TextField txtSesionTutoria;
    
    private IObservador observador;
    private SesionTutoria sesionEdicion;
    private PeriodoEscolar periodoActual;
    private ProgramaEducativo programaEducativo;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpFechaProgramada.setEditable(false);
    }    
    
    public void inicializarDatos(IObservador observador, SesionTutoria sesion, PeriodoEscolar periodo, ProgramaEducativo programa){
        this.observador = observador;
        this.sesionEdicion = sesion;
        this.periodoActual = periodo;
        this.programaEducativo = programa;
        
        cargarDatosPorDefecto();
        configurarDatePicker();
        if(sesionEdicion != null){
            cargarDatosEdicion();
        }else{
            calcularNumeroSesion();
        }
    }
    
    private void cargarDatosPorDefecto(){
        if(periodoActual != null){
            txtPeriodoEscolar.setText(periodoActual.getNombre());
        }
        txtPeriodoEscolar.setEditable(false);
        txtSesionTutoria.setEditable(false);
    }
    
    private void configurarDatePicker() {
        dpFechaProgramada.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                
                if (empty || date.isBefore(LocalDate.now()) || (date.getDayOfWeek() == DayOfWeek.SATURDAY || 
                                         date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); 
                }
            }
        });
    }
    
    private void calcularNumeroSesion(){
        HashMap<String, Object> respuesta = SesionTutoriaImpl.obtenerSesionesPorPeriodo(
                periodoActual.getIdPeriodo(), 
                programaEducativo.getIdProgramaEducativo());
        
        if(!(boolean)respuesta.get("error")){
            ArrayList<SesionTutoria> sesiones = (ArrayList<SesionTutoria>) respuesta.get("sesiones");
            int siguienteNumero = sesiones.size() + 1;
            txtSesionTutoria.setText(String.valueOf(siguienteNumero));
        }else{
            Utilidades.mostrarAlerta("Error", "No se pudo calcular el número de sesión", Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    
    private void cargarDatosEdicion(){
        txtSesionTutoria.setText(String.valueOf(sesionEdicion.getNumeroSesion()));
        if(sesionEdicion.getFechaSesion() != null){
            dpFechaProgramada.setValue(LocalDate.parse(sesionEdicion.getFechaSesion()));
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        lblErrorFecha.setText("");
        
        if(dpFechaProgramada.getValue() == null){
            lblErrorFecha.setText("Campo obligatorio");
            return;
        }
        
        LocalDate fechaSeleccionada = dpFechaProgramada.getValue();
        
        if(validarReglasDeNegocio(fechaSeleccionada)){
            if(sesionEdicion == null){
                registrarSesion();
            }else{
                editarSesion();
            }
        }
    }
    
    private void registrarSesion(){
        SesionTutoria nuevaSesion = new SesionTutoria();
        nuevaSesion.setIdPeriodo(periodoActual.getIdPeriodo());
        nuevaSesion.setIdProgramaEducativo(programaEducativo.getIdProgramaEducativo());
        nuevaSesion.setNumeroSesion(Integer.parseInt(txtSesionTutoria.getText()));
        nuevaSesion.setFechaSesion(dpFechaProgramada.getValue().toString());
        
        HashMap<String, Object> respuesta = SesionTutoriaImpl.registrarSesion(nuevaSesion);
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Registro exitoso", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", "Sesion " + nuevaSesion.getNumeroSesion());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void editarSesion(){
        sesionEdicion.setFechaSesion(dpFechaProgramada.getValue().toString());
        
        HashMap<String, Object> respuesta = SesionTutoriaImpl.editarSesion(sesionEdicion);
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Edición exitosa", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Edicion", "Sesion " + sesionEdicion.getNumeroSesion());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVentana(){
        ((Stage) txtSesionTutoria.getScene().getWindow()).close();
    }
    
    private boolean validarReglasDeNegocio(LocalDate fecha) {
        if (!esFechaEnRangoValido(fecha)) {
            return false;
        }

        ArrayList<SesionTutoria> sesiones = obtenerSesionesDelPeriodo();
        if (sesiones == null) {
            return false;
        }

        int numeroSesionActual = Integer.parseInt(txtSesionTutoria.getText());
        return esFechaCoherenteConSesiones(fecha, sesiones, numeroSesionActual);
    }
    
    private boolean esFechaEnRangoValido(LocalDate fecha) {
        if (fecha.isBefore(LocalDate.now())) {
            lblErrorFecha.setText("La fecha no puede ser anterior a hoy");
            return false;
        }

        if (periodoActual.getFechaFin() != null) {
            LocalDate finPeriodo = periodoActual.getFechaFin().toLocalDate();
            if (fecha.isAfter(finPeriodo)) {
                lblErrorFecha.setText("La fecha excede el cierre del periodo escolar (" + finPeriodo + ")");
                return false;
            }
        }
        return true;
    }
    
    private ArrayList<SesionTutoria> obtenerSesionesDelPeriodo() {
        HashMap<String, Object> respuesta = SesionTutoriaImpl.obtenerSesionesPorPeriodo(
                periodoActual.getIdPeriodo(), 
                programaEducativo.getIdProgramaEducativo());

        if ((boolean) respuesta.get("error")) {
            Utilidades.mostrarAlerta("Error", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
            return null;
        }

        return (ArrayList<SesionTutoria>) respuesta.get("sesiones");
    }
    
    private boolean esFechaCoherenteConSesiones(LocalDate fechaNueva, ArrayList<SesionTutoria> sesiones, int numSesionActual) {
        for (SesionTutoria s : sesiones) {
            if (sesionEdicion != null && s.getIdSesion() == sesionEdicion.getIdSesion()) {
                continue; 
            }

            LocalDate fechaExistente = LocalDate.parse(s.getFechaSesion());

            if (fechaExistente.equals(fechaNueva)) {
                lblErrorFecha.setText("Ya existe una tutoría programada para esta fecha");
                return false;
            }

            if (!esOrdenCronologicoValido(fechaNueva, fechaExistente, s.getNumeroSesion(), numSesionActual)) {
                return false;
            }
        }
        return true;
    }
    private boolean esOrdenCronologicoValido(LocalDate fechaNueva, LocalDate fechaExistente, int numSesionComparar, int numSesionActual) {
        if (numSesionComparar == (numSesionActual - 1) && !fechaNueva.isAfter(fechaExistente)) {
                lblErrorFecha.setText("La fecha debe ser posterior a la Sesión " + numSesionComparar + " (" + fechaExistente + ")");
                return false;
        }

        if (numSesionComparar == (numSesionActual + 1) && !fechaNueva.isBefore(fechaExistente)) {
                lblErrorFecha.setText("La fecha debe ser anterior a la Sesión " + numSesionComparar + " (" + fechaExistente + ")");
                return false;
        }

        return true;
    }
    
    
}