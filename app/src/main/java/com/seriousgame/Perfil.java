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

    private ArrayList<cTienda> ImgPerfil;

    private ArrayList<cUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // deshabilita el titol
        getSupportActionBar().hide();

        sTema = getIntent().getExtras().getString("stema");

        ArrayList<cUser> user;
        user = (ArrayList<cUser>)getIntent().getSerializableExtra("user");
        users = user;

        ArrayList<cTienda> imgs;
        imgs = (ArrayList<cTienda>)getIntent().getSerializableExtra("tienda");
        ImgPerfil = imgs;

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
                Intent intent = new Intent(Perfil.this, Tema.class);

                intent.putExtra("user", users);
                intent.putExtra("tienda", ImgPerfil);

                startActivityForResult(intent, 1234);
            }

        });

        img = findViewById(R.id.imgTienda);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Tienda.class);

                intent.putExtra("user", users);
                intent.putExtra("tienda", ImgPerfil);

                startActivityForResult(intent, 12345);
            }

        });

    }
}
