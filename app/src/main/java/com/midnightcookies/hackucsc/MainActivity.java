package com.midnightcookies.hackucsc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import android.provider.Settings.Secure;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation = null;

    /*Array Genres = null;
    String Global_songTitle = null;
    String userName = null;*/
    String[] Genres = {"rock1", "rock2"};
    JSONArray json = new JSONArray(Arrays.asList(Genres));

    String Global_songTitle;
    String Global_songAlbum;
    String Global_songArtist;
    String userName = "Anonymous";
    Boolean havelocation = false;
    int GetDistance = 5;

    ///protected Microphone ambienceMic;
    protected Microphone ambienceMic = new Microphone();

    protected boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("STATUS", "onCreate");

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = SP.edit();
        editor.putInt("prevamp", 0);
        editor.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*//Initialize Parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);*/

        IntentFilter iF = new IntentFilter();

        iF.addAction("com.spotify.music.metadatachanged");
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.htc.music.metachanged");
        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.sonyericsson.music.metachanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.andrew.apollo.metachanged");

        iF.addAction("com.spotify.android.music.metadatachanged");

        iF.addAction("com.spotify.music.queuechanged");
        iF.addAction("com.spotify.music");
        iF.addAction("com.spotify.mobile.android.queuechanged");
        iF.addAction("com.spotify.music.android.playbackstatechanged");

        registerReceiver(mReceiver, iF);


        ///final Intent VCServiceIntent = new Intent(this, VolumeControl.class);
        ///ambienceMic = new Microphone();

        loop();

        //ambienceMic.stopRecording();

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @Override
    public void onDestroy() {
        Log.e("STATUS", "onDestroy");
        unregisterReceiver(mReceiver);
        ////ambienceMic.stopRecording();

        super.onDestroy();
        finish();
    }

    @Override
    protected void onStart() {
        Log.e("STATUS", "onStart");
        mGoogleApiClient.connect();

        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.e("STATUS", "onStop");
        //ambienceMic.stopRecording();
        //ambienceMic.pauseRecording();
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("STATUS", "onPause");
        //ambienceMic.pauseRecording();
        //ambienceMic.stopRecording();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("STATUS", "onResume");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentsettings = new Intent(this, SettingsActivity.class);
            startActivity(intentsettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {
            Intent intentsettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentsettings);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //
    //Location Services
    //
    @Override
    public void onConnected(Bundle bundle) {
        //Get last known location
        //TODO: replace last known location with location updates
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        havelocation = true;
        //TODO: if (mLastLocation != null) {

        Log.d("Location (Latitude)", String.valueOf(mLastLocation.getLatitude()));
        Log.d("Location (Longitude)", String.valueOf(mLastLocation.getLongitude()));


        //TODO: if (mLastLocation != null) {
        //
        //Get Close Objects
        //
        //Get parameters to pass to parsecloud server
        HashMap<String, ParseGeoPoint> GetCloseObjectsOurHash = new HashMap<>();
        ParseGeoPoint point2 = new ParseGeoPoint(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        GetCloseObjectsOurHash.put("clientGeoPoint", point2);
        //GetCloseObjectsOurHash.put("clientLatitude", mLastLocation.getLatitude());
        //GetCloseObjectsOurHash.put("clientLongitude", mLastLocation.getLongitude());
        //GetCloseObjectsOurHash.put("clientGetDistance", GetDistance);
        //Get Results from cloudlocation function
        ParseCloud.callFunctionInBackground("GetCloseObjects", GetCloseObjectsOurHash, new FunctionCallback<ArrayList>() {
                    @Override
                    public void done(ArrayList object, ParseException e) {
                        if (object != null) {
                            int objectsize = object.size();
                            for (int i = 0; i < objectsize; i++) {
                                Log.d("Location: ", String.valueOf(object.get(i)));
                            }
                        }
                    }
                }
        );
        //

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void loop() {
        final Handler loopVolumeControl = new Handler();
        loopVolumeControl.postDelayed(new Runnable() {
            public void run() {
                ///if (recording == true) {
                //ambienceMic.stopRecording();
                //ambienceMic.getFileName();
                //ambienceMic.deleteFile();
                ///recording = false;
                ///}
                ///ambienceMic.startRecording();
                ///recording = true;

                //Every 3 seconds
                loopVolumeControl.postDelayed(this, 2000);
                ///loopVolumeControl.postDelayed(this, 300);
                ///Log.d("Test", "start service reached");
                ///startService(VCServiceIntent);


                delayRecord delayRecord = new delayRecord();
                delayRecord.execute();
            }
        }, 150);
    }

    public class delayRecord extends AsyncTask<String, Void, String> {
        final Intent VCServiceIntent = new Intent(MainActivity.this, VolumeControl.class);

        protected String doInBackground(String... params) {
            ambienceMic.startRecording();
            recording = true;
            return Double.toString(ambienceMic.getAmplitude());
        }

        protected void onPostExecute(String strAmplitude) {
            double recAmplitudedouble = Double.parseDouble(strAmplitude);
            int recAmplitude = (int) recAmplitudedouble;
            //ambienceMic.stopRecording();
            //ambienceMic.getFileName();
            //ambienceMic.deleteFile();

            ////Log.d("Amplitude", "" + recAmplitude);
            Bundle b = new Bundle();
            b.putInt("amplitude", recAmplitude);
            VCServiceIntent.putExtras(b);
            startService(VCServiceIntent);

            ////ambienceMic.pauseRecording();
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            Log.v("msidentity_tag ", action + " / " + cmd);
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            Global_songTitle = track;
            Global_songAlbum = album;
            Global_songArtist = artist;
            Log.v("msidentity_tag", artist + ":" + album + ":" + track);
            Toast.makeText(MainActivity.this, track, Toast.LENGTH_SHORT).show();

            if (havelocation == true) {
                sharemusic();
            }
        }
    };


    public void sharemusic() {
        //
        //Make New Object
        //
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
        userName = SP.getString("musicsharing_username", "Anonymous");
        ParseObject info = new ParseObject("Info");
        String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        info.put("identifier", android_id);
        ParseGeoPoint point = new ParseGeoPoint(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        info.put("locationpoint", point);
        info.put("Genres", json);
        info.put("songTitle", Global_songTitle);
        info.put("songAlbum", Global_songAlbum);
        info.put("songArtist", Global_songArtist);
        info.put("userName", userName);
        info.saveInBackground();
    }
}