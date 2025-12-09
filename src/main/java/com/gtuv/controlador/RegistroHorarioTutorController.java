/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gtuv.controlador;

import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.HorarioTutoriaImpl;
import com.gtuv.dominio.SesionTutoriaImpl;
import com.gtuv.modelo.dao.HorarioTutoriasDAO;
import com.gtuv.modelo.dao.SesionTutoriaDAO;
import com.gtuv.modelo.pojo.HorarioTutor;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.SesionTutoria;
import com.gtuv.modelo.pojo.Usuario; 
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.sql.Connection;
import com.gtuv.modelo.ConexionBD; 
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gurov
 */
public class RegistroHorarioTutorController implements Initializable {

    @FXML
    private ComboBox<ProgramaEducativo> cmbProgramaEducativo;
    @FXML
    private TableView<SesionTutoria> tblSesiones;
    @FXML
    private TableColumn<SesionTutoria, Integer> colNumeroSesion;
    @FXML
    private TableColumn<SesionTutoria, String> colFechaSesion;
    @FXML
    private TableColumn<SesionTutoria, String> colHoraInicio;
    @FXML
    private TableColumn<SesionTutoria, String> colEstado;
    @FXML
    private Label lblSesionSeleccionada;
    @FXML
    private ComboBox<String> cmbHora;
    @FXML
    private ComboBox<String> cmbMinuto;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnActualizar; 

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (Sesion.getUsuario() == null) {
            Usuario usuarioPrueba = new Usuario();
            usuarioPrueba.setIdUsuario(1); // El ID que pusimos en el script SQL
            usuarioPrueba.setNombre("Juan Pérez");
            Sesion.setUsuario(usuarioPrueba);
        }
        configurarTabla(); 
        configurarListeners(); 
        cargarCombos(); 
    }    
    
    private void configurarTabla() {
        colNumeroSesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        colFechaSesion.setCellValueFactory(new PropertyValueFactory<>("fechaSesion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
       
        colHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicioTexto"));
        
        Utilidades.alinearCentro(colNumeroSesion, colHoraInicio, colEstado);
    }
    
  private void configurarListeners() {
        cmbProgramaEducativo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarTablaSesiones(newVal.getIdProgramaEducativo());
            }
        });
       
        
        tblSesiones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarDetallesSesion(newVal);
            } else {
                limpiarFormulario();
            }
        });
    }
  
  private void cargarCombos() {
       
        HashMap<String, Object> respuestaPE = ProgramaEducativoImpl.obtenerProgramasEducativos();
        if (!(boolean) respuestaPE.get("error")) {
             List<ProgramaEducativo> lista = (List<ProgramaEducativo>) respuestaPE.get("programas");
             cmbProgramaEducativo.setItems(FXCollections.observableArrayList(lista));
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuestaPE.get("mensaje"), Alert.AlertType.ERROR);
        }

      
        ObservableList<String> horas = FXCollections.observableArrayList();
        for (int i = 7; i <= 21; i++) { horas.add(String.format("%02d", i)); }
        cmbHora.setItems(horas);
        cmbMinuto.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
    }

    private void cargarTablaSesiones(int idProgramaEducativo) {
        
        int idTutor = Sesion.getUsuario().getIdUsuario();
        
        
        HashMap<String, Object> respuesta = SesionTutoriaImpl.obtenerSesionesPorPrograma(idProgramaEducativo, idTutor);
        
        if (!(boolean) respuesta.get("error")) {
            List<SesionTutoria> sesiones = (List<SesionTutoria>) respuesta.get("sesiones");
            tblSesiones.setItems(FXCollections.observableArrayList(sesiones));
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

  private void mostrarDetallesSesion(SesionTutoria sesion) {
        lblSesionSeleccionada.setText("Sesión " + sesion.getNumeroSesion());
        HorarioTutor horario = sesion.getHorario();

        
        boolean esSesionCerrada = "CERRADA".equalsIgnoreCase(sesion.getEstado());

       
        cmbHora.setDisable(esSesionCerrada);
        cmbMinuto.setDisable(esSesionCerrada);

  
        if (esSesionCerrada) {
            
            btnGuardar.setVisible(false);
            btnActualizar.setVisible(false);
        } else {
            
            if (horario != null && horario.getHoraInicio() != null) {

                btnGuardar.setVisible(false);    
                btnActualizar.setVisible(true); 
                
               
                String[] partesHora = horario.getHoraInicio().toString().split(":");
                cmbHora.setValue(partesHora[0]);
                cmbMinuto.setValue(partesHora[1]);
            } else {
               
                btnGuardar.setVisible(true);      
                btnActualizar.setVisible(false);  
                
               
                cmbHora.setValue(null);
                cmbMinuto.setValue(null);
            }
        }
    }
    
    private void limpiarFormulario() {
        lblSesionSeleccionada.setText("--");
        cmbHora.setValue(null);
        cmbMinuto.setValue(null);
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Regresar", "¿Deseas salir?", "Perderás los cambios no guardados.");
        if (confirmar) {
            ((Stage) btnRegresar.getScene().getWindow()).close();
        }
    }

   
    @FXML
    private void clicGuardar(ActionEvent event) {
        SesionTutoria sesionSeleccionada = tblSesiones.getSelectionModel().getSelectedItem();
        
        
        if (sesionSeleccionada == null) {
            Utilidades.mostrarAlerta("Selección requerida", "Selecciona una sesión de la tabla.", Alert.AlertType.WARNING);
            return;
        }

        if (sesionSeleccionada.getHorario() != null) {
            Utilidades.mostrarAlerta("Acción inválida", "Esta sesión ya tiene horario. Usa el botón 'Actualizar'.", Alert.AlertType.WARNING);
            return;
        }

        if (cmbHora.getValue() == null || cmbMinuto.getValue() == null) {
            Utilidades.mostrarAlerta("Campos vacíos", "Debes seleccionar Hora y Minutos.", Alert.AlertType.WARNING);
            return;
        }

       
        Time horaSeleccionada = obtenerHoraSeleccionada();
        
        if (!esHorarioValido(horaSeleccionada)) {
            Utilidades.mostrarAlerta("Horario fuera de rango", "El horario de atención no puede exceder las 21:00 hrs.", Alert.AlertType.WARNING);
            return;
        }

     
        HorarioTutor nuevoHorario = new HorarioTutor();
        nuevoHorario.setIdSesion(sesionSeleccionada.getIdSesion());
        nuevoHorario.setIdTutor(Sesion.getUsuario().getIdUsuario()); 
        nuevoHorario.setHoraInicio(horaSeleccionada);

        HashMap<String, Object> respuesta = HorarioTutoriaImpl.registrarHorario(nuevoHorario);

        procesarRespuesta(respuesta); 
    }
    
    @FXML
    private void clicActualizar(ActionEvent event) {
        SesionTutoria sesionSeleccionada = tblSesiones.getSelectionModel().getSelectedItem();
        
       
        if (sesionSeleccionada == null || sesionSeleccionada.getHorario() == null) {
            Utilidades.mostrarAlerta("Selección requerida", "Selecciona una sesión que ya tenga horario asignado.", Alert.AlertType.WARNING);
            return;
        }
        
        if (cmbHora.getValue() == null || cmbMinuto.getValue() == null) {
            Utilidades.mostrarAlerta("Campos vacíos", "Debes seleccionar Hora y Minutos.", Alert.AlertType.WARNING);
            return;
        }

       
        Time horaSeleccionada = obtenerHoraSeleccionada();

        if (!esHorarioValido(horaSeleccionada)) {
            Utilidades.mostrarAlerta("Horario fuera de rango", "El horario de atención no puede exceder las 21:00 hrs.", Alert.AlertType.WARNING);
            return;
        }

       
        HorarioTutor horarioEdicion = sesionSeleccionada.getHorario();
        horarioEdicion.setHoraInicio(horaSeleccionada);

        HashMap<String, Object> respuesta = HorarioTutoriaImpl.actualizarHorario(horarioEdicion);

        procesarRespuesta(respuesta);
    }

   

    private Time obtenerHoraSeleccionada() {
        String horaStr = cmbHora.getValue() + ":" + cmbMinuto.getValue() + ":00";
        return Time.valueOf(horaStr);
    }

    private boolean esHorarioValido(Time horaSQL) {
        LocalTime hora = horaSQL.toLocalTime();
        LocalTime horaLimite = LocalTime.of(21, 0); 
        return !hora.isAfter(horaLimite);
    }

    private void procesarRespuesta(HashMap<String, Object> respuesta) {
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlerta("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            limpiarFormulario();
            
            
            if (cmbProgramaEducativo.getValue() != null) {
                cargarTablaSesiones(cmbProgramaEducativo.getValue().getIdProgramaEducativo());
            }
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
        
}
