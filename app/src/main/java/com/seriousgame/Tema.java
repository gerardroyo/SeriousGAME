package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Tema extends AppCompatActivity {

    private cTema Temas;
    private String sTema;

    private ImageView img;
    private int destino;
    private int origenEnv = 1;
    private int positionTema;
    private int logro2;
    private int progres;
    //public static int positionTOP;

    private ArrayList<cUser> Users = new ArrayList<cUser>();
    private ArrayList<cTienda> Tiendas = new ArrayList<cTienda>();

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 12346 && resultCode == RESULT_OK) {
            finish();
            startActivity(getIntent());

            AdaptadorTema adaptador = new AdaptadorTema(this, MainActivity.Temas.get(positionTema).getDificultad());

            ListView lst = (ListView)findViewById(R.id.listCustom2);
            lst.setAdapter(adaptador);

            int fallos = data.getExtras().getInt("fallos");
            int aciertos = data.getExtras().getInt("aciertos");
            if(fallos == 2) {
                View parentLayout = findViewById(android.R.id.content);
                SnackBar.snackBarErrorFallos(parentLayout);
            }
            if(aciertos == 10){
                View parentLayout = findViewById(android.R.id.content);
                SnackBar.snackBarAprendido(parentLayout);
            }

        }

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            finish();
            startActivity(getIntent());
        }
        if (requestCode == 12345 && resultCode == RESULT_OK) {
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        destino = getIntent().getExtras().getInt("destino");
        positionTema = getIntent().getExtras().getInt("positionTema");

        AdaptadorTema adaptador = new AdaptadorTema(this, MainActivity.Temas.get(positionTema).getDificultad());

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptador);

        // deshabilita el titol
        getSupportActionBar().hide();

        TextView tv = (TextView)findViewById(R.id.tvMoney);
        tv.setText(Integer.toString(MainActivity.User.get(0).getMonedas()));

        ImageView img = (ImageView) findViewById(R.id.imgTema);
        //String nombre = tema.getNombre();
        String src = "@drawable/" + MainActivity.sTema;
        src = src.toLowerCase();
        img.setImageResource(getResources().getIdentifier(src,"drawable", getOpPackageName()));

        img = findViewById(R.id.imgTema);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img = findViewById(R.id.imgPerfil);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tema.this, Perfil.class);

                destino = 2;
                intent.putExtra("destino", destino);
                intent.putExtra("origen", origenEnv);

                startActivityForResult(intent, 1234);
            }

        });

        img = findViewById(R.id.imgTienda);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tema.this, Tienda.class);

                destino = 3;
                intent.putExtra("destino", destino);
                intent.putExtra("origen", origenEnv);

                startActivityForResult(intent, 12345);
            }

        });

    }

    class AdaptadorTema extends ArrayAdapter<cDificultad> {

        private Context context;

        public AdaptadorTema(Context context, ArrayList<cDificultad> datos) {
            super(context, R.layout.activity_mostrar_dificultad, datos);
            this.context = context;
        }

        public View getView(final int positionDificultad, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_dificultad, null);

            final cDificultad dificultad = (cDificultad)getItem(positionDificultad);

            if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getBloqueado() == false) {
                final ImageView img = (ImageView) item.findViewById(R.id.imgDificultad);
                String nombre = dificultad.getImgColor();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tvTema = (TextView) item.findViewById(R.id.tvDificultad);

                if(dificultad.getLecciones().get(0).getLeccionActual() >= dificultad.getLecciones().get(0).getLeccionMax()) {
                    tvTema.setText(dificultad.getNombre() + ": COMPLETADO");

                    MainActivity.Logros.get(1).setProgreso(100);
                    if(MainActivity.Logros.get(3).getProgreso() != 100) {
                        MainActivity.dif1++;
                        int progres4 = MainActivity.Logros.get(3).getProgreso();
                        if(MainActivity.dif1 <= 2) {
                            MainActivity.Logros.get(3).setProgreso(progres4 + 33);
                        }

                    } else {

                        MainActivity.Logros.get(3).setProgreso(100);
                    }

                    if (dificultad.getId() == 3) {
                        MainActivity.Logros.get(3).setProgreso(100);
                        int progres6 = MainActivity.Logros.get(5).getProgreso();
                        MainActivity.Logros.get(5).setProgreso(progres6 + 33);
                        MainActivity.logro6++;
                        if(MainActivity.logro6 >= 3) {
                            MainActivity.Logros.get(5).setProgreso(100);
                        }
                    }

                    if(dificultad.getId() == positionDificultad || dificultad.getId() == 3) {
                    } else {
                        MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad + 1).setBloqueado(false);
                    }
                } else {
                    String leccionActualString = Integer.toString(dificultad.getLecciones().get(0).getLeccionActual());
                    String leccionMaxString = Integer.toString(dificultad.getLecciones().get(0).getLeccionMax());
                    tvTema.setText(dificultad.getNombre() + ": " +  leccionActualString + "/" + leccionMaxString);
                }
            } else {
                final ImageView imgBN = (ImageView) item.findViewById(R.id.imgDificultad);
                String nombre = dificultad.getImgBN();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                imgBN.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tvTema = (TextView) item.findViewById(R.id.tvDificultad);
                tvTema.setText(dificultad.getNombre() + ": BLOQUEADO");
            }


            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getBloqueado() == false) {
                        Intent intent = new Intent(Tema.this, PreguntaTexto.class);

                        intent.putExtra("tema", MainActivity.sTema);
                        intent.putExtra("dificultad", dificultad);
                        intent.putExtra("positionDificultad", positionDificultad);
                        intent.putExtra("positionTema", positionTema);

                        startActivityForResult(intent, 12346);
                    } else {
                        AlertDialog ad;
                        ad = new AlertDialog.Builder(Tema.this).create();
                        ad.setTitle("¡BLOQUEADO!");
                        ad.setMessage("Para jugar este nivel debes completar el anterior.");

                        ad.setButton(AlertDialog.BUTTON_POSITIVE,"VALE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                        ad.show();
                    }
                }
            });
            return (item);
        }
    }
}

