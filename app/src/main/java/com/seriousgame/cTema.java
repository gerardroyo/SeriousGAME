package com.seriousgame;

import java.io.Serializable;

public class cTema implements Serializable {

    private String nombre;

    public cTema(String sNombre) {

        nombre = sNombre;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
