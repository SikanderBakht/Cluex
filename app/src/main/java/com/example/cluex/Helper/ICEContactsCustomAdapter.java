package com.example.cluex.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cluex.Activities.AddContact;
import com.example.cluex.Activities.ICEContactsActivity;
import com.example.cluex.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.cluex.Helper.AppController.setContactDatas;
import static com.example.cluex.R.layout.row_item;
import static java.lang.Thread.sleep;

/**
 * Created by sikander on 7/17/17.
 */

public class ICEContactsCustomAdapter extends ArrayAdapter <SetContactData> implements View.OnClickListener {
    private ArrayList<SetContactData> dataSet;

    ICEContactsActivity CHECKING ;
    Context mContext;
    AppController app;


    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;

        // TextView txtVersion;
        ImageView info;
    }

    public ICEContactsCustomAdapter(ArrayList<SetContactData> data, Context context) {
        super(context, row_item, data);

     //   CHECKING=new ICEContactsActivity();

        dataSet = data;



        //   app=new AppController();

        mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        SetContactData setContactData =(SetContactData)object;

        switch (v.getId())
        {

        }
    }






    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        SetContactData setContactData;
            setContactData = dataSet.get(position);
           //  dataSet.getItem(position);


           // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(row_item, parent, false);



            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            //   viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        // Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        // result.startAnimation(animation);


       // lastPosition  = position;

        viewHolder.txtName.setText(setContactData.getName());
        viewHolder.txtType.setText(setContactData.getType());
        //viewHolder.txtVersion.setText(setContactData.getVersion_number());
        //  viewHolder.info.setOnClickListener(this);
        //   viewHolder.info.setTag(position);

        viewHolder.info.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                myClickHandler(v, position);


//                viewHolder = new ViewHolder();
  //              LayoutInflater inflater = LayoutInflater.from(getContext());





            }
        });





        return result;

        // Return the completed view to render on screen
        //      return convertView;
    }






    public void myClickHandler(View v,int position)
    {


      //  dataSet.get(position).getName();
      //  dataSet.get(position).getType();
        String RegID="salman";

        ICEContactDelete(dataSet.get(position).getName(),dataSet.get(position).getType(),RegID,position);


    //    dataSet.add(new SetContactData("salman","03211111"));




        Toast.makeText(mContext.getApplicationContext(), "ButtonCLicked",
                Toast.LENGTH_SHORT).show();
    }





    private void ICEContactDelete( final String name,final String contact,final String UserID,final int position )
    {
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

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_DELETEICE, new Response.Listener<String>()
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
                        dataSet.remove(position);

                        notifyDataSetChanged();


                        Log.d("io", "Register Response2: " );
                        Toast.makeText(mContext, errorMsg, Toast.LENGTH_LONG).show();







                        // Launch login activity

                    }
                    else
                    {
                        Toast.makeText(mContext, errorMsg, Toast.LENGTH_LONG).show();

                        Log.d("io", "Register Response3: " );
                        // Error occurred in registration. Get the error message
                        String errorMsg1 = jObj.getString("error_msg");

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
                //         hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", UserID);
                    params.put("contact_name", name);
                  params.put("contact_number", contact);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
