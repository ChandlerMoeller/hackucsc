package com.midnightcookies.hackucsc;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
//import android.app.ActivityManager;
import android.widget.Toast;
import android.content.Context;

public class VolumeControl extends IntentService {

    public VolumeControl(){
        super("VolumeControlIntentService");
    }

    @Override
    protected void onHandleIntent(Intent volumeIntent) {

        AudioManager audioOutputManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Toast.makeText(this, "test if audioflux is running"+audioOutputManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), Toast.LENGTH_LONG).show();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
            if (SP.getBoolean("musicsharing_switch", false)) {
                if (!(audioOutputManager.isBluetoothA2dpOn() || audioOutputManager.isWiredHeadsetOn())) {
                    if (audioOutputManager.getStreamVolume(AudioManager.STREAM_SYSTEM) >= Integer.parseInt(SP.getString("audio_list_max","5"))) {
                        audioOutputManager.setStreamVolume(AudioManager.STREAM_SYSTEM, Integer.parseInt(SP.getString("audio_list_max", "5")), 0);
                    }
                    if (audioOutputManager.getStreamVolume(AudioManager.STREAM_SYSTEM) <= Integer.parseInt(SP.getString("audio_list_min", "2"))) {
                        audioOutputManager.setStreamVolume(AudioManager.STREAM_SYSTEM, Integer.parseInt(SP.getString("audio_list_min", "2")), 0);
                    }
                }
                //Toast.makeText(this, "" + audioOutputManager.getStreamVolume(AudioManager.STREAM_SYSTEM), Toast.LENGTH_LONG).show();
            }
    }

}
