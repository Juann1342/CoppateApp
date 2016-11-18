package com.coppate.g04.coppate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OpinionUsuario extends Activity {

    private static final int REQUEST_COMENTAR = 1;
    private static final int STATIC_INTEGER_VALUE = 1;
    private static final String PUBLIC_STATIC_STRING_IDENTIFIER = "";
    private static final Integer PUBLIC_STATIC_INT_CALIFICACION = 0;
    ImageView foto_perfil;
    ImageView estrella;
    Button btn_calificar;
    TextView comentarios;
    TextView txt_puntuacion;
    Integer punt;

    ListView lista_comentarios = null;

    ArrayAdapter<String> adapter;

    public static String comments;
    //creamos una lista con los comentarios de otros usuarios y las puntuaciones (tienen que ser de la base de datos
    ArrayList<String> coments_list = new ArrayList<String>();
    ArrayList<Integer> punt_list = new ArrayList<Integer>();

    Funciones funciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion_usuario);

        funciones  = new Funciones(getApplicationContext());

        lista_comentarios = (ListView) findViewById(R.id.ou_lista_comentarios);

        // esta puntuacion tiene que hacerla de un promedio de la base de datos de calificaciones de usuario
        punt = 0;

        funciones.mostrarFecha();

        btn_calificar = (Button) findViewById(R.id.ou_btn_calificar);
        foto_perfil = (ImageView) findViewById(R.id.ou_perfil_pict);
        estrella = (ImageView) findViewById(R.id.ou_calif_total);
        //comentarios = (TextView) findViewById(R.id.ou_comentarios);
        txt_puntuacion = (TextView)findViewById(R.id.ou_txt_puntuacion);

        estrella.setImageResource(R.drawable.estrella_vacia);
        foto_perfil.setImageResource(R.drawable.foto_perfil);

        // seteamos la calificacion dependiendo de la calificacion en la base de datos
        cambiarColor(estrella, (float) punt);

        btn_calificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * aca iria:
                *
                * if(UsuarioNotCalificaAOtroUsuario){
                *   goCalificar(v);
                * }else{
                *   funciones.mostrarToastCorto("Ya has calificado a este usuario");
                * }
                *
                 */

                goCalificar(v);
            }
        });
    }

    //funcion que pasa a pantalla de calificacion de usuario
    private void goCalificar(View v){
        funciones.playSoundPickButton();
        Intent pantalla = new Intent(getApplicationContext(), CalificarUsuario.class);
        pantalla.putExtra("comentarios", comments);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in,R.anim.left_out).toBundle();
        startActivityForResult(pantalla, REQUEST_COMENTAR,bndlanimation);
    }

    @Override
    public void onBackPressed()
    {
        OpinionUsuario.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_COMENTAR && resultCode == RESULT_OK) {

            switch(requestCode) {
                case (STATIC_INTEGER_VALUE): {
                    if (resultCode == Activity.RESULT_OK) {

                        //tomamos los datos del edittext y la calificacion de la activity calificarUsuario
                        String newText = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
                        Integer calificacion = data.getIntExtra("Calificacion",PUBLIC_STATIC_INT_CALIFICACION);
                        comments = newText;

                        //agregamos los datos a las lista correspondientes
                        coments_list.add(comments);
                        punt_list.add(calificacion);

                        for (int i = 0;i<coments_list.size();i++){

                            comments = coments_list.get(i)+"\n";
                        }

                        // actualizamos todos los valores
                        //actualizarComentarios(coments_list);
                        mostrarListaComentarios(coments_list);
                        actualizarPuntuacion(calificacion);
                        float promedio = promedioPuntuacion(punt_list);
                        cambiarColor(estrella,promedio);
                    }
                    break;
                }
            }

        }


    }

    private void mostrarListaComentarios(ArrayList<String> lista){
        try {
            // seteamos un adaptador para la lista de comentarios
            adapter = new ArrayAdapter<String>(OpinionUsuario.this, android.R.layout.simple_list_item_1);

            //recorremos la lista total de comentarios y los asignamos al adaptador anterior
            for(int i = 0;i<lista.size();i++){
                this.adapter.add(lista.get(i));
            }

            // seteamos el adaptador en la lista
            lista_comentarios.setAdapter(adapter);
        }catch (Exception e){
           funciones.mostrarToastCorto("Se ha producido un error");
        }
    }

    private void actualizarPuntuacion(Integer puntuacion){
        this.punt += puntuacion;
    }

    private float promedioPuntuacion(ArrayList<Integer> lista){
        float promedio = (float) 0.0;
        int total = lista.size();
        // si no hay al menos 10 calificaciones de usuario, se retorna un valor inferior a 1.0
        if(total < 10){
            return (float) 0.5;
        }

        float suma = (float) 0.0;

        for (int i = 0;i<lista.size();i++){
            suma += lista.get(i);
        }
        promedio = suma/(float)total;

        return promedio;
    }

    private void cambiarColor(ImageView estrella, float num) {

        estrella.setImageResource(R.drawable.estrella_vacia);
        if(num < 1.0){
            estrella.setImageResource(R.drawable.estrella_vacia);
        }
        if (num >= 1.0 && num < 1.5) {
            estrella.setImageResource(R.drawable.estrella1);
        } else if (num >= 1.5 && num < 2.5) {
            estrella.setImageResource(R.drawable.estrella2);
        } else if (num >= 2.5 && num < 3.5) {
            estrella.setImageResource(R.drawable.estrella3);
        } else if (num >= 3.5 && num < 4.5) {
            estrella.setImageResource(R.drawable.estrella4);
        } else if (num >= 4.5 && num <= 5.0) {
            estrella.setImageResource(R.drawable.estrella5);
        }
        setTextoPuntuacion(num);
    }

    private void setTextoPuntuacion(float num){
        if(num < 1.0){
            txt_puntuacion.setText("Este usuario aun no tiene suficientes calificaciones");
        }else{
            txt_puntuacion.setText("Puntuacion del usuario: '"+(String.valueOf(num))+ "' de un total de: 5.0");
        }
    }

    /*private void actualizarComentarios(ArrayList<String> comentario){
        String temp = "";

        for(int i=0;i<comentario.size();i++){
            temp = temp + comentario.get(i)+"\n";

        }
        this.comentarios.setText(temp);
    }*/
}
