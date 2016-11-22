package com.udacity.stockhawk;

import android.app.Application;

import com.udacity.stockhawk.ui.MainActivity;

import timber.log.Timber;

public class StockHawkApp extends Application {

    public MainActivity mainActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}
