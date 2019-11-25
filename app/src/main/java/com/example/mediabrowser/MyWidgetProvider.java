package com.example.mediabrowser;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {


    public static final String BUTTTON_PLAY = "butttonPlay";
    public static final String BUTTTON_BACK = "butttonBack";
    public static final String BUTTTON_TYPE = "butttonType";
    public static final int ACTION_BUTTTON_PLAY = 1;
    public static final int ACTION_BUTTTON_PAUSE = 2;


    private RemoteViews remoteViews;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.update,
                    "Back");
            remoteViews.setTextViewText(R.id.button2,
                    "Play");
            remoteViews.setTextViewText(R.id.button3,
                    "Next");

            Intent intent = new Intent(context, MyWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setOnClickPendingIntent(R.id.update,
                    getPendingSelfIntent(context, BUTTTON_BACK));
            remoteViews.setOnClickPendingIntent(R.id.button2,
                    getPendingSelfIntent(context, BUTTTON_PLAY));

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }



        // Build the intent to call the service
        Intent intent = new Intent(context.getApplicationContext(),
                UpdateWidgetService.class);
        context.startService(intent);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        int butttonType = 0;
        if (BUTTTON_PLAY.equals(intent.getAction())) {
            Toast.makeText(context, "Click play", Toast.LENGTH_SHORT).show();

            butttonType = ACTION_BUTTTON_PLAY;
        } else if (BUTTTON_BACK.equals(intent.getAction())) {
            butttonType = ACTION_BUTTTON_PAUSE;
            Toast.makeText(context, "Click back", Toast.LENGTH_SHORT).show();
        }


        Intent serviceIntent = new Intent(context.getApplicationContext(),
                UpdateWidgetService.class);
        serviceIntent.putExtra(BUTTTON_TYPE, butttonType);
        context.startService(serviceIntent);

    }
}
