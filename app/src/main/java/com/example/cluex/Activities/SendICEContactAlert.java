package com.example.cluex.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cluex.Helper.AppController;
import com.example.cluex.Helper.SessionManager;
import com.example.cluex.Helper.SetContactData;
import com.example.cluex.Helper.UserLocation;
import com.example.cluex.R;

import java.util.ArrayList;
import java.util.Map;

public class SendICEContactAlert extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Context context;
    Button sendBtn;
    Button cancelBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String random="hello";
    ArrayList<SetContactData> arrGetNumbers=new ArrayList<SetContactData>();
    int count=0;
    int index=0;
    String MessageInTheBox="";
    double latitude;
    double longitude;
    private Map<String,String> locationDetialsMapObject;
    String address;
    String message="hello";
    private UserLocation userLocationObject;
    StringBuffer smsBody;
    String checkTypeAlert="hello";
    String alertMessage="hello";
    AppController getNumbers;
    ArrayList<String> arr= new ArrayList<String>();
    private SessionManager session;
    String username;
    String RegisteredUser_ID;
    EditText MessageBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_icecontact_alert);

        session = new SessionManager(getApplicationContext());
        RegisteredUser_ID = session.getUsername(username);

        getNumbers=new AppController();
        getNumbers = (AppController) getApplicationContext();
      //  getNumbers.ICEContactFillUp(RegisteredUser_ID);

     //   app.ICEContactFillUp(RegisteredUser_ID);


        for(int i=0;i<getNumbers.setContactDatas.size();i++) {

          arrGetNumbers.add(getNumbers.setContactDatas.get(i)) ;

        }

        for(int i=0;i<arrGetNumbers.size();i++) {

           arr.add ( arrGetNumbers.get(i).getType().toString());

        }

    // private static final
        smsBody = new StringBuffer();

        Bundle extras = getIntent().getExtras();

       checkTypeAlert= extras.getString("alertType");
        //if()

       //    Log.d("yo",checkTypeAlert);


         userLocationObject = new UserLocation(this);

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        cancelBtn=(Button) findViewById(R.id.btnSMSCancel);


//        txtphoneNo = (EditText) findViewById(R.id.editText);

        txtMessage = (EditText) findViewById(R.id.editText2);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(userLocationObject.getLocation()!=null) {


                    latitude = userLocationObject.getLatitude();
                    longitude = userLocationObject.getLongitude();

                    locationDetialsMapObject = userLocationObject.LocationDetails(latitude, longitude);


             //       address = locationDetialsMapObject.get("url");





                    if(checkTypeAlert.matches("Roberry"))
                    {
                        alertMessage= "Robbery has taken place";

                    }

                    if(checkTypeAlert.matches("Accident"))
                    {
                        alertMessage= "An Accident has taken place";

                    }

                    if(checkTypeAlert.matches("Medical"))
                    {
                        alertMessage= "I am in medical emergency";

                    }

                    if(checkTypeAlert.matches("Fire"))
                    {
                        alertMessage= "Fire has been erupted";

                    }

                    if(checkTypeAlert.matches("Natural Disaster"))
                    {
                        alertMessage= "A Natural disaster has taken place";

                    }

                    if(checkTypeAlert.matches("Harassment"))
                    {
                        alertMessage= "Some one is harassing me";

                    }

                    if(checkTypeAlert.matches("Kidnapping"))
                    {
                        alertMessage= "I am being kidnapped";

                    }

                    if(checkTypeAlert.matches("Terrorist Attack"))
                    {
                        alertMessage= "A terrorist attack has taken place";

                    }



                    sendSMSMessage(latitude,longitude);

                }

                else
                {

                    Log.d("sal","Location not available");

                }

            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(SendICEContactAlert.this, HomeAlertActivity.class);
                startActivity(intent);



            }



        });


        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {





        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            //Do something
            count++;
            if(count==3) {
            //    sendSMSMessage(random);
            }

        }
        return true;
    }


    protected void sendSMSMessage(double latitude, double longitude ) {
        phoneNo = arr.get(index).toString();

        MessageBox= (EditText) findViewById(R.id.editText2);
        MessageInTheBox= MessageBox.getText().toString();
        smsBody.append(alertMessage);
        smsBody.append("\n");
        //smsBody.append("/n");

        if(MessageInTheBox.length()>0)
        {
            smsBody.append(RegisteredUser_ID);;
            smsBody.append("  ");
            smsBody.append("said:");
            System.getProperty("line.separator");

            smsBody.append(MessageInTheBox);
            smsBody.append("\n");

        }
        smsBody.append("http://maps.google.com/maps?q=");
        smsBody.append(latitude);
        smsBody.append(",");
        smsBody.append(longitude);
        message=smsBody.toString();


       // message = Address;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    for(int i=0;i<arr.size();i++) {
                        smsManager.sendTextMessage(arr.get(i).toString(), null,message, null, null);

                    }
                    Toast.makeText(getApplicationContext(), message,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

}
