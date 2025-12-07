
package com.gtuv.utlidad;

import com.gtuv.GestorTutoriasUV;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;


public class Utilidades {
    private Utilidades(){
        
        throw new UnsupportedOperationException(ERROR_CLASE_UTILERIA);
    }
    
    public static final String ERROR_BD = "Error de conexion con la base de datos...";
    public static final String ERROR_CLASE_UTILERIA = "Esta clase no debe ser instanciada...";
    public static final String ERROR_MENSAJE_VISTA = "¡Oh, no! Algo salió mal… :( No pudimos procesar tu operación,"
                               + " por favor intenta más tarde";
    
    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }
    
    public static FXMLLoader obtenerVistaMemoria(String url){
        
        return new FXMLLoader(GestorTutoriasUV.class.getResource(url));
    }
}
