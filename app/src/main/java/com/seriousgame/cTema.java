package com.seriousgame;

import java.io.Serializable;
import java.util.ArrayList;

public class cTema implements Serializable {

    private int id;
    private String nombre;
    private int puntos = 0;
    private ArrayList<cDificultad> dificultad = new ArrayList<cDificultad>();

    public cTema(int sId, String sNombre) {

        id = sId;
        nombre = sNombre;
        dificultad.add(new cDificultad(1, "Fácil", "lvlfacilcolor", "lvlfacilgris", false,1, 0, 3));
        dificultad.add(new cDificultad(2, "Normal", "lvlnormalcolor", "lvlnormalgris", true,2, 0, 3));
        dificultad.add(new cDificultad(3, "Difícil", "lvldificilcolor", "lvldificilgris", true,3, 0, 3));
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntos() { return puntos; }

    public void setPuntos(int puntos) { this.puntos = puntos; }

    public ArrayList<cDificultad> getDificultad() { return dificultad; }

    public void setDificultad(ArrayList<cDificultad> dificultad) { this.dificultad = dificultad; }
}
