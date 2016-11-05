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

        acerca.setText("Aplicacion desarrollada por \n" +
                "los alumnos de la universidad de Ezeiza\n" +
                "Juan Nuñez\n" +
                "Guillermo Bazzi\n" +
                "Luis Cikotik\n" +
                "Daniel Enciso\n" +
                "Gabriel Cordova\n" +
                "Rosario Barrientos\n" +
                "Julian Arsuaga\n" +
                "para la materia Desarrollo de Software dictada por los profesores\n" +
                "Ing. Sebastian Ramirez\n" +
                "Ing. Federico Brucchieri");

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setMessage("Muchas Gracias por Conocernos. Hasta luego")
                        .setTitle("Saludos")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
            }
        });
    }
    public void onBackPressed() {
        AcercaDe.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
