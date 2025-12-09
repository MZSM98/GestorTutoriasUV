package com.gtuv.controlador;

import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuAdministradorController implements Initializable {

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnTutorados;
    @FXML
    private Label lblUsuario;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Label lblRol;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosUsuario();
    }
    
    private void cargarDatosUsuario() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario != null) {
            lblUsuario.setText(usuario.getNombreCompleto());
            lblRol.setText("Administrador");
        }
    }

    @FXML
    private void clicUsuarios(ActionEvent event) {
        abrirVentanaModal("/com/gtuv/vista/FXMLGestionUsuario.fxml", "Gestión de Usuarios");
    }

    @FXML
    private void clicTutorados(ActionEvent event) {
        abrirVentanaModal("/com/gtuv/vista/FXMLGestionTutorado.fxml", "Gestión de Tutorados");
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        Usuario usuario = Sesion.getUsuario();
        int roles = contarRoles(usuario);

        if (roles > 1) {
            irAMenu("/com/gtuv/vista/FXMLMenuResponsabilidad.fxml", "Selección de Rol");
        } else {
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Cerrar Sesión", 
                    "¿Desea cerrar su sesión?", 
                    "Será redirigido a la pantalla de inicio.");
            
            if (confirmar) {
                Sesion.cerrarSesion();
                irAMenu("/com/gtuv/vista/FXMLInicioSesion.fxml", "Inicio de Sesión");
            }
        }
    }
    
    private int contarRoles(Usuario usuario) {
        int cantidad = 0;
        if (usuario.isEsAdministrador()) cantidad++;
        if (usuario.isEsTutor()) cantidad++;
        if (usuario.isEsCoordinador()) cantidad++;
        if (usuario.isEsJefeCarrera()) cantidad++;
        return cantidad;
    }

    private void abrirVentanaModal(String ruta, String titulo) {
        try {
            FXMLLoader loader = Utilidades.obtenerVistaMemoria(ruta);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.initModality(Modality.APPLICATION_MODAL); 
            stage.showAndWait();
        } catch (IOException e) {
            Utilidades.mostrarAlerta("Error", "No se pudo cargar la ventana: " + ruta, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void irAMenu(String ruta, String titulo) {
        try {
            Stage stageActual = (Stage) lblRol.getScene().getWindow();
            
            FXMLLoader loader = Utilidades.obtenerVistaMemoria(ruta);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            stageActual.setScene(scene);
            stageActual.setTitle(titulo);
            stageActual.centerOnScreen();
            stageActual.show();
        } catch (IOException e) {
            Utilidades.mostrarAlerta("Error de navegación", "No se pudo cargar la vista: " + ruta, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}