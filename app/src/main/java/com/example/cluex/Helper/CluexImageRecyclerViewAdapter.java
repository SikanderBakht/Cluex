package com.example.cluex.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cluex.R;

import java.io.File;

/**
 * Created by sikander on 8/3/17.
 */

public class CluexImageRecyclerViewAdapter extends RecyclerView.Adapter<CluexImageRecyclerViewAdapter.cluexImageViewHolder> {

    private File imagesFile;

    public CluexImageRecyclerViewAdapter(File imageFiles) {
        this.imagesFile = imageFiles;
    }



    private Bitmap setReducedImageSize(cluexImageViewHolder holder, int position) {

        holder.getCluexImageView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int targetImageViewWidth = holder.getCluexImageView().getMeasuredWidth();
        int targetImageViewHeight = holder.getCluexImageView().getMeasuredHeight();


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagesFile.listFiles()[position].getAbsolutePath(), bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth/targetImageViewWidth, cameraImageHeight/targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        //Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        //tempImageViewJava.setImageBitmap(photoReducedSizeBitmp);
        return BitmapFactory.decodeFile(imagesFile.listFiles()[position].getAbsolutePath(),bmOptions);


    }



    @Override
    public cluexImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_image,parent,false);
        return new cluexImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(cluexImageViewHolder holder, int position) {

        File imageFile= imagesFile.listFiles()[position];
        //Bitmap cluexImageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        Bitmap cluexImageBitmap = setReducedImageSize(holder,position);
        holder.getCluexImageView().setImageBitmap(cluexImageBitmap);

    }

    @Override
    public int getItemCount() {
        if(imagesFile!=null)
        {
            return imagesFile.listFiles().length;
        }
        return 0;

    }

    public static class cluexImageViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView cluexImageView;


        public cluexImageViewHolder(View itemView) {
            super(itemView);
            this.cluexImageView = (ImageView) itemView.findViewById(R.id.recycler_view_image_xml);
        }

        public ImageView getCluexImageView() {
            return cluexImageView;
        }

    };
}

