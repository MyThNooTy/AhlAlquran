package com.samcotec.ahlalquran;

import android.app.Application;

/**
 * Created by MyTh on 12/10/2017.
 */

public class App extends Application {
    private static App app  = null;


    public void onCreate() {
        super.onCreate();
        app = this;

    }

    public static App getApp() {
        return app;
    }
}