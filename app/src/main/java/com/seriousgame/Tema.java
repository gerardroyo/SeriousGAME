package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    //public static int positionTOP;

    private ArrayList<cUser> Users = new ArrayList<cUser>();
    private ArrayList<cTienda> Tiendas = new ArrayList<cTienda>();

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 12346 && resultCode == RESULT_OK) {
            int fallos = data.getExtras().getInt("fallos");
            int aciertos = data.getExtras().getInt("aciertos");
            if(fallos == 2) {
                snackBarError();
            }
            if(aciertos == 10){
                snackBarAprendido();
            }

        }

        if (requestCode == 1234 && resultCode == RESULT_OK) {
        }
        if (requestCode == 12345 && resultCode == RESULT_OK) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        destino = getIntent().getExtras().getInt("destino");

        AdaptadorTema adaptador = new AdaptadorTema(this, MainActivity.Dificultad);

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

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_dificultad, null);

            final cDificultad dificultad = (cDificultad) getItem(position);

            ImageView img = (ImageView) item.findViewById(R.id.imgDificultad);
            String nombre = dificultad.getImgColor();
            String src = "@drawable/" + nombre;
            src = src.toLowerCase();
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

            TextView tvTema = (TextView) item.findViewById(R.id.tvDificultad);

            String leccionActualString = Integer.toString(MainActivity.Lecciones.get(position).getLeccionActual());
            String leccionMaxString = Integer.toString(MainActivity.Lecciones.get(position).getLeccionMax());
            tvTema.setText(dificultad.getNombre() + ": " +  leccionActualString + "/" + leccionMaxString);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Tema.this, PreguntaTexto.class);

                    intent.putExtra("tema", MainActivity.sTema);
                    intent.putExtra("dificultad", dificultad.getId());
                    intent.putExtra("position", position);

                    startActivityForResult(intent, 12346);
                }
            });

            return (item);

        }
    }

    public void snackBarAprendido(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(parentLayout, "¡Lección completada!", Snackbar.LENGTH_LONG);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#1cc61c"));

        snack.show();
    }

    public void snackBarError(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(parentLayout, "Fallaste damasiado...", Snackbar.LENGTH_LONG);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#9d000a"));

        snack.show();
    }

}

