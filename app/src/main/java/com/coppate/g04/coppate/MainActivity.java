package com.coppate.g04.coppate;

import android.app.ActivityOptions;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Evento[] eventos;
    private Gson gson = new Gson();
    private static final int NOTIF_ALERTA_ID = 1;
    // boton que redirige a la actitiy crear evento
    Button probando;
    Button btn_crear_evento;
    Button otrosUsuarios;
    TextView txt_mis_eventos;

    Funciones funciones;

    ArrayAdapter<String> adaptador;
    ArrayAdapter<String> participo;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.button) {
            LoginManager.getInstance().logOut();
            goLoginScreen(); //cierra sesion y dirige a la pantalla de login
            //return true;
        }
        if (id == R.id.perfil) {
            goPerfil();

        }

        if (id == R.id.mm_acerca_de) {
            goAcercaDe();
        }


        return super.onOptionsItemSelected(item);
    }

    public void goAcercaDe() {
        Intent acerca = new Intent(MainActivity.this, AcercaDe.class);
        startActivity(acerca);
    }  //dirige a la pantalla acerca de


    private Notification getBigTextStyle(Notification.Builder builder, Usuario user) {

        Notification notif = new Notification.Builder(getApplicationContext())
                .setContentTitle("Invitacion a Evento")
                .setContentText(user.getNombre() + " ha creado un evento y quiere invitarte")
                .setSmallIcon(R.drawable.icono32)
                //.setLargeIcon(R.drawable.icono64R.drawable.icono64)
                .setStyle(new Notification.BigTextStyle()
                        .bigText("Haga click en esta notificacion si desea obtener mas información o participar del evento creado por " + user.getNombre()))
                .build();

        return notif;
    }

    public void pruebaDescripcion(){
        
        /*
        NotificationManager notificador = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notificacion = new Notification(android.R.drawable.sym_call_missed, "Llamada perdidad", System.currentTimeMillis());
        Intent notificacionIntent = new Intent(getApplicationContext(), DescripcionEvento.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificacionIntent, 0);
        notificacion.setLatestEventInfo(getApplicationContext(), "Llamada perdida", "Tienes una llamada perdida del número 666777888.", contentIntent);

        notificador.notify(NOTIF_ALERTA_ID, notificacion);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {  //si no hay sesion iniciada pasa a la pantalla de login
            goLoginScreen();
        }

        funciones = new Funciones(getApplicationContext());

        txt_mis_eventos = (TextView) findViewById(R.id.txtview_mis_eventos);
        probando = (Button)findViewById(R.id.probando);

        probando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPerfil();
            }
        });
        funciones.mostrarToastCorto("Toast Point 1");
        listarEventoPorOwner();
        try {
            // ##############################################
            // creamos un list view para darle funcionalidad a la app
            final ListView lista_mis_eventos;
            lista_mis_eventos = (ListView) findViewById(R.id.ma_listar_mis_eventos);

            // hay que hacer que el "ARRAYADAPTER lo tome de la base de datos y luego recorrerlo, ahora esta a manopla

/*            String objeto = "Creo Evento: ";
            String num = "";
            for(int i = 0;i<30;i++){
                adaptador.add(objeto+i);
            }*/


            adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

            funciones.mostrarToastCorto("Toast previo al crash");
            if (this.eventos == null){
                funciones.mostrarToastCorto("Eventos is null");
            }
            funciones.mostrarToastCorto("Size of eventos: " + this.eventos.length);
//            for (Evento evento: eventos) {
//                adaptador.add(evento.getUbicacion());
//            }

            String textoPrueba = eventos[0].getNombre();
            funciones.mostrarToastCorto("Listar: " + textoPrueba);
            adaptador.add(textoPrueba);

            lista_mis_eventos.setAdapter(adaptador);

             /* obtenemos el id del evento actual que se selecciona de la lista y lo cargamos en el activity descripcion de evento*/
            lista_mis_eventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Integer id_evento = lista_mis_eventos.getPositionForView(view);
                        Intent intent_descripcion = new Intent(MainActivity.this, EditarEvento.class);

                        // le pasamos el parametro del ide de evento para tomarlo en la pantalla de DESCRIPCION DE EVENTO y mostrar los datos necesarios
                        intent_descripcion.putExtra("ID_evento", id_evento);
                        // creamos la animacion de deslizamiento
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
                        // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
                        startActivity(intent_descripcion, bndlanimation);
                    }catch (Exception e){
                        funciones.mostrarToastCorto("Error al cargar la siguiente pantalla");
                    }
                }
            });


            // termina el listview
        }catch (Exception e){
            funciones.mostrarToastCorto("No fue posible cargar la lista de eventos");
        }


        try {
            // ##############################################
            // creamos un list view para darle funcionalidad a la app
            // en la que podamos movernos por una lista dentro de los posibles eventos y hacer click sobre alguno de ellos
            final ListView lista_eventos_a_participar;
            lista_eventos_a_participar = (ListView) findViewById(R.id.ma_eventos_donde_participo);

            participo = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

            // hay que hacer que el "ARRAYADAPTER lo tome de la base de datos y luego recorrerlo, ahora esta a manopla

            String objeto = "Participo en Evento: ";
            String num = "";
            for(int i = 4;i>0;i--){
                participo.add(objeto+i);
            }
            lista_eventos_a_participar.setAdapter(participo);


             /* obtenemos el id del evento actual que se selecciona de la lista y lo cargamos en el activity descripcion de evento*/
            lista_eventos_a_participar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Integer id_evento = lista_eventos_a_participar.getPositionForView(view);
                        Intent intent_descripcion = new Intent(MainActivity.this, DescripcionEvento.class);

                        // le pasamos el parametro del ide de evento para tomarlo en la pantalla de DESCRIPCION DE EVENTO y mostrar los datos necesarios
                        intent_descripcion.putExtra("ID_evento", id_evento);
                        // creamos la animacion de deslizamiento
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
                        // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
                        startActivity(intent_descripcion, bndlanimation);
                    }catch (Exception e){
                        funciones.mostrarToastCorto("Error al cargar la siguiente pantalla");
                    }
                }
            });


            // termina el listview
        }catch (Exception e){
            funciones.mostrarToastCorto("No fue posible cargar la lista de eventos");
        }

        btn_crear_evento = (Button) findViewById(R.id.ma_crear_evento);
        btn_crear_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCrearEvento(v);
            }
        });

        /*comentar = (Button) findViewById(R.id.opinion);
        comentar.setOnClickListener(new View.OnClickListener() {*/
        otrosUsuarios = (Button) findViewById(R.id.btnOtrosUser);
        otrosUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPerfilOtrosUsuarios(v);
            }
        });


        Resources res = getResources();

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", getResources().getDrawable(R.drawable.icomis));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", getResources().getDrawable(R.drawable.icobuscarr));
        tabs.addTab(spec);


        tabs.setCurrentTab(1);


        //listarEventoPorOwner();
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);  //dirige a la pantalla de login y ejecuta solo esa
    }

    private void goCrearEvento(View v){
        Intent intent = new Intent(MainActivity.this, CrearEvento.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(intent,bndlanimation);//pasa a pantalla de Crear Evento
        funciones.playSoundPickButton(v);
    }

    private void goPerfilOtrosUsuarios(View v){
        /*Intent intent = new Intent(MainActivity.this, OpinionUsuario.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(intent,bndlanimation);*/
        Intent intent = new Intent(MainActivity.this, PerfilOtrosUser.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(intent,bndlanimation);
    }

    private void goMapa() {
        Intent intent = new Intent(this, MapsActivityCercanos.class);
        startActivity(intent);
    }  //dirige a la pantalla de mapa

    public void irMapa(View view) {
        goMapa();
    }

    private void goBuscar() {
        Intent intent = new Intent(this, BuscarEvento.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(intent,bndlanimation);
    }  //dirige a la pantalla buscar

    public void irBuscar(View view) {
        goBuscar();
    }

    private void goPerfil() {
        Intent intent = new Intent(this, perfil.class);
        startActivity(intent);
    }  //dirige a la pantalla perfil

    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void listarEventoPorOwner() {
        // Petición GET
        VolleySingleton.
                getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_BY_OWNER + "?idOwner=" + Usuario.getInstance().getId_usuario(),
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
                                        //Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }

                        )
                );
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("eventos");
                    // Parsear con Gson
                    this.eventos = gson.fromJson(mensaje.toString(), Evento[].class);
                    funciones.mostrarToastLargo("Toast procesarResp: " + String.valueOf(eventos.length));
                    funciones.mostrarToastLargo(eventos[0].getNombre());
                    break;
                case "2": // FALLIDO
                    //String mensaje2 = response.getString("mensaje");
                    break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir")
                    .setIcon(R.drawable.icono32)
                    .setMessage("Estás seguro que deseas salir de la aplicación?")
                    .setNegativeButton("No, estoy coppado", null)//sin listener
                    .setPositiveButton("Si, muy seguro", new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //salimos de la aplicacion al pulsar en la afirmacion
                            MainActivity.this.finish();
                        }
                    })
                    .show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);

    }
    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(MainActivity.this);
        dialogo2.setTitle("Salir de la aplicacion");
        dialogo2.setMessage("¿Esta seguro que desea salir de la aplicacion?");
        dialogo2.setCancelable(false);
        dialogo2.setIcon(R.drawable.icono32);
        dialogo2.setPositiveButton("Si, muy seguro", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo2, int id) {
                // actualizar campos de la base de datos
                finish();
            }
        });
        dialogo2.setNegativeButton("No, quiero seguir coppandome", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo2, int id) {

            }
        });
        dialogo2.show();

        MainActivity.this.finish();*/


}


