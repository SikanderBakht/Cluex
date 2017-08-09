package com.example.cluex.Activities;

import android.Manifest;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cluex.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.example.cluex.Helper.*;
import com.example.cluex.SQL.SQLiteHandler;

public class ImmediateAlert extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private static final String TAG = "abc";
    private GoogleMap mMap;
    LocationManager locationManager;

    private Location location;
    private SessionManager session;
    private ProgressDialog pDialog;

    private String provider;
    private String username;
    private String title;
    private String description;
    private String type;
    private String RegisteredUser_ID;
    private String countryName = "pakistan";
    private String cityName = "lahore";
    private String zipCode = "000123";
    private String province = "punjab";
    private String state = "pakistan";
    private String streetAddress = "salman";

    private String severe;
    private Double lat;
    private Double lng;

    TextView editText_alert, editText_location;
    ImageView alertImage;
    String alert_type, commentOnAlert = "hello world!!!";
    ImageButton cancel_button, submit_button, addPhoto_button, addComment_button;

    boolean gps_enabled = false;
    boolean network_enabled = false;
    ConnectionStatus connectionStatus;
    UserLocation userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediate_alert);

        session = new SessionManager(getApplicationContext());
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        username = "";
        description=" ";

        location=null;
        editText_alert = (TextView) findViewById(R.id.title_alert_id);
        editText_location = (TextView) findViewById(R.id.user_location_id);
        alertImage = (ImageView) findViewById(R.id.alertType_imageView_id);
        cancel_button = (ImageButton) findViewById(R.id.cancel_imageButton_id);
        submit_button = (ImageButton) findViewById(R.id.send_imageButton_id);
        addPhoto_button = (ImageButton) findViewById(R.id.addPhoto_imageButton_id);
        addComment_button = (ImageButton) findViewById(R.id.addComment_imageButton_id);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        //   int res = extras.getInt("resourseInt");
        alert_type = extras.getString("alertType");

        title = "I see " + alert_type;  // parameter
        type = "Global";
        severe = "1";
        editText_alert.setText(title);


        connectionStatus = new ConnectionStatus(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.user_location_map_id);

        mapFragment.getMapAsync(this);



     //   boolean isConnected=connectionStatus.checkInternetConnection();
     //   boolean isGps=connectionStatus.is_gps_available();


      /*  if(!isGps )
         {
      //      finish();
         }


     //   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

     //   is_gps_available();

        if(!isConnected)
        {
    //        finish();

        }

*






        //    find_Location(this);  // get lat and lng of user location

/*
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(this, "Permission is not Granted",
                    Toast.LENGTH_SHORT).show();


            return;
        }
        locationManager.requestLocationUpdates(provider, 1000, 1, this);
        location = locationManager.getLastKnownLocation(provider);

/*
        if (location!=null && mMap!=null)
        {
            onLocationChanged(location);
        }
        else{
            Log.i("Location info" , "location failed");
        }

*/


    }

    // onclick of cancel button
    public void onClickCancel(View view) {

        Intent intent = new Intent(this, HomeAlertActivity.class);
        startActivity(intent);
        finish();

    }

    public void onClickSubmit(View view) {


        boolean isConnected=connectionStatus.checkInternetConnection();
        boolean isGps=connectionStatus.is_gps_available();

        if (isConnected && isGps) {

            while(location==null) {

              find_Location(this);
                if (location != null) {
                    LocationDetails(lat, lng);
                }
            }



        if(location!=null && LocationDetails(lat,lng) ) {

            RegisteredUser_ID = session.getUsername(username);
            Toast.makeText(this, RegisteredUser_ID, Toast.LENGTH_SHORT).show();

            // Tag used to cancel the request
            String tag_string_req = "req_postAlert";

            pDialog.setMessage("posting alert ...");
            //  showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_ImmediateAlert, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Login Response: " + response.toString());
                    //        hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {


                            Toast.makeText(getApplicationContext(), "Alert has been posted", Toast.LENGTH_SHORT).show();

                            // Launch main activity
                            Intent intent = new Intent(ImmediateAlert.this, HomeAlertActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Post Alert Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    //     hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", RegisteredUser_ID);
                    params.put("latitude", lat.toString());
                    params.put("longitude", lng.toString());
                    params.put("title", title);
                    params.put("type", type);
                    params.put("severe", severe);
                    params.put("description", description);
                    params.put("country", countryName);
                    params.put("zipCode", zipCode);
                    params.put("city", cityName);
                    params.put("state", state);
                    params.put("province", province);
                    params.put("streetAddress", streetAddress);

                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    /*    HashMap<String,String> user= db.getUserDetails();
        if(user.size()>0){

            Toast.makeText(this,user.get("name"), Toast.LENGTH_SHORT).show();
        }

*/      }     }
        else{
          /*  if (isConnected && isGps) {
                find_Location(this);
                if (location != null) {
                    LocationDetails(lat, lng);
                }
            } else*/ if (!isConnected && !isGps) {
                Toast.makeText(this, "Turn on internet connection and Location", Toast.LENGTH_LONG).show();
            } else if (isConnected && !isGps) {

                Toast.makeText(this, "Enable your Location ", Toast.LENGTH_LONG).show();

            } else if (!isConnected && isGps) {
                Toast.makeText(this, "Enable your internet connection ", Toast.LENGTH_LONG).show();
            }

        }


        }




    public void onClickAddComment(View view) {


        Intent intent = new Intent(this, addComment_ImmediateAlertActivity.class);
        intent.putExtra("alertType", alert_type);
        //    startActivity(intent);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2


    }


    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            commentOnAlert = data.getStringExtra("COMMENT");

            description = commentOnAlert;   //parameter

         //   Toast.makeText(this, commentOnAlert, Toast.LENGTH_LONG).show();

        }
    }


    public void onClickAddPhoto(View view) {

        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);


    }


    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // int res = extras.getInt("resourseInt");
        alert_type = extras.getString("alertType");

        title = "I see " + alert_type;  // parameter
        editText_alert.setText(title);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        locationManager.removeUpdates(this);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(this,"On Provider Enabled Caled",Toast.LENGTH_LONG).show();


    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        find_Location(this);

        if (location != null) {
            //
             LocationDetails(lat, lng);
            // onLocationChanged(location);
        } else {
            Log.i("Location info", "location failed");
        }


    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lng = location.getLongitude();

        Log.i("Location info: lat", "" + lat);
        Log.i("Location info: lng", "" + lng);

        mMap.clear();
        // Add a marker in Sydney and move the camera
        LatLng mylocation = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(mylocation).title("your location"));
        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));


        LocationDetails(lat, lng);  // set the name of user location in textview


    }

    private boolean LocationDetails(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);
            if (listAddresses != null && listAddresses.size() > 0) {
                streetAddress = listAddresses.get(0).getAddressLine(0);
                countryName = listAddresses.get(0).getCountryName();
                cityName = listAddresses.get(0).getLocality();
                zipCode = "0000"; //listAddresses.get(0).getPostalCode();
                province = "punjab";
                state = "Pakistan";
                editText_location.setText(streetAddress);

                return true;
            }
        } catch (IOException e) {
           // e.printStackTrace();


        }

        return false;
    }


    public void is_gps_available() {

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }


    }


    public void find_Location(Context con) {
        Log.d("Find Location", "in find_location");

                userLocation=new UserLocation(con);
                location = userLocation.getLocation();


                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    mMap.clear();  //  clear the tags on map


                    LatLng mylocation = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(mylocation).title("your location"));
                    //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));


                }
    }
}


