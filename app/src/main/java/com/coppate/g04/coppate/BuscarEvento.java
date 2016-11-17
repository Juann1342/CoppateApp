package com.coppate.g04.coppate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;



public class BuscarEvento extends AppCompatActivity {

    // definimos los elementos que usamos en la clase
    Spinner tipo;
    Spinner sexo;
    Button buscar;
    Button acceso_de_codigo;
    EditText ingreso_codigo;
    Boolean codigo;
    Funciones funciones;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_evento);
        getSupportActionBar().hide();

        funciones = new Funciones(getApplicationContext());
        //HttpClient httpClient = new DefaultHttpClient();
       // HttpPost
        buscar = (Button)findViewById(R.id.buscar);

        codigo = false;
        acceso_de_codigo = (Button)findViewById(R.id.be_ingresar_codigo);
        ingreso_codigo = (EditText)findViewById(R.id.be_txt_ingresar_codigo);

        ingreso_codigo.setEnabled(codigo);

        tipo = (Spinner) findViewById(R.id.spinner_tipo);

        // esta lista es provisional los datos lo tenemos que sacar de la BD

        List lista = new ArrayList();
        lista.add("Deporte");
        lista.add("Viaje");
        lista.add("Fiesta");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(arrayAdapter);

        // tipo.setOnItemSelectedListener(); esta linea la deje porque hay que seguir cuando este la BD

        sexo = (Spinner) findViewById(R.id.spinner_sexo);

        // esta lista es provisional los datos lo tenemos que sacar de la BD

        List lista1 = new ArrayList();
        lista1.add("Mascolino");
        lista1.add("Femenino");
        lista1.add("Mixto");
        lista1.add("Transexual");

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(arrayAdapter1);

        // sexo.setOnItemSelectedListener(); esta linea la deje porque hay que seguir cuando este la BD
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        acceso_de_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigo = true;
                ingreso_codigo.setEnabled(codigo);
                ingreso_codigo.setVisibility(v.VISIBLE);
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tomamos el codigo del evento (por mas que no se haya seleccionado para buscar uno
                String str_code = ingreso_codigo.getText().toString();
                // chequeamos que el usuario haya seleccionado para ingresar el codigo
                if(codigo){
                    // comprobamos si el string del codigo esta vacio, si lo esta notificamos al usuario
                    // esto nos sirve para evitar complejo en la base de datos
                    if((str_code == null) || (str_code.equals(""))){
                        funciones.mostrarToastCorto("No se ha ingresado el codigo de busqueda");
                    }else {
                        // cuando el codigo no este nulo, le pasamos la busqueda
                        realizarBusqueda(codigo,str_code);
                    }
                }else {
                    // cuando el usuario no haya seleccionado el codigo, se realizara una busqueda comun
                    realizarBusqueda(codigo,str_code);
                }
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private void realizarBusqueda(Boolean codigo, String texto){
        if(codigo){
            // hacemos una busqueda en la base de datos del codigo ingresado
            funciones.mostrarToastLargo("Realizando la busqueda del codigo: "+ texto);
            //BuscarEvento.this.finish();
            ingresarADescripcionDeEvento(texto);
            overridePendingTransition(R.anim.reingreso, R.anim.nothing);
        }else {
            // buscamos los eventos segun los filtros
            funciones.mostrarToastLargo("Realizando busqueda comun");
            BuscarEvento.this.finish();
            overridePendingTransition(R.anim.reingreso, R.anim.nothing);
        }
    }

    private void ingresarADescripcionDeEvento(String codigo){
        try {
            Intent intent_descripcion = new Intent(BuscarEvento.this, InvitacionEvento.class);
            // le pasamos el parametro del ide de evento para tomarlo en la pantalla de DESCRIPCION DE EVENTO y mostrar los datos necesarios
            intent_descripcion.putExtra("Codigo", codigo);
            // creamos la animacion de deslizamiento
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
            // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
            startActivity(intent_descripcion, bndlanimation);
        }catch (Exception e){
            funciones.mostrarToastCorto("Error al cargar la siguiente pantalla");
        }
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("BuscarEvento Page") // TODO: Define a title for the content shown.
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
    }

    public void onBackPressed() {
        BuscarEvento.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
