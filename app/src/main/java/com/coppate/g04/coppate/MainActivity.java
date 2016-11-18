package com.coppate.g04.coppate;

import android.app.ActivityOptions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final int NOTIF_ALERTA_ID = 1;
    // boton que redirige a la actitiy crear evento
    Button probando;
    Button btn_crear_evento;
    Button otrosUsuarios;
    TextView txt_mis_eventos;
    android.support.v7.app.AlertDialog alert;

    // tomamos los ListView para mostrar los eventos cercanos, de otros y propios
    ListView eventos_de_otros;
    ListView lista_mis_eventos;
    ListView lista_eventos_a_participar;

    // generamos las listas para los eventos de otros (el listview que muestra)
    ArrayList<String> lista_eventos_mios = new ArrayList<String>();
    ArrayList<String> lista_eventos_cercanos = new ArrayList<String>();
    ArrayList<String> lista_eventos_otros_participo = new ArrayList<String>();

    Funciones funciones;

    // creamos los adaptadores para los listview
    ArrayAdapter<String> adaptador;
    ArrayAdapter<String> participo;
    ArrayAdapter<String> adapt_eventos_otros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        funciones = new Funciones(getApplicationContext());

        // cargamos los datos en pantalla de los textview,listview,y botones
        // los textos
        txt_mis_eventos = (TextView) findViewById(R.id.txtview_mis_eventos);
        // los botones
        btn_crear_evento = (Button) findViewById(R.id.ma_crear_evento);
        otrosUsuarios = (Button) findViewById(R.id.btnOtrosUser);
        probando = (Button)findViewById(R.id.probando);
        //los listview
        lista_mis_eventos = (ListView) findViewById(R.id.ma_listar_mis_eventos);
        lista_eventos_a_participar = (ListView) findViewById(R.id.ma_eventos_donde_participo);
        eventos_de_otros = (ListView) findViewById(R.id.ma_lv_eventos_cercanos);

        final android.support.v7.app.AlertDialog alert = null;      //Comprueba si está activado el gps
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertNoGps();
        }

        if (AccessToken.getCurrentAccessToken() == null) {  //si no hay sesion iniciada pasa a la pantalla de login
            goLoginScreen();
        }else{
            try {
                Usuario.getInstance().setId_usuario(Profile.getCurrentProfile().getId());

                Usuario.getInstance().setNombre(Profile.getCurrentProfile().getFirstName());
                Usuario.getInstance().setApellido(Profile.getCurrentProfile().getLastName());
                Usuario.getInstance().setEmail(Profile.getCurrentProfile().getName());
                Usuario.getInstance().setFecha_nacimiento("2016-11-11");
                Usuario.getInstance().setId_sexo(1);
                Usuario.getInstance().setAlias(Profile.getCurrentProfile().getName());
                Usuario.getInstance().setFoto("URI de la foto");   //Profile.getCurrentProfile().getProfilePictureUri(128,128).toString()
                funciones.mostrarToastLargo("Hola :" + Usuario.getInstance().getNombre() + " " + Usuario.getInstance().getApellido());
            }catch (Exception e){
                funciones.mostrarToastCorto(e.toString());
            }
        }

        Resources res = getResources();

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", getResources().getDrawable(R.drawable.tab1));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", getResources().getDrawable(R.drawable.tab2));
        tabs.addTab(spec);


        tabs.setCurrentTab(2);

        // inicializamos los arraylist que nos serviran para cargar los datos de los adaptadores para los listview
        lista_eventos_mios = new ArrayList<String>();
        lista_eventos_cercanos = new ArrayList<String>();
        lista_eventos_otros_participo = new ArrayList<String>();

        // cargamos datos de prueba que tienen que venir de la BD, tanto los propios como los otros
        lista_eventos_cercanos.add("Evento cercano: 1");
        lista_eventos_mios.add("Eventos mios: 1");
        lista_eventos_otros_participo.add("Eventos participo: 1");

        // llamammos a las funciones que listan los eventos cercanos, de otros y mios
        mostrarEventosCercanos(lista_eventos_cercanos);
        mostrarMisEventos(lista_eventos_mios);
        mostrarEventosEnQueParticipo(lista_eventos_otros_participo);


        btn_crear_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCrearEvento(v);
            }
        });

        otrosUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPerfilOtrosUsuarios(v);
            }
        });

        probando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPerfil();
                //goAcercaDe();
            }
        });

        // hacemos que tome los eventos de la base de datos
        listarEventoPorOwner();
    }

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

    private void mostrarMisEventos(ArrayList<String> array_mis_eventos){
        try {
            adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
            // hay que hacer que el "ARRAYADAPTER lo tome de la base de datos y luego recorrerlo, ahora esta a manopla
            /*String objeto = "Creo Evento: ";
            String num = "";
            for(int i = 0;i<30;i++){
                adaptador.add(objeto+i);
            }*/
            // recorremos la lista de los eventos que trae de la base de datos y los cargamos en el adaptadorde mis eventos
            for (int i = 0;i<array_mis_eventos.size();i++){
                adaptador.add(array_mis_eventos.get(i));
            }
            lista_mis_eventos.setAdapter(adaptador);
             /* obtenemos el id del evento actual que se selecciona de la lista y lo cargamos en el activity descripcion de evento*/
            lista_mis_eventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    entrarAMiEvento(view);
                }
            });
            // termina el listview
        }catch (Exception e){
            funciones.mostrarToastCorto("No fue posible cargar la lista de eventos");
        }
    }

    private void entrarAMiEvento(View v){
        try {
            Integer id_evento = lista_mis_eventos.getPositionForView(v);
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

    private void mostrarEventosEnQueParticipo(ArrayList<String> array_eventos_participo){
        try {
            participo = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
            // hay que hacer que el "ARRAYADAPTER lo tome de la base de datos y luego recorrerlo, ahora esta a manopla
            /*String objeto = "Participo en Evento: ";
            String num = "";
            for(int i = 4;i>0;i--){
                participo.add(objeto+i);
            }*/
            for (int i = 0;i<array_eventos_participo.size();i++){
                participo.add(array_eventos_participo.get(i));
            }

            lista_eventos_a_participar.setAdapter(participo);
             // obtenemos el id del evento actual que se selecciona de la lista y lo cargamos en el activity descripcion de evento
            lista_eventos_a_participar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    entrarADescripcionEvento(view);
                }
            });
            // termina el listview
        }catch (Exception e){
            funciones.mostrarToastCorto("No fue posible cargar la lista de eventos");
        }
    }

    private void entrarADescripcionEvento(View v){
        try {
            Integer id_evento = lista_eventos_a_participar.getPositionForView(v);
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

    private void mostrarEventosCercanos(ArrayList<String> array_eventos_cercanos){

        try {
            // inicializamos el adapter para mostrar los eventos cercanos
            adapt_eventos_otros = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
            // le cargamos los datos que deben traerlos de la base de datos.
            for (int i = 0; i<array_eventos_cercanos.size(); i++){
                adapt_eventos_otros.add(array_eventos_cercanos.get(i));
            }
            eventos_de_otros.setAdapter(adapt_eventos_otros);
            // hacemos que el listview tome el item que seleccionamos
            eventos_de_otros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    entrarAEvento(view);
                }
            });
        }catch (Exception e){
            funciones.mostrarToastCorto("Se ha producido un error al cargar los eventos cercanos");
        }
    }

    private void entrarAEvento(View v){
        Integer id_evento_cercano = eventos_de_otros.getPositionForView(v);
        Intent intent_entrar_a_evento = new Intent(MainActivity.this, InvitacionEvento.class);
        // le pasamos el parametro del id de evento para tomarlo en la pantalla de INVITACION A EVENTO y mostrar los datos necesarios
        intent_entrar_a_evento.putExtra("ID_evento", id_evento_cercano);
        // creamos la animacion de deslizamiento
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
        // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
        startActivity(intent_entrar_a_evento, bndlanimation);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }*/

    private void AlertNoGps() { //Funcion que da la opción de activar el gps
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
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
        funciones.playSoundPickButton();
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

    public void getUsuarioPorID(String id_user){
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(new JsonObjectRequest(
                    Request.Method.GET,
                    Constantes.GET_USER_BY_ID+"id_usuario="+id_user,
                    null,
                    new Response.Listener<JSONObject>(){

                        @Override
                        public void onResponse(JSONObject response) {
                            respuestaGetUsuarioPorID(response);
                        }
                    }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    funciones.mostrarToastCorto(("Se ha producido un Error Volley: " + error.getMessage()));
                        }
                }
                ) {
                @Override
               public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Accept", "application/json");
                    return headers;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8" + getParamsEncoding();
                }
        });

    }

    private void respuestaGetUsuarioPorID(JSONObject response){
        // y ahora??
    }


    public void listarEventoPorOwner() {

        try {
            // Actualizar datos en el servidor
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                    // creo una nueva instancia del singleton de volley
                    // y le asigno a la constante getbyowner (dentro obtenereventoporowner) y le paso en el archivo ese
                    // el parametro que espera (dentro de la funcion isset()

                    // despues le paso en el singleton de usuario get instance get idusuario
                    // por ahora se puede hardcodear el id de usuario para hacer las pruebas
                    new JsonObjectRequest(
                            Request.Method.GET,
                        /* asi es la consulta real, yo lo modifico para hacer la prueba - Constantes.GET_BY_OWNER + "?idOwner="+ Usuario.getInstance().getIdUsuario()*/
                            Constantes.GET_BY_OWNER + "?idOwner=" + Usuario.getInstance().getId_usuario(), /*esto deberia traer el usuario 1 que yo cree*/

                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Procesar la respuesta del servidor

                                    procesarRespuesta(response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    funciones.mostrarToastCorto(("Se ha producido un Error Volley: " + error.getMessage()));
                                }
                            }

                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("Accept", "application/json");
                            return headers;
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8" + getParamsEncoding();
                        }
                    }
            );
        }catch (Exception e){
            funciones.mostrarToastCorto("Error al captar los datos");
        }

    }

    /**
     * Procesa la respuesta obtenida desde el sevidor
     *
     * @param response Objeto Json
     */

    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String nombre = response.getString("mensaje");


            /*try {
                funciones.mostrarToastCorto("Hasta aca funca.. se rompe en el getJSONObject");

                funciones.mostrarToastCorto(Usuario.getInstance().getId_usuario().toString());
                funciones.mostrarToastCorto(response.getJSONObject("descrip_evento").toString());
            }catch (Exception e){
                funciones.mostrarToastCorto("Se ha producido un error al cargar la descripcion del evento");
                funciones.mostrarToastCorto(response.toString()+" :siempre me da codigo 2");
            }*/

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    funciones.mostrarToastLargo("Estado: "+estado + " - Mensaje: "+ nombre);
                    // esto tendria que mostrar lo que yo defino en el json con descripcion de evento
                    txt_mis_eventos.setText(response.getString("id_owner"));
                    /*Toast.makeText(
                            getApplicationContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show();*/
                    // Enviar código de éxito
                    //getApplicationContext().setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    //getApplicationContext().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    funciones.mostrarToastLargo("Estado: "+estado + " - Mensaje: "+ nombre);
                    /*Toast.makeText(
                            getApplicationContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show();*/
                    // Enviar código de falla
                    //getApplicationContext().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    //View.getContext().finish();
                    break;
                case "3":
                    // Mostrar mensaje
                    // esta siempre devolviendo el codigo 3 de estado y no tengo idea de por que

                    funciones.mostrarToastLargo("Estado: "+estado + " - Mensaje: "+ nombre);
                    // esto tendria que mostrar lo que yo defino en el json con descripcion de evento
                    txt_mis_eventos.setText(response.getString("id_owner"));
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            funciones.mostrarToastCorto("Se ha producido un error");
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // no me funciona el codigo generico aun
            //funciones.mostrarDialogoPregunta("Salir","Estas seguro capo?","Si, pa", "no, pa");


            // creamos un nuevo dialogo de alerta y lo seteamos en transparente
            Dialog customDialog = null;
            customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
            // con este tema personalizado evitamos los bordes por defecto
            //customDialog = new Dialog(this,R.style.AppTheme);
            //deshabilitamos el título por defecto
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //obligamos al usuario a pulsar los botones para cerrarlo
            customDialog.setCancelable(false);
            //establecemos el contenido de nuestro dialog para poder visualizarlo en pantalla
            customDialog.setContentView(R.layout.dialog);

            // creamos y mostramos el titulo en pantalla
            TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
            titulo.setText("Salir de la aplicación");

            // creamos y mostramos el mensaje que deseamos visualizar
            TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
            contenido.setText("Estás seguro que deseas salir de la aplicación?");

            // seteamos el texto del boton afirmativo como el texto del propio boton
            Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
            aceptar.setText("Si, muy seguro");
            aceptar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    // si el usuario presiona en aceptar, se cierra la aplicación
                    MainActivity.this.finish();

                }
            });

            // seteamos el texto del boton negativo como el texto del propio boton
            Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);
            cancelar.setText("No, estoy Coppado");
            final Dialog finalCustomDialog1 = customDialog;
            cancelar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    // si el usuario presiona en aceptar, se cierra el cuadro y vuele al activity que lo llamo.
                    finalCustomDialog1.dismiss();
                }
            });
            customDialog.show();

            /*final Dialog finalCustomDialog = customDialog;
            ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    finalCustomDialog.dismiss();
                }
            });*/


            /*DialogoPersonalizado dp = new DialogoPersonalizado();
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
                    .show();*/
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


