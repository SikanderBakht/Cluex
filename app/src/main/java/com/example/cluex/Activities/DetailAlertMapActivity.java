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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.cluex.SQL.SQLiteHandler;
import com.example.cluex.Helper.*;


public class DetailAlertMapActivity  extends FragmentActivity implements OnMapReadyCallback, LocationListener {

        private static final String TAG = com.example.cluex.Activities.DetailAlertMapActivity.class.getSimpleName();
        private GoogleMap mMap;
        LocationManager locationManager;
        private SQLiteHandler db;
        private Location location;
        private SessionManager session;
        private ProgressDialog pDialog;

        private String provider;
        private String username;
        private String title;

        private String description;
        private String type;
        private String RegisteredUser_ID;
        private String countryName;
        private String cityName;
        private String zipCode;
        private String province;
        private String state;
        private String streetAddress;
        private String severe;
        private String incidentPlace;
        private String incidentTime;


        private Double lat;
        private Double lng;
        UserLocation userLocationObj;
        Map<String,String> locationDetailsMapObj;




        TextView editText_alert, editText_location;
        ImageView alertImage;
        String alert_type, commentOnAlert = "hello world!!!";
        ImageButton cancel_button, submit_button, addPhoto_button, addComment_button;

        boolean gps_enabled = false;
        boolean network_enabled = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //    setContentView(R.layout.activity_immediate_alert);
            setContentView(R.layout.activity_detail_alert_map);


            userLocationObj=new UserLocation(getApplicationContext());
            session = new SessionManager(getApplicationContext());
            // Progress dialog
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);

            username = "";

            editText_alert = (TextView) findViewById(R.id.title_alert_id);
            editText_location = (TextView) findViewById(R.id.user_location_id);
            alertImage = (ImageView) findViewById(R.id.alertType_imageView_id);
            cancel_button = (ImageButton) findViewById(R.id.detailAlert_cancel_imageButton_id);
            submit_button = (ImageButton) findViewById(R.id.send_imageButton_id);
            addPhoto_button = (ImageButton) findViewById(R.id.addPhoto_imageButton_id);
            addComment_button = (ImageButton) findViewById(R.id.addComment_imageButton_id);


            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                return;
            }
            lat = extras.getDouble("latitude");
            lng = extras.getDouble("longitude");
            //   int res = extras.getInt("resourseInt");
            alert_type = extras.getString("alertType");
            incidentPlace = extras.getString("incidentPlace");
            incidentTime = extras.getString("when");

            locationDetailsMapObj=userLocationObj.LocationDetails(lat,lng);


            if(locationDetailsMapObj!=null) {
                countryName = locationDetailsMapObj.get("countryName");
                cityName = locationDetailsMapObj.get("cityName");
                streetAddress = locationDetailsMapObj.get("streetAddress");
                zipCode = locationDetailsMapObj.get("zipCode");
                state = locationDetailsMapObj.get("state");
                province = locationDetailsMapObj.get("province");

           //     editText_location.setText(streetAddress);
            }
            else{
                Toast.makeText(getApplicationContext(), "Loaction Detaisl Not Avaiable", Toast.LENGTH_LONG);

                return;

            }
            /*

            cityName = extras.getString("cityName");
            countryName = extras.getString("countryName");
            streetAddress = extras.getString("streetAddress");
            zipCode=extras.getString("zipCode");
            province=extras.getString("province");
            state=extras.getString("state");

            */



            title = "I see " + alert_type;  // parameter
            type = "Global";

            if (extras.get("severity") == "High")
                severe = "1";

            else {

                severe = "0";

            }

            editText_alert.setText(title);


            cancel_button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), HomeAlertActivity.class);

                    startActivity(intent);


                }
            });


            //////////////////////////////////////////


            //   is_gps_available();


            ///////////////////////////////////////////


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.user_location_map_id);

            mapFragment.getMapAsync(this);
        }



  // onclick of cancel button
        public void onClickCancel(View view) {

            Intent intent = new Intent(getApplicationContext(), HomeAlertActivity.class);
            startActivity(intent);


        }

        public void onClickSubmit(View view) {

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
                            Intent intent = new Intent(DetailAlertMapActivity.this, HomeAlertActivity.class);
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
                    params.put("country",countryName);
                    params.put("zipCode",zipCode);
                    params.put("city",cityName);
                    params.put("state",state);
                    params.put("province",province);
                    params.put("streetAddress",streetAddress);
                    params.put("incidentTime",incidentTime);
                    params.put("incidentPlace",incidentPlace);

                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

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

                Toast.makeText(this, commentOnAlert,
                        Toast.LENGTH_LONG).show();

            }
        }


        public void onClickAddPhoto(View view) {

            Intent intent = new Intent(this, addComment_ImmediateAlertActivity.class);
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
       //     locationManager.removeUpdates(this);
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

         //   find_Location(this);
        //    Log.i("Location info: lat", "" + lat);
         //   Log.i("Location info: lng", "" + lng);

            //   mMap.clear();
            // Add a marker in Sydney and move the camera
            LatLng mylocation = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(mylocation).title(streetAddress));
            //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));
            editText_location.setText(streetAddress);


       //    LocationDetails(lat, lng);  // set the name of user location in textview






        }

        @Override
        public void onLocationChanged(Location location) {

            /*
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

       //     LocationDetails(lat, lng);  // set the name of user location



                */


        }

   /* void LocationDetails(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                streetAddress = listAddresses.get(0).getAddressLine(0);
                countryName = listAddresses.get(0).getCountryName();
                cityName = listAddresses.get(0).getLocality();
                zipCode = listAddresses.get(0).getPostalCode();
                province="punjab";
                state="Pakistan";


                editText_location.setText(streetAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


*/
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

        /*

        public void find_Location(Context con) {
            Log.d("Find Location", "in find_location");

            String location_context = Context.LOCATION_SERVICE;
            locationManager = (LocationManager) con.getSystemService(location_context);
            List<String> providers = locationManager.getProviders(true);
            for (String provider : providers) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(provider, 1000, 0,
                        new LocationListener() {

                            public void onLocationChanged(Location location) {
                            }

                            public void onProviderDisabled(String provider) {
                            }

                            public void onProviderEnabled(String provider) {
                            }

                            public void onStatusChanged(String provider, int status,
                                                        Bundle extras) {
                            }
                        });
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    onLocationChanged(location);

                }
            }
        }

*/

    }


