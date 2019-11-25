package com.example.mediabrowser;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static com.example.mediabrowser.MyWidgetProvider.BUTTTON_TYPE;

public class UpdateWidgetService extends Service {
    private static final String TAG = "music";

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
    }

    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        Log.i(TAG, "onStart :");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            int buttonType = intent.getIntExtra(BUTTTON_TYPE, 0);

            Log.i(TAG, "buttonType :" + buttonType);
            if (buttonType == MyWidgetProvider.ACTION_BUTTTON_PLAY) {

            } else if (buttonType == MyWidgetProvider.ACTION_BUTTTON_PAUSE) {

            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}