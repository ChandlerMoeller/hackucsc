package com.midnightcookies.hackucsc;

import android.app.Activity;
import android.media.MediaRecorder;

import java.io.File;

/**
 * Created by Brian on 1/30/16.
 */
public class Microphone {
    //SetAudioSource in order to store Audio file on phone.
    //Once done we should be able to call Amplitude.

    private Microphone recorder = null;
    File dir;
    String file;

    public Activity activity;

    public void startRecording(){
        if(recorder == null){
            recorder = new Microphone();
            recorder.setAudioSource(Microphone.AudioSource.MIC);

        }

    }



}
