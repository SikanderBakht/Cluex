package com.example.cluex.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cluex.R;

import java.io.File;

/**
 * Created by sikander on 8/6/17.
 */

public class CluexGalleryImageSliderAdapter extends PagerAdapter{

    private Context context;
    private LayoutInflater inflateImageSlider;
    private String[] cluexImagesFilePaths;

    public CluexGalleryImageSliderAdapter(Context context)
    {
        this.context=context;
    }

    private String[] getImagesPathFromDirectory()
    {
        String[] fileNames = {""};
        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"DCIM/Camera/cluex_image_gallery");
        if(path.exists())
        {
            fileNames = path.list();
        }

        return fileNames;
    }

    @Override
    public int getCount() {
        return getImagesPathFromDirectory().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return true;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflateImageSlider = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderView = inflateImageSlider.inflate(R.layout.image_slider, container, false);

        cluexImagesFilePaths=getImagesPathFromDirectory();

        ImageView imageSlider = (ImageView) sliderView.findViewById(R.id.cluex_image_slider_image_view_xml);

        Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/cluex_image_gallery/"+cluexImagesFilePaths[position]);
        imageSlider.setImageBitmap(myBitmap);

        container.addView(sliderView);
        return sliderView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
