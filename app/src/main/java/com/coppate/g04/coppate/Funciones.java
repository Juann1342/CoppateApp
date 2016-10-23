package com.coppate.g04.coppate;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Jul-note on 22/10/2016.
 */

public class Funciones implements Parcelable {

    public Funciones() {
    }

    public void mostrarToastLargo(String str, Context context) {
        try {
            Toast toast2 =
                    Toast.makeText(context,
                            str.toString(), Toast.LENGTH_LONG);


            toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

            toast2.show();
        } catch (Exception e) {
            mostrarToastCorto("Error no se ha podido mostrar el texto en pantalla", context);
        }
    }

    public void mostrarToastCorto(String str, Context context) {
        try {
            Toast toast2 =
                    Toast.makeText(context,
                            str.toString(), Toast.LENGTH_SHORT);


            toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

            toast2.show();
        } catch (Exception e) {
            mostrarToastCorto("Error no se ha podido mostrar el texto en pantalla", context);
        }
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
