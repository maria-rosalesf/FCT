package org.example.model;

public class Direccion {
    private int id; 
    private String calle;
    private int numero; 
    private int cp; 
    private String provincia; 
    private String ciudad; 

    public Direccion() {
        id = 0; 
        calle = "";
        numero = 0; 
        cp = 0; 
        provincia = ""; 
        ciudad = ""; 
    }

    public Direccion(String calle, int numero, int cp, String provincia, String ciudad) {
        this.calle = calle; 
        this.numero = numero; 
        this.cp = cp; 
        this.provincia = provincia; 
        this.ciudad = ciudad; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}