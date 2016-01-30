package com.midnightcookies.hackucsc;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;


public class VolumeControl extends IntentService {


    public VolumeControl(){
        super("VolumeControlIntentService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        /*// Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        ...
        // Do work here, based on the contents of dataString
        ...*/
    }



}
