package com.coppate.g04.coppate;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultadoBusqueda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);
    }

    //el la siguiente clase cambiamos la fuente
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
