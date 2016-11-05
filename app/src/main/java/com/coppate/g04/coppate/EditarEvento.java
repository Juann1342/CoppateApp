package com.coppate.g04.coppate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditarEvento extends Activity {

    Button ubicacion;
    FloatingActionButton editar_evento;
    Button cancelar_evento;
    Button guardar_cambios;
    EditText descripcion_evento;
    EditText nombre_evento;
    EditText lugar_encuentro;
    EditText costo_evento;
    EditText cupo_min;
    EditText cupo_max;
    EditText edad_desde;
    EditText edad_hasta;

    Funciones funciones;

    Integer id_evento;
    Boolean guardado = null;

    AlertDialog.Builder dialogo;


    // creamos un bundle que nos recuperara los extras que hayamos puesto en la otra actividad
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        guardado = false;

        dialogo = new AlertDialog.Builder(getApplicationContext());

        b = getIntent().getExtras();

        id_evento = b.getInt("ID_evento");

        /* evento_en_bd = getEvento(id_evento, id_usuario);
        *
        *  descripcion_evento.setText(evento_en_bd.getCampoDescripcion);
        *  y asi con los de abajo..
        *
            nombre_evento = (EditText)findViewById(R.id.ee_nombre_evento);
            lugar_encuentro = (EditText)findViewById(R.id.ee_lugar);
            costo_evento = (EditText)findViewById(R.id.ee_costo);
            cupo_max = (EditText)findViewById(R.id.ee_cupoMax);
            cupo_min = (EditText)findViewById(R.id.ee_cupoMin);
            edad_desde = (EditText)findViewById(R.id.ee_edadDesde);
            edad_hasta = (EditText)findViewById(R.id.ee_edadHasta);

        *
        *
        * */

        funciones = new Funciones(getApplicationContext());

        ubicacion = (Button)findViewById(R.id.ee_ubicacion_mapa);
        editar_evento = (FloatingActionButton)findViewById(R.id.ee_editar_evento);
        cancelar_evento = (Button)findViewById(R.id.ee_cancelar_evento);
        guardar_cambios = (Button)findViewById(R.id.ee_guardar_cambios);
        descripcion_evento = (EditText) findViewById(R.id.ee_descripcion_evento);
        nombre_evento = (EditText)findViewById(R.id.ee_nombre_evento);
        lugar_encuentro = (EditText)findViewById(R.id.ee_lugar);
        costo_evento = (EditText)findViewById(R.id.ee_costo);
        cupo_max = (EditText)findViewById(R.id.ee_cupoMax);
        cupo_min = (EditText)findViewById(R.id.ee_cupoMin);
        edad_desde = (EditText)findViewById(R.id.ee_edadDesde);
        edad_hasta = (EditText)findViewById(R.id.ee_edadHasta);

        editar_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_cambios.setVisibility(View.VISIBLE);
                editar_evento.setEnabled(true);
                cancelar_evento.setEnabled(true);
                descripcion_evento.setEnabled(true);
                nombre_evento.setEnabled(true);
                lugar_encuentro.setEnabled(true);
                costo_evento.setEnabled(true);
                cupo_min.setEnabled(true);
                cupo_max.setEnabled(true);
                edad_hasta.setEnabled(true);
                edad_desde.setEnabled(true);
                //guardar_cambios.setEnabled(true);
            }
        });

        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_mapa = new Intent(EditarEvento.this,MapsActivityCercanos.class);
                startActivity(intent_mapa);
            }
        });

        cancelar_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EditarEvento.this);
                dialogo1.setTitle("Cancelar Evento");
                dialogo1.setMessage("¿Esta seguro de Cancelar el Evento?");
                dialogo1.setCancelable(false);
                dialogo1.setIcon(R.drawable.icono32);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        // actualizar campos de la base de datos de fecha y cerrar evento
                        finish();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });

        guardar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo2 = new AlertDialog.Builder(EditarEvento.this);
                dialogo2.setTitle("Guardar Datos");
                dialogo2.setMessage("¿Esta seguro que desea actualizar los valores al evento?");
                dialogo2.setCancelable(false);
                dialogo2.setIcon(R.drawable.icono32);
                dialogo2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo2, int id) {
                        // actualizar campos de la base de datos
                        finish();
                    }
                });
                dialogo2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo2, int id) {

                    }
                });
                dialogo2.show();
            }
        });

    }

    public void onBackPressed() {
        EditarEvento.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
