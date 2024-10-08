package com.kurko67.controlsalones.util.paginator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarPassword {
    public static void main(String[] args) {

        var password = "123";
        System.out.println("Pass sin encrip: " + password);
        System.out.println("Pass  encrip: " + encriptarPassword(password));
    }

    public static String encriptarPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
