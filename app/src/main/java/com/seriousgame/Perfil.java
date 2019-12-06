package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity {

    private ImageView img;

    private String sTema;
    private int destino;
    private int origenEnv = 2;
    private int origenRec;

    private ArrayList<cTienda> ImgPerfil;

    private ArrayList<cUser> users;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 12345 && resultCode == RESULT_OK) {
            destino = data.getExtras().getInt("destino");
            if(destino != 2) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        destino = getIntent().getExtras().getInt("destino");
        origenRec = getIntent().getExtras().getInt("origen");

        // deshabilita el titol
        getSupportActionBar().hide();

        AdaptadorLogros adaptador = new AdaptadorLogros(this, MainActivity.Logros);

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptador);

        ImageView img = (ImageView)findViewById(R.id.imageView2);

        for (int i = 0; i < MainActivity.Tienda.size(); i++){
            if (MainActivity.Tienda.get(i).getComprado() == true && MainActivity.Tienda.get(i).getEquipado() == true){
                String nombre = MainActivity.Tienda.get(i).getNombre();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tv = (TextView)findViewById(R.id.tvNombreUser);
                tv.setText(MainActivity.User.get(0).getNombre());
            }
        }

        img = findViewById(R.id.imgInicio);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                destino = 1;
                intent.putExtra("destino", destino);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

        img = findViewById(R.id.imgTienda);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(origenRec == 3) {
                    Intent intent = new Intent();
                    destino = 3;
                    intent.putExtra("destino", destino);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(Perfil.this, Tienda.class);

                    /*intent.putExtra("user", users);
                    intent.putExtra("tienda", ImgPerfil);
                    intent.putExtra("stema", sTema);*/
                    destino = 3;
                    intent.putExtra("destino", destino);
                    intent.putExtra("origen", origenEnv);

                    startActivityForResult(intent, 12345);
                }
            }

        });

    }

    class AdaptadorLogros extends ArrayAdapter<cLogro> {

        private Context context;

        public AdaptadorLogros(Context context, ArrayList<cLogro> datos) {
            super(context, R.layout.activity_mostrar_logros, datos);
            this.context = context;
        }

        public View getView(final int positionLogro, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_logros, null);

            final cLogro logro = (cLogro) getItem(positionLogro);

            ImageView img = (ImageView) item.findViewById(R.id.imgLogro);
            String nombre = logro.getImg().toLowerCase();
            String src = "@drawable/" + nombre;
            src = src.toLowerCase();
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

            TextView tvTema = (TextView) item.findViewById(R.id.tvNombre);
            tvTema.setText(logro.getNombre());

            ProgressBar pb = (ProgressBar) item.findViewById(R.id.pb2);
            pb.setProgress(logro.getProgreso());

            TextView tvProgreso = (TextView) item.findViewById(R.id.tvProgreso);
            String progreso = Integer.toString(logro.getProgreso());
            tvProgreso.setText(progreso + " %");

            return (item);
        }
    }

}
