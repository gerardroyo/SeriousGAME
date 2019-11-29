package com.seriousgame;

import java.io.Serializable;

public class cLecciones implements Serializable {

    private int LeccionActual;
    private int LeccionMax;

    public cLecciones(int sLeccionActual, int sLeccionMax) {
        LeccionActual = sLeccionActual;
        LeccionMax = sLeccionMax;
    }

    public int getLeccionActual() { return LeccionActual; }

    public void setLeccionActual(int leccionActual) { LeccionActual = leccionActual; }

    public int getLeccionMax() { return LeccionMax; }

    public void setLeccionMax(int leccionMax) { LeccionMax = leccionMax; }
}
