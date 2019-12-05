package com.seriousgame;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class cDificultad implements Serializable {
    private int id;
    private String nombre;
    private String imgColor;
    private String imgBN;
    private boolean bloqueado;
    private ArrayList<cLecciones> leccion = new ArrayList<cLecciones>();


    public cDificultad(int sId, String sNombre, String sImgColor, String sImgBN, boolean sBloqueado, int sIdLeccion, int sLeccionActual, int sLeccionMax) {
        id = sId;
        nombre = sNombre;
        imgColor = sImgColor;
        imgBN = sImgBN;
        bloqueado = sBloqueado;
        leccion.add(new cLecciones(sIdLeccion, sLeccionActual, sLeccionMax));
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImgColor() { return imgColor; }

    public void setImgColor(String imgColor) { this.imgColor = imgColor; }

    public String getImgBN() { return imgBN; }

    public void setImgBN(String imgBN) { this.imgBN = imgBN; }

    public boolean getBloqueado() { return bloqueado; }

    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

    public ArrayList<cLecciones> getLecciones() { return leccion; }

    public void setLecciones(ArrayList<cLecciones> lecciones) { this.leccion = lecciones; }

}
