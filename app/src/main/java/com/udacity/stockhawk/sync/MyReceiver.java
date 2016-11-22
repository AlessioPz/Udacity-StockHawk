package com.udacity.stockhawk.sync;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.udacity.stockhawk.StockHawkApp;
import com.udacity.stockhawk.ui.MainActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msgError = intent.getStringExtra("error_updating");
        if (msgError != null && !msgError.isEmpty()) {

            ((StockHawkApp)context.getApplicationContext()).mainActivity.showMessage(msgError);

        }
    }
}
