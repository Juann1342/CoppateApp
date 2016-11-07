package com.coppate.g04.coppate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PerfilOtrosUser extends AppCompatActivity {

    private Button comentar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_otros_user);


        comentar = (Button) findViewById(R.id.opinion);
        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilOtrosUser.this, OpinionUsuario.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_in, R.anim.left_out).toBundle();
                startActivity(intent, bndlanimation);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        PerfilOtrosUser.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);
    }
}