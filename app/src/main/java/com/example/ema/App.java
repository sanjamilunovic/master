package com.example.ema;

import android.app.Application;

import com.bugsnag.android.Bugsnag;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bugsnag.init(this);
    }
}
