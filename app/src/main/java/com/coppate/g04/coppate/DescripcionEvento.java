package com.coppate.g04.coppate;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DescripcionEvento extends AppCompatActivity {

    ImageView foto_perfil;
    ImageView calif_total;
    TextView puntuacion;
    TextView descrip_evento;
    TextView usuario_creador;
    Button cancelar_asistencia;
    Button mostrar_ubicacion;
    Funciones funciones;
    String latitud;
    String longitud;

    Integer id_evento;

    // creamos un bundle que nos recuperara los extras que hayamos puesto en la otra actividad
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_evento);

        b = getIntent().getExtras();

        id_evento = b.getInt("ID_evento");

        latitud = "(getLatituddelabasededatos())";
        longitud = "(getLongituddelabasededatos())";

        funciones = new Funciones(getApplicationContext());

        foto_perfil = (ImageView) findViewById(R.id.de_foto_perfil);
        descrip_evento = (TextView) findViewById(R.id.de_descrip_evento);
        usuario_creador = (TextView) findViewById(R.id.de_usuario_creador);
        cancelar_asistencia = (Button) findViewById(R.id.de_cancelar_asistencia);
        mostrar_ubicacion = (Button) findViewById(R.id.de_ubicacion_mapa);
        calif_total = (ImageView) findViewById(R.id.de_calif_total);
        puntuacion = (TextView) findViewById(R.id.de_puntuacion);

        foto_perfil.setImageResource(R.drawable.foto_perfil);
        descrip_evento.setText("Descripcion del evento: "+id_evento.toString()+ " (hay que traerlo de la base de datos y cambiar el num de id de evento por el nombre del evento");
        usuario_creador.setText("Usuario XXXXX, obtenido a traves del id evento: "+id_evento.toString()+ " (hay que traerlo de la base de datos)");
        calif_total.setImageResource(R.drawable.estrella_vacia);
        puntuacion.setText("Puntuacion total del usuario XXXX a traves del id evento: "+id_evento.toString()+ " (de la base de datos)");



        cancelar_asistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // hay actualizar en la base de datos una persona que se ha sumado al evento.
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(DescripcionEvento.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Esta seguro de Cancelar su Asistencia a este Evento?");
                dialogo1.setCancelable(true);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        // actualizar campos de la base de datos de fecha y cancelar asistencia (dar de baja al usuario en el evento)
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

        mostrar_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DescripcionEvento.this,MapsActivityCercanos.class);
                startActivity(intent);
            }
        });

        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation ampliar;
                ampliar = AnimationUtils.loadAnimation(DescripcionEvento.this,R.anim.ampliar);
                ampliar.reset();
                foto_perfil.startAnimation(ampliar);

                Intent intent_opinion = new Intent(DescripcionEvento.this,OpinionUsuario.class);

                //creamos la nueva actividad de opinion y le cargamos la animacion
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
                startActivity(intent_opinion,bndlanimation);
            }
        });

    }

    private void actualizarPantalla(ImageView foto,TextView descripcion_evento,TextView usuario_creador,ImageView calificacion,TextView puntuacion){

        this.foto_perfil = foto;
        this.descrip_evento = descripcion_evento;
        this.usuario_creador = usuario_creador;
        this.calif_total = calificacion;
        this.puntuacion = puntuacion;
    }
}

