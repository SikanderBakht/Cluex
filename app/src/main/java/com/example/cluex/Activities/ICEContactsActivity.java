package com.example.cluex.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cluex.Helper.AppConfig;
import com.example.cluex.Helper.AppController;
import com.example.cluex.Helper.SessionManager;
import com.example.cluex.Helper.SetContactData;
import com.example.cluex.Helper.ICEContactsCustomAdapter;
import com.example.cluex.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ICEContactsActivity extends AppCompatActivity {

    //  private static final android.R.attr R = ;
    ArrayList<SetContactData> setContactDatas=new ArrayList<SetContactData>();
    ListView listView;
    Button button1;
    Button button2;
    private ICEContactsCustomAdapter adapter;
    private SessionManager session;
    private String RegisteredUser_ID;
    private String username;
    private JSONObject obj;
    AppController appli;
    MenuView.ItemView ch;
   // private JSONArray<obj> hello=new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icecontacts);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);button8

        appli=new AppController();
        listView=(ListView)findViewById(R.id.list);
        button1 = (Button) findViewById(R.id.button8);
        button2=(Button) findViewById(R.id.button4);
     // ch=  (MenuView.ItemView) findViewById(R.id.item_info);
     //   setContactDatas = new ArrayList<>();
        session = new SessionManager(getApplicationContext());
        RegisteredUser_ID = session.getUsername(username);
      //  RegisteredUser_ID="salman";
//        ICEContactFillUp(RegisteredUser_ID);

        adapter= new ICEContactsCustomAdapter(appli.setContactDatas,getApplicationContext());



        listView.setAdapter(adapter);


        Toast.makeText(getApplicationContext(), "You clicked on NO " + appli.setContactDatas.size(), Toast.LENGTH_SHORT).show();




        //  appli=(AppController) getApplicationContext();





        //JSONGETFUNCTION

         // ICEContactFillUp(RegisteredUser_ID);

      //  setContactDatas.add(new SetContactData("Salman", "03218832069"));
       // int size= setContactDatas.size();



//        if(appli.checking.matches("CHECK"))
  //      {


    //        adapter.notifyDataSetChanged();

      //  }




                button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ICEContactsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ICEContactsActivity.this);
                }



                builder.setCancelable(false)

                        .setPositiveButton("Add New Contact", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {

                                //Intent myIntent = new Intent(getApplicationContext(),AddContact.class);
                                //  myIntent.putExtra("key", value); //Optional parameters
                                //   startActivity(myIntent);

                                Intent i = new Intent(ICEContactsActivity.this, AddContact.class);
                                startActivityForResult(i, 1);


                                // Write your code here to invoke YES event

                                //  Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton("Add From Contacts", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i = new Intent(ICEContactsActivity.this, MobileContactsActivity.class);
                                startActivityForResult(i, 2);


                                // Write your code here to invoke NO event
                                // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();

                            }
                        })


                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User pressed Cancel button. Write Logic Here
                                // Toast.makeText(getApplicationContext(), "You clicked on Cancel",
                                //       Toast.LENGTH_SHORT).show();

                                dialog.cancel();
                            }
                        })



                        .setIcon(R.drawable.ic_launcher)

                        .setTitle("Alert")

                        .setMessage("This is 1 Action alert Dialog, Press OK to continue");

               final AlertDialog diag = builder.create();

                diag.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        diag.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.textColor));
                        diag.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.textColor));
                        diag.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.textColor));
                    }
                });

                diag.show();


                //   Intent myIntent = new Intent(getApplicationContext(),HomeAlertActivity.class);
                //  myIntent.putExtra("key", value); //Optional parameters
                //        startActivity(myIntent);

            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ICEContactsActivity.this, HomeAlertActivity.class);
                startActivity(intent);



            }

        });

            }






















    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            String message=data.getStringExtra("Message");
            String messege=data.getStringExtra("Messege");




            ICEContactAdd(message,messege,RegisteredUser_ID);

         //   adapter= new ICEContactsCustomAdapter(appli.setContactDatas,getApplicationContext());



           // listView.setAdapter(adapter);



         //      adapter.notifyDataSetChanged();

            //notifyDataSetChanged();



            // textView1.setText(message);
        }

        if(requestCode==2)
        {
            String message=data.getStringExtra("Message");
            String messege=data.getStringExtra("Messege");
//         appli.setContactDatas.add(new SetContactData(message, messege));






           ICEContactAdd(message,messege,RegisteredUser_ID);

  //          adapter= new ICEContactsCustomAdapter(setContactDatas,getApplicationContext());



    //        listView.setAdapter(adapter);



           //   adapter.notifyDataSetChanged();


      //      adapter.notifyDataSetChanged();
            // textView1.setText(message);
        }




    }








