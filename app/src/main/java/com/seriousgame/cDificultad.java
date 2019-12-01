package com.seriousgame;

import java.io.Serializable;
import java.util.ArrayList;

public class cDificultad implements Serializable {
    private int id;
    private String nombre;
    private String imgColor;
    //private String imgBN;

    public cDificultad(int sId, String sNombre, String sImgColor/*, String sImgBN */) {

        id = sId;
        nombre = sNombre;
        imgColor = sImgColor;
        //imgBN = sImgBN;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImgColor() { return imgColor; }

    public void setImgColor(String imgColor) { this.imgColor = imgColor; }

    /*public String getImgBN() { return imgBN; }

    public void setImgBN(String imgBN) { this.imgBN = imgBN; }*/

}
