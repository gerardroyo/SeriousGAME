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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String sTema;

    public static ArrayList<cTema> Temas = new ArrayList<cTema>();

    public static ArrayList<cDificultad> Dificultad = new ArrayList<cDificultad>();

    public static ArrayList<cLecciones> Lecciones = new ArrayList<cLecciones>();

    public static ArrayList<cUser> User = new ArrayList<cUser>();

    public static ArrayList<cTienda> Tienda = new ArrayList<cTienda>();

    public static ArrayList<cLogro> Logros = new ArrayList<cLogro>();

    private int destino = 1;
    private int origen = 0;

    public static int logro6 = 0;
    public static int dif1 = 0;
    public static int fet1 = 0;
    public static int fet2 = 0;
    public static int fet3 = 0;
    public static int fet4 = 0;
    public static int fet5 = 0;
    public static int fet6 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Temas.clear();
        Temas.add(new cTema(1, "Sumar"));
        Temas.add(new cTema(2, "Restar"));
        Temas.add(new cTema(3, "Multiplicar"));

        User.clear();
        User.add(new cUser("Sin nombre", "imgperfil"));

        Tienda.clear();
        Tienda.add(new cTienda("Koi", "koibn", 250, true, true));
        Tienda.add(new cTienda("Macaco",  "macacobn", 250, false, false));
        Tienda.add(new cTienda("UngaUnga",  "ungaungabn", 250, false, false));
        Tienda.add(new cTienda("Kabuki", "kabukibn", 350, false, false));
        Tienda.add(new cTienda("Monje", "monjebn", 450, false, false));
        Tienda.add(new cTienda("Samurai", "samuraibn", 550, false, false));

        Logros.clear();
        Logros.add(new cLogro(1, "Completa 1 leccion", 0, "logro1", 100, 25));
        Logros.add(new cLogro(2, "Completa 1 dificultad", 0, "logro2", 150, 50));
        Logros.add(new cLogro(3, "Completa 10 operaciones seguidas sin fallar", 0, "logro3", 150, 50));
        Logros.add(new cLogro(4, "Completa 1 tema", 0, "logro4", 300, 250));
        Logros.add(new cLogro(5, "Completa 50 operaciones seguidas sin fallar", 0, "logro5", 350, 300));
        Logros.add(new cLogro(6, "Completa todos los temas", 0, "logro6", 1000, 1000));

        AdaptadorTemas adaptador = new AdaptadorTemas(this, Temas);

        ListView lst = (ListView)findViewById(R.id.listCustom);
        lst.setAdapter(adaptador);

        // deshabilita el titol
        getSupportActionBar().hide();

    }

    class AdaptadorTemas extends ArrayAdapter<cTema> {

        private Context context;

        public AdaptadorTemas(Context context, ArrayList<cTema> datos) {
            super(context, R.layout.activity_mostrar_lista_main, datos);
            this.context = context;
        }

        public View getView(final int positionTema, View convertView, ViewGroup parent) {



            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_lista_main, null);

            final cTema tema = (cTema) getItem(positionTema);

            ImageView img = (ImageView) item.findViewById(R.id.imgTema);
            String nombre = tema.getNombre().toLowerCase();
            String src = "@drawable/" + nombre;
            src = src.toLowerCase();
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

            TextView tvTema = (TextView) item.findViewById(R.id.tvNombre);
            tvTema.setText(tema.getNombre());

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, Tema.class);

                    /*intent.putExtra("tema", tema);
                    intent.putExtra("user", User);
                    intent.putExtra("tienda", ImgPerfil);
                    intent.putExtra("stema", sTema);*/
                    sTema = tema.getNombre();
                    intent.putExtra("positionTema", positionTema);
                    intent.putExtra("destino", destino);
                    intent.putExtra("origen", origen);

                    context.startActivity(intent);
                }
            });

            return (item);
        }
    }

}
