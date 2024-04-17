package org.example.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.example.dao.LoginDAO;
import org.example.model.Direccion;
import org.example.model.Profesional;
import org.example.servidor.Protocolo;

public class LoginThread extends Thread {
    private Socket cliente;
    private Protocolo protocolo;
    private boolean loginCorrecto;
    private boolean registroCorrecto; 
    private String correo;
    private String contrasenia;
    private LoginDAO dao;

    public LoginThread(Socket cliente, Protocolo protocolo) {
        this.cliente = cliente;
        this.protocolo = protocolo;
        this.dao = new LoginDAO();
        loginCorrecto = false; 
        registroCorrecto = false; 
    }

    public boolean loginExitoso() {
        return loginCorrecto;
    }

    public boolean registroExitoso() {
        return registroCorrecto; 
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
            String input, output;

            String tipoAccion = entrada.readUTF(); 
            do {                

                if (tipoAccion.equals("Login")) {
                    output = protocolo.procesar(tipoAccion + " ");
                    salida.writeUTF(output); 
                    salida.flush();

                    input = entrada.readUTF();
                    correo = input.split(";")[0];
                    contrasenia = input.split(";")[1];

                    // Validar las credenciales del usuario utilizando la clase LoginDAO
                    loginCorrecto = dao.verificarCredenciales(correo, contrasenia);

                    output = protocolo.procesar("Login " + loginCorrecto);
                    salida.writeUTF(output);
                    salida.flush();

                } else if (tipoAccion.equals("Registro")) {

                    String [] datos = entrada.readUTF().split(";"); 
                    String nombre = datos[0];
                    String apellidos = datos[1];
                    String dni =  datos[2];
                    String fechaNacimiento = datos[3];
                    String telefono = datos[4];
                    String correo = datos[5];
                    String contrasenia = datos[6];            
                    String calle = datos[7];
                    int numero = Integer.parseInt(datos[8]);
                    int cp = Integer.parseInt(datos[9]);
                    String provincia = datos[10];
                    String ciudad = datos[11];
                    String especialidad = datos[12];

                    // Crear objetos Direccion y Profesional
                    Direccion direccion = new Direccion(calle, numero, cp, provincia, ciudad);
                    Profesional profesional = new Profesional(nombre, apellidos, correo, contrasenia, fechaNacimiento,
                            telefono, dni, direccion, especialidad);

                    // Utilizar el DAO para registrar al profesional
                    registroCorrecto = dao.registroProfesional(direccion, profesional);
                    System.out.println( " registro: " + registroCorrecto);
                    output = protocolo.procesar("Registro " + registroCorrecto);
                    System.out.println(output);
                    salida.writeUTF(output);
                    salida.flush();
                }                

            } while (!loginCorrecto || !registroCorrecto);
        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        } 
    }   
}