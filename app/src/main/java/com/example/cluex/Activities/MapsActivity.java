package com.example.cluex.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cluex.Helper.AppConfig;
import com.example.cluex.Helper.AppController;
import com.example.cluex.Helper.ConnectionStatus;
import com.example.cluex.Helper.MapMarkerBounce;
import com.example.cluex.Helper.PostedAlertDetail;
import com.example.cluex.Helper.RecyclerViewAdapter;
import com.example.cluex.Helper.SessionManager;
import com.example.cluex.Helper.UserLocation;
import com.example.cluex.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    LocationManager locationManager;
    String provider;
    String volleyResponse;
    private List<PostedAlertDetail> coordinatesArrayObj;
    private ArrayList<PostedAlertDetail> addNotificationsOnMap=new ArrayList<PostedAlertDetail>();
    private HashMap<Marker,Integer> hashMap=new HashMap<Marker, Integer>();
    private HashMap<Integer,Marker> hashMap1=new HashMap<Integer, Marker>();
    private PostedAlertDetail postedAlertDetailObj;
    ConnectionStatus connectionStatus;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private Location location;
    UserLocation userLocation;
    RecyclerView recyclerView;
    ArrayList<String> Number;
    ArrayList<String> CheckNumber;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;
    Button btn;
    private String username;
    private String RegisteredUser_ID;
    MapMarkerBounce mmb = new MapMarkerBounce();
 //   private final Handler mHandler;
   // private Runnable mAnimation;

    Button submitButton;
    SeekBar simpleSeekBar;
    int radii=0;
    int count=0;
    int zoomLevel=0;
    Circle mapCircle;
    Circle mapCircleAnother;
    Integer progressChangedValue = 0;
    LatLng DummyCheck;
    int CWL=0;
    CardView hello;
    String streetAddress;
    String countryName;
    String cityName;
    TextView editText_alert, editText_location,editText_First;
    LinearLayout submit_button;
    ImageButton submission,cancellation;
    private Double lat;
    private Double lng;
    private Double latDummyCheck;
    private Double longDummyCheck;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        coordinatesArrayObj=new ArrayList<PostedAlertDetail>();
      //  simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBarZone);
        // perform seek bar change listener event used for getting the progress value
       // hello= (CardView) findViewById(R.id.card);
       // hello.setVisibility(View.INVISIBLE);
       // editText_location= (EditText)findViewById(R.id.editTextMaps);
       // submit_button= (LinearLayout)findViewById(R.id.submitbuttonlayout);
       // submit_button.setVisibility(View.INVISIBLE);
     //   editText_alert= (EditText)findViewById(R.id.editTextMapSIde);

        session = new SessionManager(getApplicationContext());

      //  submission = (ImageButton) findViewById(R.id.send_imageButtonMaps_id);
      //  cancellation = (ImageButton) findViewById(R.id.cancel_imageButtonMaps_id);



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
                                   tempObj.getString("Date"), tempObj.getString("Time"),0);

                           coordinatesArrayObj.add(postedAlertDetailObj);

                       }



                       AddItemsToRecyclerViewArrayList();

                       RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number,CheckNumber);

                       HorizontalLayout = new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                       recyclerView.setLayoutManager(HorizontalLayout);

                       recyclerView.setAdapter(RecyclerViewHorizontalAdapter);


//                       mMap.setOnMapLongClickListener(MapsActivity.this);



       /*                simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                               progressChangedValue = progress;
                               drawCircle(new LatLng(coordinatesArrayObj.get(1).getPostedAlertLat(),coordinatesArrayObj.get(1).getPostedAlertLng() ),progressChangedValue,CWL);
                                hello.setVisibility(View.VISIBLE);

                           }

                           public void onStartTrackingTouch(SeekBar seekBar) {
                               // TODO Auto-generated method stub
                           }

                           public void onStopTrackingTouch(SeekBar seekBar) {
                               Toast.makeText(MapsActivity.this, "Seek bar progress is :" + progressChangedValue,
                                       Toast.LENGTH_SHORT).show();
                           }
                       });

                       mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                           @Override
                           public void onCameraMoveStarted(int i) {

                               if(count==1)
                               {
                                   hello.setVisibility(View.INVISIBLE);
                               }


                           }
                       });

                       mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                           @Override
                           public void onCameraIdle() {


                               if(count==1)
                               {
                                   hello.setVisibility(View.VISIBLE);

                               }



                           }
                       });

*/

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
                .findFragmentById(R.id.map1);

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

    //    btn=(Button) findViewById(R.id.button);

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
                    LatLng myloc = new LatLng(coordinatesArrayObj.get(RecyclerViewItemPosition).getPostedAlertLat(),coordinatesArrayObj.get(RecyclerViewItemPosition).getPostedAlertLng());


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myloc, 20));

                    mmb.onMarkerClick(hashMap1.get(RecyclerViewItemPosition) );



                    //     mMap.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

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







        //     putTagOnMap();  /// ////////////////////////////////////////////


    }




    private void drawCircle(LatLng latLng, int radius,int checkWhichListener) {




    if (mapCircleAnother != null) {

        mapCircleAnother.remove();
    }



        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle

        if(CWL==1) {
            circleOptions.center(latLng);
        }

        else if (CWL==0)
        {
            circleOptions.center(DummyCheck);

        }
      //  Toast.makeText(getApplicationContext(), DummyCheck.latitude , Toast.LENGTH_LONG).show();

        if(CWL==1) {

            // Radius of the circle
            radii=            calculateZoomLevel(50,1000);

            circleOptions.radius(1000);
        }
        else if (CWL!=1) {

            circleOptions.radius(progressChangedValue*100);
            radii=            calculateZoomLevel(50,progressChangedValue*100);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DummyCheck, radii ));


        }
        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        mapCircleAnother=            mMap.addCircle(circleOptions);




