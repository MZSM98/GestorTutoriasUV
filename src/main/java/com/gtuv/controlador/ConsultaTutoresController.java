package com.gtuv.controlador;

import com.gtuv.dominio.TutorImpl;
import com.gtuv.modelo.pojo.Tutor;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConsultaTutoresController implements Initializable {

    @FXML
    private TableView<Tutor> tblTutores;
    @FXML
    private TableColumn colNoTrabajador;
    @FXML
    private TableColumn colApPaterno;
    @FXML
    private TableColumn colApMaterno;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colNoTutorados; 
    
    @FXML
    private TextField txtBuscarTutor;
    
    private ObservableList<Tutor> listaTutores;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        llenarTabla();
        configurarBusqueda();
    }    
    
    private void configurarTabla(){
        
        Utilidades.alinearIzquierda(
            colNoTrabajador, 
            colApPaterno, 
            colApMaterno, 
            colNombre, 
            colCorreo
        );

        Utilidades.alinearCentro(colNoTutorados);
        colNoTrabajador.setCellValueFactory(new PropertyValueFactory<>("noTrabajador"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colNoTutorados.setCellValueFactory(new PropertyValueFactory<>("noTutorados"));
    }
    
    private void llenarTabla(){
        HashMap<String, Object> respuesta = TutorImpl.obtenerTutores();
        
        if(!(boolean)respuesta.get("error")){
            ArrayList<Tutor> tutoresBD = (ArrayList<Tutor>) respuesta.get("tutores");
            listaTutores = FXCollections.observableArrayList();
            listaTutores.addAll(tutoresBD);
            tblTutores.setItems(listaTutores);
        }else{
            Utilidades.mostrarAlerta("Error de conexión", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    

    private void clicAsignar(ActionEvent event) {
        Tutor tutorSeleccionado = tblTutores.getSelectionModel().getSelectedItem();
        
        if(tutorSeleccionado != null){
            irPantallaAsignacion(tutorSeleccionado);
        }else{
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar un tutor de la lista para asignar tutorados", Alert.AlertType.WARNING);
        }
    }
    
    private void irPantallaAsignacion(Tutor tutor){
        try{
            FXMLLoader loader = Utilidades.obtenerVistaMemoria("vista/FXMLAsignacionTutorado.fxml");
            Parent root = loader.load();
            
            // AsignacionTutoradoController controlador = loader.getController();
            // controlador.inicializarDatos(tutor); 
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Asignación de Tutorados");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            llenarTabla();
            configurarBusqueda();
            
        }catch(IOException e){
            e.printStackTrace();
            Utilidades.mostrarAlerta("Error", "No se pudo cargar la ventana de asignación", Alert.AlertType.ERROR);
        }
    }

    private void clicRegresar(ActionEvent event) {
        ((Stage) txtBuscarTutor.getScene().getWindow()).close();
    }
    
    private void configurarBusqueda(){
        if(listaTutores != null && listaTutores.size() > 0){
            FilteredList<Tutor> filtrado = new FilteredList<>(listaTutores, p -> true);
            
            txtBuscarTutor.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtrado.setPredicate(tutor -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        
                        if(tutor.getNombre().toLowerCase().contains(lower)){
                            return true;
                        }
                        if(tutor.getApellidoPaterno().toLowerCase().contains(lower)){
                            return true;
                        }
                        if(tutor.getNoTrabajador().toLowerCase().contains(lower)){
                            return true;
                        }
                        return false; 
                    });
                }
            });
            
            SortedList<Tutor> ordenados = new SortedList<>(filtrado);
            ordenados.comparatorProperty().bind(tblTutores.comparatorProperty());
            tblTutores.setItems(ordenados);
        }
    }
}