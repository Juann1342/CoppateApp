package com.coppate.g04.coppate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.facebook.Profile;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class perfil extends Activity {

    private static final String TAG = "";
    ImageButton foto_perfil;
    EditText nombre;
    EditText fecha_nac;
    EditText apodo;
    Button editar_perfil;
    String fecha_actual = "";

    Funciones funciones;

    private static int TAKE_PICTURE = 1;
    private static int SELECT_PICTURE = 2;
    private Boolean activo = false;

    private static String NOMBRE_FOTO = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        funciones = new Funciones(getApplicationContext());

        foto_perfil = (ImageButton) findViewById(R.id.ap_perfil_pict);
        nombre = (EditText) findViewById(R.id.ap_txt_Mi_nombre);
        fecha_nac = (EditText) findViewById(R.id.ap_txt_birthday);
        apodo = (EditText) findViewById(R.id.ap_apodo);
        editar_perfil = (Button) findViewById(R.id.ap_editar_perfil);

        //foto_perfil.setImageResource(Profile.getCurrentProfile().getProfilePictureUri(320,320));
        foto_perfil.setImageBitmap(getUserPic(Usuario.getInstance().getId_usuario()));

        try {
            fecha_nac.setText(Usuario.getInstance().getFecha_nacimiento());
            apodo.setText(Usuario.getInstance().getAlias());
            nombre.setText(Usuario.getInstance().getNombre());
        }catch (Exception e){
            funciones.mostrarToastCorto("se ha producido un error al cargar los datos de usuario");
        }
        /* tomar datos del perfil de usuario y cargarlos en los campos
            foto_perfil.setImageBitmap(Usuario.getInstance().getFoto());
         */

        editar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.setEnabled(true);
                fecha_nac.setEnabled(true);
                apodo.setEnabled(true);
                activo = true;
            }
        });

        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activo) {
                    tomarFoto();
                }
            }
        });
    }

    /* toma la foto y la asigna pero solo de la camara frontal (debe ser por el tamanio de la camara trasera) */
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            Bitmap bMap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/CoppateImages/" + NOMBRE_FOTO);
            /*Integer ancho = bMap.getWidth();
            Integer alto = bMap.getHeight();
            funciones.mostrarToastCorto("ancho:" +ancho.toString());
            funciones.mostrarToastCorto("alto"+alto.toString());*/
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            foto_perfil.setImageBitmap(bMap);

        }

    }

    // funcion para cargar imagen de perfil de facebook
    public Bitmap getUserPic(String userID) {
        URL imageURL = null;
        Bitmap bitmap = null;
        //Log.d(TAG, "Loading Picture");
        try{
            imageURL = new URL("http://graph.facebook.com/"+userID+"/picture?type=normal");
        }catch (Exception e){
            funciones.mostrarToastCorto("Se ha producido un error al cargar la URL");
        }
        try {
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            //bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageURL).getContent());
        } catch (Exception e) {
            //Log.d("TAG", "Loading Picture FAILED");
            funciones.mostrarToastCorto(imageURL.toString());
            funciones.mostrarToastLargo("Error: "+e.toString());
        }
        return bitmap;
        /*String urldisplay = "http://graph.facebook.com/"+userID+"/picture?type=normal";
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;*/
    }

    public void tomarFoto(){
        try {
            fecha_actual = funciones.getFechaActual();
            NOMBRE_FOTO = "coppate"+fecha_actual+".jpg";
            //Creamos el Intent para llamar a la Camara
            Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //Creamos una carpeta en la memoria del terminal
            File carpetaImagenes = new File(Environment.getExternalStorageDirectory(), "CoppateImages");
            carpetaImagenes.mkdirs();
            //ponemos el nombre de la imagen
            File imagen = new File(carpetaImagenes, NOMBRE_FOTO);
            Uri uriSavedImage = Uri.fromFile(imagen);
            //Le decimos al Intent que queremos grabar la imagen
            camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            //Lanzamos la aplicacion de la camara con retorno (forResult)
            startActivityForResult(camaraIntent, 1);
        } catch (Exception e) {
            funciones.mostrarToastCorto("Se ha producido un error al querer utilizar la camara");
        }
    }

    public void onBackPressed() {
        perfil.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
