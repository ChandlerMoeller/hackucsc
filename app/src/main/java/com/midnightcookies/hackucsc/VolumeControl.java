package com.midnightcookies.hackucsc;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.MediaStore;
//import android.app.ActivityManager;
import android.widget.Toast;
import android.content.Context;


public class VolumeControl extends IntentService {

    public VolumeControl(){
        super("VolumeControlIntentService");
    }

    @Override
    protected void onHandleIntent(Intent volumeIntent) {
        /*// Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        ...
        // Do work here, based on the contents of dataString
        ...*/
        AudioManager audioOutputManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //while( /*volume limiter is enabled*/ ){

        Toast.makeText(this, "test if audioflux is running", Toast.LENGTH_LONG).show();
        audioOutputManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER, 0);

        //if (audioOutputManager.isBluetoothA2dpOn() || audioOutputManager.isWiredHeadsetOn()) {
          //while( /*audio exceeds threshold*/  audioOutputManager.getStreamVolume(audioOutputManager.STREAM_MUSIC) ){
            //audioOutputManager.setStreamVolume(audioOutputManager.STREAM_MUSIC, audioOutputManager.ADJUST_LOWER, 0);
          //}
        //}
        //}
    }

    /*public boolean isTaskRunning(String thisApp) {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningAppProcesses();
        for(int i = 0; i < taskInfo.size(); i++){
            if (taskInfo.get(i).processName.equals(thisApp)) {
                return true;
            }
        }
        return false;
    }*/

}
