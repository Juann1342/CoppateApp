package com.coppate.g04.coppate;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class CrearEvento extends Activity {

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

    private static final int RESULT_PICK_CONTACT = 85500;
    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        textView1 = (TextView) findViewById(R.id.ce_lista_coppados);
        textView2 = (TextView) findViewById(R.id.ce_lista_coppados2);

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

        /*
        btn_invitar_contactos.setOnClickListener(new View.OnClickListener(){

            @Override
              public void onClick(View v) {
                Intent intent_invitCopp = new Intent(CrearEvento.this,InvitarCoppados.class);
                startActivity(intent_invitCopp);
            }
        });*/

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

    public void pickContact(View v)
    {
        // creamos un StartActivityForResult para mostrar los contactos
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // comprobamos si el resultado esta ok
        if (resultCode == RESULT_OK) {
            // Comprobar si el codigo de peticion, podriamos estar utilizando múltiples startActivityForResult (distinto a StartActivity)
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // el metodo GetData() va a tener el contenido URI del contacto seleccionado.
            Uri uri = data.getData();
            // consultamos el contenido de la URI
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            // tomamos los indices de las columnas del numero y el nombre de los telefonos
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            // Establecemos el valor de los textviews
            textView1.setText(name);
            textView2.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

