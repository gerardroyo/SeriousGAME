package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Guia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia);

        getSupportActionBar().hide();

        ImageView img = findViewById(R.id.imgCloseGuia);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        carregarLayout();

        TextView tv = (TextView) findViewById(R.id.tvSiguiente);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeekBar sb = (SeekBar) findViewById(R.id.seekBar);

                if(sb.getProgress() == 4){
                } else {
                    int progress = sb.getProgress();
                    sb.setProgress(progress + 1);
                    carregarLayout();
                }
            }

        });

        tv = (TextView) findViewById(R.id.tvAtras);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeekBar sb = (SeekBar) findViewById(R.id.seekBar);

                if(sb.getProgress() == 0){
                    finish();
                } else {
                    int progress = sb.getProgress();
                    sb.setProgress(progress - 1);
                    carregarLayout();
                }
            }

        });

    }

    public void carregarLayout() {

        final SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        ImageView img = (ImageView) findViewById(R.id.imgGuia);
        TextView tv = (TextView) findViewById(R.id.tvTitolsito);
        String src;
        if (sb.getProgress() == 0) {
            tv.setText("Elección del tema");

            src = "@drawable/guia1";
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
        } else if (sb.getProgress() == 1) {
            tv.setText("Inicio");

            src = "@drawable/guia2_1";
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
        } else if (sb.getProgress() == 2) {
            tv.setText("Inicio");

            src = "@drawable/guia2_2";
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
        } else if (sb.getProgress() == 3) {
            tv.setText("Perfil de Usuario");

            src = "@drawable/guia3";
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
        } else if (sb.getProgress() == 4) {
            tv.setText("Tienda");

            src = "@drawable/guia4";
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
        }


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Write code to perform some action when progress is changed.
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is started.

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is stopped.
                if (sb.getProgress() == 0) {
                    ImageView img = (ImageView) findViewById(R.id.imgGuia);
                    String src = "@drawable/guia1";
                    img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
                } else if (sb.getProgress() == 1) {
                    ImageView img = (ImageView) findViewById(R.id.imgGuia);
                    String src = "@drawable/guia2_1";
                    img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
                } else if (sb.getProgress() == 2) {
                    ImageView img = (ImageView) findViewById(R.id.imgGuia);
                    String src = "@drawable/guia2_2";
                    img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
                } else if (sb.getProgress() == 3) {
                    ImageView img = (ImageView) findViewById(R.id.imgGuia);
                    String src = "@drawable/guia3";
                    img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
                } else if (sb.getProgress() == 4) {
                    ImageView img = (ImageView) findViewById(R.id.imgGuia);
                    String src = "@drawable/guia4";
                    img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));
                }
            }
        });
    }

}
