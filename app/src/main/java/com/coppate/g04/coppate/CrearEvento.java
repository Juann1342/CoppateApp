package com.coppate.g04.coppate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class CrearEvento extends AppCompatActivity {

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

    // estos strings los tenemos que tomar de la BD, son solo de pruebas
    String[] opciones_sexo = {"Masculino", "Femenino"};
    String[] opciones_tipo = {"Social", "Privado"};
    String sexo = "";
    String tipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        //defino las posibles opciones que pueden tener los desplegables (esto despues lo tengo que reemplazar con lo que traigo
        // de la base de datos)

        spn_tipo_evento = (Spinner) findViewById(R.id.ce_spnTipoEvento);
        spn_sexo = (Spinner) findViewById(R.id.ce_spnSexoEvento);

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

        // definimos los adaptadores de las listas que tenemos en este caso las opciones de sexo y tipo de evento
        ArrayAdapter<String> adapt_sexo = new ArrayAdapter<String>(CrearEvento.this,android.R.layout.simple_spinner_item,opciones_sexo);
        ArrayAdapter<String> adapt_tipo = new ArrayAdapter<String>(CrearEvento.this,android.R.layout.simple_spinner_item,opciones_tipo);

        //seteamos los adaptadores a nuestra Lista desplegable
        spn_sexo.setAdapter(adapt_sexo);
        spn_tipo_evento.setAdapter(adapt_tipo);


        // se guarda el tipo que se ha seleccionado en la variable TIPO para una utilizacion posterior
        spn_tipo_evento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
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

        spn_sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
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

        //al hacer click en el boton, nos mostrara el texto en pantalla que se ha creado un evento
        // con el nombre que hemos ingresado
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // para mostrarlo en pantalla tipo mensaje se usa Toast
                Toast toast2 =
                        Toast.makeText(getApplicationContext(),
                                "Se ha creado el evento: "+nombre_evento.getText()+" de tipo: "+tipo +  " solo para: "+sexo, Toast.LENGTH_LONG);

                toast2.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);

                toast2.show();
                // con la funcion FINISH cerramos la activity actual y volvemos a la activity que nos llamo
                finish();
            }
        });
    }

    public void CrearEvento(View v){
        Toast toast2=
        Toast.makeText(getApplicationContext(),
                "Toast con gravity", Toast.LENGTH_SHORT);

        toast2.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);

    }

}

