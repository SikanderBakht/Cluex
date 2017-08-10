package com.example.cluex.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.cluex.R;

/**
 * Created by Awais on 8/7/2017.
 */

public class ConnectionStatus {

    Context context;
    private boolean gps_enabled;
    private boolean network_enabled;
    private  LocationManager locationManager;



    public ConnectionStatus(Context ctx) {

        context=ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;



    }

    public boolean checkInternetConnection() {

        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo i = conMgr.getActiveNetworkInfo();



        if (i == null) {
          //  Toast.makeText(context, "Turn on your internet connection!", Toast.LENGTH_LONG).show();

            return false;
        }
        if (!i.isConnected()) {
         //   Toast.makeText(context,"Turn on your internet connection!",Toast.LENGTH_LONG).show();

            return false;
        }
        if (!i.isAvailable()) {

           // Toast.makeText(context,"Turn on your internet connection!",Toast.LENGTH_LONG).show();

            return false;
        }
        return true;
    }


    public boolean is_gps_available() {

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {


         //   Toast.makeText(context,"Enable your location",Toast.LENGTH_LONG).show();
            return false;

        }
            // notify user
         /*   AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
               //     Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //   context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
        */

        return true;

    }


}
