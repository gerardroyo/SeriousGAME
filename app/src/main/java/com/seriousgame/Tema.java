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

public class Tema extends AppCompatActivity {

    private cTema Temas;
    private String sTema;

    private ImageView img;
    private int destino;
    private int origenEnv = 1;

    private ArrayList<cDificultad> Dificultad = new ArrayList<cDificultad>();
    private ArrayList<cUser> Users = new ArrayList<cUser>();
    private ArrayList<cTienda> Tiendas = new ArrayList<cTienda>();

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

        Dificultad.clear();
        Dificultad.add(new cDificultad("Fácil", "lvlfacilcolor"));
        Dificultad.add(new cDificultad("Normal", "lvlnormalcolor"));
        Dificultad.add(new cDificultad("Difícil", "lvldificilcolor"));


        cTema tema = (cTema) getIntent().getSerializableExtra("tema");
        Temas = tema;

        ArrayList<cUser> user;
        user = (ArrayList<cUser>)getIntent().getSerializableExtra("user");
        Users = user;

        ArrayList<cTienda> tienda;
        tienda = (ArrayList<cTienda>)getIntent().getSerializableExtra("tienda");
        Tiendas = tienda;

        sTema = getIntent().getExtras().getString("stema");

        AdaptadorDificultad adaptador = new AdaptadorDificultad(this, Dificultad);

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptador);

        // deshabilita el titol
        getSupportActionBar().hide();

        TextView tv = (TextView)findViewById(R.id.tvMoney);
        tv.setText(Integer.toString(user.get(0).getMonedas()));

        ImageView img = (ImageView) findViewById(R.id.imgTema);
        //String nombre = tema.getNombre();
        String src = "@drawable/" + sTema;
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

                intent.putExtra("tema", Temas);
                intent.putExtra("user", Users);
                intent.putExtra("tienda", Tiendas);
                intent.putExtra("stema", sTema);
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

                intent.putExtra("tema", Temas);
                intent.putExtra("user", Users);
                intent.putExtra("tienda", Tiendas);
                intent.putExtra("stema", sTema);
                destino = 3;
                intent.putExtra("destino", destino);
                intent.putExtra("origen", origenEnv);

                startActivityForResult(intent, 12345);
            }

        });

    }

    class AdaptadorDificultad extends ArrayAdapter<cDificultad> {

        private Context context;

        public AdaptadorDificultad(Context context, ArrayList<cDificultad> datos) {
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
            tvTema.setText(dificultad.getNombre() + ": n/n");

            /*item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Tema.this, Tema.class);

                    intent.putExtra("tema", "hola");

                    startActivity(intent);
                }
            });*/

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

