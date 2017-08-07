package com.example.cluex.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cluex.BuildConfig;
import com.example.cluex.Helper.CluexGalleryRecyclerViewImageClick;
import com.example.cluex.Helper.CluexImageRecyclerViewAdapter;
import com.example.cluex.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private static final int START_CAMERA_REQUEST_CODE=0;
    private static final int START_GALLERY_REQUEST_CODE=1;

    private String CLUEX_GALLERY_LOCATION="cluex_image_gallery";

    private Button addPhotoBtnJava;
    private RecyclerView cluexGalleryRecyclerViewJava;
    private File cluexGalleryFolder;
    private File imageFile;

    AlertDialog addPhotoDialog;







    private void initializeObjects()
    {
        addPhotoBtnJava=(Button) findViewById(R.id.add_photo_btn_xml);
        cluexGalleryRecyclerViewJava=(RecyclerView) findViewById(R.id.cluex_image_gallery_recycler_view_xml);
    }
    //---------------------------------------------------------------------------//
    private void createClueXImageGallery()
    {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        cluexGalleryFolder=new File(storageDir, CLUEX_GALLERY_LOCATION);
        if(!cluexGalleryFolder.exists())
        {
            cluexGalleryFolder.mkdirs();
        }
    }
    //---------------------------------------------------------------------------//

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_ClueX_" + timeStamp + "_";
        //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        imageFile = File.createTempFile(imageFileName,  /* prefix */".jpg",         /* suffix */cluexGalleryFolder      /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        //mImageFileLocation = image.getAbsolutePath();// unCommented By Sikander for temporary use
        return imageFile;
    }

    //---------------------------------------------------------------------------//
    private void takePhoto()
    {

        Uri photoURI = null;
        Intent  cameraIntent=new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        try
        {
            photoURI = FileProvider.getUriForFile(CameraActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent,START_CAMERA_REQUEST_CODE);
    }

    //---------------------------------------------------------------------------//




    private void selectPhotoFromGallery()
    {
        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), START_GALLERY_REQUEST_CODE);*/
        Uri photoURI = null;
        Intent galleryIntent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        try {


            photoURI = FileProvider.getUriForFile(CameraActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());


        } catch (IOException e) {
            e.printStackTrace();
        }
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


        startActivityForResult(galleryIntent, START_GALLERY_REQUEST_CODE);



    }
    //---------------------------------------------------------------------------//

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }


    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        addPhotoDialog.hide();
        if(requestCode==START_CAMERA_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {

                //Bundle extras = data.getExtras();
                //Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
                //tempImageViewJava.setImageBitmap(photoCapturedBitmap);

                //Bitmap photoCapturedBitmap = BitmapFactory.decodeFile(mImageFileLocation);
                //tempImageViewJava.setImageBitmap(photoCapturedBitmap);
                //rotateImage(setReducedImageSize());

                RecyclerView.Adapter reloadCluexAdapter= new CluexImageRecyclerViewAdapter(cluexGalleryFolder);
                cluexGalleryRecyclerViewJava.swapAdapter(reloadCluexAdapter,false);
            }
        }
        else if (requestCode==START_GALLERY_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    try {

                        copyFile(new File(getPathFromURI(data.getData())), imageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    RecyclerView.Adapter reloadCluexAdapter= new CluexImageRecyclerViewAdapter(cluexGalleryFolder);
                    cluexGalleryRecyclerViewJava.swapAdapter(reloadCluexAdapter,false);
                    // Set the image in ImageView
                    //imgView.setImageURI(selectedImageUri);
                }

            }
        }

    }
    //---------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        createClueXImageGallery();
        initializeObjects();

        RecyclerView.LayoutManager cluexGalleryLayoutManager = new GridLayoutManager(this,2);
        cluexGalleryRecyclerViewJava.setLayoutManager(cluexGalleryLayoutManager);
        RecyclerView.Adapter cluexImageRecViewAdapter = new CluexImageRecyclerViewAdapter(cluexGalleryFolder);
        cluexGalleryRecyclerViewJava.setAdapter(cluexImageRecViewAdapter);

        addPhotoBtnJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder addPhotoDialogBuilder=new AlertDialog.Builder(CameraActivity.this);
                View addPhotoDialogView= getLayoutInflater().inflate(R.layout.add_photo_dialog,null);
                Button takePhotoBtnJava=(Button)addPhotoDialogView.findViewById(R.id.take_photo_btn_xml);
                Button selectPhotoBtnJava=(Button)addPhotoDialogView.findViewById(R.id.select_photo_btn_xml);

                takePhotoBtnJava.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takePhoto();

                    }
                });
                selectPhotoBtnJava.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectPhotoFromGallery();
                    }
                });

                addPhotoDialogBuilder.setView(addPhotoDialogView);
                addPhotoDialog= addPhotoDialogBuilder.create();
                addPhotoDialog.show();

            }
        });

        CluexGalleryRecyclerViewImageClick.addTo(cluexGalleryRecyclerViewJava).setOnItemClickListener(new CluexGalleryRecyclerViewImageClick.OnItemClickListener()
        {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(CameraActivity.this, imageSliderActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "ImageView pressed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
