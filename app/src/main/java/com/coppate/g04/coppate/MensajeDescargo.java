package com.coppate.g04.coppate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MensajeDescargo extends AppCompatActivity {

    //declaramos el boton aceptar para darle funcionalidad
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_descargo);


        //le damos la funcionalidad para que busque el id y demas cosas
        aceptar = (Button) findViewById(R.id.bot_aceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            //metodo onclick para que funcione al darle click sobre el boton
            @Override
            public void onClick(View v) {
                goMainScreen();
            }
        });

    }

    private void goMainScreen() {
        Intent intent = new Intent(MensajeDescargo.this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);  //pasa a la pantalla de inicio
    }

    public void onBackPressed() {
        MensajeDescargo.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }

    //el la siguiente clase cambiamos la fuente
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
