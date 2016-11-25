package com.coppate.g04.coppate;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Aprobacion_administrador extends AppCompatActivity {

    Button copparse;
    Button rechazar;

    Funciones funciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprobacion_administrador);

        copparse = (Button)findViewById(R.id.aa_copparse);
        rechazar = (Button)findViewById(R.id.aa_rechazar);

        funciones = new Funciones(getApplicationContext());

        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.reingreso, R.anim.nothing);
            }
        });

        copparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funciones.playSoundPickButton();

                // hay actualizar en la base de datos una persona que se ha sumado al evento.
                Dialog customDialog = null;
                customDialog = new Dialog(Aprobacion_administrador.this,R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setCancelable(false);
                customDialog.setContentView(R.layout.message);
                TextView titulo = (TextView) customDialog.findViewById(R.id.message_titulo);
                titulo.setText("Agregar a Evento");

                // creamos y mostramos el mensaje que deseamos visualizar
                TextView contenido = (TextView) customDialog.findViewById(R.id.message_contenido);
                contenido.setText("Agregaste el usuario al evento");

                // seteamos el texto del boton afirmativo como el texto del propio boton
                Button aceptar = (Button) customDialog.findViewById(R.id.message_aceptar);
                aceptar.setText("Coppado");
                aceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        Aprobacion_administrador.this.finish();
                        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

                    }
                });
                customDialog.show();
            }
        });


    }

    public void onBackPressed() {
        Aprobacion_administrador.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }



}
