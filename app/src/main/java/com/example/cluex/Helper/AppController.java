package com.example.cluex.Helper;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    public static final ArrayList<SetContactData> setContactDatas=new ArrayList<SetContactData>();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    public static String checking;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    public void ICEContactFillUp( final String UserID ) {
        // Reset errors.
        //message.setError(null);
        //Contact.setError(null);
        //UserID.setError(null);

        final ArrayList<SetContactData> arr ;
        arr=new ArrayList<>();
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

                        if(setContactDatas.size()!=0)
                        {
                            for(int i=(setContactDatas.size() -1 );i>=0;i--)
                            {
                                setContactDatas.remove(i);

                            }

                        }
                        if(setContactDatas.size()==0) {

                            for (int i = 0; i < arr.size(); i++) {

                                setContactDatas.add(arr.get(i));
                            }
                        }


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



    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }






}