package com.coppate.g04.coppate;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Jul-note on 22/10/2016.
 */

public class CalificarUsuario extends Activity {

    private static final String PUBLIC_STATIC_STRING_IDENTIFIER = "";
    private static Integer PUBLIC_STATIC_INT_CALIFICACION = 0;
    ImageView foto_perfil;
    ImageView estrella1;
    ImageView estrella2;
    ImageView estrella3;
    ImageView estrella4;
    ImageView estrella5;
    Button comentar;
    EditText comentario;
    Integer puntuacion;
    Funciones funciones;
    String coments;
    Bundle b;

    public  static String RESULTADO = "resultado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_usuario);

        // le seteamos el contexto a las funciones generales
        funciones =  new Funciones(getApplicationContext());

        // la variable Bundle nos sirve para recuperar lo que nos mandaron desde la otra activity
        b = getIntent().getExtras();
        final String[] comentario_actual = {null};
        final String[] coment = {""};

        final Intent pantalla_anterior = getIntent();

        //recuperamos los datos de los comentarios
        coments = b.getString("comentarios");

        //la puntuacion la tenemos que obtener de la base de datos, es solo de prueba
        puntuacion = 0;

        comentar = (Button) findViewById(R.id.cu_btn_comentar);
        comentario = (EditText) findViewById(R.id.cu_txt_comentario);
        foto_perfil = (ImageView) findViewById(R.id.cu_perfil_pict);
        estrella1 = (ImageView) findViewById(R.id.cu_estrella1);
        estrella2 = (ImageView) findViewById(R.id.cu_estrella2);
        estrella3 = (ImageView) findViewById(R.id.cu_estrella3);
        estrella4 = (ImageView) findViewById(R.id.cu_estrella4);
        estrella5 = (ImageView) findViewById(R.id.cu_estrella5);

        estrella1.setImageResource(R.drawable.estrella_vacia);
        estrella2.setImageResource(R.drawable.estrella_vacia);
        estrella3.setImageResource(R.drawable.estrella_vacia);
        estrella4.setImageResource(R.drawable.estrella_vacia);
        estrella5.setImageResource(R.drawable.estrella_vacia);
        foto_perfil.setImageResource(R.drawable.foto_perfil);


        estrella1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarColor(estrella1, 1);
                puntuacion = 1;
            }
        });

        estrella2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarColor(estrella2, 2);
                puntuacion = 2;
            }
        });
        estrella3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarColor(estrella3, 3);
                puntuacion = 3;
            }
        });
        estrella4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarColor(estrella4, 4);
                puntuacion = 4;
            }
        });
        estrella5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarColor(estrella5, 5);
                puntuacion = 5;
            }
        });
        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    funciones.playSoundPickButton();
                    //comentario_actual[0] = comentario.getText().toString();
                    if (puntuacion == 0) {
                        funciones.mostrarToastCorto("No se ha seleccionado una puntuacion");
                    }
                    else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, getTextoDelComentario());
                        resultIntent.putExtra("Calificacion", getPuntuacion());
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        overridePendingTransition(R.anim.reingreso, R.anim.nothing);
                    }
                }catch (Exception e){
                    funciones.mostrarToastCorto("Error");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        CalificarUsuario.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }

    private void cambiarColor(ImageView estrella, int num) {

        // se que aca va un patron pero no me acuerdo bien cual puede ser

        estrella1.setImageResource(R.drawable.estrella_vacia);
        estrella2.setImageResource(R.drawable.estrella_vacia);
        estrella3.setImageResource(R.drawable.estrella_vacia);
        estrella4.setImageResource(R.drawable.estrella_vacia);
        estrella5.setImageResource(R.drawable.estrella_vacia);

        if (num == 1) {
            estrella1.setImageResource(R.drawable.estrella1);
        }
        if (num == 2) {
            estrella2.setImageResource(R.drawable.estrella2);
        }
        if (num == 3) {
            estrella3.setImageResource(R.drawable.estrella3);
        }
        if (num == 4) {
            estrella4.setImageResource(R.drawable.estrella4);
        }
        if (num == 5) {
            estrella5.setImageResource(R.drawable.estrella5);
        }
    }

    private Integer getPuntuacion(){

        return this.puntuacion;
    }

    private void agregarComentario(String comentario) {
        this.coments = this.coments + comentario;
    }

    private String getTextoDelComentario(){
        String comentario = "";
        comentario = this.comentario.getText().toString();
        return comentario;
    }

}
