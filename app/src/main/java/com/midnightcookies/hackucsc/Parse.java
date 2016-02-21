package com.midnightcookies.hackucsc;

import android.app.Application;

/**
 * Created by Chandler on 2/10/2016.
 */
public class Parse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize Parse
        com.parse.Parse.enableLocalDatastore(this);
        com.parse.Parse.initialize(this);

    }
}
