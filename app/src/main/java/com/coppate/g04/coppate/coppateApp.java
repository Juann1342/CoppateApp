package com.coppate.g04.coppate;

import android.app.Application;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/fuente6.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }
}


