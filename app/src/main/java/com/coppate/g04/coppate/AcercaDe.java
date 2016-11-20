package com.coppate.g04.coppate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AcercaDe extends AppCompatActivity {

    Button volver;
    TextView acerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        acerca = (TextView)findViewById(R.id.ad_acerca);
        volver = (Button)findViewById(R.id.ad_volver);

        acerca.setText("\n\nAplicación desarrollada por los estudiantes de la universidad de Ezeiza\n\n\n" +
                "Juan Nuñez         Guillermo Bazzi\n\n" +
                "Luis Cikotik       Daniel Enciso\n\n" +
                "Gabriel Cordova    Rosario Barrientos\n\n" +
                "Julian Arsuaga\n\n" +
                "para la materia Desarrollo de Software dictada por los profesores\n\n\n" +
                "Ing. Sebastian Ramirez\n\n" +
                "Ing. Federico Brucchieri");

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // hay actualizar en la base de datos una persona que se ha sumado al evento.
                Dialog customDialog = null;
                customDialog = new Dialog(AcercaDe.this,R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setCancelable(false);
                customDialog.setContentView(R.layout.message);
                TextView titulo = (TextView) customDialog.findViewById(R.id.message_titulo);
                titulo.setText("Agradecidos");

                // creamos y mostramos el mensaje que deseamos visualizar
                TextView contenido = (TextView) customDialog.findViewById(R.id.message_contenido);
                contenido.setText("Muchas gracias por Conocernos. Hasta luego");

                // seteamos el texto del boton afirmativo como el texto del propio boton
                Button aceptar = (Button) customDialog.findViewById(R.id.message_aceptar);
                aceptar.setText("Aceptar");
                aceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        AcercaDe.this.finish();
                        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

                    }
                });
                customDialog.show();

                /*new android.app.AlertDialog.Builder(AcercaDe.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Agradecidos")
                        .setIcon(R.drawable.icono32)
                        .setMessage("Muchas Gracias por Conocernos. Hasta luego")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //salimos de la aplicacion al pulsar en la afirmacion
                                AcercaDe.this.finish();
                            }
                        })
                        .show();
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setMessage("Muchas Gracias por Conocernos. Hasta luego")
                        .setTitle("Saludos")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });*/
            }
        });
    }
    public void onBackPressed() {
        AcercaDe.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
