package com.seriousgame;

import java.io.Serializable;
import java.util.ArrayList;

public class cTienda implements Serializable {

    private String nombre;
    private int precio;
    private boolean equipado;
    private boolean comprado;

    public cTienda(String sNombre, int sPrecio, boolean sEquipado, boolean sComprado) {

        nombre = sNombre;
        precio = sPrecio;
        equipado = sEquipado;
        comprado = sComprado;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPrecio() { return precio; }

    public void setPrecio(int precio) { this.precio = precio; }

    public boolean getEquipado() { return equipado; }

    public void setEquipado(boolean equipado) { this.equipado = equipado; }

    public boolean getComprado() { return comprado; }

    public void setComprado(boolean comprado) { this.comprado = comprado; }
}
