package com.gtuv.controlador;

import com.gtuv.dominio.AutenticacionImpl;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Sesion; 
import com.gtuv.utlidad.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InicioSesionController implements Initializable {

    @FXML
    private TextField txtNoTrabajador;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private Label lblErrorNoTrabajador;
    @FXML
    private Label lblErrorContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limpiarEtiquetas();
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lblErrorNoTrabajador.setText("");
        lblErrorContrasenia.setText("");
        
        String noTrabajador = txtNoTrabajador.getText();
        String password = txtContrasenia.getText();
        boolean valido = true;

        if(noTrabajador.isEmpty()){
            lblErrorNoTrabajador.setText("Campo obligatorio");
            valido = false;
        }
        if(password.isEmpty()){
            lblErrorContrasenia.setText("Campo obligatorio");
            valido = false;
        }

        if(valido){
            verificarCredenciales(noTrabajador, password);
        }
    }

    private void verificarCredenciales(String noTrabajador, String password){
        HashMap<String, Object> respuesta = AutenticacionImpl.autenticarUsuario(noTrabajador, password);
        
        if(!(boolean)respuesta.get("error")){
            Usuario usuario = (Usuario) respuesta.get("usuario");
            Sesion.setUsuario(usuario);
            
            int cantidadRoles = 0;
            String rolUnico = "";
            
            if(usuario.isEsAdministrador()) {
                cantidadRoles++; rolUnico = "ADMIN";
            }
            if(usuario.isEsTutor()) {
                cantidadRoles++; rolUnico = "TUTOR";
            }
            if(usuario.isEsCoordinador()) {
                cantidadRoles++; rolUnico = "COORDINADOR";
            }
            if(usuario.isEsJefeCarrera()) {
                cantidadRoles++; rolUnico = "JEFE";
            }
            
            if (cantidadRoles == 0) {
                Sesion.cerrarSesion();
                Utilidades.mostrarAlerta("Sin permisos", 
                    "Usted no tiene asignada ninguna responsabilidad en el Sistema de Gestión de Tutorías por el momento, contacte con el Director para la asignación de responsabilidades", 
                    Alert.AlertType.WARNING);
            } else if (cantidadRoles == 1) {
                irMenuDirecto(rolUnico);
            } else {
                irSeleccionResponsabilidad();
            }
            
        } else {
            Utilidades.mostrarAlerta("Error de credenciales", (String)respuesta.get("mensaje"), Alert.AlertType.WARNING);
        }
    }

    private void irMenuDirecto(String rol) {
        String fxmlDestino = "";
        switch (rol) {
            case "ADMIN": fxmlDestino = "/com/gtuv/vista/FXMLMenuAdministrador.fxml"; break;
            case "TUTOR": fxmlDestino = "/com/gtuv/vista/FXMLMenuTutor.fxml"; break;
            case "COORDINADOR": fxmlDestino = "/com/gtuv/vista/FXMLMenuCoordinador.fxml"; break;
            case "JEFE": fxmlDestino = "/com/gtuv/vista/FXMLMenuJefeCarrera.fxml"; break;
        }
        abrirVentana(fxmlDestino, "Menú Principal");
    }

    private void irSeleccionResponsabilidad() {
        abrirVentana("/com/gtuv/vista/FXMLMenuResponsabilidad.fxml", "Selección de Rol");
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            Stage escenarioPrincipal = (Stage) txtNoTrabajador.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene escena = new Scene(root);
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle(titulo);
            escenarioPrincipal.centerOnScreen();
            escenarioPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlerta("Error", "No se pudo cargar la ventana: " + fxml, Alert.AlertType.ERROR);
        }
    }
    
    private void limpiarEtiquetas(){
        lblErrorNoTrabajador.setText("");
        lblErrorContrasenia.setText("");
    }
    
}