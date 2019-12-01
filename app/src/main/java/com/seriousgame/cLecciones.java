package com.seriousgame;

import java.io.Serializable;

public class cLecciones implements Serializable {

    private int id;
    private int LeccionActual;
    private int LeccionMax;

    public cLecciones(int sId,int sLeccionActual, int sLeccionMax) {
        id = sId;
        LeccionActual = sLeccionActual;
        LeccionMax = sLeccionMax;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getLeccionActual() { return LeccionActual; }

    public void setLeccionActual(int leccionActual) { LeccionActual = leccionActual; }

    public int getLeccionMax() { return LeccionMax; }

    public void setLeccionMax(int leccionMax) { LeccionMax = leccionMax; }
}
