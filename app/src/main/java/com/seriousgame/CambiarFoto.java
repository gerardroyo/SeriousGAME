package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CambiarFoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_foto);

        getSupportActionBar().hide();

        AdaptadorFotos adaptador = new AdaptadorFotos(this, MainActivity.FotosComprado);

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptador);

        ImageView img = findViewById(R.id.imgBack);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        guardarDatos();

    }

    class AdaptadorFotos extends ArrayAdapter<cTienda> {

        private Context context;

        public AdaptadorFotos(Context context, ArrayList<cTienda> datos) {
            super(context, R.layout.activity_mostrar_cambiar_foto, datos);
            this.context = context;
        }

        public View getView(final int positionFoto, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_cambiar_foto, null);

            final cTienda foto = (cTienda) getItem(positionFoto);

            ImageView img = (ImageView)item.findViewById(R.id.imgComprada);
            String nombre = foto.getNombre();
            String src = "@drawable/" + nombre;
            src = src.toLowerCase();
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

            if(foto.getEquipado() == true) {
                img = (ImageView) item.findViewById(R.id.imgChecked);
                img.setVisibility(View.VISIBLE);

                ConstraintLayout cl = (ConstraintLayout) item.findViewById(R.id.clFoto);
                cl.setBackground(ContextCompat.getDrawable(context, R.drawable.border_all_green));
            } else {
                img = (ImageView) item.findViewById(R.id.imgChecked);
                img.setVisibility(View.INVISIBLE);
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(foto.getEquipado() == false) {
                        for(int i = 0; i < MainActivity.FotosComprado.size(); i++) {
                            if(MainActivity.FotosComprado.get(i).getEquipado() == true) {
                                MainActivity.FotosComprado.get(i).setEquipado(false);
                                for(int j = 0; j < MainActivity.Tienda.size(); j++) {
                                    if(MainActivity.FotosComprado.get(i).getNombre().equals(MainActivity.Tienda.get(j).getNombre())) {
                                        MainActivity.Tienda.get(j).setEquipado(false);
                                        //MainActivity.Tienda.get(j).setComprado(true);
                                    }
                                }
                            }
                        }
                        Intent intent = new Intent();
                        for(int i = 0; i < MainActivity.Tienda.size(); i++) {
                            if(MainActivity.Tienda.get(i).getNombre().equals(foto.getNombre())) {
                                MainActivity.Tienda.get(i).setEquipado(true);
                            }
                        }
                        MainActivity.FotosComprado.get(positionFoto).setEquipado(true);
                        setResult(RESULT_OK, intent);
                        finish();

                    }

                }
            });

            return (item);
        }
    }
    private void guardarDatos() {

        SharedPreferences preferencias = getSharedPreferences("game", Context.MODE_PRIVATE);
//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        int lecAcSumFacil = MainActivity.Temas.get(0).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcSumNormal = MainActivity.Temas.get(0).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcSumDificil = MainActivity.Temas.get(0).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcResFacil = MainActivity.Temas.get(1).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcResNormal = MainActivity.Temas.get(1).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcResDificil = MainActivity.Temas.get(1).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcMultFacil = MainActivity.Temas.get(2).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcMultNormal = MainActivity.Temas.get(2).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcMultDificil = MainActivity.Temas.get(2).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcDivFacil = MainActivity.Temas.get(3).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcDivNormal = MainActivity.Temas.get(3).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcDivDificil = MainActivity.Temas.get(3).getDificultad().get(2).getLecciones().get(0).getLeccionActual();
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        boolean difBloquSumFacil = MainActivity.Temas.get(0).getDificultad().get(0).getBloqueado();
        boolean difBloquSumNormal = MainActivity.Temas.get(0).getDificultad().get(1).getBloqueado();
        boolean difBloquSumDificil = MainActivity.Temas.get(0).getDificultad().get(2).getBloqueado();

        boolean difBloquResFacil = MainActivity.Temas.get(1).getDificultad().get(0).getBloqueado();
        boolean difBloquResNormal = MainActivity.Temas.get(1).getDificultad().get(1).getBloqueado();
        boolean difBloquResDificil = MainActivity.Temas.get(1).getDificultad().get(2).getBloqueado();

        boolean difBloquMultFacil = MainActivity.Temas.get(2).getDificultad().get(0).getBloqueado();
        boolean difBloquMultNormal = MainActivity.Temas.get(2).getDificultad().get(1).getBloqueado();
        boolean difBloquMultDificil = MainActivity.Temas.get(2).getDificultad().get(2).getBloqueado();

        boolean difBloquDivFacil = MainActivity.Temas.get(3).getDificultad().get(0).getBloqueado();
        boolean difBloquDivNormal = MainActivity.Temas.get(3).getDificultad().get(1).getBloqueado();
        boolean difBloquDivDificil = MainActivity.Temas.get(3).getDificultad().get(2).getBloqueado();
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        String usuarioNombre = MainActivity.User.get(0).getNombre();
        int usuarioMonedas = MainActivity.User.get(0).getMonedas();
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        int logro1Progres = MainActivity.Logros.get(0).getProgreso();
        int logro2Progres = MainActivity.Logros.get(1).getProgreso();
        int logro3Progres = MainActivity.Logros.get(2).getProgreso();
        int logro4Progres = MainActivity.Logros.get(3).getProgreso();
        int logro5Progres = MainActivity.Logros.get(4).getProgreso();
        int logro6Progres = MainActivity.Logros.get(5).getProgreso();
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        boolean tiendaComprado1 = MainActivity.Tienda.get(0).getComprado();
        boolean tiendaComprado2 = MainActivity.Tienda.get(1).getComprado();
        boolean tiendaComprado3 = MainActivity.Tienda.get(2).getComprado();
        boolean tiendaComprado4 = MainActivity.Tienda.get(3).getComprado();
        boolean tiendaComprado5 = MainActivity.Tienda.get(4).getComprado();
        boolean tiendaComprado6 = MainActivity.Tienda.get(5).getComprado();
//---------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Equipado <----------------------------------------------\\
        boolean tiendaEquipado1 = MainActivity.Tienda.get(0).getEquipado();
        boolean tiendaEquipado2 = MainActivity.Tienda.get(1).getEquipado();
        boolean tiendaEquipado3 = MainActivity.Tienda.get(2).getEquipado();
        boolean tiendaEquipado4 = MainActivity.Tienda.get(3).getEquipado();
        boolean tiendaEquipado5 = MainActivity.Tienda.get(4).getEquipado();
        boolean tiendaEquipado6 = MainActivity.Tienda.get(5).getEquipado();
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
        SharedPreferences.Editor editor = preferencias.edit();
//---------------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        editor.putInt("lecAcSumFacil", lecAcSumFacil);
        editor.putInt("lecAcSumNormal", lecAcSumNormal);
        editor.putInt("lecAcSumDificil", lecAcSumDificil);

        editor.putInt("lecAcResFacil", lecAcResFacil);
        editor.putInt("lecAcResNormal", lecAcResNormal);
        editor.putInt("lecAcResDificil", lecAcResDificil);

        editor.putInt("lecAcMultFacil", lecAcMultFacil);
        editor.putInt("lecAcMultNormal", lecAcMultNormal);
        editor.putInt("lecAcMultDificil", lecAcMultDificil);

        editor.putInt("lecAcDivFacil", lecAcDivFacil);
        editor.putInt("lecAcDivNormal", lecAcDivNormal);
        editor.putInt("lecAcDivDificil", lecAcDivDificil);
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        editor.putBoolean("difBloquSumFacil", difBloquSumFacil);
        editor.putBoolean("difBloquSumNormal", difBloquSumNormal);
        editor.putBoolean("difBloquSumDificil", difBloquSumDificil);

        editor.putBoolean("difBloquResFacil", difBloquResFacil);
        editor.putBoolean("difBloquResNormal", difBloquResNormal);
        editor.putBoolean("difBloquResDificil", difBloquResDificil);

        editor.putBoolean("difBloquMultFacil", difBloquMultFacil);
        editor.putBoolean("difBloquMultNormal", difBloquMultNormal);
        editor.putBoolean("difBloquMultDificil", difBloquMultDificil);

        editor.putBoolean("difBloquDivFacil", difBloquDivFacil);
        editor.putBoolean("difBloquDivNormal", difBloquDivNormal);
        editor.putBoolean("difBloquDivDificil", difBloquDivDificil);
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        editor.putString("usuarioNombre", usuarioNombre);
        editor.putInt("usuarioMonedas", usuarioMonedas);
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        editor.putInt("logro1Progres", logro1Progres);
        editor.putInt("logro2Progres", logro2Progres);
        editor.putInt("logro3Progres", logro3Progres);
        editor.putInt("logro4Progres", logro4Progres);
        editor.putInt("logro5Progres", logro5Progres);
        editor.putInt("logro6Progres", logro6Progres);
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        editor.putBoolean("tiendaComprado1", tiendaComprado1);
        editor.putBoolean("tiendaComprado2", tiendaComprado2);
        editor.putBoolean("tiendaComprado3", tiendaComprado3);
        editor.putBoolean("tiendaComprado4", tiendaComprado4);
        editor.putBoolean("tiendaComprado5", tiendaComprado5);
        editor.putBoolean("tiendaComprado6", tiendaComprado6);
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Equipado <----------------------------------------------\\
        editor.putBoolean("tiendaEquipado1", tiendaEquipado1);
        editor.putBoolean("tiendaEquipado2", tiendaEquipado2);
        editor.putBoolean("tiendaEquipado3", tiendaEquipado3);
        editor.putBoolean("tiendaEquipado4", tiendaEquipado4);
        editor.putBoolean("tiendaEquipado5", tiendaEquipado5);
        editor.putBoolean("tiendaEquipado6", tiendaEquipado6);
//---------------------------------------------------------------------------------------------------------------\\

        editor.commit();

    }

}