//@Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    //   getMenuInflater().inflate(R.menu.menu_main, menu);
    //  return true;
    //}

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    //  int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    // if (id == R.id.action_settings) {
    //    return true;
    // }

    // return super.onOptionsItemSelected(item);
    //}


    public void checker()
    {

        adapter= new ICEContactsCustomAdapter(appli.setContactDatas,getApplicationContext());



        listView.setAdapter(adapter);

    }



    private void ICEContactAdd(final String Name, final String Contact,final String UserID ) {
        // Reset errors.
        //message.setError(null);
        //Contact.setError(null);
        //UserID.setError(null);


        boolean cancel = false;
        View focusView = null;

        // Tag used to cancel the request
        String tag_string_req = "req_addICE";

       // pDialog.setMessage("Registering ...");
       // showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SAVEICE, new Response.Listener<String>() {
            boolean cancel = false;
            View focusView = null;

            @Override
            public void onResponse(String response) {
             //   Log.d(TAG, "Register Response: " + response.toString());
         //       hideDialog();
                try
                {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("error");
                    if (error.matches("false"))
                    {
                      //  jObj.getJSONArray();
                        appli.setContactDatas.add(new SetContactData(Name, Contact));
                        adapter.notifyDataSetChanged();



                        Log.d("saluMAN","Is it working?");

                        Toast.makeText(getApplicationContext(), "Succesfully Added Contact", Toast.LENGTH_LONG).show();

                        // Launch login activity

                    }
                    else
                    {


                        // Error occurred in registration. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e(TAG, "Registration Error: " + error.getMessage());
                Log.d("Jani","hello");
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
       //         hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("contact_name", Name);
                params.put("contact_number", Contact);
                params.put("username", UserID);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    public void ICEContactFillUp( final String UserID ) {
        // Reset errors.
        //message.setError(null);
        //Contact.setError(null);
        //UserID.setError(null);

        final ArrayList<SetContactData> arr ;
        arr=new ArrayList<SetContactData>();
        boolean cancel = false;
        View focusView = null;

        // Tag used to cancel the request
        String tag_string_req = "req_addICE";

        // pDialog.setMessage("Registering ...");
        // showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SHOWICE, new Response.Listener<String>()
        {
            boolean cancel = false;
            View focusView = null;

            @Override
            public void onResponse(String response) {
                Log.d("io", "Register Response: " );
                //       hideDialog();
                try
                {


                    Log.d("io", "Register Response1: " );
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("error");

                    String errorMsg = jObj.getString("error_msg");
//                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();


                    if (error.matches("false"))
                    {
                        //  jObj.getJSONArray();

                        Log.d("io", "Register Response2: " );
                        JSONObject tempObj = new JSONObject();
                        JSONArray iceContacts = new JSONArray();
                        iceContacts = jObj.getJSONArray("ICE_Contacts");
                        int total_coordinates=iceContacts.length();



                        // Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();


                        String nameToADD;
                        String numberToADD;


                        for(int i=0;i<total_coordinates;i++)
                        {
                            //


                            tempObj = iceContacts.getJSONObject(i);
                            nameToADD = (tempObj.getString("Contact_name"));
                            numberToADD = (tempObj.getString("Contact_number"));
                            arr.add(new SetContactData(nameToADD, numberToADD));





                        }


                        for(int i=0;i<arr.size();i++)
                        {

                            setContactDatas.add(arr.get(i)) ;
                        }
              //        adapter= new ICEContactsCustomAdapter(appli.setContactDatas,getApplicationContext());



                //        listView.setAdapter(adapter);

                    //    adapter.notifyDataSetChanged();


                        //      adapter= new ICEContactsCustomAdapter(arr,getApplicationContext());



                        //    listView.setAdapter(adapter);





                        // Launch login activity

                    }
                    else
                    {

                        Log.d("io", "Register Response3: " );
                        // Error occurred in registration. Get the error message
                        String errorMsg1 = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg1, Toast.LENGTH_LONG).show();

                    }
                }
                catch (JSONException e)
                {
                    Log.d("io", "Register Response5: " );
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e(TAG, "Registration Error: " + error.getMessage());
                Log.d("io", "Register Response4: " );
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //         hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", UserID);
                //    params.put("contact_number", Contact);
                //  params.put("username", UserID);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        //  return arr;

    }






}






