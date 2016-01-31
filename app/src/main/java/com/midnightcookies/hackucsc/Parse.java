package com.midnightcookies.hackucsc;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Brian on 1/30/16.
 */
@ParseClassName("Info")
public class Parse extends ParseObject {

    private String userName;
    private String songTitle;
    private String GeoPoint;
    private Array Genres;

    Parse(String AuserName, String AsongTitle, String AGeoPoint, Array AGeres){
        userName = AuserName;
        songTitle = AsongTitle;
        GeoPoint = AGeoPoint;
       //Might Not Work Since We are using an array
        Genres = AGeres;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getGeoPoint() {
        return GeoPoint;
    }

    public void setGeoPoint(String geoPoint) {
        GeoPoint = geoPoint;
    }

    public Array getGenres() {
        return Genres;
    }

    public void setGenres(Array genres) {
        Genres = genres;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}