package org.example.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.example.dao.LoginDAO;
import org.example.model.Direccion;
import org.example.model.Profesional;

public class RegistroProfesionalHandler {
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private LoginDAO dao; 

    public RegistroProfesionalHandler(Socket socket) {
        this.socket = socket;
        dao = new LoginDAO(); 
        try {
            this.entrada = new DataInputStream(socket.getInputStream());
            this.salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void procesarRegistroProfesional() {
        try {
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
            boolean registroExitoso = dao.registroProfesional(direccion, profesional);

            // Enviar respuesta al cliente
            salida.writeBoolean(registroExitoso);
            salida.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
