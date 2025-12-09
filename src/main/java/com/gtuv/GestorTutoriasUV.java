package com.gtuv;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestorTutoriasUV extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/gtuv/vista/FXMLGestionReporteTutoria.fxml"));
            Scene scene = new Scene (parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(IOException e) {
            System.out.println("No se pudo cargar la ventana de inicio sesión.");
            return;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
