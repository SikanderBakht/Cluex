package com.example.cluex.Helper;

/**
 * Created by Awais on 7/27/2017.
 */

public class PostedAlertDetail {

    private double postedAlertLat;
    private double postedAlertLng;
    private String postedAlertTitle;
    private String postedAlertTime;
    private String postedAlertDate;
    private int radius;



   public PostedAlertDetail() {

        postedAlertLat=0.0;
        postedAlertLng=0.0;
        postedAlertTitle="";
       postedAlertDate="";
       postedAlertTime="";
       radius=0;
    }

    public PostedAlertDetail(double lat, double lng, String title, String d, String t, int r ){

        postedAlertLat=lat;
        postedAlertLng=lng;
        postedAlertTitle=title;
        postedAlertTime=t;
        postedAlertDate=d;
        radius=r;

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
    public void setPostedAlertRadius(int Radius) {radius=Radius;}

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

    public int getPostedAlertRadius() {return radius;}

}
