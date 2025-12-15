package com.gtuv.controlador;

import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.stage.Stage;

public class MenuResponsabilidadController implements Initializable {

    @FXML
    private Button btnResponsabilidad1;
    @FXML
    private Button btnResponsabilidad2;
    @FXML
    private Button btnResponsabilidad3;
    @FXML
    private Label lblResponsabilidad1;
    @FXML
    private Label lblResponsabilidad2; 
    @FXML
    private Label lblResponsabilidad3;
    @FXML
    private Label lblNombre;
    
    private Usuario usuario;
    
    private class OpcionRol {
        String nombreRol;
        String rutaFXML;

        public OpcionRol(String nombreRol, String rutaFXML) {
            this.nombreRol = nombreRol;
            this.rutaFXML = rutaFXML;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = Sesion.getUsuario();
        if (usuario != null) {
            lblNombre.setText(usuario.getNombreCompleto());
            configurarBotones();
        } else {
            Utilidades.mostrarAlerta("Error de Sesión", "No hay usuario activo.", Alert.AlertType.ERROR);
            clicCerrarSesion(null);
        }
    }    

    private void configurarBotones() {
        ArrayList<OpcionRol> roles = new ArrayList<>();

        if (usuario.isEsAdministrador()) {
            roles.add(new OpcionRol("Administrador", "/com/gtuv/vista/FXMLMenuAdministrador.fxml"));
        }
        if (usuario.isEsJefeCarrera()) {
            roles.add(new OpcionRol("Jefe de Carrera", "/com/gtuv/vista/FXMLMenuJefeDeCarrera.fxml"));
        }
        if (usuario.isEsCoordinador()) {
            roles.add(new OpcionRol("Coordinador", "/com/gtuv/vista/FXMLMenuCoordinador.fxml"));
        }
        if (usuario.isEsTutor()) {
            //
            roles.add(new OpcionRol("Tutor", "/com/gtuv/vista/FXMLMenuTutor.fxml"));
        }

        ocultarBoton(btnResponsabilidad1, lblResponsabilidad1);
        ocultarBoton(btnResponsabilidad2, lblResponsabilidad2); 
        ocultarBoton(btnResponsabilidad3, lblResponsabilidad3); 
        
        if (roles.size() > 0) configurarBoton(btnResponsabilidad1, lblResponsabilidad1, roles.get(0));
        if (roles.size() > 1) configurarBoton(btnResponsabilidad2, lblResponsabilidad2, roles.get(1));
        if (roles.size() > 2) configurarBoton(btnResponsabilidad3, lblResponsabilidad3, roles.get(2));
    }

    private void ocultarBoton(Button btn, Label lbl) {
        if(btn != null) btn.setVisible(false);
        if(lbl != null) lbl.setVisible(false);
    }

    private void configurarBoton(Button btn, Label lbl, OpcionRol opcion) {
        if (btn != null && lbl != null) {
            btn.setVisible(true);
            lbl.setVisible(true);
            lbl.setText(opcion.nombreRol);
            
            btn.setOnAction(event -> irMenu(opcion.rutaFXML));
        }
    }

    private void irMenu(String rutaFXML) {
        try {
            Stage stage = (Stage) lblNombre.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestor de Tutorías UV");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            Utilidades.mostrarAlerta("Error de navegación", Utilidades.ERROR_ABRIR_VENTANA, Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion();
        irMenu("/com/gtuv/vista/FXMLInicioSesion.fxml");
    }
}