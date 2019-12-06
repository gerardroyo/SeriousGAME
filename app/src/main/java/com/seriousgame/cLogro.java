package com.seriousgame;

public class cLogro {

    private int idLogro;
    private String nombre;
    private int progreso;
    private String img;
    private int puntos;
    private int monedas;

    public cLogro(int idLogro, String nombre, int progreso, String img, int puntos, int monedas) {
        this.idLogro = idLogro;
        this.nombre = nombre;
        this.progreso = progreso;
        this.img = img;
        this.puntos = puntos;
        this.monedas = monedas;
    }

    public int getIdLogro() { return idLogro; }

    public void setIdLogro(int idLogro) { this.idLogro = idLogro; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getProgreso() { return progreso; }

    public void setProgreso(int progreso) { this.progreso = progreso; }

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

    public int getPuntos() { return puntos; }

    public void setPuntos(int puntos) { this.puntos = puntos; }

    public int getMonedas() { return monedas; }

    public void setMonedas(int monedas) { this.monedas = monedas; }
}
