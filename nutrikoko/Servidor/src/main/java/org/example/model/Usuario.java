package org.example.model;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasenia;
    private String fechaNacimiento;
    private String telefono;
    private String dni;
    private byte[] imagen;
    private Direccion direccion;

    public Usuario(String nombre, String apellidos, String correo, String contrasenia, String fechaNacimiento, String telefono, String dni, Direccion direccion) {
        this.nombre = nombre; 
        this.apellidos = apellidos; 
        this.correo = correo; 
        this.contrasenia = contrasenia; 
        this.fechaNacimiento = fechaNacimiento; 
        this.telefono = telefono; 
        this.dni = dni; 
        imagen = new byte[0]; 
        this.direccion = direccion; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}