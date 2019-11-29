package com.seriousgame;

import java.io.Serializable;
import java.util.ArrayList;

public class cTema implements Serializable {

    private ArrayList<cDificultad> Dificultad = new ArrayList<cDificultad>();

    private String nombre;

    public cTema(String sNombre) {

        nombre = sNombre;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public ArrayList<cDificultad> getDificultad() { return Dificultad; }

    public void setDificultad(ArrayList<cDificultad> dificultad) { Dificultad = dificultad; }

    public void instanciaDificultad(){
        Dificultad.clear();
        Dificultad.add(new cDificultad("Fácil", "lvlfacilcolor"));
        Dificultad.add(new cDificultad("Normal", "lvlnormalcolor"));
        Dificultad.add(new cDificultad("Difícil", "lvldificilcolor"));
    }

}
