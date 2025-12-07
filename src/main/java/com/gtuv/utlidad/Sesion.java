
package com.gtuv.utlidad;

import com.gtuv.modelo.pojo.Usuario;


public class Sesion {
    
    private Sesion(){
        throw new UnsupportedOperationException(Utilidades.ERROR_CLASE_UTILERIA);
    }
    
    private static Usuario usuarioActual;

    public static void setUsuario(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }
    
        
    public static void cerrarSesion() {
        usuarioActual = null;
    }
    
}