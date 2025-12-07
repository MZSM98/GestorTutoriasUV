
package com.gtuv.utlidad;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encriptacion {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private Encriptacion(){
        throw new UnsupportedOperationException("Es una clase de utileria y no debe ser instanciada");
    }
    
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }
}