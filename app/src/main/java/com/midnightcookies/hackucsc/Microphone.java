package com.midnightcookies.hackucsc;

import android.app.Activity;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Brian on 1/30/16.
 */
public class Microphone {
    //SetAudioSource in order to store Audio file on phone.
    //Once done we should be able to call Amplitude.

    private MediaRecorder recorder = null;
    File dir;
    String file;

    public Activity activity;

    public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(getFileName()); //Create a func to write on sd card
            try {
                recorder.prepare();
            } catch (IOException e) {
                //Generate a stackTrace if fail
                Log.e("Log_Tag", "prepare() failed");
            }
            recorder.start();
        }
    }


    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    private String getFileName() {
        dir = new File("/sdcard", "AUDIOAMP");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = dir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp3";
        return (file);
    }

    private double getAmplitude() {
        if (recorder != null) {
            double amp = recorder.getMaxAmplitude();
            return (amp);
        } else {

            return 0;
        }
    }

    private void deleteFile(){
        File sdcard = new File(file);
        sdcard.delete();
    }
}



