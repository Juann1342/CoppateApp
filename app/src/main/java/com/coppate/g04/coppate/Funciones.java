package com.coppate.g04.coppate;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.coppate.g04.coppate.R.raw.gotaagua;


/**
 * Created by Jul-note on 22/10/2016.
 */

public class Funciones implements Parcelable {

    Context contexto;
    View vista;


    public Funciones(Context context) {
        setContexto(context);
    }

    private Context getContexto(){
        return this.contexto;
    }

    private void setContexto(Context context){
        this.contexto = context;
    }

    private View getVista(){ return this.vista;}

    private void setVista(View v){ this.vista = v;}

    public void mostrarToastLargo(String str) {
        try {
            Toast toast2 =
                    Toast.makeText(getContexto(),
                            str.toString(), Toast.LENGTH_LONG);


            toast2.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);

            toast2.show();
        } catch (Exception e) {
            mostrarToastCorto("Error no se ha podido mostrar el texto en pantalla: "+getContexto().toString());
        }
    }

    public void mostrarToastCorto(String str) {
        try {
            Toast toast2 =
                    Toast.makeText(getContexto(),
                            str.toString(), Toast.LENGTH_SHORT);


            toast2.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);

            toast2.show();
        } catch (Exception e) {
            mostrarToastCorto("Error no se ha podido mostrar el texto en pantalla");
        }
    }

    public void playSoundGotaAgua() {
        MediaPlayer mp = MediaPlayer.create(getContexto(), R.raw.gotaagua);
        mp.start();
    }

    public void playSoundPickButton() {
        MediaPlayer mp = MediaPlayer.create(getContexto(), R.raw.pickbutton);
        mp.start();
    }

    public void mostrarFecha(){
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(contexto, formattedDate, Toast.LENGTH_SHORT).show();
    }

    // por ahora no funcionan los dialogos

    /*public int mostrarDialogoPregunta(String titulo,String mensaje, String no, String si){
        final int[] ok = {0};
        new android.app.AlertDialog.Builder(getContexto())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(titulo)
                .setIcon(R.drawable.icono32)
                .setMessage(mensaje)
                .setNegativeButton(no, null)//sin listener
                .setPositiveButton(si, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //salimos de la aplicacion al pulsar en la afirmacion
                        ok[0] = 1;
                    }
                })
                .show();
        return ok[0];
    }

    public void mostrarDialogoAdvertencia(String titulo,String mensaje, String aceptar){
        android.app.AlertDialog.Builder dialogo1 = new android.app.AlertDialog.Builder(getContexto());
        dialogo1.setTitle(titulo);
        dialogo1.setMessage(mensaje);
        dialogo1.setCancelable(false);
        dialogo1.setIcon(R.drawable.icono32);
        dialogo1.setPositiveButton(aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }*/

    public String getFechaActual() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    public String transformarFecha(String fecha){
        ;
        String[] separated = fecha.split("/");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = df.format(separated[2]+separated[1]+separated[0]);

        return formattedDate;
    }

    // no funciona aun la funcion de mostrar dialogo pregunta.
    public Boolean mostrarDialogoPregunta(String titulo,String pregunta,String si, String no){
        // creamos una variable final con el resulta a retornar
        final Boolean[] retorno = {null};

        // creamos un nuevo dialogo de alerta y lo seteamos en transparente
        Dialog customDialog = null;
        customDialog = new Dialog(getContexto(),R.style.Theme_Dialog_Translucent);
        // con este tema personalizado evitamos los bordes por defecto
        //customDialog = new Dialog(this,R.style.AppTheme);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialog);

        // creamos y mostramos el titulo en pantalla
        TextView title = (TextView) customDialog.findViewById(R.id.titulo);
        title.setText(titulo);

        // creamos y mostramos el mensaje que deseamos visualizar
        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText(pregunta);

        // seteamos el texto del boton afirmativo como el texto del propio boton
        Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
        aceptar.setText(si);
        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                // si el usuario presiona en aceptar, se reemplaza la variable por aceptar
                retorno[0] = true;
            }
        });

        // seteamos el texto del boton negativo como el texto del propio boton
        Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);
        cancelar.setText("Nope");
        final Dialog finalCustomDialog1 = customDialog;
        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                // mismo anterior pero en negativo
                retorno[0] = false;
            }
        });
        customDialog.show();
        return retorno[0];
    }

        /*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        mostrarToastCorto(dateFormat.format(date).toString());
        return dateFormat.format(date);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // Now formattedDate have current date/time
        Toast.makeText(contexto, formattedDate, Toast.LENGTH_SHORT).show();

    } */

    protected Funciones(Parcel in) {
    }

    public static final Creator<Funciones> CREATOR = new Creator<Funciones>() {
        @Override
        public Funciones createFromParcel(Parcel in) {
            return new Funciones(in);
        }

        @Override
        public Funciones[] newArray(int size) {
            return new Funciones[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

}
