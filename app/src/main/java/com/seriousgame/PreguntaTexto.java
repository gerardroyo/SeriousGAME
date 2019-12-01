package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PreguntaTexto extends AppCompatActivity {

    private int randTipoPregunta;

    private int rand1Pregunta;
    String rand1PreguntaString;

    private int rand2Pregunta;
    String rand2PreguntaString;

    private int resultado;
    String resultadoString;

    private ArrayList<String> numButtonShuffled = new ArrayList<String>();

    private Button btn;
    private TextView tv;
    private EditText edt;
    private ProgressBar pb;

    private int aciertos = 0;
    private int fallos = 0;
    private int progreso = 0;

    private String stema;
    private int dificultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_texto);

        // deshabilita el titol
        getSupportActionBar().hide();

        stema = getIntent().getExtras().getString("tema");
        dificultad = getIntent().getExtras().getInt("dificultad");

        ImageView img = (ImageView) findViewById(R.id.imgClose);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad;
                ad = new AlertDialog.Builder(PreguntaTexto.this).create();
                ad.setTitle("¿Estas seguro?");
                ad.setMessage("Se perderá todo el progreso de la lección.");

                ad.setButton(AlertDialog.BUTTON_POSITIVE,"SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intentFallos2 = new Intent();
                        intentFallos2.putExtra("fallos", fallos);
                        intentFallos2.putExtra("aciertos", aciertos);
                        setResult(RESULT_OK, intentFallos2);
                        finish();
                    }
                });

                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // no fem res.
                    }
                });
                ad.show();
            }

        });

        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setProgress(0);
        cargarLayout();

        btn = findViewById(R.id.btnComprobar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String holaXD = "0";
                comprobar(holaXD);
            }
        });

    }

    public void cargarLayout() {
        numsRandoms();

        if(randTipoPregunta == 1) {
            randomButton();
        }

        mostrarLayoutRandom();
    }

    public void comprobar(String numOke) {
        boolean intentFallos = true;

        if(randTipoPregunta == 1) {
            edt.setText("0");
        }

        edt = (EditText) findViewById(R.id.editText);
        if(resultadoString.equals(edt.getText().toString())) {
            correcto();
            cargarLayout();

        } else if(numOke.equals(resultadoString)) {
            correcto();
            cargarLayout();

        } else if(edt.getText().toString().isEmpty()) {
            snackBarEmpty();
        } else {
            edt.setText("");
            fallos++;
            snackBarError();
            if(fallos == 2) {
                Intent intentFallos2 = new Intent();
                intentFallos2.putExtra("fallos", fallos);
                intentFallos2.putExtra("aciertos", aciertos);
                setResult(RESULT_OK, intentFallos2);
                finish();
            }

            cargarLayout();

        }

    }

    public void correcto() {
        edt.setText("");
        snackBarCorrecto();
        aciertos++;

        pb = (ProgressBar) findViewById(R.id.pb);
        progreso = pb.getProgress();
        progreso = progreso + 10;
        pb.setProgress(progreso);

        if(aciertos == 10) {
            Intent intentAciertos = new Intent();
            intentAciertos.putExtra("fallos", fallos);
            intentAciertos.putExtra("aciertos", aciertos);
            setResult(RESULT_OK, intentAciertos);
            finish();
        }
    }

    public void snackBarCorrecto(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(parentLayout, "¡CORRECTO!", Snackbar.LENGTH_SHORT);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#1cc61c"));

        snack.show();
    }

    public void snackBarEmpty(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(parentLayout, "¡No has escrito nada!", Snackbar.LENGTH_SHORT);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#0CB7F2"));

        snack.show();
    }

    public void snackBarError(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(parentLayout, "Respuesta correcta: " + resultadoString + ".", Snackbar.LENGTH_SHORT);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#9d000a"));

        snack.show();
    }

    public void randomButton() {
        ArrayList<String> numButton = new ArrayList<String>();

        switch(stema) {
            case "Sumar":
                switch (dificultad) {
                    case 1:
                        numButton = numButton(18, 2);
                        break;
                    case 2:
                        numButton = numButton(178, 20);
                        break;
                    case 3:
                        numButton = numButton(1798, 200);
                        break;
                }
                break;
            case "Restar":
                switch (dificultad) {
                    case 1:
                        numButton = numButton(18, 2);
                        break;
                    case 2:
                        numButton = numButton(198, -198);
                        break;
                    case 3:
                        numButton = numButton(1998, -1998);
                        break;
                }
                break;
            case "Multiplicar":
                switch (dificultad) {
                    case 1:
                        numButton = numButton(81, 1);
                        break;
                    case 2:
                        numButton = numButton(9701, 100);
                        break;
                    case 3:
                        numButton = numButton(988001, 10000);
                        break;
                }
                break;

        }

        resultadoString = Integer.toString(resultado);
        numButton.add(resultadoString);
        numButtonShuffled = numButton;
        Collections.shuffle(numButtonShuffled);

    }

    public ArrayList<String> numButton(int to, int start) {
        ArrayList<String> numButton = new ArrayList<String>();

        for(int i = 0; i < 3; i++) {
            int random = (int) (Math.random()*to+start);
            String randomString = Integer.toString(random);
            numButton.add(randomString);
        }
        return numButton;
    }

    public void numsRandoms() {

        switch(dificultad) {
            case 1:
                dificultad(9, 1);
                break;
            case 2:
                dificultad(89, 10);
                break;
            case 3:
                dificultad(899, 100);
                break;
        }
    }

    public void dificultad(int to, int start) {
        randTipoPregunta = (int) (Math.random()*2+0);

        rand1Pregunta = (int) (Math.random()*to+start);
        rand1PreguntaString = Integer.toString(rand1Pregunta);

        rand2Pregunta = (int) (Math.random()*to+start);
        rand2PreguntaString = Integer.toString(rand2Pregunta);

        switch(stema) {
            case "Sumar":
                resultado = rand1Pregunta + rand2Pregunta;
                resultadoString = Integer.toString(resultado);
                break;
            case "Restar":
                resultado = rand1Pregunta - rand2Pregunta;
                resultadoString = Integer.toString(resultado);
                break;
            case "Multiplicar":
                resultado = rand1Pregunta * rand2Pregunta;
                resultadoString = Integer.toString(resultado);
                break;

        }
    }

    public void tema() {
        switch(stema) {
            case "Sumar":

                simbolo(" + ");

                break;
            case "Restar":

                simbolo(" - ");

                break;
            case "Multiplicar":

                simbolo(" x ");

                break;
        }
    }

    public void simbolo(String simbolo) {
        tv = (TextView) findViewById(R.id.tvNumeros);
        tv.setText(rand1PreguntaString + simbolo + rand2PreguntaString);
    }

    public void mostrarLayoutRandom() {
        if(randTipoPregunta == 0) {
            tv = (TextView) findViewById(R.id.tvFrase);
            tv.setText("Escribe el resultado correcto de la siguiente operación...");

            tema();

            edt = (EditText) findViewById(R.id.editText);
            edt.setVisibility(View.VISIBLE);

            btn = (Button) findViewById(R.id.btnComprobar);
            btn.setVisibility(View.VISIBLE);

            btn = (Button) findViewById(R.id.btn1);
            btn.setVisibility(View.INVISIBLE);

            btn = (Button) findViewById(R.id.btn2);
            btn.setVisibility(View.INVISIBLE);

            btn = (Button) findViewById(R.id.btn3);
            btn.setVisibility(View.INVISIBLE);

            btn = (Button) findViewById(R.id.btn4);
            btn.setVisibility(View.INVISIBLE);



        } else if (randTipoPregunta == 1){

            tv = (TextView) findViewById(R.id.tvFrase);
            tv.setText("Selecciona el resultado correcto de la siguiente operación...");

            tema();

            btn = (Button) findViewById(R.id.btn1);
            btn.setVisibility(View.VISIBLE);

            btn = (Button) findViewById(R.id.btn2);
            btn.setVisibility(View.VISIBLE);

            btn = (Button) findViewById(R.id.btn3);
            btn.setVisibility(View.VISIBLE);

            btn = (Button) findViewById(R.id.btn4);
            btn.setVisibility(View.VISIBLE);

            edt = (EditText) findViewById(R.id.editText);
            edt.setVisibility(View.INVISIBLE);

            btn = (Button) findViewById(R.id.btnComprobar);
            btn.setVisibility(View.INVISIBLE);

            btn = (Button) findViewById(R.id.btn1);
            btn.setText(numButtonShuffled.get(0));
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    comprobar(numButtonShuffled.get(0));
                }
            });

            btn = (Button) findViewById(R.id.btn2);
            btn.setText(numButtonShuffled.get(1));
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    comprobar(numButtonShuffled.get(1));
                }
            });

            btn = (Button) findViewById(R.id.btn3);
            btn.setText(numButtonShuffled.get(2));
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    comprobar(numButtonShuffled.get(2));

                }
            });

            btn = (Button) findViewById(R.id.btn4);
            btn.setText(numButtonShuffled.get(3));
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    comprobar(numButtonShuffled.get(3));
                }
            });
        }
    }

}
