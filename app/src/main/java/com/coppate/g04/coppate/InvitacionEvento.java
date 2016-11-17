package com.coppate.g04.coppate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InvitacionEvento extends AppCompatActivity {

    ImageView foto_perfil;
    ImageView calif_total;
    TextView puntuacion;
    TextView descrip_evento;
    TextView usuario_creador;
    Button copparse;
    Button mostrar_ubicacion;
    Button rechazar;
    Funciones funciones;
    String latitud;
    String longitud;

    Integer id_evento;

    // creamos un bundle que nos recuperara los extras que hayamos puesto en la otra actividad
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_evento);

        b = getIntent().getExtras();

        id_evento = b.getInt("ID_evento");

        latitud = "(getLatituddelabasededatos())";
        longitud = "(getLongituddelabasededatos())";

        funciones = new Funciones(getApplicationContext());

        foto_perfil = (ImageView) findViewById(R.id.ie_foto_perfil);
        descrip_evento = (TextView) findViewById(R.id.ie_descrip_evento);
        usuario_creador = (TextView) findViewById(R.id.ie_usuario_creador);
        copparse = (Button) findViewById(R.id.ie_copparse);
        rechazar = (Button) findViewById(R.id.ie_rechazar);
        mostrar_ubicacion = (Button) findViewById(R.id.ie_ubicacion_mapa);
        calif_total = (ImageView) findViewById(R.id.ie_calif_total);
        puntuacion = (TextView) findViewById(R.id.ie_puntuacion);

        foto_perfil.setImageResource(R.drawable.foto_perfil);
        descrip_evento.setText("Descripcion del evento: "+id_evento.toString());
        usuario_creador.setText("Usuario XXXXX, obtenido a traves del id evento: "+id_evento.toString());
        calif_total.setImageResource(R.drawable.estrella_vacia);
        puntuacion.setText("Puntuacion total del usuario XXXX a traves del id evento: "+id_evento.toString());


        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //funciones.mostrarToastCorto("Se ha rechazado la invitación a este Evento");
                finish();
                overridePendingTransition(R.anim.reingreso, R.anim.nothing);
            }
        });

        copparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                funciones.playSoundPickButton(v);

                // hay actualizar en la base de datos una persona que se ha sumado al evento.
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(InvitacionEvento.this);
                dialogo1.setTitle("Notificacion");
                dialogo1.setMessage("Se ha sumado al evento");
                dialogo1.setCancelable(false);
                dialogo1.setIcon(R.drawable.icono32);
                dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        // actualizar campos de la base de datos de fecha y cancelar asistencia (dar de baja al usuario en el evento)
                        finish();
                        overridePendingTransition(R.anim.reingreso, R.anim.nothing);
                    }
                });
                /*dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });*/
                dialogo1.show();
            }
        });

        mostrar_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funciones.playSoundPickButton(v);
                // hay que pasarle los datos de latitud y longitud y hacer que se muestre en el mapa
                Intent mostrar_ubic = new Intent(InvitacionEvento.this,MapsActivityCercanos.class);
                startActivity(mostrar_ubic);
            }
        });

        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation ampliar;
                ampliar = AnimationUtils.loadAnimation(InvitacionEvento.this,R.anim.ampliar);
                ampliar.reset();
                foto_perfil.startAnimation(ampliar);

                goPerfilUsuario();

                /*Intent intent_opinion = new Intent(InvitacionEvento.this,OpinionUsuario.class);

                //creamos la nueva actividad de opinion y le cargamos la animacion
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
                startActivity(intent_opinion,bndlanimation);*/
            }
        });

    }

    private void goPerfilUsuario(){
        /*Intent intent = new Intent(MainActivity.this, OpinionUsuario.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(intent,bndlanimation);*/
        Intent opiniones = new Intent(InvitacionEvento.this, OpinionUsuario.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(opiniones,bndlanimation);
    }

    public void onBackPressed() {
        InvitacionEvento.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
