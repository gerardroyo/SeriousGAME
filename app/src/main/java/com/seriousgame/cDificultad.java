package com.seriousgame;

import java.io.Serializable;

public class cDificultad implements Serializable {

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

    /*public String getImgBN() { return imgBN; }

    public void setImgBN(String imgBN) { this.imgBN = imgBN; }*/
}
