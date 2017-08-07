package com.example.cluex.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cluex.Helper.CluexGalleryImageSliderAdapter;
import com.example.cluex.R;

public class imageSliderActivity extends AppCompatActivity {
    private CluexGalleryImageSliderAdapter sliderCustomAdapter;
    private ViewPager sliderViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        sliderViewpager = (ViewPager) findViewById(R.id.cluex_slider_view_pager_xml);

        sliderCustomAdapter = new CluexGalleryImageSliderAdapter(this);
        sliderViewpager.setAdapter(sliderCustomAdapter);
    }
}
