package com.seriousgame;

import java.io.Serializable;
import java.util.ArrayList;

public class cDificultad implements Serializable {

    private ArrayList<cLecciones> Lecciones = new ArrayList<cLecciones>();
    private String nombre;
    private String imgColor;
    //private String imgBN;

    public cDificultad(String sNombre, String sImgColor/*, String sImgBN */) {

        nombre = sNombre;
        imgColor = sImgColor;
        //imgBN = sImgBN;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImgColor() { return imgColor; }

    public void setImgColor(String imgColor) { this.imgColor = imgColor; }

    public ArrayList<cLecciones> getLecciones() { return Lecciones; }

    public void setLecciones(ArrayList<cLecciones> lecciones) { Lecciones = lecciones; }

    /*public String getImgBN() { return imgBN; }

    public void setImgBN(String imgBN) { this.imgBN = imgBN; }*/

    public void instanciaLecciones(){
        Lecciones.clear();
        Lecciones.add(new cLecciones(0, 1));
    }

}
