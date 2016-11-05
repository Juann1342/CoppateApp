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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class perfil extends Activity {

    ImageButton foto_perfil;
    EditText nombre;
    EditText fecha_nac;
    EditText apodo;
    FloatingActionButton editar_perfil;

    Funciones funciones;

    private static int TAKE_PICTURE = 1;
    private static int SELECT_PICTURE = 2;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        foto_perfil = (ImageButton) findViewById(R.id.ap_perfil_pict);
        nombre = (EditText) findViewById(R.id.ap_txt_Mi_nombre);
        fecha_nac = (EditText) findViewById(R.id.ap_txt_birthday);
        apodo = (EditText) findViewById(R.id.ap_apodo);
        editar_perfil = (FloatingActionButton) findViewById(R.id.ap_editar_perfil);

        editar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.setEnabled(true);
                fecha_nac.setEnabled(true);
                apodo.setEnabled(true);
                foto_perfil.setEnabled(true);
            }
        });

        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //Creamos el Intent para llamar a la Camara
                    Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    //Creamos una carpeta en la memoria del terminal
                    File carpetaImagenes = new File(Environment.getExternalStorageDirectory(), "CoppateImages");
                    carpetaImagenes.mkdirs();
                    //ponemos el nombre de la imagen
                    File imagen = new File(carpetaImagenes, "foto.jpg");
                    Uri uriSavedImage = Uri.fromFile(imagen);
                    //Le decimos al Intent que queremos grabar la imagen
                    camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                    //Lanzamos la aplicacion de la camara con retorno (forResult)
                    startActivityForResult(camaraIntent, 1);
                } catch (Exception e) {
                    funciones.mostrarToastCorto("Se ha producido un error al querer utilizar la camara");
                }
            }
        });

                /*int code = TAKE_PICTURE;
                Intent tomar_foto =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(tomar_foto,code);*/
    }

    /* hay que hacer que tome la foto.*/
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            Bitmap bMap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/CoppateImages/" + "foto.jpg");
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            foto_perfil.setImageBitmap(bMap);
        }

    }

    public void onBackPressed() {
        perfil.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
