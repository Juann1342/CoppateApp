package com.coppate.g04.coppate;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        acerca.setText("Aplicacion desarrollada por los alumnos de la universidad de Ezeiza\n\n" +
                "Juan Nuñez\n\n" +
                "Guillermo Bazzi\n\n" +
                "Luis Cikotik\n\n" +
                "Daniel Enciso\n\n" +
                "Gabriel Cordova\n\n" +
                "Rosario Barrientos\n\n" +
                "Julian Arsuaga\n\n" +
                "para la materia Desarrollo de Software dictada por los profesores\n\n" +
                "Ing. Sebastian Ramirez\n\n" +
                "Ing. Federico Brucchieri");

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(AcercaDe.this)
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
