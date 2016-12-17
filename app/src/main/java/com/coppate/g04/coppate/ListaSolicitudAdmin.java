package com.coppate.g04.coppate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListaSolicitudAdmin extends AppCompatActivity {

    ListView aceptar_invitados;
    ArrayAdapter<String> adaptador;

    ArrayList<String> lista_coppantes_evento = new ArrayList<String>();



    Funciones funciones;
    Evento[] eventos;
    private Gson gson = new Gson();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_solicitud_admin);

        aceptar_invitados = (ListView) findViewById(R.id.aceptar_coppados);

        mostrarMisSolicitantes(lista_coppantes_evento);

        lista_coppantes_evento = new ArrayList<String>();


        int largo = 0;

        try {
            largo = MisEventos.getInstance().getEventos().length;
            for (int i = 0; i < largo; i++) {
                lista_coppantes_evento.add(MisEventos.getInstance().getEventos()[i].getNombre());
            }

        }
        catch (Exception e) {
            funciones.mostrarToastCorto("Deslice hacia abajo");
        }



        /*final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.rosafuxia1,R.color.violetadiseno,R.color.violeta1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        mostrarMisSolicitantes(lista_coppantes_evento);

                        finish();
                        startActivity(getIntent());


                    }
                },4000);   //Actualiza pantalla
            }
        });*/

    }



    private void mostrarMisSolicitantes(ArrayList<String> array_mis_solicitantes){
        try {
            adaptador = new ArrayAdapter<String>(ListaSolicitudAdmin.this, android.R.layout.simple_list_item_1);
            // hay que hacer que el "ARRAYADAPTER lo tome de la base de datos y luego recorrerlo, ahora esta a manopla
            /*String objeto = "Creo Evento: ";
            String num = "";
            for(int i = 0;i<30;i++){
                adaptador.add(objeto+i);
            }*/
            // recorremos la lista de los eventos que trae de la base de datos y los cargamos en el adaptadorde mis eventos
            for (int i = 0;i<array_mis_solicitantes.size();i++){
                adaptador.add(array_mis_solicitantes.get(i));
            }
            aceptar_invitados.setAdapter(adaptador);
             /* obtenemos el id del evento actual que se selecciona de la lista y lo cargamos en el activity descripcion de evento*/
            aceptar_invitados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    perfilSolicitante(view);

                }
            });
            // termina el listview
        }catch (Exception e){
            funciones.mostrarToastCorto("No fue posible cargar la lista de usuarios");
        }
    }

    private void perfilSolicitante(View v){
        try {

            Integer id_evento = aceptar_invitados.getPositionForView(v);
            Integer idEvent = Integer.valueOf(MisEventos.getInstance().getEventos()[id_evento].getId_evento());
            funciones.mostrarToastCorto("ID_evento: "+idEvent.toString());
            Intent intent_descripcion = new Intent(ListaSolicitudAdmin.this, EditarEvento.class);
            // le pasamos el parametro del ide de evento para tomarlo en la pantalla de DESCRIPCION DE EVENTO y mostrar los datos necesarios
            intent_descripcion.putExtra("ID_evento", idEvent);
            // creamos la animacion de deslizamiento
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
            // lanzamos la actividad de DESCRIPCION y le cargamos la animacion
            startActivity(intent_descripcion, bndlanimation);
            ListaSolicitudAdmin.this.finish();
        }catch (Exception e){
            funciones.mostrarToastCorto("Error al cargar la siguiente pantalla");
        }
    }







}
