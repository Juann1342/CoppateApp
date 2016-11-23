package com.coppate.g04.coppate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CrearEvento extends AppCompatActivity {

    private static final int CONTACT_PICKER_RESULT = 1000;

    private static final Double PUBLIC_STATIC_DOUBLE_LATITUD = 0.0;
    private static final Double PUBLIC_STATIC_DOUBLE_LONGITUD = 0.0;

    private static final int REQUEST_MAPA= 1;
    //definimos los componenetes que va a tener la clase y que despues pueden llamarse para operar
    EditText nombre_evento;
    EditText lugar_evento;
  //  Button fecha_evento;
    EditText cupo_min;
    EditText cupo_max;
    EditText edad_desde;
    EditText edad_hasta;
    EditText costo;
    Button btn_crear;
    //Button btn_invitar_contactos;
    Spinner spn_tipo_evento;
    Spinner spn_sexo;
    Spinner spn_contactos;
    Button mapaCrear;

    EditText descripcion;

    Bundle bundle;

    private Gson gson = new Gson();

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // estos strings los tenemos que tomar de la BD, son solo de pruebas
    String[] opciones_sexo = {"Masculino", "Femenino", "Indiferente"};
    String[] opciones_tipo = {"Social", "Privado"};
    String[] contacts_selected = {""};
    //ArrayList<String> arrayContact = new ArrayList<String>();
    String sexo = "";
    String tipo = "";

    String id_event;

    Double latitud;
    Double longitud;

    private static final int RESULT_PICK_CONTACT = 85500;
    final int CONTACT_PICK_REQUEST = 1000;
    private TextView textView1;
    private TextView textView2;
    private Boolean invita_contactos;
    private Boolean guarda_evento;

    //Creamos la lista para tomar los contactos
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;

    // creamos variable para almacenar la lista de contactos y la inicializamos
    ArrayList<Contact> ce_selectedContacts = new ArrayList<Contact>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    Funciones funciones;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();


        /* tratando de darle movimiento entre las pantallas
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);*/
        setContentView(R.layout.activity_crear_evento);

        funciones = new Funciones(getApplicationContext());
        invita_contactos = false;
        guarda_evento = false;

        textView1 = (TextView) findViewById(R.id.ce_lista_coppados);
        //   textView2 = (TextView) findViewById(R.id.ce_lista_coppados2);

        //defino las posibles opciones que pueden tener los desplegables (esto despues lo tengo que reemplazar con lo que traigo
        // de la base de datos)

        spn_tipo_evento = (Spinner) findViewById(R.id.ce_spnTipoEvento);
        spn_sexo = (Spinner) findViewById(R.id.ce_spnSexoEvento);
        spn_contactos = (Spinner) findViewById(R.id.ce_spn_contactos_activos);

        nombre_evento = (EditText) findViewById(R.id.ce_nameEvent);
        lugar_evento = (EditText) findViewById(R.id.ce_lugarEncuentro);
      //  fecha_evento = (Button) findViewById(R.id.ce_fecha_evento);
        cupo_min = (EditText) findViewById(R.id.ce_cupoMin);
        cupo_max = (EditText) findViewById(R.id.ce_cupoMax);
        edad_desde = (EditText) findViewById(R.id.ce_edadDesde);
        edad_hasta = (EditText) findViewById(R.id.ce_edadHasta);
        costo = (EditText) findViewById(R.id.ce_costo_evento);

        descripcion = (EditText)findViewById(R.id.ce_descripEvent);

        btn_crear = (Button) findViewById(R.id.ce_btnCrearEvento);
        //btn_invitar_contactos = (Button) findViewById(R.id.ce_acceso_a_contactos);
        mapaCrear = (Button) findViewById(R.id.crearEventoMapa);


        btnDatePicker = (Button) findViewById(R.id.ce_fecha_evento);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);


        // inicializamos el boton que invita contactos en falso para que no se puedan invitar contactos sin haber creado el evento
        //btn_invitar_contactos.setEnabled(invita_contactos);

        // creamos una variable de tipo LISTA DE CONTACTO
        //final ContactsList contacts_list = new ContactsList();

        // definimos los adaptadores de las listas que tenemos en este caso las opciones de sexo y tipo de evento
        ArrayAdapter<String> adapt_sexo = new ArrayAdapter<String>(CrearEvento.this, android.R.layout.simple_spinner_item, opciones_sexo);
        ArrayAdapter<String> adapt_tipo = new ArrayAdapter<String>(CrearEvento.this, android.R.layout.simple_spinner_item, opciones_tipo);
        ArrayAdapter<String> adapt_contacts = new ArrayAdapter<String>(CrearEvento.this, android.R.layout.simple_spinner_item, contacts_selected);

        //final ArrayAdapter<String> adapt_contacts = new ArrayAdapter<String>(CrearEvento.this, android.R.layout.simple_spinner_item, contacts_selected);
        final ArrayList<ContactsList> contactos = new ArrayList<ContactsList>();

        //seteamos los adaptadores a nuestra Lista desplegable
        spn_sexo.setAdapter(adapt_sexo);
        spn_tipo_evento.setAdapter(adapt_tipo);
        spn_contactos.setAdapter(adapt_contacts);


        // se guarda el tipo que se ha seleccionado en la variable TIPO para una utilizacion posterior
        spn_tipo_evento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tipo = "1";
                        break;

                    case 1:
                        tipo = "2";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no hay funcion de nada.. algo siempre se selecciona
            }
        });

        spn_sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sexo = "1";
                        break;

                    case 1:
                        sexo = "2";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no hay funcion de nada.. algo siempre se selecciona
            }
        });




        //Direcciona al mapa
        mapaCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearEvento.this, MapsActivity.class);
                startActivityForResult(intent,REQUEST_MAPA);  //pasa a pantalla de Crear Evento
            }
        });




        //al hacer click en el boton, nos mostrara el texto en pantalla que se ha creado un evento
        // con el nombre que hemos ingresado
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*if (getActualListContact().size() > 0) {
                    // para mostrarlo en pantalla tipo mensaje se usa Toast
                    funciones.mostrarToastLargo((Usuario.getInstance().getNombre() + " ha creado el evento: " + nombre_evento.getText() + " de tipo: " + tipo + " solo para: " + sexo + " y se ha enviado una notificacion a los contactos seleccionados.."));
                } else {
                    funciones.mostrarToastCorto((Usuario.getInstance().getNombre() + " ha creado el evento: " + nombre_evento.getText() + " de tipo: " + tipo + " solo para: " + sexo));
                    funciones.playSoundGotaAgua(arg0);
                }*/

                if (latitud != null) {
                    if (!invita_contactos) {

                        Dialog customDialog = null;
                        customDialog = new Dialog(CrearEvento.this, R.style.Theme_Dialog_Translucent);
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
                        titulo.setText("Invitar Contactos");

                        // creamos y mostramos el mensaje que deseamos visualizar
                        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                        contenido.setText("¿Deseas invitar Contactos al evento?");

                        // seteamos el texto del boton afirmativo como el texto del propio boton
                        Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
                        aceptar.setText("Si, invitar");
                        aceptar.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                try {
                                    guardarEvento();
                                /* if(ok en la base de datos al guardar el evento) hace lo que sigue*/
                                    obtenerIdEventoCreado();
                                    guarda_evento = true;
                                    invita_contactos = true;
                                /* hay que hacer una funcion que tome el codigo del evento ese para pasarlo
                                a goInvitarContactos()*/
                                    goInvitarContactos(id_event, invita_contactos);
                                } catch (Exception e) {
                                    funciones.mostrarToastCorto("Se ha producido un error al guardar el evento en la base de datos");
                                }

                            }
                        });

                        // seteamos el texto del boton negativo como el texto del propio boton
                        Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);
                        cancelar.setText("No");
                        final Dialog finalCustomDialog1 = customDialog;
                        cancelar.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                try {
                                    guardarEvento();
                                    guarda_evento = true;
                                    goInvitarContactos("nada", invita_contactos);
                                    //funciones.playSoundGotaAgua(arg0);
                                } catch (Exception e) {
                                    funciones.mostrarToastCorto("Se ha producido un error al guardar el evento en la base de datos");
                                }
                                // si el usuario presiona en aceptar, se cierra el cuadro y vuele al activity que lo llamo.
                                //finalCustomDialog1.dismiss();
                            }
                        });
                        customDialog.show();


                    /*AlertDialog.Builder dialogo = new AlertDialog.Builder(CrearEvento.this);
                    dialogo.setTitle("Invitar Contactos");
                    dialogo.setMessage("¿Desea invitar Contactos al evento?");
                    dialogo.setIcon(R.drawable.icono32);
                    dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo, int id) {
                            // actualizar campos de la base de datos de fecha y cancelar asistencia (dar de baja al usuario en el evento)
                            // y comprobar si en main se elimina el evento del listview

                            try{
                                guardarEvento();
                                /* if(ok en la base de datos al guardar el evento) hace lo que sigue*/
                                /*guarda_evento = true;
                                invita_contactos = true;*/
                                /* hay que hacer una funcion que tome el codigo del evento ese para pasarlo
                                a goInvitarContactos()*/
                                /*goInvitarContactos("Evento de Prueba", invita_contactos);
                            }catch (Exception e){
                                funciones.mostrarToastCorto("Se ha producido un error al guardar el evento en la base de datos");
                            }
                        }
                    });
                    dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo, int id) {
                            try {
                                guardarEvento();
                                guarda_evento = true;
                                goInvitarContactos("nada",invita_contactos);
                                //funciones.playSoundGotaAgua(arg0);
                            }catch (Exception e){
                                funciones.mostrarToastCorto("Se ha producido un error al guardar el evento en la base de datos");
                            }
                            //finish();
                        }
                    });
                    dialogo.show();*/

                        // al pulsar en crear evento se lanza el sonido de la gota de agua. no me es posible ahora corregirlo
                        // para que luego de aceptar los botones SI o NO se lance el sonido...

                    }
                }
                else{
                    funciones.mostrarToastLargo("Primero debe seleccionar una ubicación en el mapa");
                }

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //------------Fecha y Hora

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (v == btnDatePicker) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog (CrearEvento.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnTimePicker) {

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(CrearEvento.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    txtTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }

            }
        });

    }

    private void goInvitarContactos(String codigo, Boolean invitar){
        funciones.playSoundGotaAgua();
        if(invitar){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Te invito al evento que he organizado a través de Coppate. Introduce el siguente código en el buscador de eventos. Codigo: " + codigo + " :Si aún no tienes la aplicación puedes encontrarla disponible en Play Store");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            finish();
        }else {
            CrearEvento.this.finish();
            overridePendingTransition(R.anim.reingreso, R.anim.nothing);
        }
    }

    private String obtenerIdEventoCreado(){
        VolleySingleton.
                getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_ULTIMO_EVENTO_CREADO + "?idOwner=" + Usuario.getInstance().getId_usuario(),
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject respuesta) {
                                        // Procesar la respuesta Json
                                        id_event = getIdEvento(respuesta);
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
        return id_event;

    }

    /*
    *  @param response Objeto Json con la respuesta
    */
    private String getIdEvento(JSONObject respuesta) {
        try {
            // Obtener atributo "estado"
            String estado = respuesta.getString("estado");

            funciones.mostrarToastCorto("estado:" + estado);


            switch (estado) {
                case "1": // EXITO
                    //funciones.mostrarToastCorto(estado);
                    JSONArray mensaje = respuesta.getJSONArray("evento");
                    //JSONObject objeto = response.getJSONObject("evento");
                    //funciones.mostrarToastCorto("Mensaje: "+objeto.toString());

                    // utilizamos el singleton de MisEventos y le pasamos el evento actual
                    MisEventos.getInstance().setEvento(gson.fromJson(mensaje.toString(), Evento[].class));

                    id_event = MisEventos.getInstance().getEvento()[0].getId_evento();
                    break;
                case "2": // FALLIDO
                    funciones.mostrarToastCorto("Se ha producido un error al solicitar los datos del evento");
                    ;
                    break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
            funciones.mostrarToastCorto("Fallo general en la conexion");
        }
        return id_event;
    }

    /**
     * Guarda un evento en la DB
     * Acá puede empezar a romper.
     * Comienza zona de rotura.
     */
    public void guardarEvento() {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("nombre", nombre_evento.getText().toString());
        map.put("id_owner", Usuario.getInstance().getId_usuario());
        map.put("edad_min", edad_desde.getText().toString());
        map.put("edad_max", edad_hasta.getText().toString());
        map.put("cupo_min", cupo_min.getText().toString());
        map.put("cupo_max", cupo_max.getText().toString());
        map.put("costo", costo.getText().toString());
        map.put("fecha_inicio", funciones.getFechaActual()); //Solo para probar.
        map.put("fecha_fin", "2016-10-17"); //Solo para probar.
        map.put("foto", "NULL");
        map.put("ubicacion", lugar_evento.getText().toString());
        try {
            map.put("latitud", latitud.toString());
            map.put("longitud", longitud.toString());
        }catch (Exception e){
            funciones.mostrarToastCorto("No fue posible cargar los datos de la posicion en gps");
            map.put("latitud", "latitud de prueba tras fallo");
            map.put("longitud", "longitud de prueba tras fallo");
        }
        map.put("id_categoria", "1");
        map.put("desc_evento", descripcion.getText().toString());
        map.put("id_sexo", "1");
        map.put("id_estado", "1"); // 1=activo 2=finalizado 3=cancelado 4=en pausa


        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT,
                        jobject,
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
                                funciones.mostrarToastCorto(("Error Volley: " + error.getMessage()));
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
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            getApplicationContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    //getApplicationContext().setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    //getApplicationContext().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getApplicationContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    //getApplicationContext().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    //View.getContext().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Guarda un evento en la DB
     * Fin de la zona de rotura.
     */


    /* ##############################################

        TENGO QUE SEGUIR VIENDO EL ACTIVITY RESULT
       QUE NO FUNCIONA

       BUENO, AHORA SI FUNCIONA, SOLO TENGO QUE GUARDAR LOS DATOS DE CONTACTOS SELECCIONADOS

     ##################################################*/

    //este sera el resultado del activity de invitar contactos
    // por ahora no esta funcionando
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //mostrarToast("Probando si toma algo de datos");
        if (requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK) {
            //mostrarToast("Funciona.?");
            try {
                //ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
                ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("ContactosSeleccionados");
                try {
                    //mostrarToast("AL menos algo");
                    String display = "";
                    if (selectedContacts.isEmpty()) {
                        funciones.mostrarToastCorto("No se han seleccionado contactos");
                    }
                    Integer tama = selectedContacts.size();
                    funciones.mostrarToastCorto("El tamaño total de los contactos seleccionados es: " + tama.toString());
                    for (int i = 0; i < selectedContacts.size(); i++) {

                        display += (i + 1) + ". " + selectedContacts.get(i).toString() + "\n";

                    }
                    guardarContactosEnVariable(selectedContacts);
                } catch (Exception e) {
                    funciones.mostrarToastCorto("Casi.");
                }
                //mostrarToast(selectedContacts.get(1).toString());
            } catch (Exception e) {
                funciones.mostrarToastCorto("No, no funciona");
            }
            //contactsDisplay.setText("Selected Contacts : \n\n"+display);
        }
        if (requestCode == REQUEST_MAPA && resultCode == RESULT_OK){
            try {
                latitud = data.getDoubleExtra("latitud",PUBLIC_STATIC_DOUBLE_LATITUD);
                longitud= data.getDoubleExtra("longitud",PUBLIC_STATIC_DOUBLE_LONGITUD);

                funciones.mostrarToastLargo("latitud"+latitud.toString()+"\n"+"longitud"+longitud.toString());
            }catch (Exception e) {
                funciones.mostrarToastCorto("ALGO PASÓ");
            }
        }
        /*mostrarToast("Probando..");
        if (resultCode == RESULT_OK) {
            try{
                ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("Contactos");
                mostrarToast(selectedContacts.get(2).toString());
            }catch (Exception e){
                mostrarToast("Fallo algo, vamo' a ver que");
            }
        }*/
    }

    private ArrayList<Contact> getActualListContact() {
        return ce_selectedContacts;
    }

    private void setListContacts(ArrayList<Contact> contactos) {

        ce_selectedContacts = contactos;

    }

    private void guardarContactosEnVariable(ArrayList<Contact> contacts) {
        this.setListContacts(contacts);
    }


    /* por ahora no funciona... seria para mostrar un TOAST con mas estilo

    private void textIconToast(String message, int icon, int duration) {
        Toast toast = Toast.makeText(getApplicationContext(), message, duration);
        View textView = toast.getView();
        LinearLayout lay = new LinearLayout(getApplicationContext());
        lay.setOrientation(LinearLayout.HORIZONTAL);
        ImageView view = new ImageView(getApplicationContext());
        view.setImageResource(icon);
        lay.addView(view);
        lay.addView(textView);
        toast.setView(lay);
        toast.show();
    }*/
    public void pickContact() {

        /* este pedacito de codigo funciona, lo reemplazo para ver si funciona algo mas
        // creamos un StartActivityForResult para mostrar los contactos
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);   */

        try {
            Intent pantalla = new Intent(this, InvitarContactos.class);
            pantalla.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(pantalla);
        } catch (Exception e) {
            funciones.mostrarToastCorto("Error, algo paso y ni idea que");
        }

    }

    public void obtenerDatos() {
        AsyncHttpClient client = new AsyncHttpClient();
        // este es la url del server que no puede ser localhost porque se usa en el emulador de la app
        String url = "http://192.168.1.1/GetData.php";

        RequestParams parametros = new RequestParams();
        parametros.put("Edad", 18);

        /* ################################################################
        corregir el error de header para pasar los parametros
        ################################################################
         */

        /*
        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });*/
    }


    // este listener del boton ya no tiene utilidad
        /*btn_invitar_contactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent pantalla = new Intent(getApplicationContext(), InvitarContactos.class);

                //mostrarToast("Hasta aca llegamos");
                try {
                    pantalla.putExtra("Contactos", contactos);
                    setResult(RESULT_OK, pantalla);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
                    CrearEvento.this.startActivityForResult(pantalla, CONTACT_PICK_REQUEST,bndlanimation);
                    funciones.playSoundPickButton(v);

                    /* Apply our splash exit (fade out) and main
                        entry (fade in) animation transitions. */
    //overridePendingTransition(R.anim.mainfadein,R.anim.splashfadeout);
    //     } catch (Exception e) {
    //        funciones.mostrarToastCorto(e.toString());
    // }
            /*}
        });*/

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    /*public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CrearEvento Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }*/
    public void onBackPressed() {
        CrearEvento.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CrearEvento Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }
}

