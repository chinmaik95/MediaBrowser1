package com.example.mediabrowser;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static com.example.mediabrowser.MyWidgetProvider.BUTTTON_TYPE;

public class UpdateWidgetService extends Service {
    private static final String TAG = "music";

    private MediaControllerCallback mMediaControllerCallback;


    private MediaControllerCompat mMediaController;
    private MediaBrowserCompat mMediaBrowser;


    private String mServicePackageName = "com.android.car.media.localmediaplayer";
    private String mserviceClassPath = "com.android.car.media.localmediaplayer.LocalMediaBrowserService";
////////////////////////////////////////////////////////////////////////
    /**
     * This class is responsible for the implementation of Media Controller callback methods.
     */
    private class MediaControllerCallback extends MediaControllerCompat.Callback {
        @Override
        public void onSessionEvent(final String event, final Bundle extras) {
            super.onSessionEvent(event, extras);

        }

        @Override
        public void onSessionReady() {
            super.onSessionReady();
            Toast.makeText(getApplicationContext(), "OnSessionReaddy", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onMetadataChanged(final MediaMetadataCompat metadata) {

            if (metadata != null) {
                // To set and expose media metadata through interface.

                Toast.makeText(getApplicationContext(), metadata.getDescription().getTitle(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onPlaybackStateChanged(final PlaybackStateCompat state) {

            if (state != null) {
                // To set and expose media playback state through interface.
                Toast.makeText(getApplicationContext(), state.getState() + "", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            // we set the callbacks to null when the session is destroyed.

            Toast.makeText(getApplicationContext(), "OnSessionDestroyed", Toast.LENGTH_LONG).show();
        }


        @Override
        public void onQueueChanged(final List<MediaSessionCompat.QueueItem> queue) {
            super.onQueueChanged(queue);

            Toast.makeText(getApplicationContext(), "OnSessionDestroyed", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onCreate() {
        mMediaControllerCallback = new MediaControllerCallback();

        Context applicationContext = getApplicationContext();
        MediaBrowserConnectionCallback mMediaConnectionCallback = new MediaBrowserConnectionCallback();
        mMediaBrowser = new MediaBrowserCompat(applicationContext, new ComponentName(mServicePackageName, mserviceClassPath),
                mMediaConnectionCallback, null);
        mMediaBrowser.connect();

        Log.e(TAG, "onCreate()");
    }

    private class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        @Override
        public void onConnected() {

            try {
                mMediaController = new MediaControllerCompat(getApplicationContext(), mMediaBrowser.getSessionToken());


                if (mMediaController != null) {


                    mMediaController.registerCallback(mMediaControllerCallback);
                    MediaControllerCompat.TransportControls mediaControls = mMediaController.getTransportControls();


                    if (mediaControls != null) {
                        Toast.makeText(getApplicationContext(), "transport controls not null", Toast.LENGTH_LONG).show();

                        mMediaControllerCallback.onPlaybackStateChanged(mMediaController.getPlaybackState());
                        mMediaControllerCallback.onMetadataChanged(mMediaController.getMetadata());
                    }

                }


            } catch (final RemoteException e) {
                Log.d("onConnected", "onConnected" + e.toString());
                throw new RuntimeException(e);
            }
        }

        public MediaBrowserConnectionCallback() {
            super();
        }

        @Override
        public void onConnectionSuspended() {
            super.onConnectionSuspended();

            Toast.makeText(getApplicationContext(), "Connection Suspended", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onConnectionFailed() {
            super.onConnectionFailed();

            Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_LONG).show();
        }
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