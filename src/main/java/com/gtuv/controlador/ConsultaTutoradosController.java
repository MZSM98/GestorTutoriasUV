package com.gtuv.controlador;

import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.TutoradoImpl;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.Tutorado;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ConsultaTutoradosController implements Initializable {

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
    private TextField txtBuscarTutorado;
    @FXML
    private TableView<Tutorado> tblTutoradosRiesgo; 
    
    private ObservableList<Tutorado> listaTutoradosRiesgo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDatos();
        configurarBusqueda();
    }    
    
    private void configurarColumnas() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("nombreSemestre"));
        Utilidades.alinearCentro(colMatricula, colSemestre);
    }
    
    private void cargarDatos() {
        Usuario usuarioSesion = Sesion.getUsuario();
        
        if (usuarioSesion != null) {
            HashMap<String, Object> respPrograma = ProgramaEducativoImpl.obtenerProgramaPorUsuario(usuarioSesion.getIdUsuario());
            if (!(boolean) respPrograma.get("error")) {
                ProgramaEducativo programa = (ProgramaEducativo) respPrograma.get("programa");
                if (programa != null) {
                    cargarTutoradosEnRiesgo(programa.getIdProgramaEducativo());
                } else {
                    Utilidades.mostrarAlerta("Sin programa educativo", "No te encuentras asociado a un programa educativo", Alert.AlertType.WARNING);
                }
            } else {
                Utilidades.mostrarAlerta("Error", (String) respPrograma.get("mensaje"), Alert.AlertType.ERROR);
            }
        }
    }
    
    private void cargarTutoradosEnRiesgo(int idPrograma) {
        HashMap<String, Object> respuesta = TutoradoImpl.obtenerTutoradosEnRiesgo(idPrograma);
        
        if (!(boolean) respuesta.get("error")) {
            ArrayList<Tutorado> lista = (ArrayList<Tutorado>) respuesta.get("tutorados");
            listaTutoradosRiesgo = FXCollections.observableArrayList(lista);
            tblTutoradosRiesgo.setItems(listaTutoradosRiesgo);
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void configurarBusqueda() {
        if (listaTutoradosRiesgo != null) {
            FilteredList<Tutorado> filtrado = new FilteredList<>(listaTutoradosRiesgo, p -> true);
            
            txtBuscarTutorado.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrado.setPredicate(tutorado -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    
                    if (tutorado.getNombre().toLowerCase().contains(lower)) return true;
                    if (tutorado.getApellidoPaterno().toLowerCase().contains(lower)) return true;
                    if (tutorado.getApellidoMaterno() != null && tutorado.getApellidoMaterno().toLowerCase().contains(lower)) return true;
                    if (tutorado.getMatricula().toLowerCase().contains(lower)) return true;
                    
                    return false;
                });
            });
            
            SortedList<Tutorado> ordenados = new SortedList<>(filtrado);
            ordenados.comparatorProperty().bind(tblTutoradosRiesgo.comparatorProperty());
            tblTutoradosRiesgo.setItems(ordenados);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) txtBuscarTutorado.getScene().getWindow()).close();
    }
}