package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
                Intent intent = new Intent();
                destino = 1;
                intent.putExtra("destino", destino);
                setResult(RESULT_OK, intent);
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

        guardarDatos();

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

                TextView tv = (TextView) item.findViewById(R.id.tvNombre);
                tv.setText(imgperfil.getNombre());

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
                            MainActivity.FotosComprado.add(imgperfil);

                            if(origenRec == 1) {
                                Intent intent = new Intent();
                                destino = 3;
                                intent.putExtra("destino", destino);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                            if(origenRec == 2) {
                                Intent intent = new Intent();
                                destino = 5;
                                intent.putExtra("destino", destino);
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            Intent intent = new Intent();
                            destino = 3;
                            intent.putExtra("destino", destino);
                            setResult(RESULT_OK, intent);
                            finish();
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
