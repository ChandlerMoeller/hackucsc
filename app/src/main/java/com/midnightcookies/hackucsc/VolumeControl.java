package com.midnightcookies.hackucsc;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
//import android.app.ActivityManager;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;

public class VolumeControl extends IntentService {

    int recAmplitude;

    public void onStart(Intent intent, int startid) {
        super.onStart(intent, startid);
        Bundle b = intent.getExtras();
        recAmplitude = b.getInt("amplitude");
    }

    public VolumeControl() {
        super("VolumeControlIntentService");
    }

    @Override
    protected void onHandleIntent(Intent volumeIntent) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
        AudioManager audioOutputManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //int currentvolume = SP.getInt("currentvolume", -1);
        int currentvolume = audioOutputManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d("Amplitude3", "" + recAmplitude);
        //Toast.makeText(this, "test if audioflux is running"+audioOutputManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), Toast.LENGTH_LONG).show();
        int pref_max = Integer.parseInt(SP.getString("audio_list_max", "5"));
        int pref_min = Integer.parseInt(SP.getString("audio_list_min", "2"));

        if (SP.getBoolean("audio_switch", false)) {
            if ((audioOutputManager.isBluetoothA2dpOn() || audioOutputManager.isWiredHeadsetOn())) {
                if (currentvolume > pref_max + 1) {
                    //Log.e("status3", ">= Integer.parseInt(SP.getString(audio_list_max, 5)");
                    audioOutputManager.setStreamVolume(AudioManager.STREAM_MUSIC, pref_max, 0);
                    currentvolume = pref_max;
                } else if (currentvolume < pref_min + 1) {
                    //Log.e("status3", "<= Integer.parseInt(SP.getString(audio_list_min, 2)");
                    audioOutputManager.setStreamVolume(AudioManager.STREAM_MUSIC, pref_min, 0);
                    currentvolume = pref_min;
                }

            /*if (currentvolume == -1) {
                audioOutputManager.setStreamVolume(AudioManager.STREAM_MUSIC, pref_min, 0);
                currentvolume = pref_min;
            }*/
                int pref_difference = pref_max - pref_min;
                int limit_minsound_to_pref_min = 100;
                int limit_maxsound_to_pref_max = 2100;
                int limit_difference = limit_maxsound_to_pref_max - limit_minsound_to_pref_min;
                int category_size = 0;
                if (pref_difference != 0) {
                    category_size = limit_difference / pref_difference;
                }
                int[] category_limits = new int[pref_difference];
                int add_to_pref_min = 0;

                int prevamp = SP.getInt("prevamp", 0);
                int comparedrecAmplitude = (recAmplitude + prevamp) / 2;

                for (int i = 0; i < category_limits.length; i++) {
                    if (i == 0) {
                        category_limits[0] = limit_minsound_to_pref_min;
                    } else {
                        category_limits[i] = category_limits[i - 1] + category_size;
                    }
                    if (comparedrecAmplitude > category_limits[i]) {
                        Log.d("catlimiti", "" + category_limits[i]);
                        add_to_pref_min = i;
                    }
                }

                int newaudiolevel = pref_min + add_to_pref_min;
                //Log.d("newaudiolevel", "" + newaudiolevel);
                if (newaudiolevel > currentvolume) {
                    currentvolume = currentvolume + 1;
                    audioOutputManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentvolume, 0);
                }
                if (newaudiolevel < currentvolume) {
                    currentvolume = currentvolume - 1;
                    audioOutputManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentvolume, 0);
                }
                Log.d("newaudiolevel", "current volume: " + currentvolume);

                SharedPreferences.Editor editor = SP.edit();
                editor.putInt("prevamp", recAmplitude);
                editor.commit();





                ///int maxvol = audioOutputManager.getStreamMaxVolume(audioOutputManager.STREAM_MUSIC);
                ///Log.d("maxaudiolevel", "max volume: " + maxvol);
            }
            //Toast.makeText(this, "" + audioOutputManager.getStreamVolume(AudioManager.STREAM_SYSTEM), Toast.LENGTH_LONG).show();
        }
        //ambienceMic.stopRecording();
        //ambienceMic.getFileName();
        //double recAmplitude = ambienceMic.getAmplitude();
        //ambienceMic.deleteFile();
        //Log.e("Log_Tag", "" + recAmplitude);

    }


}
