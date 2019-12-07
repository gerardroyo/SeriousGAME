package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private int positionTema;
    private int positionDificultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_texto);

        // deshabilita el titol
        getSupportActionBar().hide();

        stema = getIntent().getExtras().getString("tema");

        cDificultad dificultadObj = (cDificultad) getIntent().getSerializableExtra("dificultad");
        dificultad = dificultadObj.getId();

        positionDificultad = getIntent().getExtras().getInt("positionDificultad");
        positionTema = getIntent().getExtras().getInt("positionTema");

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

        guardarDatos();

    }

    @Override
    public void onBackPressed() {
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
            View parentLayout = findViewById(android.R.id.content);
            SnackBar.snackBarEmpty(parentLayout);
        } else {
            edt.setText("");
            fallos++;

            MainActivity.Logros.get(4).setProgreso(0);
            MainActivity.Logros.get(2).setProgreso(0);

            View parentLayout = findViewById(android.R.id.content);
            SnackBar.snackBarError(parentLayout, resultadoString);
            if(fallos == 3) {
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
        View parentLayout = findViewById(android.R.id.content);
        SnackBar.snackBarCorrecto(parentLayout);
        aciertos++;

        int plusMonedas = MainActivity.User.get(0).getMonedas();
        MainActivity.User.get(0).setMonedas(plusMonedas + 15);


        switch(MainActivity.Temas.get(positionTema).getId()) {
            case 1:
                int plusPuntos = MainActivity.Temas.get(0).getPuntos();
                MainActivity.Temas.get(0).setPuntos(plusPuntos + 15);
                break;
            case 2:
                plusPuntos = MainActivity.Temas.get(1).getPuntos();
                MainActivity.Temas.get(1).setPuntos(plusPuntos + 15);
                break;
            case 3:
                plusPuntos = MainActivity.Temas.get(2).getPuntos();
                MainActivity.Temas.get(2).setPuntos(plusPuntos + 15);
                break;
            case 4:
                plusPuntos = MainActivity.Temas.get(3).getPuntos();
                MainActivity.Temas.get(3).setPuntos(plusPuntos + 15);
                break;
        }


        if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getLecciones().get(0).getLeccionActual() == 2) {
            if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getId() == 3) {
                plusMonedas = MainActivity.User.get(0).getMonedas();
                MainActivity.User.get(0).setMonedas(plusMonedas + 60);

                switch(MainActivity.Temas.get(positionTema).getId()) {
                    case 1:
                        int plusPuntos = MainActivity.Temas.get(0).getPuntos();
                        MainActivity.Temas.get(0).setPuntos(plusPuntos + 60);
                        break;
                    case 2:
                        plusPuntos = MainActivity.Temas.get(1).getPuntos();
                        MainActivity.Temas.get(1).setPuntos(plusPuntos + 60);
                        break;
                    case 3:
                        plusPuntos = MainActivity.Temas.get(2).getPuntos();
                        MainActivity.Temas.get(2).setPuntos(plusPuntos + 60);
                        break;
                    case 4:
                        plusPuntos = MainActivity.Temas.get(3).getPuntos();
                        MainActivity.Temas.get(3).setPuntos(plusPuntos + 60);
                        break;
                }
            }

            plusMonedas = MainActivity.User.get(0).getMonedas();
            MainActivity.User.get(0).setMonedas(plusMonedas + 30);

            switch(MainActivity.Temas.get(positionTema).getId()) {
                case 1:
                    int plusPuntos = MainActivity.Temas.get(0).getPuntos();
                    MainActivity.Temas.get(0).setPuntos(plusPuntos + 30);
                    break;
                case 2:
                    plusPuntos = MainActivity.Temas.get(1).getPuntos();
                    MainActivity.Temas.get(1).setPuntos(plusPuntos + 30);
                    break;
                case 3:
                    plusPuntos = MainActivity.Temas.get(2).getPuntos();
                    MainActivity.Temas.get(2).setPuntos(plusPuntos + 30);
                    break;
                case 4:
                    plusPuntos = MainActivity.Temas.get(3).getPuntos();
                    MainActivity.Temas.get(3).setPuntos(plusPuntos + 30);
                    break;
            }
        }

        pb = (ProgressBar) findViewById(R.id.pb);
        progreso = pb.getProgress();
        progreso = progreso + 10;
        pb.setProgress(progreso);

        if(MainActivity.Logros.get(4).getProgreso() < 100) {

            int progresLogro5 = MainActivity.Logros.get(4).getProgreso();
            MainActivity.Logros.get(4).setProgreso(progresLogro5 + 2);

        }

        if(MainActivity.Logros.get(2).getProgreso() < 100) {

            int progresLogro3 = MainActivity.Logros.get(2).getProgreso();
            MainActivity.Logros.get(2).setProgreso(progresLogro3 + 10);

        }

        if(aciertos == 10) {
            MainActivity.Logros.get(0).setProgreso(100);
            int progres = MainActivity.Logros.get(1).getProgreso();

            if(MainActivity.Logros.get(1).getProgreso() >=100){
            } else {
                MainActivity.Logros.get(1).setProgreso(progres + 34);
                if(MainActivity.Logros.get(1).getProgreso() >=100){
                    MainActivity.Logros.get(1).setProgreso(100);
                }
            }

            if(MainActivity.Logros.get(0).getProgreso() >= 100 && MainActivity.fet1 == 0) {
                ActuMonPuntLogros(0);
                MainActivity.fet1++;
            }
            if(MainActivity.Logros.get(1).getProgreso() >= 100 && MainActivity.fet2 == 0) {
                ActuMonPuntLogros(1);
                MainActivity.fet2++;
            }
            if(MainActivity.Logros.get(2).getProgreso() >= 100 && MainActivity.fet3 == 0) {
                ActuMonPuntLogros(2);
                MainActivity.fet3++;
            }
            if(MainActivity.Logros.get(3).getProgreso() >= 100 && MainActivity.fet4 == 0) {
                ActuMonPuntLogros(3);
                MainActivity.fet4++;
            }
            if(MainActivity.Logros.get(4).getProgreso() >= 100 && MainActivity.fet5 == 0) {
                ActuMonPuntLogros(4);
                MainActivity.fet5++;
            }
            if(MainActivity.Logros.get(5).getProgreso() >= 100 && MainActivity.fet6 == 0) {
                ActuMonPuntLogros(5);
                MainActivity.fet6++;
            }

            int leccionActualSum = MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getLecciones().get(0).getLeccionActual() + 1;
            MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getLecciones().get(0).setLeccionActual(leccionActualSum);
            Intent intentAciertos = new Intent();
            intentAciertos.putExtra("fallos", fallos);
            intentAciertos.putExtra("aciertos", aciertos);
            setResult(RESULT_OK, intentAciertos);
            finish();
        }
    }

    public void ActuMonPuntLogros(int posLogro) {
        int plusMonedas = MainActivity.User.get(0).getMonedas();
        MainActivity.User.get(0).setMonedas(plusMonedas + MainActivity.Logros.get(posLogro).getMonedas());

        switch(MainActivity.Temas.get(positionTema).getId()) {
            case 1:
                int plusPuntos = MainActivity.Temas.get(0).getPuntos();;
                MainActivity.Temas.get(0).setPuntos(plusPuntos + MainActivity.Logros.get(posLogro).getPuntos());
                break;
            case 2:
                plusPuntos = MainActivity.Temas.get(1).getPuntos();;
                MainActivity.Temas.get(1).setPuntos(plusPuntos + MainActivity.Logros.get(posLogro).getPuntos());
                break;
            case 3:
                plusPuntos = MainActivity.Temas.get(2).getPuntos();;
                MainActivity.Temas.get(2).setPuntos(plusPuntos + MainActivity.Logros.get(posLogro).getPuntos());
                break;
            case 4:
                plusPuntos = MainActivity.Temas.get(3).getPuntos();;
                MainActivity.Temas.get(3).setPuntos(plusPuntos + MainActivity.Logros.get(posLogro).getPuntos());
                break;
        }
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
            case "Dividir":
                switch (dificultad) {
                    case 1:
                        numButton = numButton(9, 1);
                        break;
                    case 2:
                        numButton = numButton(99, 11);
                        break;
                    case 3:
                        numButton = numButton(999, 111);
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
        int comptador = 0;
        String randomString = "";
        int random = (int) (Math.random()*to+start);
        randomString = Integer.toString(random);
        numButton.add(randomString);

        for(int i = 0; i < 2; i++) {
            comptador = 0;
            while(comptador < numButton.size()) {
                random = (int) (Math.random()*to+start);
                randomString = Integer.toString(random);

                for(int j = 0; j < numButton.size(); j++) {
                    if(randomString.equals(numButton.get(j)) || randomString.equals(resultadoString)){
                    } else {
                        comptador++;
                    }
                }
            }


            numButton.add(randomString);
        }
        return numButton;
    }

    public void numsRandoms() {

        if(stema.equals("Dividir")) {
            switch(dificultad) {
                case 1:
                    dificultadDividir(9, 1, 9, 1);
                    break;
                case 2:
                    dificultadDividir(99, 10, 9, 1);
                    break;
                case 3:
                    dificultadDividir(999, 100, 9, 1);
                    break;

            }
        } else {
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
    }

    public void dificultadDividir(int to, int start, int to2, int start2) {
        randTipoPregunta = (int) (Math.random()*2+0);

        rand1Pregunta = (int) (Math.random()*to+start);
        rand1PreguntaString = Integer.toString(rand1Pregunta);
        rand2Pregunta = (int) (Math.random()*to2+start2);

        while(rand2Pregunta > rand1Pregunta) {
            rand2Pregunta = (int) (Math.random()*to2+start2);
        }

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
            case "Dividir":
                resultado = rand1Pregunta / rand2Pregunta;
                resultadoString = Integer.toString(resultado);
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
            case "Dividir":
                resultado = rand1Pregunta / rand2Pregunta;
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
            case "Dividir":

                simbolo(" ÷ ");

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
    private void guardarDatos() {

        SharedPreferences preferencias = getSharedPreferences("game", Context.MODE_PRIVATE);
//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        int lecAcSumFacil = MainActivity.Temas.get(0).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcSumNormal = MainActivity.Temas.get(0).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcSumDificil = MainActivity.Temas.get(0).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcResFacil = MainActivity.Temas.get(1).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcResNormal = MainActivity.Temas.get(1).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcResDificil = MainActivity.Temas.get(1).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcMultFacil = MainActivity.Temas.get(2).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcMultNormal = MainActivity.Temas.get(2).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcMultDificil = MainActivity.Temas.get(2).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcDivFacil = MainActivity.Temas.get(3).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcDivNormal = MainActivity.Temas.get(3).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcDivDificil = MainActivity.Temas.get(3).getDificultad().get(2).getLecciones().get(0).getLeccionActual();
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        boolean difBloquSumFacil = MainActivity.Temas.get(0).getDificultad().get(0).getBloqueado();
        boolean difBloquSumNormal = MainActivity.Temas.get(0).getDificultad().get(1).getBloqueado();
        boolean difBloquSumDificil = MainActivity.Temas.get(0).getDificultad().get(2).getBloqueado();

        boolean difBloquResFacil = MainActivity.Temas.get(1).getDificultad().get(0).getBloqueado();
        boolean difBloquResNormal = MainActivity.Temas.get(1).getDificultad().get(1).getBloqueado();
        boolean difBloquResDificil = MainActivity.Temas.get(1).getDificultad().get(2).getBloqueado();

        boolean difBloquMultFacil = MainActivity.Temas.get(2).getDificultad().get(0).getBloqueado();
        boolean difBloquMultNormal = MainActivity.Temas.get(2).getDificultad().get(1).getBloqueado();
        boolean difBloquMultDificil = MainActivity.Temas.get(2).getDificultad().get(2).getBloqueado();

        boolean difBloquDivFacil = MainActivity.Temas.get(3).getDificultad().get(0).getBloqueado();
        boolean difBloquDivNormal = MainActivity.Temas.get(3).getDificultad().get(1).getBloqueado();
        boolean difBloquDivDificil = MainActivity.Temas.get(3).getDificultad().get(2).getBloqueado();
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        String usuarioNombre = MainActivity.User.get(0).getNombre();
        int usuarioMonedas = MainActivity.User.get(0).getMonedas();
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        int logro1Progres = MainActivity.Logros.get(0).getProgreso();
        int logro2Progres = MainActivity.Logros.get(1).getProgreso();
        int logro3Progres = MainActivity.Logros.get(2).getProgreso();
        int logro4Progres = MainActivity.Logros.get(3).getProgreso();
        int logro5Progres = MainActivity.Logros.get(4).getProgreso();
        int logro6Progres = MainActivity.Logros.get(5).getProgreso();
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        boolean tiendaComprado1 = MainActivity.Tienda.get(0).getComprado();
        boolean tiendaComprado2 = MainActivity.Tienda.get(1).getComprado();
        boolean tiendaComprado3 = MainActivity.Tienda.get(2).getComprado();
        boolean tiendaComprado4 = MainActivity.Tienda.get(3).getComprado();
        boolean tiendaComprado5 = MainActivity.Tienda.get(4).getComprado();
        boolean tiendaComprado6 = MainActivity.Tienda.get(5).getComprado();
//---------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Equipado <----------------------------------------------\\
        boolean tiendaEquipado1 = MainActivity.Tienda.get(0).getEquipado();
        boolean tiendaEquipado2 = MainActivity.Tienda.get(1).getEquipado();
        boolean tiendaEquipado3 = MainActivity.Tienda.get(2).getEquipado();
        boolean tiendaEquipado4 = MainActivity.Tienda.get(3).getEquipado();
        boolean tiendaEquipado5 = MainActivity.Tienda.get(4).getEquipado();
        boolean tiendaEquipado6 = MainActivity.Tienda.get(5).getEquipado();
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
        SharedPreferences.Editor editor = preferencias.edit();
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        editor.putInt("lecAcSumFacil", lecAcSumFacil);
        editor.putInt("lecAcSumNormal", lecAcSumNormal);
        editor.putInt("lecAcSumDificil", lecAcSumDificil);

        editor.putInt("lecAcResFacil", lecAcResFacil);
        editor.putInt("lecAcResNormal", lecAcResNormal);
        editor.putInt("lecAcResDificil", lecAcResDificil);

        editor.putInt("lecAcMultFacil", lecAcMultFacil);
        editor.putInt("lecAcMultNormal", lecAcMultNormal);
        editor.putInt("lecAcMultDificil", lecAcMultDificil);

        editor.putInt("lecAcDivFacil", lecAcDivFacil);
        editor.putInt("lecAcDivNormal", lecAcDivNormal);
        editor.putInt("lecAcDivDificil", lecAcDivDificil);
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        editor.putBoolean("difBloquSumFacil", difBloquSumFacil);
        editor.putBoolean("difBloquSumNormal", difBloquSumNormal);
        editor.putBoolean("difBloquSumDificil", difBloquSumDificil);

        editor.putBoolean("difBloquResFacil", difBloquResFacil);
        editor.putBoolean("difBloquResNormal", difBloquResNormal);
        editor.putBoolean("difBloquResDificil", difBloquResDificil);

        editor.putBoolean("difBloquMultFacil", difBloquMultFacil);
        editor.putBoolean("difBloquMultNormal", difBloquMultNormal);
        editor.putBoolean("difBloquMultDificil", difBloquMultDificil);

        editor.putBoolean("difBloquDivFacil", difBloquDivFacil);
        editor.putBoolean("difBloquDivNormal", difBloquDivNormal);
        editor.putBoolean("difBloquDivDificil", difBloquDivDificil);
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        editor.putString("usuarioNombre", usuarioNombre);
        editor.putInt("usuarioMonedas", usuarioMonedas);
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        editor.putInt("logro1Progres", logro1Progres);
        editor.putInt("logro2Progres", logro2Progres);
        editor.putInt("logro3Progres", logro3Progres);
        editor.putInt("logro4Progres", logro4Progres);
        editor.putInt("logro5Progres", logro5Progres);
        editor.putInt("logro6Progres", logro6Progres);
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        editor.putBoolean("tiendaComprado1", tiendaComprado1);
        editor.putBoolean("tiendaComprado2", tiendaComprado2);
        editor.putBoolean("tiendaComprado3", tiendaComprado3);
        editor.putBoolean("tiendaComprado4", tiendaComprado4);
        editor.putBoolean("tiendaComprado5", tiendaComprado5);
        editor.putBoolean("tiendaComprado6", tiendaComprado6);
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Equipado <----------------------------------------------\\
        editor.putBoolean("tiendaEquipado1", tiendaEquipado1);
        editor.putBoolean("tiendaEquipado2", tiendaEquipado2);
        editor.putBoolean("tiendaEquipado3", tiendaEquipado3);
        editor.putBoolean("tiendaEquipado4", tiendaEquipado4);
        editor.putBoolean("tiendaEquipado5", tiendaEquipado5);
        editor.putBoolean("tiendaEquipado6", tiendaEquipado6);
//---------------------------------------------------------------------------------------------------------------\\

        editor.commit();

    }

}
