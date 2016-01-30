package com.midnightcookies.hackucsc;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.MediaStore;
//import android.app.ActivityManager;


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

        //while( /*volume limiter is enabled*/ ){
        if (isBluetoothA2dpOn() || AudioManager.isWiredHeadsetOn()) {
          //while( /*audio exceeds threshold*/ ){
            //AudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, );
          //}
        }
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