if(CWL==1)
{

    CWL=0;
}




    }

    private int calculateZoomLevel(int screenWidth,int length) {
        double equatorLength = 40075004; // in meters
        double widthInPixels = screenWidth;
        double metersPerPixel = equatorLength / 256;
        int zoomLevel = 1;
        while ((metersPerPixel * widthInPixels) > length) {
            metersPerPixel /= 2;
            ++zoomLevel;
        }
        Log.i("ADNAN", "zoom level = "+zoomLevel);
        return zoomLevel;
    }

  /*  @Override
    public void onMapLongClick(LatLng point) {





        Toast.makeText(getApplicationContext(), "ButtonClicked " , Toast.LENGTH_LONG).show();
        CWL=1;

drawCircle(point,CWL,CWL);









        DummyCheck=point;
        latDummyCheck=DummyCheck.latitude;
        longDummyCheck=DummyCheck.longitude;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, radii ));
        LocationDetails(point.latitude,point.longitude);

        submit_button.setVisibility(View.VISIBLE);
        hello.setVisibility(View.VISIBLE);


count=1;


    }

*/










    public void find_Location(Context con) {
        Log.d("Find Location", "in find_location");

        userLocation=new UserLocation(con);
        location = userLocation.getLocation();


        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            mMap.clear();  //  clear the tags on map


            LatLng mylocation = new LatLng(lat, lng);
         //   mMap.addMarker(new MarkerOptions().position(mylocation).title("your location"));
            //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
          //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));


        }
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
      //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,12));


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

    private boolean LocationDetails(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);
            if (listAddresses != null && listAddresses.size() > 0) {
                streetAddress = listAddresses.get(0).getAddressLine(0);
                countryName = listAddresses.get(0).getCountryName();
                cityName = listAddresses.get(0).getLocality();
                editText_location.setText(streetAddress);

                return true;
            }
        } catch (IOException e) {
            // e.printStackTrace();


        }

        return false;
    }




    private void putTagOnMap()
    {

        if(mMap!=null) {
            for (int i = 0; i < coordinatesArrayObj.size(); i++) {

                //   while(itr.hasNext()){


                LatLng mylocation = new LatLng(coordinatesArrayObj.get(i).getPostedAlertLat(), coordinatesArrayObj.get(i).getPostedAlertLng());
                //     LatLng mylocation = new LatLng(coordinatesArrayObj1.get(i).getPostedAlertLat(), coordinatesArrayObj1.get(i).getPostedAlertLng())
              Marker marker=  mMap.addMarker(new MarkerOptions().position(mylocation).title(coordinatesArrayObj.get(i).getPostedAlertTitle()).snippet(coordinatesArrayObj.get(i).getPostedAlertDate()).snippet(""+i));

                hashMap.put(marker,i);
                hashMap1.put(i,marker);


                //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));


            }




        //    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
          //      @Override
            //    public boolean onMarkerClick(Marker marker) {
                  //  Intent intent = new Intent(MapsActivity.this, HomeAlertActivity.class);
                   // startActivity(intent);
              //      int position= hashMap.get(marker);
               //     HorizontalLayout.scrollToPositionWithOffset(position, 0);






//                    return false;
  //              }
    //        });



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


        Intent intent = new Intent(MapsActivity.this,HomeAlertActivity.class);
        startActivity(intent);
        finish();


    }

}
