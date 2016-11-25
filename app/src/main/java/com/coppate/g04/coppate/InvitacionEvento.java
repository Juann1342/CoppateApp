package com.coppate.g04.coppate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InvitacionEvento extends AppCompatActivity {

    ImageView foto_perfil;
    ImageView calif_total;
    TextView puntuacion;
    TextView descrip_evento;
    TextView usuario_creador;
    TextView nombre_evento;
    Button copparse;
    Button mostrar_ubicacion;
    Button rechazar;
    Funciones funciones;
    String latitud;
    String longitud;

    Integer id_evento;

    // creamos un bundle que nos recuperara los extras que hayamos puesto en la otra actividad
    Bundle b;

    private Gson gson = new Gson();

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
        nombre_evento = (TextView)findViewById(R.id.ie_nombre_evento);
        copparse = (Button) findViewById(R.id.ie_copparse);
        rechazar = (Button) findViewById(R.id.ie_rechazar);
        mostrar_ubicacion = (Button) findViewById(R.id.ie_ubicacion_mapa);
        calif_total = (ImageView) findViewById(R.id.ie_calif_total);
        puntuacion = (TextView) findViewById(R.id.ie_puntuacion);

        foto_perfil.setImageResource(R.drawable.foto_perfil);
        descrip_evento.setText("Descripcion: "+id_evento.toString());
        usuario_creador.setText("Usuario XXXXX, obtenido a traves del id evento: "+id_evento.toString());
        calif_total.setImageResource(R.drawable.estrella_vacia);
        puntuacion.setText("Puntuacion total del usuario XXXX a traves del id evento: "+id_evento.toString());

        obtenerDatosEventoPorID();

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

                funciones.playSoundPickButton();

                // hay actualizar en la base de datos una persona que se ha sumado al evento.
                Dialog customDialog = null;
                customDialog = new Dialog(InvitacionEvento.this,R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setCancelable(false);
                customDialog.setContentView(R.layout.message);
                TextView titulo = (TextView) customDialog.findViewById(R.id.message_titulo);
                titulo.setText("Asistencia a Evento");

                // creamos y mostramos el mensaje que deseamos visualizar
                TextView contenido = (TextView) customDialog.findViewById(R.id.message_contenido);
                contenido.setText("Te has sumado a este evento");

                // seteamos el texto del boton afirmativo como el texto del propio boton
                Button aceptar = (Button) customDialog.findViewById(R.id.message_aceptar);
                aceptar.setText("Coppado");
                aceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        InvitacionEvento.this.finish();
                        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

                    }
                });
                customDialog.show();

                /*AlertDialog.Builder dialogo1 = new AlertDialog.Builder(InvitacionEvento.this);
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
                /*dialogo1.show();*/
            }
        });

        mostrar_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funciones.playSoundPickButton();
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

    //el la siguiente clase cambiamos la fuente
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    private void goPerfilUsuario(){
        /*Intent intent = new Intent(MainActivity.this, OpinionUsuario.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(intent,bndlanimation);*/
        Intent opiniones = new Intent(InvitacionEvento.this, OpinionUsuario.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(opiniones,bndlanimation);
    }

    private void obtenerDatosEventoPorID(){
        //funciones.mostrarToastCorto("Id_evento en Invitacion: "+id_evento.toString());
        VolleySingleton.
                getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_BY_ID + "?idEvento=" + id_evento,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        funciones.mostrarToastCorto("Debug1: Error en obtenerDatosEventoPorID");
                                    }
                                }

                        )
                );
    }

    /*
    *  @param response Objeto Json con la respuesta
    */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    //funciones.mostrarToastCorto(estado);
                    JSONArray mensaje = response.getJSONArray("evento");

                    // utilizamos el singleton de MisEventos y le pasamos el evento actual de otro usuario que nos paso o vimos el codigo
                    MisEventos.getInstance().setEventoOtroUsuario(gson.fromJson(mensaje.toString(), Evento[].class));
                    //funciones.mostrarToastLargo("Toast procesarResp: " + String.valueOf(MisEventos.getInstance().getEventos().length));
                    /*usuario_creador = (TextView) findViewById(R.id.ie_usuario_creador);
                    // fijarse eso
                    // ver el usuario actual
                    calif_total = (ImageView) findViewById(R.id.ie_calif_total);
                    puntuacion = (TextView) findViewById(R.id.ie_puntuacion);

                    // foto del usuario del perfil
                    foto_perfil.setImageResource(R.drawable.foto_perfil);
                    usuario_creador.setText("Usuario XXXXX, obtenido a traves del id evento: "+id_evento.toString());
                    puntuacion.setText("Puntuacion total del usuario XXXX a traves del id evento: "+id_evento.toString());*/

                    nombre_evento.setText("Evento: "+MisEventos.getInstance().getEventoOtroUsuario()[0].getNombre());
                    descrip_evento.setText("Descripcion: "+MisEventos.getInstance().getEventoOtroUsuario()[0].getDesc_evento());

                    break;
                case "2": // FALLIDO
                    funciones.mostrarToastCorto("No hemos encontrado el evento que buscas");
                    nombre_evento.setText("Evento no encontrado..");
                    descrip_evento.setText("");
                    break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
            funciones.mostrarToastCorto("Se ha producido un error al buscar el evento con el codigo :"+id_evento.toString()+" que ingresaste..");
        }

    }

    public void onBackPressed() {
        InvitacionEvento.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
