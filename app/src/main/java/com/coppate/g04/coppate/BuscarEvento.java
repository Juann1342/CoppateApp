package com.coppate.g04.coppate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class BuscarEvento extends AppCompatActivity {

    // definimos los elementos que usamos en la clase
    Spinner tipo;
    Spinner sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_evento);

        tipo = (Spinner) findViewById(R.id.spinner_tipo);

        // esta lista es provisional los datos lo tenemos que sacar de la BD

        List lista = new ArrayList();
        lista.add("Deporte");
        lista.add("Viaje");
        lista.add("Fiesta");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,lista);
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

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,lista1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(arrayAdapter1);

        // sexo.setOnItemSelectedListener(); esta linea la deje porque hay que seguir cuando este la BD
    }
}
