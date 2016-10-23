package com.coppate.g04.coppate;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

//Aplicación peincipal

/**
 * Created by Juan on 08/09/2016.
 */

public class coppateApp extends Application {

    @Override
    //Metodo inicializa el sdk de Facebook
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }
}


