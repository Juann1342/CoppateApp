package com.coppate.g04.coppate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.HashMap;
import java.util.Map;

public class EditarEvento extends Activity {

  //  Button ubicacion;
    Button editar_evento;
    Button cancelar_evento;
    Button guardar_cambios;
    Button solicitud_evento;
    EditText descripcion_evento;
    TextView nombre_evento;
    EditText lugar_encuentro;
    EditText costo_evento;
    EditText cupo_min;
    EditText cupo_max;
    EditText edad_desde;
    EditText edad_hasta;
    Spinner spn_tipo_evento;
    Spinner spn_sexo;

    String[] opciones_sexo = {"Masculino", "Femenino", "Indiferente"};
    String[] opciones_tipo = {"Social", "Privado"};
    String sexo = "";
    String tipo = "";


    //  TextView titulo_evento;

    Funciones funciones;

    Integer id_event;
    Integer id_eveto;

    Boolean cambios = null;

    AlertDialog.Builder dialogo;

    private Gson gson = new Gson();

    private static final int REQUEST_MAPA= 1;
    private static final Double PUBLIC_STATIC_DOUBLE_LATITUD = 0.0;
    private static final Double PUBLIC_STATIC_DOUBLE_LONGITUD = 0.0;
    Double latitud;
    Double longitud;


    // creamos un bundle que nos recuperara los extras que hayamos puesto en la otra actividad
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        cambios = false;

        dialogo = new AlertDialog.Builder(getApplicationContext());

        b = getIntent().getExtras();

        id_event= b.getInt("ID_evento");

        /* evento_en_bd = getEvento(id_evento, id_usuario);
        *
        *  descripcion_evento.setText(evento_en_bd.getCampoDescripcion);
        *  y asi con los de abajo..
        *
            nombre_evento = (EditText)findViewById(R.id.ee_nombre_evento);
            lugar_encuentro = (EditText)findViewById(R.id.ee_lugar);
            costo_evento = (EditText)findViewById(R.id.ee_costo);
            cupo_max = (EditText)findViewById(R.id.ee_cupoMax);
            cupo_min = (EditText)findViewById(R.id.ee_cupoMin);
            edad_desde = (EditText)findViewById(R.id.ee_edadDesde);
            edad_hasta = (EditText)findViewById(R.id.ee_edadHasta);

        *
        *
        * */

        funciones = new Funciones(getApplicationContext());

      //  ubicacion = (Button)findViewById(R.id.ee_ubicacion_mapa);
        editar_evento = (Button) findViewById(R.id.ee_editar_evento);
        cancelar_evento = (Button)findViewById(R.id.ee_cancelar_evento);
        solicitud_evento = (Button)findViewById(R.id.ee_solicitud);
        guardar_cambios = (Button)findViewById(R.id.ee_guardar_cambios);
        descripcion_evento = (EditText) findViewById(R.id.ee_descripcion_evento);
        nombre_evento = (TextView)findViewById(R.id.ee_nombre_evento);
        lugar_encuentro = (EditText)findViewById(R.id.ee_lugar);
        costo_evento = (EditText)findViewById(R.id.ee_costo);
        cupo_max = (EditText)findViewById(R.id.ee_cupoMax);
        cupo_min = (EditText)findViewById(R.id.ee_cupoMin);
        edad_desde = (EditText)findViewById(R.id.ee_edadDesde);
        edad_hasta = (EditText)findViewById(R.id.ee_edadHasta);
        spn_tipo_evento = (Spinner) findViewById(R.id.ce_spnTipoEvento);
        spn_sexo = (Spinner) findViewById(R.id.ce_spnSexoEvento);

     //   ubicacion.setEnabled(false);

      //  titulo_evento = (TextView)findViewById(R.id.ee_usuario);

        nombre_evento.setText("Probandooooooooo");

        obtenerDatosEventoPorID();


        editar_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_cambios.setVisibility(View.VISIBLE);
                editar_evento.setEnabled(true);
                cancelar_evento.setEnabled(true);
                solicitud_evento.setEnabled(true);
                descripcion_evento.setEnabled(true);
                nombre_evento.setEnabled(true);
                lugar_encuentro.setEnabled(true);
                costo_evento.setEnabled(true);
                cupo_min.setEnabled(true);
                cupo_max.setEnabled(true);
                edad_hasta.setEnabled(true);
                edad_desde.setEnabled(true);
                cambios = true;
             //   ubicacion.setEnabled(true);
                //guardar_cambios.setEnabled(true);
            }
        });


        // definimos los adaptadores de las listas que tenemos en este caso las opciones de sexo y tipo de evento
        ArrayAdapter<String> adapt_sexo = new ArrayAdapter<String>(EditarEvento.this, android.R.layout.simple_spinner_item, opciones_sexo);
        ArrayAdapter<String> adapt_tipo = new ArrayAdapter<String>(EditarEvento.this, android.R.layout.simple_spinner_item, opciones_tipo);

        //seteamos los adaptadores a nuestra Lista desplegable
        spn_sexo.setAdapter(adapt_sexo);
        spn_tipo_evento.setAdapter(adapt_tipo);


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

                    case 2:
                        sexo="3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no hay funcion de nada.. algo siempre se selecciona
            }
        });



