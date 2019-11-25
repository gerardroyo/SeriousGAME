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

public class Tienda extends AppCompatActivity {

    private ImageView img;

    private ArrayList<cTienda> ImgPerfil;

    private ArrayList<cUser> users;

    private String sTema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        ArrayList<cUser> user;
        user = (ArrayList<cUser>)getIntent().getSerializableExtra("user");
        users = user;

        ArrayList<cTienda> imgs;
        imgs = (ArrayList<cTienda>)getIntent().getSerializableExtra("tienda");
        ImgPerfil = imgs;

        sTema = getIntent().getExtras().getString("stema");

        AdaptadorTienda adaptador = new AdaptadorTienda(this, ImgPerfil);

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptador);

        // deshabilita el titol
        getSupportActionBar().hide();

        TextView tv = (TextView)findViewById(R.id.tvMoney);
        tv.setText(Integer.toString(users.get(0).getMonedas()));

        /*img = findViewById(R.id.imgTema);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });*/

    }

    class AdaptadorTienda extends ArrayAdapter<cTienda> {

        private Context context;

        public AdaptadorTienda(Context context, ArrayList<cTienda> datos) {
            super(context, R.layout.activity_mostrar_tienda, datos);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_tienda, null);

            final cTienda imgperfil = (cTienda) getItem(position);

            ImageView img = (ImageView) item.findViewById(R.id.imgImgPerfil);
            String nombre = imgperfil.getNombre();
            String src = "@drawable/" + nombre;
            src = src.toLowerCase();
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

            TextView tv = (TextView) item.findViewById(R.id.tvImgPerfil);
            tv.setText(imgperfil.getNombre());

            tv = (TextView) item.findViewById(R.id.tvMoney);
            tv.setText(Integer.toString(imgperfil.getPrecio()));

            img = findViewById(R.id.imgPerfil);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Tienda.this, Perfil.class);

                    intent.putExtra("user", users);
                    intent.putExtra("stema", sTema);
                    intent.putExtra("tienda", ImgPerfil);

                    startActivityForResult(intent, 1234);
                }

            });

            img = findViewById(R.id.imgInicio);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Tienda.this, Tema.class);

                    intent.putExtra("user", users);
                    intent.putExtra("tienda", ImgPerfil);

                    startActivityForResult(intent, 12345);
                }

            });

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
