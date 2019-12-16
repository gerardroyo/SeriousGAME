package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String sTema;

    public static ArrayList<cTema> Temas = new ArrayList<cTema>();

    /*public static ArrayList<cDificultad> Dificultad = new ArrayList<cDificultad>();
    public static ArrayList<cLecciones> Lecciones = new ArrayList<cLecciones>();*/

    public static ArrayList<cUser> User = new ArrayList<cUser>();

    public static ArrayList<cTienda> Tienda = new ArrayList<cTienda>();

    public static ArrayList<cTienda> FotosComprado = new ArrayList<cTienda>();

    public static ArrayList<cLogro> Logros = new ArrayList<cLogro>();

    private int destino = 1;
    private int origen = 0;

    public static int logro6 = 0;
    /*public static int dif1 = 0;
    public static int dif2 = 0;
    public static int dif3 = 0;*/
    public static int fet1 = 0;
    public static int fet2 = 0;
    public static int fet3 = 0;
    public static int fet4 = 0;
    public static int fet5 = 0;
    public static int fet6 = 0;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12345 && resultCode == RESULT_OK) {
            int destino0 = data.getExtras().getInt("destino");
            if (destino0 == 0){
                Intent intent = new Intent(MainActivity.this, Tema.class);

                startActivityForResult(intent, 12345);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Temas.clear();
        Temas.add(new cTema(1, "Sumar"));
        Temas.add(new cTema(2, "Restar"));
        Temas.add(new cTema(3, "Multiplicar"));
        Temas.add(new cTema(4, "Dividir"));

        User.clear();
        User.add(new cUser("Sin nombre", "imgperfil"));

        Tienda.clear();
        Tienda.add(new cTienda("Koi", "koibn", 250, true, true));
        Tienda.add(new cTienda("Macaco",  "macacobn", 250, false, false));
        Tienda.add(new cTienda("UngaUnga",  "ungaungabn", 250, false, false));
        Tienda.add(new cTienda("Kabuki", "kabukibn", 350, false, false));
        Tienda.add(new cTienda("Monje", "monjebn", 450, false, false));
        Tienda.add(new cTienda("Samurai", "samuraibn", 550, false, false));

        Logros.clear();
        Logros.add(new cLogro(1, "Completa 1 leccion", 0, "logro1", 100, 25));
        Logros.add(new cLogro(2, "Completa 3 lecciones", 0, "logro2", 150, 50));
        Logros.add(new cLogro(3, "Completa 10 operaciones seguidas sin fallar", 0, "logro3", 150, 50));
        Logros.add(new cLogro(4, "Completa 1 tema", 0, "logro4", 300, 250));
        Logros.add(new cLogro(5, "Completa 50 operaciones seguidas sin fallar", 0, "logro5", 350, 300));
        Logros.add(new cLogro(6, "Completa todos los temas", 0, "logro6", 1000, 1000));

        AdaptadorTemas adaptador = new AdaptadorTemas(this, Temas);

        ListView lst = (ListView)findViewById(R.id.listCustom);
        lst.setAdapter(adaptador);

        // deshabilita el titol
        getSupportActionBar().hide();

        cargarPreferencias();

        FotosComprado.clear();
        for(int i = 0; i < Tienda.size(); i++) {
            if(Tienda.get(i).getComprado() == true) {
                FotosComprado.add(Tienda.get(i));
            }
        }

        ImageView img = findViewById(R.id.imgAyuda);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Guia.class);

                startActivity(intent);
            }

        });

        guardarDatos();

    }

    private void cargarPreferencias() {
        SharedPreferences preferencias = getSharedPreferences("game", Context.MODE_PRIVATE);

//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        int lecAcSumFacil = preferencias.getInt("lecAcSumFacil", 0);
        int lecAcSumNormal = preferencias.getInt("lecAcSumNormal", 0);
        int lecAcSumDificil = preferencias.getInt("lecAcSumDificil", 0);

        int lecAcResFacil = preferencias.getInt("lecAcResFacil", 0);
        int lecAcResNormal = preferencias.getInt("lecAcResNormal", 0);
        int lecAcResDificil = preferencias.getInt("lecAcResDificil", 0);

        int lecAcMultFacil = preferencias.getInt("lecAcMultFacil", 0);
        int lecAcMultNormal = preferencias.getInt("lecAcMultNormal", 0);
        int lecAcMultDificil = preferencias.getInt("lecAcMultDificil", 0);

        int lecAcDivFacil = preferencias.getInt("lecAcDivFacil", 0);
        int lecAcDivNormal = preferencias.getInt("lecAcDivNormal", 0);
        int lecAcDivDificil = preferencias.getInt("lecAcDivDificil", 0);
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        boolean difBloquSumFacil = preferencias.getBoolean("difBloquSumFacil", false);
        boolean difBloquSumNormal = preferencias.getBoolean("difBloquSumNormal", true);
        boolean difBloquSumDificil = preferencias.getBoolean("difBloquSumDificil", true);

        boolean difBloquResFacil = preferencias.getBoolean("difBloquResFacil", false);
        boolean difBloquResNormal = preferencias.getBoolean("difBloquResNormal", true);
        boolean difBloquResDificil = preferencias.getBoolean("difBloquResDificil", true);

        boolean difBloquMultFacil = preferencias.getBoolean("difBloquMultFacil", false);
        boolean difBloquMultNormal = preferencias.getBoolean("difBloquMultNormal", true);
        boolean difBloquMultDificil = preferencias.getBoolean("difBloquMultDificil", true);

        boolean difBloquDivFacil = preferencias.getBoolean("difBloquDivFacil", false);
        boolean difBloquDivNormal = preferencias.getBoolean("difBloquDivNormal", true);
        boolean difBloquDivDificil = preferencias.getBoolean("difBloquDivDificil", true);
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        String usuarioNombre = preferencias.getString("usuarioNombre", "Sin nombre");
        int usuarioMonedas = preferencias.getInt("usuarioMonedas", 0);
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        int logro1Progres = preferencias.getInt("logro1Progres", 0);
        int logro2Progres = preferencias.getInt("logro2Progres", 0);
        int logro3Progres = preferencias.getInt("logro3Progres", 0);
        int logro4Progres = preferencias.getInt("logro4Progres", 0);
        int logro5Progres = preferencias.getInt("logro5Progres", 0);
        int logro6Progres = preferencias.getInt("logro6Progres", 0);
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        boolean tiendaComprado1 = preferencias.getBoolean("tiendaComprado1", true);
        boolean tiendaComprado2 = preferencias.getBoolean("tiendaComprado2", false);
        boolean tiendaComprado3 = preferencias.getBoolean("tiendaComprado3", false);
        boolean tiendaComprado4 = preferencias.getBoolean("tiendaComprado4", false);
        boolean tiendaComprado5 = preferencias.getBoolean("tiendaComprado5", false);
        boolean tiendaComprado6 = preferencias.getBoolean("tiendaComprado6", false);
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        boolean tiendaEquipado1 = preferencias.getBoolean("tiendaEquipado1", true);
        boolean tiendaEquipado2 = preferencias.getBoolean("tiendaEquipado2", false);
        boolean tiendaEquipado3 = preferencias.getBoolean("tiendaEquipado3", false);
        boolean tiendaEquipado4 = preferencias.getBoolean("tiendaEquipado4", false);
        boolean tiendaEquipado5 = preferencias.getBoolean("tiendaEquipado5", false);
        boolean tiendaEquipado6 = preferencias.getBoolean("tiendaEquipado6", false);
//---------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------\\
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        Temas.get(0).getDificultad().get(0).getLecciones().get(0).setLeccionActual(lecAcSumFacil);
        Temas.get(0).getDificultad().get(1).getLecciones().get(0).setLeccionActual(lecAcSumNormal);
        Temas.get(0).getDificultad().get(2).getLecciones().get(0).setLeccionActual(lecAcSumDificil);

        Temas.get(1).getDificultad().get(0).getLecciones().get(0).setLeccionActual(lecAcResFacil);
        Temas.get(1).getDificultad().get(1).getLecciones().get(0).setLeccionActual(lecAcResNormal);
        Temas.get(1).getDificultad().get(2).getLecciones().get(0).setLeccionActual(lecAcResDificil);

        Temas.get(2).getDificultad().get(0).getLecciones().get(0).setLeccionActual(lecAcMultFacil);
        Temas.get(2).getDificultad().get(1).getLecciones().get(0).setLeccionActual(lecAcMultNormal);
        Temas.get(2).getDificultad().get(2).getLecciones().get(0).setLeccionActual(lecAcMultDificil);

        Temas.get(3).getDificultad().get(0).getLecciones().get(0).setLeccionActual(lecAcDivFacil);
        Temas.get(3).getDificultad().get(1).getLecciones().get(0).setLeccionActual(lecAcDivNormal);
        Temas.get(3).getDificultad().get(2).getLecciones().get(0).setLeccionActual(lecAcDivDificil);
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        Temas.get(0).getDificultad().get(0).setBloqueado(difBloquSumFacil);
        Temas.get(0).getDificultad().get(1).setBloqueado(difBloquSumNormal);
        Temas.get(0).getDificultad().get(2).setBloqueado(difBloquSumDificil);

        Temas.get(1).getDificultad().get(0).setBloqueado(difBloquResFacil);
        Temas.get(1).getDificultad().get(1).setBloqueado(difBloquResNormal);
        Temas.get(1).getDificultad().get(2).setBloqueado(difBloquResDificil);

        Temas.get(2).getDificultad().get(0).setBloqueado(difBloquMultFacil);
        Temas.get(2).getDificultad().get(1).setBloqueado(difBloquMultNormal);
        Temas.get(2).getDificultad().get(2).setBloqueado(difBloquMultDificil);

        Temas.get(3).getDificultad().get(0).setBloqueado(difBloquDivFacil);
        Temas.get(3).getDificultad().get(1).setBloqueado(difBloquDivNormal);
        Temas.get(3).getDificultad().get(2).setBloqueado(difBloquDivDificil);
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        User.get(0).setNombre(usuarioNombre);
        User.get(0).setMonedas(usuarioMonedas);
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        Logros.get(0).setProgreso(logro1Progres);
        Logros.get(1).setProgreso(logro2Progres);
        Logros.get(2).setProgreso(logro3Progres);
        Logros.get(3).setProgreso(logro4Progres);
        Logros.get(4).setProgreso(logro5Progres);
        Logros.get(5).setProgreso(logro6Progres);
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        Tienda.get(0).setComprado(tiendaComprado1);
        Tienda.get(1).setComprado(tiendaComprado2);
        Tienda.get(2).setComprado(tiendaComprado3);
        Tienda.get(3).setComprado(tiendaComprado4);
        Tienda.get(4).setComprado(tiendaComprado5);
        Tienda.get(5).setComprado(tiendaComprado6);
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        Tienda.get(0).setEquipado(tiendaEquipado1);
        Tienda.get(1).setEquipado(tiendaEquipado2);
        Tienda.get(2).setEquipado(tiendaEquipado3);
        Tienda.get(3).setEquipado(tiendaEquipado4);
        Tienda.get(4).setEquipado(tiendaEquipado5);
        Tienda.get(5).setEquipado(tiendaEquipado6);
    }

    private void guardarDatos() {

        SharedPreferences preferencias = getSharedPreferences("game", Context.MODE_PRIVATE);
//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        int lecAcSumFacil = Temas.get(0).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcSumNormal = Temas.get(0).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcSumDificil = Temas.get(0).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcResFacil = Temas.get(1).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcResNormal = Temas.get(1).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcResDificil = Temas.get(1).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcMultFacil = Temas.get(2).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcMultNormal = Temas.get(2).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcMultDificil = Temas.get(2).getDificultad().get(2).getLecciones().get(0).getLeccionActual();

        int lecAcDivFacil = Temas.get(3).getDificultad().get(0).getLecciones().get(0).getLeccionActual();
        int lecAcDivNormal = Temas.get(3).getDificultad().get(1).getLecciones().get(0).getLeccionActual();
        int lecAcDivDificil = Temas.get(3).getDificultad().get(2).getLecciones().get(0).getLeccionActual();
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        boolean difBloquSumFacil = Temas.get(0).getDificultad().get(0).getBloqueado();
        boolean difBloquSumNormal = Temas.get(0).getDificultad().get(1).getBloqueado();
        boolean difBloquSumDificil = Temas.get(0).getDificultad().get(2).getBloqueado();

        boolean difBloquResFacil = Temas.get(1).getDificultad().get(0).getBloqueado();
        boolean difBloquResNormal = Temas.get(1).getDificultad().get(1).getBloqueado();
        boolean difBloquResDificil = Temas.get(1).getDificultad().get(2).getBloqueado();

        boolean difBloquMultFacil = Temas.get(2).getDificultad().get(0).getBloqueado();
        boolean difBloquMultNormal = Temas.get(2).getDificultad().get(1).getBloqueado();
        boolean difBloquMultDificil = Temas.get(2).getDificultad().get(2).getBloqueado();

        boolean difBloquDivFacil = Temas.get(3).getDificultad().get(0).getBloqueado();
        boolean difBloquDivNormal = Temas.get(3).getDificultad().get(1).getBloqueado();
        boolean difBloquDivDificil = Temas.get(3).getDificultad().get(2).getBloqueado();
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        String usuarioNombre = User.get(0).getNombre();
        int usuarioMonedas = User.get(0).getMonedas();
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        int logro1Progres = Logros.get(0).getProgreso();
        int logro2Progres = Logros.get(1).getProgreso();
        int logro3Progres = Logros.get(2).getProgreso();
        int logro4Progres = Logros.get(3).getProgreso();
        int logro5Progres = Logros.get(4).getProgreso();
        int logro6Progres = Logros.get(5).getProgreso();
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        boolean tiendaComprado1 = Tienda.get(0).getComprado();
        boolean tiendaComprado2 = Tienda.get(1).getComprado();
        boolean tiendaComprado3 = Tienda.get(2).getComprado();
        boolean tiendaComprado4 = Tienda.get(3).getComprado();
        boolean tiendaComprado5 = Tienda.get(4).getComprado();
        boolean tiendaComprado6 = Tienda.get(5).getComprado();
//---------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Equipado <----------------------------------------------\\
        boolean tiendaEquipado1 = Tienda.get(0).getEquipado();
        boolean tiendaEquipado2 = Tienda.get(1).getEquipado();
        boolean tiendaEquipado3 = Tienda.get(2).getEquipado();
        boolean tiendaEquipado4 = Tienda.get(3).getEquipado();
        boolean tiendaEquipado5 = Tienda.get(4).getEquipado();
        boolean tiendaEquipado6 = Tienda.get(5).getEquipado();
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

    class AdaptadorTemas extends ArrayAdapter<cTema> {

        private Context context;

        public AdaptadorTemas(Context context, ArrayList<cTema> datos) {
            super(context, R.layout.activity_mostrar_lista_main, datos);
            this.context = context;
        }

        public View getView(final int positionTema, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_lista_main, null);

            final cTema tema = (cTema) getItem(positionTema);

            ImageView img = (ImageView) item.findViewById(R.id.imgTema);
            String nombre = tema.getNombre().toLowerCase();
            String src = "@drawable/" + nombre;
            src = src.toLowerCase();
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

            TextView tvTema = (TextView) item.findViewById(R.id.tvNombre);
            tvTema.setText(tema.getNombre());

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, Tema.class);

                    /*intent.putExtra("tema", tema);
                    intent.putExtra("user", User);
                    intent.putExtra("tienda", ImgPerfil);
                    intent.putExtra("stema", sTema);*/
                    sTema = tema.getNombre();
                    intent.putExtra("positionTema", positionTema);
                    intent.putExtra("destino", destino);
                    intent.putExtra("origen", origen);

                    startActivityForResult(intent, 12345);
                }
            });

            return (item);
        }
    }

}
