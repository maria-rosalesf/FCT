package org.example.servidor;

public class Protocolo {
    private static final int LOGIN = 0;
    private static final int VERIFICAR_LOGIN = 1;  
    private static final int ACCESO_APLICACION = 2; 
    private static final int VERIFICAR_REGISTRO = 3;
    //private static final int SALIR = 4; 

    private int estado;
    private String mensaje; 
    
    public Protocolo() {
        estado = LOGIN; 
        mensaje = ""; 
    }

    public String procesar(String entrada) {
        if (entrada.split(" ")[0].equals("Login")) {
            switch (estado) {
                case LOGIN:
                    mensaje = "login";
                    estado = VERIFICAR_LOGIN; 
                    break;
                case VERIFICAR_LOGIN:
                    if (entrada.split(" ")[1].equals("true")) {
                        estado = ACCESO_APLICACION; 
                        mensaje = "logueado"; 
                    } else {
                        estado = LOGIN; 
                        mensaje = "volverALoguear"; 
                    }
                    break;
                case ACCESO_APLICACION:
                    mensaje = "loginCorrecto"; 
                    break; 
            }
        } else if (entrada.split(" ")[0].equals("Registro")) {
            estado = VERIFICAR_REGISTRO; 
            switch (estado) {
                case VERIFICAR_REGISTRO:
                    if (entrada.split(" ")[1].equals("true")) {
                        estado = ACCESO_APLICACION; 
                        mensaje = "registrado"; 
                    } else {
                        estado = VERIFICAR_REGISTRO; 
                        mensaje = "volverARegistrar"; 
                    }
                    break;
                case ACCESO_APLICACION:
                    mensaje = "registroCorrecto";
                    break; 
            }
        }

        return mensaje; 
    }
}