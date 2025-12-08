package com.gtuv.controlador;

import com.gtuv.dominio.TutoradoImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestionTutoradoController implements Initializable, IObservador {

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnRegistrar;
    @FXML
    private TableView<Tutorado> tblTutorados;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colApPaterno;
    @FXML
    private TableColumn colApMaterno;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colProgramaEducativo;
    @FXML
    private TextField textBuscar;
    
    private ObservableList<Tutorado> tutorados;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        llenarTabla();
        configurarBusqueda();
    }    
    
    private void configurarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colProgramaEducativo.setCellValueFactory(new PropertyValueFactory("nombreProgramaEducativo"));
    }
    
    private void llenarTabla() {
        HashMap<String, Object> respuesta = TutoradoImpl.obtenerTutorados();
        if (!(boolean) respuesta.get("error")) {
            ArrayList<Tutorado> lista = (ArrayList<Tutorado>) respuesta.get("tutorados");
            tutorados = FXCollections.observableArrayList();
            tutorados.addAll(lista);
            tblTutorados.setItems(tutorados);
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void configurarBusqueda() {
        if (tutorados != null && tutorados.size() > 0) {
            FilteredList<Tutorado> filtrado = new FilteredList<>(tutorados, p -> true);
            textBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrado.setPredicate(tutorado -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (tutorado.getNombre().toLowerCase().contains(lower)) return true;
                    if (tutorado.getApellidoPaterno().toLowerCase().contains(lower)) return true;
                    if (tutorado.getMatricula().toLowerCase().contains(lower)) return true;
                    return false;
                });
            });
            SortedList<Tutorado> ordenados = new SortedList<>(filtrado);
            ordenados.comparatorProperty().bind(tblTutorados.comparatorProperty());
            tblTutorados.setItems(ordenados);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) tblTutorados.getScene().getWindow()).close();
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        irFormulario(null);
    }
    
    @FXML
    private void clicEditar(ActionEvent event) {
        Tutorado seleccionado = tblTutorados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            irFormulario(seleccionado);
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Seleccione un tutorado para editar", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void clicDarDeBaja(ActionEvent event) {
        Tutorado seleccionado = tblTutorados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Confirmar baja", "¿Está seguro de eliminar a " + seleccionado.getNombre() + "?");
            if (confirmar) {
                darDeBajaTutorado(seleccionado.getIdTutorado());
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Seleccione un tutorado para dar de baja", Alert.AlertType.WARNING);
        }
    }
    
    private void darDeBajaTutorado(int idTutorado) {
        HashMap<String, Object> respuesta = TutoradoImpl.darBajaTutorado(idTutorado);
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlerta("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            llenarTabla();
            configurarBusqueda();
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void irFormulario(Tutorado tutorado) {
        try {
            FXMLLoader loader = Utilidades.obtenerVistaMemoria("vista/FXMLFormularioTutorado.fxml");
            Parent root = loader.load();
            FormularioTutoradoController controlador = loader.getController();
            controlador.inicializarDatos(this, tutorado);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Formulario Tutorado");
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        llenarTabla();
        textBuscar.setText("");
        configurarBusqueda();
    }
}