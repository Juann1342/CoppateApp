
package com.coppate.g04.coppate;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.coppate.g04.coppate.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    Button btn_crear_evento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_crear_evento = (Button)findViewById(R.id.ma_crear_evento);
        btn_crear_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CrearEvento.class);
                startActivity(intent);  //pasa a pantalla de Crear Evento
            }
        });


        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("",
                res.getDrawable(android.R.drawable.ic_menu_search));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("",
                res.getDrawable(android.R.drawable.ic_input_get));
        tabs.addTab(spec);

        /*spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("SALIR",
                res.getDrawable(android.R.drawable.ic_menu_add));
        tabs.addTab(spec);*/

        tabs.setCurrentTab(1);





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







  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(this,"nuevo grupo",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id==R.id.opcion2){
            Toast.makeText(this,"nueva difusión",Toast.LENGTH_LONG).show();
        }

        else if(id==R.id.opcion6){
            Toast.makeText(this,"lupa",Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.opcion7){
            //Toast.makeText(this,"lupa",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }
*/
}

