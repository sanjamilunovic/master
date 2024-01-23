package com.example.ema;

import android.app.Application;

import com.bugsnag.android.Bugsnag;

public class App extends Application {

    @Override
    public void onCreate() {
        try{
        super.onCreate();
            Bugsnag.start(getApplicationContext());
        }catch(Exception ex){
            ex.printStackTrace();
            Bugsnag.notify(ex);
        }

    }
}
