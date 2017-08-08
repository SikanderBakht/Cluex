package com.example.cluex.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cluex.Activities.ImmediateAlert;
import com.example.cluex.Activities.SendICEContactAlert;
import com.example.cluex.R;

import java.util.List;
import com.example.cluex.Activities.DetailAlert;
/**
 * Created by Awais on 7/4/2017.
 */

public class AlertAdapter extends RecyclerView.Adapter<HomeAlertActivityRecyclerViewHolder> {

    private List<AlertIconsDetails> itemList;
    private Context context;

    public AlertAdapter(Context context, List<AlertIconsDetails> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public HomeAlertActivityRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_grid_layout, null);
        HomeAlertActivityRecyclerViewHolder rcv = new HomeAlertActivityRecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(HomeAlertActivityRecyclerViewHolder holder, final int position) {
        final String alertType=itemList.get(position).getName();



        holder.countryName.setText(alertType);
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        holder.detailAlertButtonJava.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAlert.class);


                intent.putExtra("alertType",alertType);

                context.startActivity(intent);
            }
        });


        holder.immediateAlertButtonJava.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImmediateAlert.class);
                intent.putExtra("alertType",alertType);
                context.startActivity(intent);
                Toast.makeText(context,"Immediate Alert",Toast.LENGTH_LONG).show();
            }
        });




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(alertType);
                alertDialogBuilder.setTitle("Alert");



                alertDialogBuilder.setPositiveButton("Immediate Alert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(context, ImmediateAlert.class);


                        intent.putExtra("alertType",alertType);

                        Log.d("checkMe",alertType);

                        context.startActivity(intent);


                          Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("Detail Alert",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, DetailAlert.class);


                        intent.putExtra("alertType",alertType);

                        context.startActivity(intent);
                    }
                });


                alertDialogBuilder.setNeutralButton("Send SMS to ICE Contacts", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, SendICEContactAlert.class);


                        intent.putExtra("alertType",alertType);

                        context.startActivity(intent);
                    }
                });



             final AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                              @Override
                                              public void onShow(DialogInterface arg0) {
                                                  alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.textColor));
                                                  alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.textColor));
                                                  alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.textColor));
                                              }
                                          });


                alertDialog.show();


            }
        });



    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}