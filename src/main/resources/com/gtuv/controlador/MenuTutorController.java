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

/**
 * FXML Controller class
 *
 * @author User
 */
public class MenuTutorController implements Initializable {

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblRol;
    @FXML
    private Button btnTutorias;
    @FXML
    private Button btnReportes;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarUsuario();
    }    
    
    private void configurarUsuario() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario != null) {
            lblUsuario.setText(usuario.getNombreCompleto());
            lblRol.setText("Tutor Académico");
        }
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

    @FXML
    private void clicSesiones(ActionEvent event) {
        abrirVentanaModal("/com/gtuv/vista/FXMLHorarioTutoria.fxml", "Horario de Tutoría");
    }

    @FXML
    private void clicReportes(ActionEvent event) {
        abrirVentanaModal("/com/gtuv/vista/FXMLGestionReporteTutoria.fxml", "Gestión de Reporte de Tutoría");
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
            e.printStackTrace();
            Utilidades.mostrarAlerta("No podemos navegar", Utilidades.ERROR_ABRIR_VENTANA , Alert.AlertType.ERROR);
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
            e.printStackTrace();
            Utilidades.mostrarAlerta("No podemos navegar", Utilidades.ERROR_ABRIR_VENTANA , Alert.AlertType.ERROR);
        }
    }
}