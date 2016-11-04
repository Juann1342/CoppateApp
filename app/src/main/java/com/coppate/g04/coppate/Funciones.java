package com.coppate.g04.coppate;

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.coppate.g04.coppate.R.raw.gotaagua;


/**
 * Created by Jul-note on 22/10/2016.
 */

public class Funciones implements Parcelable {

    Context contexto;


    public Funciones(Context context) {
        setContexto(context);
    }

    private Context getContexto(){
        return this.contexto;
    }

    private void setContexto(Context context){
        this.contexto = context;
    }

    public void mostrarToastLargo(String str) {
        try {
            Toast toast2 =
                    Toast.makeText(getContexto(),
                            str.toString(), Toast.LENGTH_LONG);


            toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

            toast2.show();
        } catch (Exception e) {
            mostrarToastCorto("Error no se ha podido mostrar el texto en pantalla");
        }
    }

    public void mostrarToastCorto(String str) {
        try {
            Toast toast2 =
                    Toast.makeText(getContexto(),
                            str.toString(), Toast.LENGTH_SHORT);


            toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

            toast2.show();
        } catch (Exception e) {
            mostrarToastCorto("Error no se ha podido mostrar el texto en pantalla");
        }
    }

    public void playSoundGotaAgua(View v) {
        MediaPlayer mp = MediaPlayer.create(getContexto(), R.raw.gotaagua);
        mp.start();
    }

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
