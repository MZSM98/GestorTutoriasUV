package com.gtuv.controlador;

import com.gtuv.dominio.UsuarioImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.Usuario;
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

public class GestionUsuarioController implements Initializable, IObservador {

    @FXML
    private TableColumn colApPaterno;
    @FXML
    private TableColumn colApMaterno;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableView<Usuario> tblUsuarios;
    @FXML
    private TableColumn colNoTrabajador;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TextField textBuscar;
    
    private ObservableList<Usuario> usuarios;
    
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
        colNoTrabajador.setCellValueFactory(new PropertyValueFactory("noTrabajador"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }
    
    private void llenarTabla(){
        HashMap<String, Object> respuesta = UsuarioImpl.obtenerUsuarios();
        
        if(!(boolean)respuesta.get("error")){
            ArrayList<Usuario> usuariosBD = (ArrayList<Usuario>) respuesta.get("usuarios");
            usuarios = FXCollections.observableArrayList();
            usuarios.addAll(usuariosBD);
            tblUsuarios.setItems(usuarios);
        }else{
            Utilidades.mostrarAlerta("Error de conexión", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        ((Stage) textBuscar.getScene().getWindow()).close();
    }

    @FXML
    private void clicDarDeBaja(ActionEvent event) {
        Usuario usuarioSeleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        
        if(usuarioSeleccionado != null){
            boolean confirmacion;
            confirmacion = Utilidades.mostrarAlertaConfirmacion("Confirmar eliminación", "¿está seguro de continuar?"
                    , "Está a punto de eliminar el registro del Académico(a) "
                    + usuarioSeleccionado.getNombre() + usuarioSeleccionado.getApellidoPaterno()+ 
                            "\nCon Número de trabajador: "+ usuarioSeleccionado.getNoTrabajador() +"\n esta acción no se puede revertir");
                if(confirmacion)
                    darDeBajaUsuario(usuarioSeleccionado.getIdUsuario());
        }else{
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar un usuario para darlo de baja", Alert.AlertType.WARNING);
        }
    }
    
    private void darDeBajaUsuario(int idUsuario){
        HashMap<String, Object> respuesta = UsuarioImpl.darBajaUsuario(idUsuario);
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Éxito", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            llenarTabla();
            configurarBusqueda();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        Usuario usuarioSeleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if(usuarioSeleccionado != null){
            irFormulario(usuarioSeleccionado);
        }else{
            Utilidades.mostrarAlerta("Selección requerida", "Debe seleccionar un usuario para editarlo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        irFormulario(null);
    }
    
    private void irFormulario(Usuario usuario){
        try{
            FXMLLoader loader = Utilidades.obtenerVistaMemoria("vista/FXMLFormularioUsuario.fxml");
            Parent root = loader.load();
            FormularioUsuarioController controlador = loader.getController();
            controlador.inicializarDatos(this, usuario);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Formulario Usuario");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void configurarBusqueda(){
        if(usuarios != null && usuarios.size() > 0){
            FilteredList<Usuario> filtrado = new FilteredList<>(usuarios, p -> true);
            textBuscar.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtrado.setPredicate(usuario -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        if(usuario.getNombre().toLowerCase().contains(lower)){
                            return true;
                        }
                        if(usuario.getApellidoPaterno().toLowerCase().contains(lower)){
                            return true;
                        }
                        if(usuario.getNoTrabajador().toLowerCase().contains(lower)){
                            return true;
                        }
                        return false;
                    });
                }
            });
            SortedList<Usuario> ordenados = new SortedList<>(filtrado);
            ordenados.comparatorProperty().bind(tblUsuarios.comparatorProperty());
            tblUsuarios.setItems(ordenados);
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        llenarTabla();
        textBuscar.setText("");
        configurarBusqueda();
    }
}