package com.coppate.g04.coppate;

/**
 * Created by Jul-note on 17/12/2016.
 */

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_seccion3 extends Fragment {

    Funciones funciones;
    ListView eventos_que_participo;

    private ArrayList<FilaEvento> arrayEventosParticipo;
    private EventoListaAdapter eventoListaAdapterParticipo;

    private Gson gson = new Gson();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_seccion3, container, false);
        iniciar_vista(vista);
        //  "Inflamos" el archivo XML correspondiente a esta sección.
        return vista;
    }

    private void iniciar_vista(View v){
        funciones = new Funciones(getActivity().getApplicationContext());
        eventos_que_participo = (ListView) v.findViewById(R.id.ma_eventos_donde_participo);
        listarEventoEnQueParticipo();
        mostrarEventosParticipo();

    }

    private void mostrarEventosParticipo(){
        arrayEventosParticipo = new ArrayList<FilaEvento>();
        eventoListaAdapterParticipo = new EventoListaAdapter(getActivity(),arrayEventosParticipo);
        try {
            // inicializamos el eventoListaAdapter para mostrar los eventos cercanos
            /*adapt_eventos_otros = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
            // le cargamos los datos que deben traerlos de la base de datos.*/
            int largo = MisEventos.getInstance().getEventos_que_participo().length;
            Integer sexo = 0;
            Integer categor = 0;
            for (int i = 0; i<largo; i++) {
                sexo = Integer.valueOf(MisEventos.getInstance().getEventos_que_participo()[i].getId_sexo());
                categor = Integer.valueOf(MisEventos.getInstance().getEventos_que_participo()[i].getId_categoria());
                if (sexo == 1) {
                    if (categor == 1) {
                        arrayEventosParticipo.add(new FilaEvento(MisEventos.getInstance().getEventos_que_participo()[i].getNombre(), MisEventos.getInstance().getEventos_que_participo()[i].getDesc_evento(), R.drawable.masculino, R.drawable.personas));
                    } else if (categor == 2) {
                        arrayEventosParticipo.add(new FilaEvento(MisEventos.getInstance().getEventos_que_participo()[i].getNombre(), MisEventos.getInstance().getEventos_que_participo()[i].getDesc_evento(), R.drawable.masculino, R.drawable.pelota));
                    }
                } else if (sexo == 2) {
                    if (categor == 1) {
                        arrayEventosParticipo.add(new FilaEvento(MisEventos.getInstance().getEventos_que_participo()[i].getNombre(), MisEventos.getInstance().getEventos_que_participo()[i].getDesc_evento(), R.drawable.femenino, R.drawable.personas));
                    } else if (categor == 2) {
                        arrayEventosParticipo.add(new FilaEvento(MisEventos.getInstance().getEventos_que_participo()[i].getNombre(), MisEventos.getInstance().getEventos_que_participo()[i].getDesc_evento(), R.drawable.femenino, R.drawable.pelota));
                    }
                } else if (sexo == 3) {
                    if (categor == 1) {
                        arrayEventosParticipo.add(new FilaEvento(MisEventos.getInstance().getEventos_que_participo()[i].getNombre(), MisEventos.getInstance().getEventos_que_participo()[i].getDesc_evento(), R.drawable.unisex, R.drawable.personas));
                    } else if (categor == 2) {
                        arrayEventosParticipo.add(new FilaEvento(MisEventos.getInstance().getEventos_que_participo()[i].getNombre(), MisEventos.getInstance().getEventos_que_participo()[i].getDesc_evento(), R.drawable.unisex, R.drawable.pelota));
                    }
                }

            }
            eventos_que_participo.setAdapter(eventoListaAdapterParticipo);
            // hacemos que el listview tome el item que seleccionamos
            eventos_que_participo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    entrarADescripcionEvento(view);
                }
            });

        }catch (Exception e){
            funciones.mostrarToastCorto("Se ha producido un error al cargar los eventos cercanos");
        }
    }

    private void entrarADescripcionEvento(View v){
        try {
            Integer id_evento = eventos_que_participo.getPositionForView(v);
            Intent intent_descripcion = new Intent(getActivity(), DescripcionEvento.class);
            // le pasamos el parametro del ide de evento para tomarlo en la pantalla de DESCRIPCION DE EVENTO y mostrar los datos necesarios
            intent_descripcion.putExtra("ID_evento", id_evento);
            // creamos la animacion de deslizamiento
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
            // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
            startActivity(intent_descripcion, bndlanimation);
        }catch (Exception e){
            //funciones.mostrarToastCorto("Error al cargar la siguiente pantalla");
        }
    }

    public void listarEventoEnQueParticipo(){
        VolleySingleton.
                getInstance(getActivity().getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_EVENTOS_BY_MIEMBRO + "?idUsuario=" + Usuario.getInstance().getId_usuario(),
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        seteaEventosMiembro(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        funciones.mostrarToastCorto("Debug1: Error en listarEventoEnQueParticipo");
                                    }
                                }

                        )
                );
    }

    private void seteaEventosMiembro(JSONObject response){
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("eventos");
                    // Parsear con Gson
                    MisEventos.getInstance().setEventos_que_participo(gson.fromJson(mensaje.toString(), Evento[].class));
                    //funciones.mostrarToastLargo("Toast procesarResp: " + String.valueOf(MisEventos.getInstance().getEventos().length));
                    //funciones.mostrarToastLargo(MisEventos.getInstance().getEventos()[0].getNombre());
                    break;
                case "2": // FALLIDO
                    funciones.mostrarToastCorto("Debug2: Error en seteaEventosMiembro");;
                    break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
        }
    }
}
