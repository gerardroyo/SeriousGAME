package com.seriousgame;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBar {

    public static void snackBarCorrecto(View parentLayout){

        Snackbar snack = Snackbar.make(parentLayout, "¡CORRECTO!", Snackbar.LENGTH_SHORT);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#1cc61c"));

        snack.show();
    }

    public static void snackBarEmpty(View parentLayout){
        Snackbar snack = Snackbar.make(parentLayout, "¡No has escrito nada!", Snackbar.LENGTH_SHORT);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#0CB7F2"));

        snack.show();
    }

    public static void snackBarError(View parentLayout,String resultadoString){
        Snackbar snack = Snackbar.make(parentLayout, "Respuesta correcta: " + resultadoString + ".", Snackbar.LENGTH_SHORT);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#9d000a"));

        snack.show();
    }

    public static void snackBarErrorFallos(View parentLayout){
        Snackbar snack = Snackbar.make(parentLayout, "Fallaste el máximo de veces: 2", Snackbar.LENGTH_LONG);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#9d000a"));

        snack.show();
    }

    public static void snackBarAprendido(View parentLayout){
        Snackbar snack = Snackbar.make(parentLayout, "¡Lección completada!", Snackbar.LENGTH_LONG);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#1cc61c"));

        snack.show();
    }

}
