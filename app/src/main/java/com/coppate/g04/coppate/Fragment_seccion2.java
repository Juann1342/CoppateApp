package com.coppate.g04.coppate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;


public class Fragment_seccion2 extends Fragment {

    Funciones funciones;

    private ArrayList<FilaEvento> arrayEventosPropios;
    private EventoListaAdapter eventoListaAdapterPropios;

    ListView mis_eventos;

    private Gson gson = new Gson();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_seccion2, container, false);
        initUI(vista);
        //  "Inflamos" el archivo XML correspondiente a esta sección.
        return vista;
    }

    private void initUI(View v){
        mis_eventos = (ListView) v.findViewById(R.id.ma_listar_mis_eventos);
        listarEventoPorOwner();
        mostrarEventosPropios();
        /*FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.ma_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCrearEvento(v);
            }
        });*/
    }

    private void goCrearEvento(View v){
        Intent intent = new Intent(getActivity(), CrearEvento.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(intent,bndlanimation);//pasa a pantalla de Crear Evento
        funciones.playSoundPickButton();
    }

    private void mostrarEventosPropios(){
        funciones = new Funciones(getActivity().getApplicationContext());
        arrayEventosPropios = new ArrayList<FilaEvento>();
        eventoListaAdapterPropios = new EventoListaAdapter(getActivity(),arrayEventosPropios);
        try {
            // inicializamos el eventoListaAdapter para mostrar los eventos cercanos
            /*adapt_eventos_otros = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
            // le cargamos los datos que deben traerlos de la base de datos.*/
            int largo_propios = MisEventos.getInstance().getEventos().length;
            Integer sexo_propios = 0;
            Integer categoria_propios = 0;
            for (int i = 1; i<largo_propios; i++) {
                sexo_propios = Integer.valueOf(MisEventos.getInstance().getEventos()[largo_propios-i].getId_sexo());
                categoria_propios = Integer.valueOf(MisEventos.getInstance().getEventos()[largo_propios-i].getId_categoria());
                if (sexo_propios == 1) {
                    if (categoria_propios == 1) {
                        arrayEventosPropios.add(new FilaEvento(MisEventos.getInstance().getEventos()[largo_propios-i].getNombre(), MisEventos.getInstance().getEventos()[largo_propios-i].getDesc_evento(), R.drawable.masculino, R.drawable.personas));
                    } else if (categoria_propios == 2) {
                        arrayEventosPropios.add(new FilaEvento(MisEventos.getInstance().getEventos()[largo_propios-i].getNombre(), MisEventos.getInstance().getEventos()[largo_propios-i].getDesc_evento(), R.drawable.masculino, R.drawable.pelota));
                    }
                } else if (sexo_propios == 2) {
                    if (categoria_propios == 1) {
                        arrayEventosPropios.add(new FilaEvento(MisEventos.getInstance().getEventos()[largo_propios-i].getNombre(), MisEventos.getInstance().getEventos()[largo_propios-i].getDesc_evento(), R.drawable.femenino, R.drawable.personas));
                    } else if (categoria_propios == 2) {
                        arrayEventosPropios.add(new FilaEvento(MisEventos.getInstance().getEventos()[largo_propios-i].getNombre(), MisEventos.getInstance().getEventos()[largo_propios-i].getDesc_evento(), R.drawable.femenino, R.drawable.pelota));
                    }
                } else if (sexo_propios == 3) {
                    if (categoria_propios == 1) {
                        arrayEventosPropios.add(new FilaEvento(MisEventos.getInstance().getEventos()[largo_propios-i].getNombre(), MisEventos.getInstance().getEventos()[largo_propios-i].getDesc_evento(), R.drawable.unisex, R.drawable.personas));
                    } else if (categoria_propios == 2) {
                        arrayEventosPropios.add(new FilaEvento(MisEventos.getInstance().getEventos()[largo_propios-i].getNombre(), MisEventos.getInstance().getEventos()[largo_propios-i].getDesc_evento(), R.drawable.unisex, R.drawable.pelota));
                    }
                }
                /*cercanos_prueba.agregarTitulo(MisEventos.getInstance().getEventosCercanos()[i].getNombre());
                cercanos_prueba.agregarDescripcion(MisEventos.getInstance().getEventosCercanos()[i].getDesc_evento());*/

            }
            mis_eventos.setAdapter(eventoListaAdapterPropios);
            // hacemos que el listview tome el item que seleccionamos
            mis_eventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    entrarAMiEvento(view);
                }
            });

        }catch (Exception e){
            funciones.mostrarToastCorto("Se ha producido un error al cargar los eventos propios");
        }
    }

    private void entrarAMiEvento(View v){
        try {

            Integer id_evento = mis_eventos.getPositionForView(v);
            Integer idEvent = Integer.valueOf(MisEventos.getInstance().getEventos()[id_evento].getId_evento());
            //funciones.mostrarToastCorto("ID_evento: "+idEvent.toString());
            Intent intent_descripcion = new Intent(getActivity(), EditarEvento.class);
            // le pasamos el parametro del ide de evento para tomarlo en la pantalla de DESCRIPCION DE EVENTO y mostrar los datos necesarios
            intent_descripcion.putExtra("ID_evento", idEvent);
            // creamos la animacion de deslizamiento
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
            // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
            startActivity(intent_descripcion, bndlanimation);
            //MainActivity.this.finish();
        }catch (Exception e){
            //funciones.mostrarToastCorto("Error al cargar la siguiente pantalla");
        }
    }



    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void listarEventoPorOwner() {
        // Petición GET
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(new JsonObjectRequest(
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
                                        funciones.mostrarToastCorto("Debug1: Error en listarEventoPorOwner");
                                    }
                                }

                        ){
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

    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("eventos");
                    // Parsear con Gson
                    MisEventos.getInstance().setEventos(gson.fromJson(mensaje.toString(), Evento[].class));
                    //funciones.mostrarToastLargo("Toast procesarResp: " + String.valueOf(MisEventos.getInstance().getEventos().length));
                    //funciones.mostrarToastLargo(MisEventos.getInstance().getEventos()[0].getNombre());
                    break;
                case "2": // FALLIDO
                    funciones.mostrarToastCorto("Debug2: Error en procesarRespuesta");;
                    break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
        }

    }
}