/*
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditarEvento.this, MapsActivity.class);
                startActivityForResult(intent,REQUEST_MAPA);
            }
        });
*/

        solicitud_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goListaSolicitud();
            }
        });


        cancelar_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog customDialog = null;
                customDialog = new Dialog(EditarEvento.this,R.style.Theme_Dialog_Translucent);
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
                titulo.setText("Cambios en el Evento");

                // creamos y mostramos el mensaje que deseamos visualizar
                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                contenido.setText("Estás seguro que deseas cancelar el evento?");

                // seteamos el texto del boton afirmativo como el texto del propio boton
                Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
                aceptar.setText("Si, muy seguro");
                aceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // si el usuario presiona en aceptar, se cierra la aplicación
                        goMain();

                    }
                });

                // seteamos el texto del boton negativo como el texto del propio boton
                Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);
                cancelar.setText("No, aun no");
                final Dialog finalCustomDialog1 = customDialog;
                cancelar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // si el usuario presiona en aceptar, se cierra el cuadro y vuele al activity que lo llamo.
                        finalCustomDialog1.dismiss();
                        overridePendingTransition(R.anim.reingreso, R.anim.nothing);
                    }
                });
                customDialog.show();

                /*AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EditarEvento.this);
                dialogo1.setTitle("Cancelar Evento");
                dialogo1.setMessage("¿Esta seguro de Cancelar el Evento?");
                dialogo1.setCancelable(false);
                dialogo1.setIcon(R.drawable.icono32);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        // actualizar campos de la base de datos de fecha y cerrar evento
                        finish();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();*/
            }
        });

        guardar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog customDialog = null;
                customDialog = new Dialog(EditarEvento.this,R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setCancelable(false);
                customDialog.setContentView(R.layout.dialog);

                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                titulo.setText("Guardar Datos en el Evento");
                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                contenido.setText("Estás seguro que deseas actualizar los valores del evento?");

                Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
                aceptar.setText("Si, muy seguro");
                aceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // aca va una funcion para guardar datos y actualizar datos en la base de datos..
                        actualizarEvento();
                        // si el usuario presiona en aceptar, se cierra la aplicación
                        goMain();

                    }
                });

                // seteamos el texto del boton negativo como el texto del propio boton
                Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);
                cancelar.setText("No, seguir editando");
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
                /*AlertDialog.Builder dialogo2 = new AlertDialog.Builder(EditarEvento.this);
                dialogo2.setTitle("Guardar Datos");
                dialogo2.setMessage("¿Esta seguro que desea actualizar los valores al evento?");
                dialogo2.setCancelable(false);
                dialogo2.setIcon(R.drawable.icono32);
                dialogo2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo2, int id) {
                        // actualizar campos de la base de datos
                        finish();
                    }
                });
                dialogo2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo2, int id) {

                    }
                });
                dialogo2.show();*/
            }
        });

    }

    //el la siguiente clase cambiamos la fuente
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    private void obtenerDatosEventoPorID(){
        VolleySingleton.
                getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_BY_ID + "?idEvento=" + id_event,
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
                                        //funciones.mostrarToastCorto("Debug1: Error en obtenerDatosEventoPorID");
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

            //funciones.mostrarToastCorto("estado:" +estado);


            switch (estado) {
                case "1": // EXITO
                    //funciones.mostrarToastCorto(estado);
                    JSONArray mensaje = response.getJSONArray("evento");
                    //JSONObject objeto = response.getJSONObject("evento");
                    //funciones.mostrarToastCorto("Mensaje: "+objeto.toString());

                    // utilizamos el singleton de MisEventos y le pasamos el evento actual
                    MisEventos.getInstance().setEvento(gson.fromJson(mensaje.toString(), Evento[].class));
                    //funciones.mostrarToastLargo("Toast procesarResp: " + String.valueOf(MisEventos.getInstance().getEventos().length));

                    nombre_evento.setText(MisEventos.getInstance().getEvento()[0].getNombre());
                    descripcion_evento.setText(MisEventos.getInstance().getEvento()[0].getDesc_evento());
                    lugar_encuentro.setText(MisEventos.getInstance().getEvento()[0].getUbicacion());
                    costo_evento.setText(MisEventos.getInstance().getEvento()[0].getCosto().toString());
                    cupo_min.setText(MisEventos.getInstance().getEvento()[0].getCupo_min().toString());
                    cupo_max.setText(MisEventos.getInstance().getEvento()[0].getCupo_max().toString());
                    edad_desde.setText(MisEventos.getInstance().getEvento()[0].getEdad_min().toString());
                    edad_hasta.setText(MisEventos.getInstance().getEvento()[0].getEdad_max().toString());
                    String sex = MisEventos.getInstance().getEvento()[0].getId_sexo();
                    if (sex == "1") {
                        opciones_sexo[0] = "Masculino";
                        opciones_sexo[1] = "Femenino";
                        opciones_sexo[2] = "Indiferente";
                    }
                    if (sex == "2") {
                        opciones_sexo[0] = "Femenino";
                        opciones_sexo[1] = "Masculino";
                        opciones_sexo[2] = "Indiferente";
                    }
                    if(sex == "3"){
                        opciones_sexo[0] = "Indistinto";
                        opciones_sexo[1] = "Femenino";
                        opciones_sexo[2] = "Masculino";
                    }


               //     titulo_evento.setText("Descripción del evento: "+MisEventos.getInstance().getEvento()[0].getNombre()+", del usuario: "+Usuario.getInstance().getNombre()+" "+Usuario.getInstance().getApellido());

                    // Obtener array "metas" Json
                    /*JSONArray mensaje = response.getJSONArray("evento");
                    // Parsear con Gson
                    MisEventos.getInstance().setEventos(gson.fromJson(mensaje.toString(), Evento[].class));
                    //funciones.mostrarToastLargo("Toast procesarResp: " + String.valueOf(MisEventos.getInstance().getEventos().length));
                    funciones.mostrarToastLargo(MisEventos.getInstance().getEventos()[0].getNombre());*/
                    break;
                case "2": // FALLIDO
                    //funciones.mostrarToastCorto("Debug2: Error en procesarRespuesta");
                    break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
            //funciones.mostrarToastCorto("Fallo?");
        }

    }

    public void actualizarEvento() {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_evento",String.valueOf(id_event));
        map.put("edad_min", edad_desde.getText().toString());
        map.put("edad_max", edad_hasta.getText().toString());
        map.put("cupo_min", cupo_min.getText().toString());
        map.put("cupo_max", cupo_max.getText().toString());
        map.put("costo", costo_evento.getText().toString());
        map.put("ubicacion", lugar_encuentro.getText().toString());
        map.put("id_categoria", tipo);
        map.put("desc_evento", descripcion_evento.getText().toString());
        map.put("id_sexo", sexo);
        map.put("id_estado", "1"); // 1=activo 2=finalizado 3=cancelado 4=en pausa

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.UPDATE_EVENTO,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                updateEvento(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //funciones.mostrarToastCorto(("Error Volley: " + error.getMessage()));
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
    private void updateEvento(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            //funciones.mostrarToastCorto("Mensaje: "+mensaje +"\n"+"Estado: "+estado);
            switch (estado) {
                case "1":
                    // Mostrar mensaje

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

    public void onBackPressed() {
        if(cambios){

            Dialog customDialog = null;
            customDialog = new Dialog(EditarEvento.this,R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setCancelable(false);
            customDialog.setContentView(R.layout.dialog);

            TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
            titulo.setText("Cambios en el Evento");
            TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
            contenido.setText("Estás seguro que deseas salir sin actualizar los valores del evento?");

            Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
            aceptar.setText("Si, muy seguro");
            aceptar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    // aca va una funcion para guardar datos y actualizar datos en la base de datos..

                    // si el usuario presiona en aceptar, se cierra la aplicación
                    goMain();

                }
            });

            // seteamos el texto del boton negativo como el texto del propio boton
            Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);
            cancelar.setText("No, seguir editando");
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
            /*AlertDialog.Builder dialogo2 = new AlertDialog.Builder(EditarEvento.this);
            dialogo2.setTitle("Cambios en el Evento");
            dialogo2.setMessage("¿Esta seguro que desea salir sin actualizar los valores del evento?");
            dialogo2.setCancelable(false);
            dialogo2.setIcon(R.drawable.icono32);
            dialogo2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo2, int id) {

                }
            });
            dialogo2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo2, int id) {
                    // actualizar campos de la base de datos
                    EditarEvento.this.finish();
                    //finish();
                }
            });
            dialogo2.show();*/
        }else{
            goMain();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MAPA && resultCode == RESULT_OK) {
            try {
                latitud = data.getDoubleExtra("latitud", PUBLIC_STATIC_DOUBLE_LATITUD);
                longitud = data.getDoubleExtra("longitud", PUBLIC_STATIC_DOUBLE_LONGITUD);
            } catch (Exception e) {
                //funciones.mostrarToastCorto("ALGO PASÓ");
            }
        }
    }

    private void goMain(){
        Intent mainotravez = new Intent(EditarEvento.this,MainActivity.class);
        startActivity(mainotravez);
        EditarEvento.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }


     private void goListaSolicitud(){
         Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
         Intent aprobar = new Intent(EditarEvento.this, Aprobacion_administrador.class);
         startActivity(aprobar,bndlanimation);
     }


}
