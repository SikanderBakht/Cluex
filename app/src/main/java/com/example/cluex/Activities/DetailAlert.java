package com.example.cluex.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cluex.Helper.ConnectionStatus;
import com.example.cluex.Helper.UserLocation;
import com.example.cluex.R;

import java.util.Map;


public class DetailAlert extends AppCompatActivity {

      Spinner incidentSpinner;
      Spinner severitySpinner;
    Spinner whenSpinner;
    Spinner whereSpinner;

    TextView detailAlertTitle;


    String incidentSpinnerSelectedValue;
    String severitySpinnerSelectedValue;
    String whenSpinnerSelectedValue;
    String whereSpinnerSelectedValue;
    double lat,lng;
    UserLocation userLocationObj;
    private Map<String,String> locationDetaislMapObj;

    private String alertType;
    private String countryName="abc";
    private String cityName="abc";
    private String zipCode="abc";
    private String province="abc";
    private String state="abc";
    private String streetAddress="abc";
    private ConnectionStatus connectionStatus;



    private ToggleButton toggleButton_continue;

    String severity_Array [] ={
            "Select one",
            "High",
            "Low"

    };

    String[] incident_Array = {
            "Select one ",
            "Bank",
            "Home",
            "School",
            "College",
            "University",
            "Road",
            "Public Building",
            "Market/store ",
            "Other"

    };

    String[]  time_Array={
            "Select one",
            "now",
            "less than 15 minutes ago",
            "15 to 30 minutes ago",
            "30 minutes to an hour ago",
            "over an hour ago"

    };

    String [] when_Array={ "Select one" , "here" , "somewhere else"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_alert);

        connectionStatus=new ConnectionStatus(this);


        // Get reference of SpinnerView from layout/activity_detial_alert
        incidentSpinner =(Spinner)findViewById(R.id.incident_type_spinner_id);
        severitySpinner =(Spinner)findViewById(R.id.severity_spinner_id);
        whenSpinner=  (Spinner) findViewById(R.id.when_spinner_id);
        whereSpinner= (Spinner)findViewById(R.id.where_spinner_id);

        detailAlertTitle=(TextView)findViewById(R.id.detailAert_title_id);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        //   int res = extras.getInt("resourseInt");

        alertType=extras.getString("alertType");
        detailAlertTitle.setText(alertType);

        boolean isConnected=connectionStatus.is_gps_available();
        boolean isGps=connectionStatus.checkInternetConnection();



        if (!isConnected && !isGps) {
            Toast.makeText(this, "Turn on internet connection and Location", Toast.LENGTH_LONG).show();
        } else if (isConnected && !isGps) {

            Toast.makeText(this, "Enable your Location ", Toast.LENGTH_LONG).show();

        } else if (!isConnected && isGps) {
            Toast.makeText(this, "Enable your internet connection ", Toast.LENGTH_LONG).show();
        }

        if(!isConnected || !isGps) finish();





        /////////////////////////////////  incident spinner start   ////////////////////////////////////////////////////


        ArrayAdapter<String> incident_adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,incident_Array){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);

                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        incidentSpinner.setAdapter(incident_adapter);

        incidentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
             //   int index=incidentSpinner.getSelectedItemPosition();
                incidentSpinnerSelectedValue = incidentSpinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //////////////////////////////Spinner incident end//////////////////////////////////////////





        /////////////////////////////////  severity spinner    ////////////////////////////////////////////////////


        ArrayAdapter<String> severity_adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,severity_Array){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);

                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        severitySpinner.setAdapter(severity_adapter);

        severitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
              //  int index=incidentSpinner.getSelectedItemPosition();
                 severitySpinnerSelectedValue = incidentSpinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        //////////////////////////////Spinner severity end//////////////////////////////////////////






        /////////////////////////////////  spinner when start    ////////////////////////////////////////////////////


        ArrayAdapter<String> when_adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,time_Array){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);

                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };







        whenSpinner.setAdapter(when_adapter);

        whenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
             //   int index=whenSpinner.getSelectedItemPosition();
                whenSpinnerSelectedValue = incidentSpinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });





        ////////////////////////////// spinner when  end//////////////////////////////////////////





        /////////////////////////////////   spinner  where start   ////////////////////////////////////////////////////


        ArrayAdapter<String> where_adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,when_Array){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);

                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };







        whereSpinner.setAdapter(where_adapter);

        whereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
              //  int index=whereSpinner.getSelectedItemPosition();
                whereSpinnerSelectedValue=whereSpinner.getSelectedItem().toString();

                if(whereSpinnerSelectedValue==when_Array[1]){




                      userLocationObj=new UserLocation(getApplicationContext());

                        lat=userLocationObj.getLatitude();
                        lng=userLocationObj.getLongitude();

//                       locationDetaislMapObj=userLocationObj.LocationDetails(lat,lng);


                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });





        //////////////////////////////Spinner where end//////////////////////////////////////////

        ContinueDetailAlert();

    }


    public void ContinueDetailAlert() {

        toggleButton_continue = (ToggleButton) findViewById(R.id.continue_toggleButton_id);


        toggleButton_continue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
              //  result.append("toggleButton1 : ").append(toggleButton_continue.getText());


                Intent intent = new Intent (DetailAlert.this,DetailAlertMapActivity.class);

            //    if(locationDetaislMapObj!=null)
                {

                    intent.putExtra("severity", severitySpinnerSelectedValue);
                    intent.putExtra("incidentPlace", incidentSpinnerSelectedValue);
                    intent.putExtra("when", whenSpinnerSelectedValue);
                    intent.putExtra("latitude", lat);
                    intent.putExtra("longitude", lng);
                    intent.putExtra("alertType", alertType);


                    /*
                    intent.putExtra("countryName", countryName);
                    intent.putExtra("cityName", cityName);
                    intent.putExtra("streetAddress", streetAddress);
                    intent.putExtra("zipCode", zipCode);
                    intent.putExtra("state", state);
                    intent.putExtra("province", province);


                    */

                 /*
                    intent.putExtra("countryName", locationDetaislMapObj.get("countryName"));
                    intent.putExtra("cityName", locationDetaislMapObj.get("cityName"));
                    intent.putExtra("streetAddress", locationDetaislMapObj.get("streetAddress"));
                    intent.putExtra("zipCode", locationDetaislMapObj.get("zipCode"));
                    intent.putExtra("state", locationDetaislMapObj.get("state"));
                    intent.putExtra("province", locationDetaislMapObj.get("province"));
                    */
                }
                startActivity(intent);



        //        Toast.makeText(DetailAlert.this,whenSpinnerSelectedValue +" " + lat +" "+ lng ,Toast.LENGTH_SHORT).show();

            }

        });

    }
}






