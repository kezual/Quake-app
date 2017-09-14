package com.example.android.quakeapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {
    private Double quakeMag;
    private String quakeLocation;
    private Date quakeTime;
    private String quakeUrl;

    private SimpleDateFormat myDateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");

    public Earthquake(double quakeMag, String quakeLocation, long quakeTime, String quakeUrl){
        this.quakeMag = quakeMag;
        this.quakeLocation = quakeLocation;
        this.quakeTime = new Date(quakeTime);
        this.quakeUrl = quakeUrl;
    }

    public Double getQuakeMag() { return quakeMag; }

    public String getQuakeLocation() {
        return quakeLocation;
    }

    public String getQuakeTime() {
        //String stringDate = DateFormat.getDateTimeInstance().format(quakeTime);
        return myDateFormat.format(quakeTime);
    }

    public String getQuakeUrl() { return quakeUrl; }
}
