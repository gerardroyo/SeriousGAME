package com.seriousgame;

import java.io.Serializable;
import java.util.ArrayList;

public class cTema implements Serializable {

    private int id;
    private String nombre;

    public cTema(int sId, String sNombre) {

        id = sId;
        nombre = sNombre;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

}
