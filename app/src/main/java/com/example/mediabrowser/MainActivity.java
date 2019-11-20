package com.example.mediabrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.media.browse.MediaBrowser;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaControllerCompat mMediaController;
    private MediaBrowserCompat mMediaBrowser;


    private String mServicePackageName = "com.android.car.media.localmediaplayer";
    private String mserviceClassPath = "com.android.car.media.localmediaplayer.LocalMediaBrowserService";
    private TextView mSongView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSongView = findViewById(R.id.song);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MediaBrowserConnectionCallback mMediaConnectionCallback = new MediaBrowserConnectionCallback();
        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(mServicePackageName, mserviceClassPath), mMediaConnectionCallback, null);
        mMediaBrowser.connect();
    }

        private class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback{
            @Override
            public void onConnected(){

                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();

                try{
                    mMediaController = new MediaControllerCompat(MainActivity.this,mMediaBrowser.getSessionToken());
                    if(mMediaController != null){
                        Toast.makeText(getApplicationContext(),"Not null",Toast.LENGTH_LONG).show();
                        MediaControllerCompat.TransportControls mediaControls = mMediaController.getTransportControls();

                        if(mediaControls!=null){
                            mediaControls.play();
                            Toast.makeText(getApplicationContext(),"Not null",Toast.LENGTH_LONG).show();
                        }
                        else{
                            mSongView.setText(mMediaController.getMetadata().getDescription().getTitle());
                        }



                    }
                }
                catch (final RemoteException e){
                    Log.d("onConnected","onConnected"+e.toString());
                    throw new RuntimeException(e);
                }
            }


            public MediaBrowserConnectionCallback(){
                super();
            }


            @Override
            public void onConnectionSuspended() {
                super.onConnectionSuspended();
                Toast.makeText(getApplicationContext(),"ConnectionSuspended",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectionFailed() {
                super.onConnectionFailed();
                Toast.makeText(getApplicationContext(),"ConnectionFailed",Toast.LENGTH_LONG).show();
            }
        }

}
