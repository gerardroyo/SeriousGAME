package com.seriousgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

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

            if(destino == 5) {
                Intent intent = new Intent(Perfil.this, Tienda.class);

                destino = 3;
                intent.putExtra("destino", destino);
                intent.putExtra("origen", origenEnv);

                startActivityForResult(intent, 12345);
            }
            else if(destino == 3) {
                Intent intent = new Intent(Perfil.this, Tienda.class);

                destino = 3;
                intent.putExtra("destino", destino);
                intent.putExtra("origen", origenEnv);

                startActivityForResult(intent, 12345);
            }
            else if(destino == 1) {
                Intent intent = new Intent();

                destino = 0;
                intent.putExtra("destino", destino);
                setResult(RESULT_OK, intent);
                finish();
            } else if(destino != 2) {
                finish();
            }
        }
        if (requestCode == 12347 && resultCode == RESULT_OK) {
            finish();
            startActivity(getIntent());
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

        AdaptadorLogros adaptadorLogros = new AdaptadorLogros(this, MainActivity.Logros);

        ListView lst = (ListView)findViewById(R.id.listCustom2);
        lst.setAdapter(adaptadorLogros);

        ImageView img = (ImageView)findViewById(R.id.imageView2);

        for (int i = 0; i < MainActivity.FotosComprado.size(); i++){
            if (MainActivity.FotosComprado.get(i).getEquipado() == true){
                String nombre = MainActivity.FotosComprado.get(i).getNombre();
                String src = "@drawable/" + nombre;
                src = src.toLowerCase();
                img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

                TextView tv = (TextView)findViewById(R.id.tvNombreUser);
                tv.setText(MainActivity.User.get(0).getNombre());
            }
        }

        img = findViewById(R.id.imageView2);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, CambiarFoto.class);

                startActivityForResult(intent, 12347);

            }

        });

        img = findViewById(R.id.imReset);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad;
                ad = new AlertDialog.Builder(Perfil.this).create();
                ad.setTitle("¿Estas seguro?");
                ad.setMessage("Se borrará todo el proceso y empezarás de nuevo.");

                ad.setButton(AlertDialog.BUTTON_POSITIVE,"SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        resetDatos();

                        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });

                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // no fem res.
                    }
                });
                ad.show();

            }

        });

        TextView tvNombre = (TextView) findViewById(R.id.tvNombreUser);
        tvNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                AlertDialog ad;

                ad = new AlertDialog.Builder(Perfil.this).create();
                ad.setTitle("Nuevo nombre: ");

                // Ahora forzamos que aparezca el editText
                final EditText edtValor = new EditText(Perfil.this);
                ad.setView(edtValor);

                ad.setButton(AlertDialog.BUTTON_POSITIVE,"ACTUALITZAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        String valorString = edtValor.getText().toString();
                        MainActivity.User.get(0).setNombre(valorString);
                        finish();
                        startActivity(getIntent());

                    }
                });

                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // no fem res.
                        //MonedaSeleccionada = false;
                    }
                });

                ad.show();

                // el Show es asíncrono.

            }

        });

        tvNombre = (TextView) findViewById(R.id.tvLogros);
        tvNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdaptadorLogros adaptadorLogros = new AdaptadorLogros(Perfil.this, MainActivity.Logros);

                ListView lst = (ListView)findViewById(R.id.listCustom2);
                lst.setAdapter(adaptadorLogros);

                TextView tv = (TextView)findViewById(R.id.tvLogros);
                tv.setTextColor(Color.parseColor("#0CB7F2"));

                tv = (TextView)findViewById(R.id.tvLader);
                tv.setTextColor(Color.parseColor("#757575"));

                tv = (TextView)findViewById(R.id.tvColor1);
                tv.setBackgroundColor(Color.parseColor("#0CB7F2"));

                tv = (TextView)findViewById(R.id.tvColor2);
                tv.setBackgroundColor(Color.parseColor("#FFFFFF"));

            }

        });

        tvNombre = (TextView) findViewById(R.id.tvLader);
        tvNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdaptadorLader adaptadorLader = new AdaptadorLader(Perfil.this, MainActivity.Temas);

                ListView lst = (ListView)findViewById(R.id.listCustom2);
                lst.setAdapter(adaptadorLader);

                TextView tv = (TextView)findViewById(R.id.tvLogros);
                tv.setTextColor(Color.parseColor("#757575"));

                tv = (TextView)findViewById(R.id.tvLader);
                tv.setTextColor(Color.parseColor("#0CB7F2"));

                tv = (TextView)findViewById(R.id.tvColor1);
                tv.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tv = (TextView)findViewById(R.id.tvColor2);
                tv.setBackgroundColor(Color.parseColor("#0CB7F2"));

            }

        });

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

                    destino = 3;
                    intent.putExtra("destino", destino);
                    intent.putExtra("origen", origenEnv);

                    startActivityForResult(intent, 12345);
                }
            }

        });

        guardarDatos();

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

    class AdaptadorLader extends ArrayAdapter<cTema> {

        private Context context;

        public AdaptadorLader(Context context, ArrayList<cTema> datos) {
            super(context, R.layout.activity_mostrar_lader, datos);
            this.context = context;
        }

        public View getView(final int positionLogro, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_mostrar_lader, null);

            final cTema PuntosTema = (cTema) getItem(positionLogro);

            ImageView img = (ImageView) item.findViewById(R.id.imgTema);
            String nombre = PuntosTema.getNombre().toLowerCase();
            String src = "@drawable/" + nombre;
            src = src.toLowerCase();
            img.setImageResource(getResources().getIdentifier(src, "drawable", getOpPackageName()));

            TextView tvTema = (TextView) item.findViewById(R.id.tvPuntos);
            tvTema.setText(PuntosTema.getPuntos() + " pts.");

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

    private void resetDatos() {

        SharedPreferences preferencias = getSharedPreferences("game", Context.MODE_PRIVATE);
//----------------------------------------------> Leccion Actual <----------------------------------------------\\
        int lecAcSumFacil = 0;
        int lecAcSumNormal = 0;
        int lecAcSumDificil = 0;

        int lecAcResFacil = 0;
        int lecAcResNormal = 0;
        int lecAcResDificil = 0;

        int lecAcMultFacil = 0;
        int lecAcMultNormal = 0;
        int lecAcMultDificil = 0;

        int lecAcDivFacil = 0;
        int lecAcDivNormal = 0;
        int lecAcDivDificil = 0;
//----------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Dificultad Bloqueado <----------------------------------------------\\
        boolean difBloquSumFacil = false;
        boolean difBloquSumNormal = true;
        boolean difBloquSumDificil = true;

        boolean difBloquResFacil = false;
        boolean difBloquResNormal = true;
        boolean difBloquResDificil = true;

        boolean difBloquMultFacil = false;
        boolean difBloquMultNormal = true;
        boolean difBloquMultDificil = true;

        boolean difBloquDivFacil = false;
        boolean difBloquDivNormal = true;
        boolean difBloquDivDificil = true;
//--------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Nombre/Monedas Usuario <----------------------------------------------\\
        String usuarioNombre = "Sin nombre";
        int usuarioMonedas = 10000;
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Logros Progreso <----------------------------------------------\\
        int logro1Progres = 0;
        int logro2Progres = 0;
        int logro3Progres = 0;
        int logro4Progres = 0;
        int logro5Progres = 0;
        int logro6Progres = 0;
//---------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Comprado <----------------------------------------------\\
        boolean tiendaComprado1 = true;
        boolean tiendaComprado2 = false;
        boolean tiendaComprado3 = false;
        boolean tiendaComprado4 = false;
        boolean tiendaComprado5 = false;
        boolean tiendaComprado6 = false;
//---------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------> Tienda Equipado <----------------------------------------------\\
        boolean tiendaEquipado1 = true;
        boolean tiendaEquipado2 = false;
        boolean tiendaEquipado3 = false;
        boolean tiendaEquipado4 = false;
        boolean tiendaEquipado5 = false;
        boolean tiendaEquipado6 = false;
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

