package com.coppate.g04.coppate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coppate.g04.coppate.Constantes;
import com.coppate.g04.coppate.VolleySingleton;
import com.coppate.g04.coppate.Usuario;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;


public class CrearEvento extends Activity {

    private static final int CONTACT_PICKER_RESULT = 1000;
    //definimos los componenetes que va a tener la clase y que despues pueden llamarse para operar
    EditText nombre_evento;
    EditText lugar_evento;
    EditText fecha_evento;
    EditText cupo_min;
    EditText cupo_max;
    EditText edad_desde;
    EditText edad_hasta;
    EditText costo;
    Button btn_crear;
    Button btn_invitar_contactos;
    Spinner spn_tipo_evento;
    Spinner spn_sexo;
    Spinner spn_contactos;
    Button mapaCrear;

    // estos strings los tenemos que tomar de la BD, son solo de pruebas
    String[] opciones_sexo = {"Masculino", "Femenino", "Indiferente"};
    String[] opciones_tipo = {"Social", "Privado"};
    String[] contacts_selected = {""};
    //ArrayList<String> arrayContact = new ArrayList<String>();
    String sexo = "";
    String tipo = "";

    private static final int RESULT_PICK_CONTACT = 85500;
    final int CONTACT_PICK_REQUEST = 1000;
    private TextView textView1;
    private TextView textView2;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* tratando de darle movimiento entre las pantallas
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);*/
        setContentView(R.layout.activity_crear_evento);

        funciones = new Funciones(getApplicationContext());

        textView1 = (TextView) findViewById(R.id.ce_lista_coppados);
        textView2 = (TextView) findViewById(R.id.ce_lista_coppados2);

        //defino las posibles opciones que pueden tener los desplegables (esto despues lo tengo que reemplazar con lo que traigo
        // de la base de datos)

        spn_tipo_evento = (Spinner) findViewById(R.id.ce_spnTipoEvento);
        spn_sexo = (Spinner) findViewById(R.id.ce_spnSexoEvento);
        spn_contactos = (Spinner) findViewById(R.id.ce_spn_contactos_activos);

        nombre_evento = (EditText) findViewById(R.id.ce_nameEvent);
        lugar_evento = (EditText) findViewById(R.id.ce_lugarEncuentro);
        fecha_evento = (EditText) findViewById(R.id.ce_fecha_evento);
        cupo_min = (EditText) findViewById(R.id.ce_cupoMin);
        cupo_max = (EditText) findViewById(R.id.ce_cupoMax);
        edad_desde = (EditText) findViewById(R.id.ce_edadDesde);
        edad_hasta = (EditText) findViewById(R.id.ce_edadHasta);
        costo = (EditText) findViewById(R.id.ce_costo_evento);

        btn_crear = (Button) findViewById(R.id.ce_btnCrearEvento);
        btn_invitar_contactos = (Button) findViewById(R.id.ce_acceso_a_contactos);
        mapaCrear = (Button) findViewById(R.id.crearEventoMapa);


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
                        tipo = parent.getItemAtPosition(position).toString();
                        break;

                    case 1:
                        tipo = parent.getItemAtPosition(position).toString();
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
                        sexo = "Hombres";
                        break;

                    case 1:
                        sexo = "Mujeres";
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
                startActivity(intent);  //pasa a pantalla de Crear Evento
            }
        });

        //al hacer click en el boton, nos mostrara el texto en pantalla que se ha creado un evento
        // con el nombre que hemos ingresado
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (getActualListContact().size() > 0) {
                    // para mostrarlo en pantalla tipo mensaje se usa Toast
                    funciones.mostrarToastLargo((Usuario.getInstance().getNombre() + " ha creado el evento: " + nombre_evento.getText() + " de tipo: " + tipo + " solo para: " + sexo + " y se ha enviado una notificacion a los contactos seleccionados.."));
                } else {
                    funciones.mostrarToastCorto((Usuario.getInstance().getNombre() + " ha creado el evento: " + nombre_evento.getText() + " de tipo: " + tipo + " solo para: " + sexo));
                    funciones.playSoundGotaAgua(arg0);
                }
                guardarEvento(); // Crucen los dedos
                // con la funcion FINISH cerramos la activity actual y volvemos a la activity que nos llamo
                finish();
            }
        });


        btn_invitar_contactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantalla = new Intent(getApplicationContext(), InvitarContactos.class);

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
                } catch (Exception e) {
                    funciones.mostrarToastCorto(e.toString());
                }
            }
        });


    }

    /**
     * Guarda un evento en la DB
     * Acá puede empezar a romper.
     * Comienza zona de rotura.
     */
    public void guardarEvento() {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_owner", "1");
        map.put("edad_min", edad_desde.getText().toString());
        map.put("edad_max", edad_hasta.getText().toString());
        map.put("cupo_min", cupo_min.getText().toString());
        map.put("cupo_max", cupo_max.getText().toString());
        map.put("fecha", "2016-10-17"); //Solo para probar.
        map.put("foto", "NULL");
        map.put("ubicacion", lugar_evento.getText().toString());
        map.put("latitud", "166.123");
        map.put("longitud", "99.333");
        map.put("id_categoria", "1");
        map.put("desc_evento", "Prueba conexión con DB");
        map.put("id_sexo", "1");

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
}

