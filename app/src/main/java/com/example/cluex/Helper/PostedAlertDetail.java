package com.example.cluex.Helper;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Awais on 7/27/2017.
 */

public class PostedAlertDetail {

    private double postedAlertLat;
    private double postedAlertLng;
    private String postedAlertTitle;
    private String postedAlertTime;
    private String postedAlertDate;



   public PostedAlertDetail() {

        postedAlertLat=0.0;
        postedAlertLng=0.0;
        postedAlertTitle="";
       postedAlertDate="";
       postedAlertTime="";
    }

    public  PostedAlertDetail(double lat, double lng, String title,String d, String t ){

        postedAlertLat=lat;
        postedAlertLng=lng;
        postedAlertTitle=title;
        postedAlertTime=t;
        postedAlertDate=d;

    }

    public void setPostedAlertLat(double lat) {

        postedAlertLat=lat;

    }

    public void setPostedAlertLng(double lng){
        postedAlertLng=lng;
    }
    public void setPostedAlertTitle(String title){
        postedAlertTitle=title;
    }

    public double getPostedAlertLat()
    {
        return postedAlertLat;
    }

    public double getPostedAlertLng() {
        return postedAlertLng;
    }

    public String getPostedAlertTitle(){
        return postedAlertTitle;
    }

    public String getPostedAlertTime()
    {
        return postedAlertTime;
    }

    public String getPostedAlertDate()
    {
        return postedAlertDate;
    }

}
