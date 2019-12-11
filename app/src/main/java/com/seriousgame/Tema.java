package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Tema extends AppCompatActivity {

    private cTema Temas;
    private String sTema;

    private ImageView img;
    private int destino;
    private int origenEnv = 1;
    private int positionTema;
    private int logro2;
    private int progres;
    //public static int positionTOP;

    private ArrayList<cUser> Users = new ArrayList<cUser>();
    private ArrayList<cTienda> Tiendas = new ArrayList<cTienda>();

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 12346 && resultCode == RESULT_OK) {
            finish();
            startActivity(getIntent());

            AdaptadorTema adaptador = new AdaptadorTema(this, MainActivity.Temas.get(positionTema).getDificultad());

            ListView lst = (ListView)findViewById(R.id.listCustom2);
            lst.setAdapter(adaptador);

            int fallos = data.getExtras().getInt("fallos");
            int aciertos = data.getExtras().getInt("aciertos");
            if(fallos == 2) {
                View parentLayout = findViewById(android.R.id.content);
                SnackBar.snackBarErrorFallos(parentLayout);
            }
            if(aciertos == 10){
                View parentLayout = findViewById(android.R.id.content);
                SnackBar.snackBarAprendido(parentLayout);
            }

        }

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            finish();
            startActivity(getIntent());
        }
        if (requestCode == 12345 && resultCode == RESULT_OK) {
            destino = data.getExtras().getInt("destino");
            if(destino == 3) {
                Intent intent = new Intent(Tema.this, Tienda.class);

                destino = 3;
                intent.putExtra("destino", destino);
                intent.putExtra("origen", origenEnv);

                startActivityForResult(intent, 12345);
            } else {
                finish();
                startActivity(getIntent());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        destino = getIntent().getExtras().getInt("destino");
        positionTema = getIntent().getExtras().getInt("positionTema");

        AdaptadorTema adaptador = new AdaptadorTema(this, MainActivity.Temas.get(positionTema).getDificultad());

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptador);

        // deshabilita el titol
        getSupportActionBar().hide();

        TextView tv = (TextView)findViewById(R.id.tvMoney);
        tv.setText(Integer.toString(MainActivity.User.get(0).getMonedas()));

        ImageView img = (ImageView) findViewById(R.id.imgTema);
        //String nombre = tema.getNombre();
        String src = "@drawable/" + MainActivity.sTema;
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

                destino = 3;
                intent.putExtra("destino", destino);
                intent.putExtra("origen", origenEnv);

                startActivityForResult(intent, 12345);
            }

        });

        guardarDatos();

    }

    class AdaptadorTema extends ArrayAdapter<cDificultad> {

        private Context context;

        public AdaptadorTema(Context context, ArrayList<cDificultad> datos) {
            super(context, R.layout.activity_mostrar_dificultad, datos);
            this.context = context;
        }

        public View getView(final int positionDificultad, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_dificultad, null);

            final cDificultad dificultad = (cDificultad)getItem(positionDificultad);

            if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getBloqueado() == false) {
                final ImageView img = (ImageView) item.findViewById(R.id.imgDificultad);
                String nombre = dificultad.getImgColor();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tvTema = (TextView) item.findViewById(R.id.tvDificultad);

                if(dificultad.getLecciones().get(0).getLeccionActual() >= dificultad.getLecciones().get(0).getLeccionMax()) {
                    tvTema.setText(dificultad.getNombre() + ": COMPLETADO");
                    MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).setCompletado(true);
                    int progres4 = MainActivity.Logros.get(3).getProgreso();
                    MainActivity.Logros.get(1).setProgreso(100);
                    if(MainActivity.Logros.get(3).getProgreso() != 100) {
                        if( MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getCompletado() == true && MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getComplet1() == 0) {
                            MainActivity.Logros.get(3).setProgreso(progres4 + 33);
                            MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).setComplet1(1);
                        }
                    } else {

                        MainActivity.Logros.get(3).setProgreso(100);
                    }

                    if (dificultad.getId() == 3) {
                        MainActivity.Logros.get(3).setProgreso(100);
                        int progres6 = MainActivity.Logros.get(5).getProgreso();
                        MainActivity.Logros.get(5).setProgreso(progres6 + 33);
                        MainActivity.logro6++;
                        if(MainActivity.logro6 >= 3) {
                            MainActivity.Logros.get(5).setProgreso(100);
                        }
                    }

                    if(dificultad.getId() == positionDificultad || dificultad.getId() == 3) {
                    } else {
                        MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad + 1).setBloqueado(false);
                    }
                } else {
                    String leccionActualString = Integer.toString(dificultad.getLecciones().get(0).getLeccionActual());
                    String leccionMaxString = Integer.toString(dificultad.getLecciones().get(0).getLeccionMax());
                    tvTema.setText(dificultad.getNombre() + ": " +  leccionActualString + "/" + leccionMaxString);
                }
            } else {
                final ImageView imgBN = (ImageView) item.findViewById(R.id.imgDificultad);
                String nombre = dificultad.getImgBN();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                imgBN.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tvTema = (TextView) item.findViewById(R.id.tvDificultad);
                tvTema.setText(dificultad.getNombre() + ": BLOQUEADO");
            }


            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(MainActivity.Temas.get(positionTema).getDificultad().get(positionDificultad).getBloqueado() == false) {
                        Intent intent = new Intent(Tema.this, PreguntaTexto.class);

                        intent.putExtra("tema", MainActivity.sTema);
                        intent.putExtra("dificultad", dificultad);
                        intent.putExtra("positionDificultad", positionDificultad);
                        intent.putExtra("positionTema", positionTema);

                        startActivityForResult(intent, 12346);
                    } else {
                        AlertDialog ad;
                        ad = new AlertDialog.Builder(Tema.this).create();
                        ad.setTitle("¡BLOQUEADO!");
                        ad.setMessage("Para jugar este nivel debes completar el anterior.");

                        ad.setButton(AlertDialog.BUTTON_POSITIVE,"VALE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                        ad.show();
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

