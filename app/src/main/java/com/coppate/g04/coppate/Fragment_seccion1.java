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


public class Fragment_seccion1 extends Fragment {

    Funciones funciones;

    private ArrayList<FilaEvento> arrayEventos;
    private EventoListaAdapter eventoListaAdapter;

    ListView eventos_de_otros;

    private Gson gson = new Gson();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //  "Inflamos" el archivo XML correspondiente a esta sección.
        View vista = inflater.inflate(R.layout.fragment_seccion1, container, false);
        iniciar_funciones(vista);
        // Y lo devolvemos
        return vista;
    }

    private void iniciar_funciones(View v){
        eventos_de_otros = (ListView) v.findViewById(R.id.ma_lv_eventos_cercanos);
        getEventosCercanos();
        mostrarEventosCercanos();
        Button btn_buscar =(Button) v.findViewById(R.id.ma_botonBuscar);
        btn_buscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                goBuscar(arg0);

            }
        });

        Button btn_mapa =(Button)v.findViewById(R.id.ma_botonMapa);
        btn_mapa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goMapa(v);
            }
        });
    }

    private void goMapa(View vista) {
        funciones = new Funciones(getActivity().getApplicationContext());
        Intent mapa_cercanos = new Intent(getActivity(), MapsActivityCercanos.class);
        startActivity(mapa_cercanos);
    }  //dirige a la pantalla de mapa

    private void goBuscar(View vista) {
        funciones = new Funciones(getActivity().getApplicationContext());
        Intent busqueda_de_eventos = new Intent(getActivity(), BuscarEvento.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivity(busqueda_de_eventos,bndlanimation);
    }  //dirige a la pantalla buscar


    private void mostrarEventosCercanos(){
        funciones = new Funciones(getActivity().getApplicationContext());
        arrayEventos = new ArrayList<FilaEvento>();
        eventoListaAdapter = new EventoListaAdapter(getActivity(),arrayEventos);
        try {
            // inicializamos el eventoListaAdapter para mostrar los eventos cercanos
            /*adapt_eventos_otros = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
            // le cargamos los datos que deben traerlos de la base de datos.*/
            int largo = MisEventos.getInstance().getEventosCercanos().length;
            int largoX = 25; // seteamos un largo a mano hasta que definamos la posicion de los eventos cercanos
            Integer sexo = 0;
            Integer categor = 0;
            for (int i = 1; i<largoX; i++) {
                sexo = Integer.valueOf(MisEventos.getInstance().getEventosCercanos()[largo-i].getId_sexo());
                categor = Integer.valueOf(MisEventos.getInstance().getEventosCercanos()[largo-i].getId_categoria());
                if (sexo == 1) {
                    if (categor == 1) {
                        arrayEventos.add(new FilaEvento(MisEventos.getInstance().getEventosCercanos()[largo-i].getNombre(), MisEventos.getInstance().getEventosCercanos()[largo-i].getDesc_evento(), R.drawable.masculino, R.drawable.personas));
                    } else if (categor == 2) {
                        arrayEventos.add(new FilaEvento(MisEventos.getInstance().getEventosCercanos()[largo-i].getNombre(), MisEventos.getInstance().getEventosCercanos()[largo-i].getDesc_evento(), R.drawable.masculino, R.drawable.pelota));
                    }
                } else if (sexo == 2) {
                    if (categor == 1) {
                        arrayEventos.add(new FilaEvento(MisEventos.getInstance().getEventosCercanos()[largo-i].getNombre(), MisEventos.getInstance().getEventosCercanos()[largo-i].getDesc_evento(), R.drawable.femenino, R.drawable.personas));
                    } else if (categor == 2) {
                        arrayEventos.add(new FilaEvento(MisEventos.getInstance().getEventosCercanos()[largo-i].getNombre(), MisEventos.getInstance().getEventosCercanos()[largo-i].getDesc_evento(), R.drawable.femenino, R.drawable.pelota));
                    }
                } else if (sexo == 3) {
                    if (categor == 1) {
                        arrayEventos.add(new FilaEvento(MisEventos.getInstance().getEventosCercanos()[largo-i].getNombre(), MisEventos.getInstance().getEventosCercanos()[largo-i].getDesc_evento(), R.drawable.unisex, R.drawable.personas));
                    } else if (categor == 2) {
                        arrayEventos.add(new FilaEvento(MisEventos.getInstance().getEventosCercanos()[largo-i].getNombre(), MisEventos.getInstance().getEventosCercanos()[largo-i].getDesc_evento(), R.drawable.unisex, R.drawable.pelota));
                    }
                }
                /*cercanos_prueba.agregarTitulo(MisEventos.getInstance().getEventosCercanos()[i].getNombre());
                cercanos_prueba.agregarDescripcion(MisEventos.getInstance().getEventosCercanos()[i].getDesc_evento());*/

            }
            eventos_de_otros.setAdapter(eventoListaAdapter);
            // hacemos que el listview tome el item que seleccionamos
            eventos_de_otros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    entrarAEvento(view);
                }
            });

        }catch (Exception e){
            funciones.mostrarToastCorto("Se ha producido un error al cargar los eventos cercanos");
        }
    }

    private void entrarAEvento(View v){
        Integer id_evento_cercano = eventos_de_otros.getPositionForView(v);
        Integer idEvent = Integer.valueOf(MisEventos.getInstance().getEventosCercanos()[id_evento_cercano].getId_evento());
        Intent intent_entrar_a_evento = new Intent(getActivity(), InvitacionEvento.class);
        // le pasamos el parametro del id de evento para tomarlo en la pantalla de INVITACION A EVENTO y mostrar los datos necesarios
        intent_entrar_a_evento.putExtra("ID_evento", idEvent);
        // creamos la animacion de deslizamiento
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
        // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
        startActivity(intent_entrar_a_evento, bndlanimation);
    }

    public void getEventosCercanos(){
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(new JsonObjectRequest(
                Request.Method.GET,
                Constantes.GET,
                null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaEventos(response);
                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        funciones.mostrarToastCorto(("Se ha producido un Error Volley: " + error.getMessage()));
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
        });

    }
    private void procesarRespuestaEventos(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("eventos");
                    // Parsear con Gson
                    MisEventos.getInstance().setEventosCercanos(gson.fromJson(mensaje.toString(), Evento[].class));
                    //funciones.mostrarToastLargo("Toast procesarResp: " + String.valueOf(MisEventos.getInstance().getEventos().length));
                    //funciones.mostrarToastLargo(MisEventos.getInstance().getEventos()[0].getNombre());
                    break;
                case "2": // FALLIDO
                    funciones.mostrarToastCorto("Debug2: No encontró el registro");;
                    break;
            }

        } catch (JSONException e) {
            //Log.d(TAG, e.getMessage());
        }

    }
}
