package com.example.cluex.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cluex.Helper.AppConfig;
import com.example.cluex.Helper.AppController;
import com.example.cluex.Helper.PostedAlertDetail;
import com.example.cluex.Helper.RecyclerViewAdapter;
import com.example.cluex.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    LocationManager locationManager;
    String provider;
    String volleyResponse;
    private List<PostedAlertDetail> coordinatesArrayObj;
    private HashMap<Marker,Integer> hashMap=new HashMap<Marker, Integer>();
    private PostedAlertDetail postedAlertDetailObj;

    RecyclerView recyclerView;
    ArrayList<String> Number;
    ArrayList<String> CheckNumber;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;
    Button btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        coordinatesArrayObj=new ArrayList<PostedAlertDetail>();



       getAllCoordinates(new VolleyCallback() {
           @Override
           public void onSuccess(String response) {
               try {
                   JSONObject jObj = new JSONObject(response);
                   boolean error = jObj.getBoolean("error");

                   // Check for error node in json
                   if (!error) {


                       JSONObject tempObj = new JSONObject();
                       JSONArray coord_arr = new JSONArray();
                       coord_arr = jObj.getJSONArray("Loc_Coord");
                       int total_coordinates = coord_arr.length();
                       double lat = 0.0, lng = 0.0;
                       for (int i = 0; i < total_coordinates; i++) {
                           tempObj = coord_arr.getJSONObject(i);

                           lat = Double.parseDouble(tempObj.getString("latitiude"));
                           lng = Double.parseDouble(tempObj.getString("longitude"));

                           postedAlertDetailObj = new PostedAlertDetail(lat, lng, tempObj.getString("Title"),
                                   tempObj.getString("Date"), tempObj.getString("Time"));

                           coordinatesArrayObj.add(postedAlertDetailObj);

                       }
                       AddItemsToRecyclerViewArrayList();

                       RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number,CheckNumber);

                       HorizontalLayout = new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                       recyclerView.setLayoutManager(HorizontalLayout);

                       recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

                       putTagOnMap();   // add marker on google map

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

       });

  //      int siz= coordinatesArrayObj.size();


        // get all the coordinates of the posted alert from the server

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

      //  SupportMapFragment mapFrag = ((SupportMapFragment) getChildFragmentManager()
        //        .findFragmentById(R.id.map)).getMap();

        mapFragment.getMapAsync(this);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. Seef the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(this,"Permission is not Granted",
                    Toast.LENGTH_SHORT).show();


            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location!=null && mMap!=null)
        {
           onLocationChanged(location);
        }
        else{
            Log.i("Location info" , "location failed");
        }

//        mMap.setOnMarkerClickListener();

        btn=(Button) findViewById(R.id.button);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview1);

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
/*        AddItemsToRecyclerViewArrayList();

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number,CheckNumber);

        HorizontalLayout = new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

*/

        // Adding on item click listener to RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MapsActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {



                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting clicked value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);

                    // Showing clicked item value on screen using toast message.
                    //         Toast.makeText(MainActivity.this, Number.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //    RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(CheckNumber);

                //     RecyclerViewHorizontalAdapter.();

                //  HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                //  recyclerView.setLayoutManager(HorizontalLayout);

                //       recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

                HorizontalLayout.scrollToPositionWithOffset(9, 0);

            }

        });






        //     putTagOnMap();  /// ////////////////////////////////////////////


    }







    public void AddItemsToRecyclerViewArrayList(){

        Number = new ArrayList<>();
        CheckNumber=new ArrayList<>();
        for(int i=0;i<coordinatesArrayObj.size();i++) {
         Number.add(coordinatesArrayObj.get(i).getPostedAlertTitle());
        }

        for(int i=0;i<coordinatesArrayObj.size();i++) {
            CheckNumber.add(coordinatesArrayObj.get(i).getPostedAlertDate());
        }


       /*
        CheckNumber=new ArrayList<>();
        Number.add("Fire");
        Number.add("Medical Emergency");
        Number.add("Harassment");
        Number.add("Fire");
        Number.add("Medical Emergency");
        Number.add("Fire");
        Number.add("Harassment");
        Number.add("Fire");
        Number.add("Medical Emergency");
        Number.add("Kidnapping");
        CheckNumber.add("AskariX,Lahore");
        CheckNumber.add("Shahdara,Lahore");
        CheckNumber.add("Kareem Market,Lahore");
        CheckNumber.add("Mochi Pura,Lahore");
        CheckNumber.add("Hiran Minar,Lahore");
        CheckNumber.add("Sazgar Mandi,Lahore");
        CheckNumber.add("Yadgar Mandi,Lahore");
        CheckNumber.add("Shahid Bagh,Lahore");
        CheckNumber.add("Gol Bagh,Lahore");
        CheckNumber.add("Neelum Bank,Lahore");
*/

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int s = coordinatesArrayObj.size();


        if(mMap!=null) {

/*
            for (int i = 0; i < coordinatesArrayObj.size(); i++) {

         //   while(itr.hasNext()){


                LatLng mylocation = new LatLng(coordinatesArrayObj.get(i).getPostedAlertLat(), coordinatesArrayObj.get(i).getPostedAlertLng());
           //     LatLng mylocation = new LatLng(coordinatesArrayObj1.get(i).getPostedAlertLat(), coordinatesArrayObj1.get(i).getPostedAlertLng());

                mMap.addMarker(new MarkerOptions().position(mylocation).title(coordinatesArrayObj.get(i).getPostedAlertTitle()));
                //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));


            }
            */

        }


    }

    @Override
    protected void onResume() {
        super.onResume();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(this,"Permission is not Granted",
                    Toast.LENGTH_SHORT).show();


            return;
        }
        locationManager.requestLocationUpdates(provider,20000,1,this);



    }

    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        double lat=location.getLatitude();
        double lng=location.getLongitude();

        Log.i("Location info: lat", ""+lat);
        Log.i("Location info: lng",""+lng);

//        mMap.clear();
        // Add a marker in Sydney and move the camera
        LatLng mylocation = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(mylocation).title("Marker in my location"));
     //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,12));


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





    private void putTagOnMap()
    {

        if(mMap!=null) {
            for (int i = 0; i < coordinatesArrayObj.size(); i++) {

                //   while(itr.hasNext()){


                LatLng mylocation = new LatLng(coordinatesArrayObj.get(i).getPostedAlertLat(), coordinatesArrayObj.get(i).getPostedAlertLng());
                //     LatLng mylocation = new LatLng(coordinatesArrayObj1.get(i).getPostedAlertLat(), coordinatesArrayObj1.get(i).getPostedAlertLng());

              Marker marker=  mMap.addMarker(new MarkerOptions().position(mylocation).title(coordinatesArrayObj.get(i).getPostedAlertTitle()).snippet(coordinatesArrayObj.get(i).getPostedAlertDate()).snippet(""+i));

                hashMap.put(marker,i);

                //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));


            }


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                  //  Intent intent = new Intent(MapsActivity.this, HomeAlertActivity.class);
                   // startActivity(intent);
                    int position= hashMap.get(marker);
                    HorizontalLayout.scrollToPositionWithOffset(position, 0);






                    return false;
                }
            });



        }
    }

    private void getAllCoordinates(final VolleyCallback volleyCallback) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_AlertCoord, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        volleyResponse=response;

                        volleyCallback.onSuccess(volleyResponse);




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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                // params.put("username", email);
                //params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();

        int size=coordinatesArrayObj.size();

    }

}
