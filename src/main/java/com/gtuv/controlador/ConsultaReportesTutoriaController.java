package com.gtuv.controlador;

import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.ReporteTutoriaImpl;
import com.gtuv.dominio.TutorImpl;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.ReporteTutoria;
import com.gtuv.modelo.pojo.Tutor;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ConsultaReportesTutoriaController implements Initializable {

    @FXML
    private TableView<ReporteTutoria> tblReportesTutoria;
    @FXML
    private TableColumn<ReporteTutoria, Integer> colNoSesion;
    @FXML
    private TableColumn<ReporteTutoria, String> colFechaElaboracion; 
    @FXML
    private TableColumn<ReporteTutoria, Integer> colTotalAsistencia;
    @FXML
    private TableColumn<ReporteTutoria, Integer> colTotalEnRiesgo;
    @FXML
    private TableColumn<ReporteTutoria, String> colEstado;
    @FXML
    private ComboBox<Tutor> comboTutor;
    
    private ProgramaEducativo programaEducativo;
    private ObservableList<ReporteTutoria> listaReportes;
    private ObservableList<Tutor> listaTutores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarProgramaEducativo(); 
        if (programaEducativo != null) {
            cargarTutores();       
            configurarListenerCombo(); 
        }
    }    

    private void configurarTabla(){
        colNoSesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        colFechaElaboracion.setCellValueFactory(new PropertyValueFactory<>("fechaSesion")); 
        colTotalAsistencia.setCellValueFactory(new PropertyValueFactory<>("totalAsistencia"));
        colTotalEnRiesgo.setCellValueFactory(new PropertyValueFactory<>("totalRiesgo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        Utilidades.alinearCentro(colNoSesion, colTotalAsistencia, colTotalEnRiesgo, colEstado);
    }
    
    private void cargarProgramaEducativo() {
        Usuario coordinador = Sesion.getUsuario();
        if (coordinador != null) {
            HashMap<String, Object> respuesta = ProgramaEducativoImpl.obtenerProgramaPorUsuario(coordinador.getIdUsuario());
            if (!(boolean) respuesta.get("error")) {
                programaEducativo = (ProgramaEducativo) respuesta.get("programa");
            } else {
                Utilidades.mostrarAlerta("Error", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
            }
        }
    }

    private void cargarTutores() {
        if (programaEducativo != null) {
            HashMap<String, Object> respuesta = TutorImpl.obtenerTutoresPorProgramaEducativo(programaEducativo.getIdProgramaEducativo());
            
            if (!(boolean) respuesta.get("error")) {
                ArrayList<Tutor> tutoresEncontrados = (ArrayList<Tutor>) respuesta.get("tutores");
                listaTutores = FXCollections.observableArrayList();
                listaTutores.addAll(tutoresEncontrados);
                comboTutor.setItems(listaTutores);
            } else {
                Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
        }
    }

    private void configurarListenerCombo() {
        comboTutor.valueProperty().addListener(new ChangeListener<Tutor>() {
            @Override
            public void changed(ObservableValue<? extends Tutor> observable, Tutor oldValue, Tutor newValue) {
                if (newValue != null) {
                    cargarReportesDeTutor(newValue.getIdUsuario());
                }
            }
        });
    }

    private void cargarReportesDeTutor(int idTutor) {
        if (programaEducativo == null) return;

        HashMap<String, Object> respuesta = ReporteTutoriaImpl.obtenerReportesPorTutor(idTutor, programaEducativo.getIdProgramaEducativo());
        
        if (!(boolean) respuesta.get("error")) {
            ArrayList<ReporteTutoria> reportes = (ArrayList<ReporteTutoria>) respuesta.get("reportes");
            listaReportes = FXCollections.observableArrayList(reportes);
            tblReportesTutoria.setItems(listaReportes);
            
            if (listaReportes.isEmpty()) {
                Utilidades.mostrarAlerta("Sin datos", "El tutor seleccionado no ha entregado reportes para este programa educativo.", Alert.AlertType.INFORMATION);
            }
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) tblReportesTutoria.getScene().getWindow()).close();
    }

    @FXML
    private void clicResponder(ActionEvent event) {
        ReporteTutoria seleccion = tblReportesTutoria.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            Utilidades.mostrarAlerta("Funcionalidad en desarrollo", "Aquí se abriría el detalle del reporte " + seleccion.getIdReporteTutoria(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Seleccione un reporte de la tabla para ver detalles o responder.", Alert.AlertType.WARNING);
        }
    }

}