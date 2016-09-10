package com.coppate.juan.coppate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(AccessToken.getCurrentAccessToken()==null){  //si no hay sesion iniciada pasa a la pantalla de login
            goLoginScreen();
        }
    }
    private void goLoginScreen() {
        Intent intent = new Intent(this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);  //dirige a la pantalla de login y ejecuta solo esa
    }
    public void logout(View view){
        LoginManager.getInstance().logOut();;
        goLoginScreen(); //cierra sesion y dirige a la pantalla de login
    }
}


