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

    private int destino = 1;
    private int origen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Temas.clear();
        Temas.add(new cTema(1, "Sumar"));
        Temas.add(new cTema(2, "Restar"));
        Temas.add(new cTema(3, "Multiplicar"));

        /*Dificultad.clear();
        Dificultad.add(new cDificultad(1, "Fácil", "lvlfacilcolor", 1, 0, 3));
        Dificultad.add(new cDificultad(2, "Normal", "lvlnormalcolor", 2, 0, 3));
        Dificultad.add(new cDificultad(3, "Difícil", "lvldificilcolor", 3, 0, 4));*/

        /*Lecciones.clear();
        Lecciones.add(new cLecciones(1, 0, 4));
        Lecciones.add(new cLecciones(2, 0, 3));
        Lecciones.add(new cLecciones(3, 0, 4));*/

        User.clear();
        User.add(new cUser("Sin nombre", "imgperfil"));

        Tienda.clear();
        Tienda.add(new cTienda("Awesome", 250, true, true));
        Tienda.add(new cTienda("Paleta", 250, false, false));
        Tienda.add(new cTienda("Zombie", 250, false, false));
        Tienda.add(new cTienda("Buneary", 350, false, false));
        Tienda.add(new cTienda("Esqueleto", 450, false, false));
        Tienda.add(new cTienda("Link", 550, false, false));

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

        /*public void escojerColor(int tipo, View item, TextView tv, cPokimon pokemon) {
            String tipoString = "";

            switch (tipo) {
                case 1:

                    tv.setBackgroundColor(Color.parseColor("#73C457"));
                    tipoString = "Planta";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 2:

                    tv.setBackgroundColor(Color.parseColor("#DF4E2F"));
                    tipoString = "Fuego";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 3:

                    tv.setBackgroundColor(Color.parseColor("#B95943"));
                    tipoString = "Lucha";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 4:

                    tv.setBackgroundColor(Color.parseColor("#329BFE"));
                    tipoString = "Agua";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 6:

                    tv.setBackgroundColor(Color.parseColor("#71584A"));
                    tipoString = "Siniestro";
                    introducirTiposString(pokemon, tv, tipoString);

                    tv.setTextColor(Color.parseColor("#FFFFFFFF"));

                    break;
                case 7:

                    tv.setBackgroundColor(Color.parseColor("#C2B4B2"));
                    tipoString = "Normal";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 8:

                    tv.setBackgroundColor(Color.parseColor("#A7B33B"));
                    tipoString = "Bicho";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 5:

                    tv.setBackgroundColor(Color.parseColor("#DEC054"));
                    tipoString = "Tierra";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 9:

                    tv.setBackgroundColor(Color.parseColor("#6A9BE8"));
                    tipoString = "Volador";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 10:

                    tv.setBackgroundColor(Color.parseColor("#884A7A"));
                    tipoString = "Veneno";
                    introducirTiposString(pokemon, tv, tipoString);

                    tv.setTextColor(Color.parseColor("#FFFFFFFF"));

                    break;
                case 11:

                    tv.setBackgroundColor(Color.parseColor("#D06B8D"));
                    tipoString = "Psíquico";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 17:

                    tv.setBackgroundColor(Color.parseColor("#5A5478"));
                    tipoString = "Dragon";
                    introducirTiposString(pokemon, tv, tipoString);

                    tv.setTextColor(Color.parseColor("#FFFFFFFF"));

                    break;
                case 14:

                    tv.setBackgroundColor(Color.parseColor("#BCAA63"));
                    tipoString = "Roca";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 12:

                    tv.setBackgroundColor(Color.parseColor("#FDABFD"));
                    tipoString = "Hada";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 13:

                    tv.setBackgroundColor(Color.parseColor("#6E6DAD"));
                    tipoString = "Fantasma";
                    introducirTiposString(pokemon, tv, tipoString);

                    tv.setTextColor(Color.parseColor("#FFFFFFFF"));

                    break;
                case 16:

                    tv.setBackgroundColor(Color.parseColor("#F4CB5C"));
                    tipoString = "Eléctrico";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 18:

                    tv.setBackgroundColor(Color.parseColor("#7EDAFD"));
                    tipoString = "Hielo";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                case 15:

                    tv.setBackgroundColor(Color.parseColor("#B2A8BC"));
                    tipoString = "Acero";
                    introducirTiposString(pokemon, tv, tipoString);

                    break;
                default:

                    tv.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }
        }*/
    }

}
