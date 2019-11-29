package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        sTema = getIntent().getExtras().getString("stema");

        ArrayList<cUser> user;
        user = (ArrayList<cUser>)getIntent().getSerializableExtra("user");
        users = user;

        ArrayList<cTienda> imgs;
        imgs = (ArrayList<cTienda>)getIntent().getSerializableExtra("tienda");
        ImgPerfil = imgs;

        sTema = getIntent().getExtras().getString("stema");

        ImageView img = (ImageView)findViewById(R.id.imageView2);

        for (int i = 0; i < imgs.size(); i++){
            if (imgs.get(i).getComprado() == true && ImgPerfil.get(i).getEquipado() == true){
                String nombre = imgs.get(i).getNombre();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tv = (TextView)findViewById(R.id.tvNombreUser);
                tv.setText(users.get(0).getNombre());
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

                    intent.putExtra("user", users);
                    intent.putExtra("tienda", ImgPerfil);
                    intent.putExtra("stema", sTema);
                    destino = 3;
                    intent.putExtra("destino", destino);
                    intent.putExtra("origen", origenEnv);

                    startActivityForResult(intent, 12345);
                }
            }

        });

    }
}
