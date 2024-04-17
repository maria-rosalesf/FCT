package org.example.model;

public class Profesional extends Usuario {
    private String especialidad; 

    public Profesional(String nombre, String apellidos, String correo, String contrasenia, String fechaNacimiento, String telefono, String dni, Direccion direccion, String especialidad) {
        super(nombre, apellidos, correo, contrasenia, fechaNacimiento, telefono, dni, direccion);
        this.especialidad = especialidad; 
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }    
}