
package com.gtuv.utlidad;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;


public class RestriccionCampos {
    
    private RestriccionCampos(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA
        );
    }
    
    public static void limitarLongitud(TextInputControl campotexto, int maxLength) {
        campotexto.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > maxLength) {
                return null; 
            }
            return change;
        }));
    }

    public static void soloLetras(TextInputControl campotexto) {
        aplicarPatron(campotexto, "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*");
    }
    
    public static void limitarCantidadNumeros(TextInputControl campoTexto, int maxLength) {
         campoTexto.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*") && newText.length() <= maxLength) {
                return change;
            }
            return null;
        }));
    }

    public static void aplicarPatron(TextInputControl campoTexto, String regex) {
        campoTexto.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches(regex)) {
                return change;
            }
            return null;
        }));
    }
}
