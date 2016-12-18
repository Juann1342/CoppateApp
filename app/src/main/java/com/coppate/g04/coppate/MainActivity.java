package com.coppate.g04.coppate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    private static final int NOTIF_ALERTA_ID = 1;

    // boton que redirige a la actitiy crear evento
    Button probando;

    //ImageButton ircrear;

    TextView txt_mis_eventos;
    Button botonBuscar;
    android.support.v7.app.AlertDialog alert;

    private ProgressDialog progress;

    FloatingActionButton fab;

    // tomamos los ListView para mostrar los eventos cercanos, de otros y propios
    ListView eventos_de_otros;
    ListView lista_mis_eventos;
    ListView lista_eventos_a_participar;

    // generamos las listas para los eventos de otros (el listview que muestra)
    ArrayList<String> lista_eventos_mios = new ArrayList<String>();
    ArrayList<String> lista_eventos_otros_participo = new ArrayList<String>();

    Evento[] eventos;
    private Gson gson = new Gson();

    // creamos los adaptadores para los listview
    ArrayAdapter<String> adaptador;
    ArrayAdapter<String> participo;

    private ArrayList<FilaEvento> arrayEventos;
    private EventoListaAdapter eventoListaAdapter;

    Handler mHandler;

    private LinearLayout LayoutMisEventos;

    //Log
    private final String TAG = getClass().getSimpleName();


    private Adaptador_ViewPagerPrincipal Adaptador_ViewPagerPrincipal;
    private ViewPager ViewPager;

    Boolean actualizar;

    Funciones funciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        funciones = new Funciones(getApplicationContext());

        final LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // Iniciamos la barra de tabs
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayoutPrincipal);
        // agregamos las 2 tabs que tenemos con los eventos cercanos y los propios
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        // Iniciamos el viewPager.
        ViewPager = (ViewPager) findViewById(R.id.ViewPagerPrincipal);
        // Creamos el adaptador, al cual le pasamos por parámetro el gestor de Fragmentos y muy importante, el nº de tabs o secciones que hemos creado.
        Adaptador_ViewPagerPrincipal = new Adaptador_ViewPagerPrincipal(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        // Y los vinculamos.
        ViewPager.setAdapter(Adaptador_ViewPagerPrincipal);

        // Y por último, vinculamos el viewpager con el control de tabs para sincronizar ambos.
        tabLayout.setupWithViewPager(ViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.miseventos2);
        tabLayout.getTabAt(1).setIcon(R.drawable.pin2);
        tabLayout.getTabAt(2).setIcon(R.drawable.icono32);

        ImageView tab = (ImageView) LayoutInflater.from(this).inflate(R.layout.image_boton, null);
        ImageView tab2 = (ImageView)LayoutInflater.from(this).inflate(R.layout.buscar_boton_tab,null);
        ImageView tab3 = (ImageView)LayoutInflater.from(this).inflate(R.layout.tab_eventos_propios,null);

        tabLayout.getTabAt(0).setCustomView(tab2);
        tabLayout.getTabAt(1).setCustomView(tab);
        tabLayout.getTabAt(2).setCustomView(tab3);

        if (AccessToken.getCurrentAccessToken() == null) {  //si no hay sesion iniciada pasa a la pantalla de login
            goLoginScreen();
        }else{
            try {
                Usuario.getInstance().setId_usuario(Profile.getCurrentProfile().getId());
                Usuario.getInstance().setNombre(Profile.getCurrentProfile().getFirstName());
                Usuario.getInstance().setApellido(Profile.getCurrentProfile().getLastName());
                Usuario.getInstance().setEmail(Profile.getCurrentProfile().getName());
                Usuario.getInstance().setFecha_nacimiento("2016-11-11");
                Usuario.getInstance().setId_sexo(1);
                Usuario.getInstance().setAlias(Profile.getCurrentProfile().getName());
                Usuario.getInstance().setFoto(Profile.getCurrentProfile().getProfilePictureUri(128,128));
                funciones.mostrarToastLargo("Hola :" + Usuario.getInstance().getNombre() + " " + Usuario.getInstance().getApellido());
            }catch (Exception e){
                funciones.mostrarToastCorto(e.toString());
            }
        }

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertNoGps();
        }

        if(MisEventos.getInstance().getActualizar()){
            descargar();
        }

        //+*******************************************************************************************

        /*

        Resources res = getResources();

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", getResources().getDrawable(R.drawable.tab1));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", getResources().getDrawable(R.drawable.tab2));
        tabs.addTab(spec);


        tabs.setCurrentTab(1);*/

    }

    // funcion que sirve para crear un hilo en segundo plano mientras se cargan los datos de la base de datos en las listas de pantalla
    public void descargar(){

        progress=new ProgressDialog(this);
        progress.setMessage("Actualizando la lista de eventos....");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIcon(R.drawable.icono32);
        // progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        if(MisEventos.getInstance().getActualizar()){
                            MisEventos.getInstance().setActualizar(false);
                            actualizar = false;
                        }
                        jumpTime += 2;
                        progress.setProgress(jumpTime);
                        sleep(300);
                        if(jumpTime == totalProgressTime){
                            progress.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                    catch (InterruptedException e) {
                        //Log.e(TAG, e.getMessage());
                    }
                }
            }
        };
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    //el la siguiente clase cambiamos la fuente
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.button) {
            LoginManager.getInstance().logOut();
            goLoginScreen(); //cierra sesion y dirige a la pantalla de login
            //return true;
        }
        if (id == R.id.perfil) {
            goPerfil();

        }

        if (id == R.id.mm_acerca_de) {
            goAcercaDe();
        }

        return super.onOptionsItemSelected(item);
    }

    public void goAcercaDe() {
        Intent acerca = new Intent(MainActivity.this, AcercaDe.class);
        startActivity(acerca);
    }  //dirige a la pantalla acerca de


    private void AlertNoGps() { //Funcion que da la opción de activar el gps
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);  //dirige a la pantalla de login y ejecuta solo esa
    }

    private void goPerfil() {
        Intent intent = new Intent(this, perfil.class);
        startActivity(intent);
    }  //dirige a la pantalla Perfil


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // no me funciona el codigo generico aun
            //funciones.mostrarDialogoPregunta("Salir","Estas seguro capo?","Si, pa", "no, pa");


            // creamos un nuevo dialogo de alerta y lo seteamos en transparente
            Dialog customDialog = null;
            customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
            // con este tema personalizado evitamos los bordes por defecto
            //customDialog = new Dialog(this,R.style.AppTheme);
            //deshabilitamos el título por defecto
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //obligamos al usuario a pulsar los botones para cerrarlo
            customDialog.setCancelable(false);
            //establecemos el contenido de nuestro dialog para poder visualizarlo en pantalla
            customDialog.setContentView(R.layout.dialog);

            // creamos y mostramos el titulo en pantalla
            TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
            titulo.setText("Salir de la aplicación");

            // creamos y mostramos el mensaje que deseamos visualizar
            TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
            contenido.setText("Estás seguro que deseas salir de la aplicación?");

            // seteamos el texto del boton afirmativo como el texto del propio boton
            Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
            aceptar.setText(" Si, muy seguro  ");
            aceptar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    // si el usuario presiona en aceptar, se cierra la aplicación
                    MainActivity.this.finish();

                }
            });

            // seteamos el texto del boton negativo como el texto del propio boton
            Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);
            cancelar.setText("No, estoy Coppado");
            final Dialog finalCustomDialog1 = customDialog;
            cancelar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    // si el usuario presiona en aceptar, se cierra el cuadro y vuele al activity que lo llamo.
                    finalCustomDialog1.dismiss();
                }
            });
            customDialog.show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);

    }
}