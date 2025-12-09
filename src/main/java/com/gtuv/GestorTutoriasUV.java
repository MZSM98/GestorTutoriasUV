package com.gtuv;

import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Sesion;
import com.gtuv.utlidad.Utilidades;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class GestorTutoriasUV extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        try {
            
            Parent parent = FXMLLoader.load(getClass().getResource("/com/gtuv/vista/FXMLInicioSesion.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch(IOException e) {
            Utilidades.mostrarAlerta("Error", Utilidades.ERROR_ABRIR_VENTANA, Alert.AlertType.ERROR);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
