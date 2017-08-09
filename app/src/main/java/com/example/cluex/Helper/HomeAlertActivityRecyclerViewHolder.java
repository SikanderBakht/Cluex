package com.example.cluex.Helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluex.R;

/**
 * Created by Awais on 7/4/2017.
 */

public class HomeAlertActivityRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;
    public Button detailAlertButtonJava;
    public Button immediateAlertButtonJava;

    public HomeAlertActivityRecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
        detailAlertButtonJava = (Button) itemView.findViewById(R.id.detail_alert_btn_xml);
        immediateAlertButtonJava = (Button) itemView.findViewById(R.id.immediate_alert_btn_xml);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}