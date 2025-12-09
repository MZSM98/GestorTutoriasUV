package com.gtuv.controlador;

import com.gtuv.dominio.TutorImpl;
import com.gtuv.dominio.TutoradoImpl;
import com.gtuv.modelo.pojo.Tutor;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AsignacionTutoradoController implements Initializable {

    private static final int LIMITE_TUTORADOS = 20;

    @FXML
    private Label lblNombreTutor;
    @FXML
    private TextField txtBuscarTutorado;
    
    @FXML
    private TableView<Tutorado> tblTutoradosDisponibles;
    @FXML
    private TableColumn colNombreDisponible;
    @FXML
    private TableColumn colSemestreDisponible;
    @FXML
    private TableColumn colMatriculaDisponible;
    @FXML
    private TableView<Tutorado> tblTutoradosAsignados;
    @FXML
    private TableColumn colNombreAsignado;
    @FXML
    private TableColumn colSemestreAsignado;
    @FXML
    private TableColumn colMatriculaAsignado;
    
    @FXML
    private Label lblContadorTutorados; 
    
    @FXML
    private Button btnAsignar; 

    private Tutor tutorSesion;
    private ObservableList<Tutorado> listaDisponibles;
    private ObservableList<Tutorado> listaAsignados; 
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
    }    
    
    public void inicializarDatos(Tutor tutor){
        this.tutorSesion = tutor;
        lblNombreTutor.setText(tutor.getNombreCompleto());
        cargarTablas();
        configurarBusqueda();
    }
    
    private void configurarColumnas(){
        Utilidades.alinearIzquierda(colNombreAsignado,
                                    colNombreDisponible);
        
        Utilidades.alinearCentro(colSemestreAsignado,
                                 colSemestreDisponible,
                                 colMatriculaAsignado,
                                 colMatriculaDisponible);
        
        colNombreDisponible.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colSemestreDisponible.setCellValueFactory(new PropertyValueFactory<>("nombreSemestre"));
        colNombreAsignado.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colSemestreAsignado.setCellValueFactory(new PropertyValueFactory<>("nombreSemestre"));
        colMatriculaAsignado.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMatriculaDisponible.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    }
    
    private void cargarTablas(){
        if(tutorSesion != null){
            
            HashMap<String, Object> respuestaDisponible = TutoradoImpl.obtenerTutoradosDisponibles();
            if(!(boolean)respuestaDisponible.get("error")){
                listaDisponibles = FXCollections.observableArrayList((ArrayList<Tutorado>)respuestaDisponible.get("tutorados"));
                tblTutoradosDisponibles.setItems(listaDisponibles);
            } else {
                Utilidades.mostrarAlerta("Error", (String)respuestaDisponible.get("mensaje"), Alert.AlertType.ERROR);
            }
            
            HashMap<String, Object> respuestaAsignados = TutoradoImpl.obtenerTutoradosPorTutor(tutorSesion.getIdUsuario());
            if(!(boolean)respuestaAsignados.get("error")){
                listaAsignados = FXCollections.observableArrayList((ArrayList<Tutorado>)respuestaAsignados.get("tutorados"));
                tblTutoradosAsignados.setItems(listaAsignados);
                
                actualizarContadorUI();
            } else {
                Utilidades.mostrarAlerta("Error", (String)respuestaAsignados.get("mensaje"), Alert.AlertType.ERROR);
            }
        }
    }
    
    private void actualizarContadorUI() {
        int totalAsignados = listaAsignados != null ? listaAsignados.size() : 0;
        lblContadorTutorados.setText("(" + totalAsignados + "/" + LIMITE_TUTORADOS + ")");
        
        if (totalAsignados >= LIMITE_TUTORADOS) {
            btnAsignar.setDisable(true); 
            lblContadorTutorados.setStyle("-fx-text-fill: red;"); 
        } else {
            btnAsignar.setDisable(false); 
            lblContadorTutorados.setStyle("-fx-text-fill: black;");
        }
    }

    @FXML
    private void clicAsignar(ActionEvent event) {
        if (listaAsignados.size() >= LIMITE_TUTORADOS) {
            Utilidades.mostrarAlerta("Límite alcanzado", 
                    "Este tutor ya tiene el máximo de " + LIMITE_TUTORADOS + " tutorados asignados.", 
                    Alert.AlertType.WARNING);
            return;
        }

        Tutorado tutoradoSeleccionado = tblTutoradosDisponibles.getSelectionModel().getSelectedItem();
        
        if(tutoradoSeleccionado != null){
            HashMap<String, Object> respuesta = TutorImpl.asignarTutorado(tutorSesion.getIdUsuario(), tutoradoSeleccionado.getIdTutorado());
            
            if(!(boolean)respuesta.get("error")){
                Utilidades.mostrarAlerta("Asignación correcta", 
                        "El estudiante " + tutoradoSeleccionado.getNombreCompleto() + " ha sido asignado.", 
                        Alert.AlertType.INFORMATION);
                
                txtBuscarTutorado.setText(""); 
                cargarTablas(); 
                configurarBusqueda(); 
            } else {
                Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Seleccione un estudiante de la lista 'Sin asignación' para asignarlo.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicDesasignar(ActionEvent event) {
        Tutorado tutoradoSeleccionado = tblTutoradosAsignados.getSelectionModel().getSelectedItem();
        
        if(tutoradoSeleccionado != null){
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Confirmar desasignación", 
                    "¿Estás seguro?", 
                    "Se removerá al estudiante " + tutoradoSeleccionado.getNombreCompleto() + " de la lista de este tutor.");
            
            if(confirmar){
                HashMap<String, Object> respuesta = TutorImpl.desasignarTutorado(tutorSesion.getIdUsuario(),
                           tutoradoSeleccionado.getIdTutorado());
                
                if(!(boolean)respuesta.get("error")){
                    Utilidades.mostrarAlerta("Desasignación correcta", "El estudiante: "
                            + tutoradoSeleccionado.getNombreCompleto() +"\nya no es tutorado del"
                            + "\nTutor(a): " + tutorSesion.getNombreCompleto()
                            + "\ny puede ser asignado a un tutor diferente", Alert.AlertType.INFORMATION);
                    
                    txtBuscarTutorado.setText("");
                    cargarTablas();
                    configurarBusqueda();
                } else {
                    Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Seleccione un estudiante de la lista 'Asignados' para removerlo.", Alert.AlertType.WARNING);
        }
    }
    
    private void configurarBusqueda(){
        if(listaDisponibles != null){
            FilteredList<Tutorado> filtrado = new FilteredList<>(listaDisponibles, p -> true);
            
            txtBuscarTutorado.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtrado.setPredicate(tutorado -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        
                        if(tutorado.getNombre().toLowerCase().contains(lower)){
                            return true;
                        }
                        if(tutorado.getApellidoPaterno().toLowerCase().contains(lower)){
                            return true;
                        }
                        if(tutorado.getApellidoMaterno().toLowerCase().contains(lower)){
                            return true;
                        }
                        if(tutorado.getMatricula().toLowerCase().contains(lower)){
                            return true;
                        }
                        return false;
                    });
                }
            });
            
            SortedList<Tutorado> ordenados = new SortedList<>(filtrado);
            ordenados.comparatorProperty().bind(tblTutoradosDisponibles.comparatorProperty());
            tblTutoradosDisponibles.setItems(ordenados);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) txtBuscarTutorado.getScene().getWindow()).close();
    }
}