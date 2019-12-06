package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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
    private int destino;
    private int origenEnv = 3;
    private int origenRec = 3;

    private ArrayList<cTienda> ImgPerfil;

    private ArrayList<cUser> users;

    private String sTema;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            destino = data.getExtras().getInt("destino");
            if(destino != 3) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        destino = getIntent().getExtras().getInt("destino");
        origenRec = getIntent().getExtras().getInt("origen");

        ArrayList<cUser> user;
        user = (ArrayList<cUser>)getIntent().getSerializableExtra("user");
        users = user;

        ArrayList<cTienda> imgs;
        imgs = (ArrayList<cTienda>)getIntent().getSerializableExtra("tienda");
        ImgPerfil = imgs;

        sTema = getIntent().getExtras().getString("stema");

        AdaptadorTienda adaptador = new AdaptadorTienda(this, MainActivity.Tienda);

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptador);

        // deshabilita el titol
        getSupportActionBar().hide();

        TextView tv = (TextView)findViewById(R.id.tvMoney);
        tv.setText(Integer.toString(MainActivity.User.get(0).getMonedas()));

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

        img = findViewById(R.id.imgPerfil);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(origenRec == 2) {
                    Intent intent = new Intent();
                    destino = 2;
                    intent.putExtra("destino", destino);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(Tienda.this, Perfil.class);

                    destino = 2;
                    intent.putExtra("destino", destino);
                    intent.putExtra("origen", origenEnv);

                    startActivityForResult(intent, 1234);
                }
            }

        });

    }

    class AdaptadorTienda extends ArrayAdapter<cTienda> {

        private Context context;

        public AdaptadorTienda(Context context, ArrayList<cTienda> datos) {
            super(context, R.layout.activity_mostrar_tienda, datos);
            this.context = context;
        }

        public View getView(final int positionImg, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_tienda, null);

            final cTienda imgperfil = (cTienda) getItem(positionImg);

            if(imgperfil.getComprado() == true) {


                ConstraintLayout cl = (ConstraintLayout) item.findViewById(R.id.clTienda);
                cl.setBackgroundColor(Color.parseColor("#bababa"));

                ImageView img = (ImageView) item.findViewById(R.id.imgLogro);
                String nombre = imgperfil.getNombreBN();
                String src = "@drawable/" + nombre;
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                img = (ImageView) item.findViewById(R.id.imgMoneyImg);
                src = "@drawable/monedabn";
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                img = (ImageView) item.findViewById(R.id.imgComprar);
                src = "@drawable/comprarbn";
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tv1 = (TextView) item.findViewById(R.id.tvMoneyImg);
                tv1.setTextColor(Color.parseColor("#000000"));
            } else {
                ImageView img = (ImageView) item.findViewById(R.id.imgLogro);
                String nombre = imgperfil.getNombre();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tv = (TextView) item.findViewById(R.id.tvNombre);
                tv.setText(imgperfil.getNombre());

                tv = (TextView) item.findViewById(R.id.tvMoneyImg);
                tv.setText(Integer.toString(imgperfil.getPrecio()));
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(imgperfil.getComprado() == false) {
                        if(imgperfil.getPrecio() <= MainActivity.User.get(0).getMonedas()) {
                            MainActivity.Tienda.get(positionImg).setComprado(true);
                            MainActivity.User.get(0).setMonedas(MainActivity.User.get(0).getMonedas() - imgperfil.getPrecio());
                            finish();
                            startActivity(getIntent());
                        } else {
                            View parentLayout = findViewById(android.R.id.content);
                            SnackBar.snackBarErrorCompra(parentLayout);
                        }
                    } else {
                        View parentLayout = findViewById(android.R.id.content);
                        SnackBar.snackBarYaCompraste(parentLayout);
                    }

                }
            });

            return (item);
        }
    }
}
