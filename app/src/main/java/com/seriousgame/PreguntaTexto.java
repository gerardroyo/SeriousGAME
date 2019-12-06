package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        View parentLayout = findViewById(android.R.id.content);
        SnackBar.snackBarCorrecto(parentLayout);
        aciertos++;

        int plusMonedas = MainActivity.User.get(0).getMonedas();
        MainActivity.User.get(0).setMonedas(plusMonedas + 15);

        int plusPuntos = MainActivity.User.get(0).getPuntos();;
        MainActivity.User.get(0).setPuntos(plusPuntos + 15);

        if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getLecciones().get(0).getLeccionActual() == 2) {
            if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getId() == 3) {
                plusMonedas = MainActivity.User.get(0).getMonedas();
                MainActivity.User.get(0).setMonedas(plusMonedas + 60);

                plusPuntos = MainActivity.User.get(0).getPuntos();;
                MainActivity.User.get(0).setPuntos(plusPuntos + 60);
            }

            plusMonedas = MainActivity.User.get(0).getMonedas();
            MainActivity.User.get(0).setMonedas(plusMonedas + 30);

            plusPuntos = MainActivity.User.get(0).getPuntos();;
            MainActivity.User.get(0).setPuntos(plusPuntos + 30);
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

        if(aciertos == 1) {
            MainActivity.Logros.get(0).setProgreso(100);
            int progres = MainActivity.Logros.get(1).getProgreso();
            if(MainActivity.Logros.get(1).getProgreso() >=100){
            } else {
                MainActivity.Logros.get(1).setProgreso(progres + 33);
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

        int plusPuntos = MainActivity.User.get(0).getPuntos();;
        MainActivity.User.get(0).setPuntos(plusPuntos + MainActivity.Logros.get(posLogro).getPuntos());
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
