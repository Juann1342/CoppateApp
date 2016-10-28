package com.coppate.g04.coppate;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_evento);

        //HttpClient httpClient = new DefaultHttpClient();
       // HttpPost

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
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
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
}
