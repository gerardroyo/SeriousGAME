package com.seriousgame;

import java.io.Serializable;

public class cUser implements Serializable {

    private String nombre;
    private String foto;
    private int monedas = 10;


    public cUser(String sNombre, String sFoto) {

        nombre = sNombre;
        foto = sFoto;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFoto() {return foto; }

    public void setFoto(String foto) {this.foto = foto; }

    public int getMonedas() { return monedas; }

    public void setMonedas(int monedas) { this.monedas = monedas; }
}
