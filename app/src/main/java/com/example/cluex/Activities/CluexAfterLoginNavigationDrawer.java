package com.example.cluex.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cluex.BuildConfig;
import com.example.cluex.Helper.BottomNavigationViewHelper;
import com.example.cluex.Helper.SessionManager;
import com.example.cluex.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CluexAfterLoginNavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationViewHelper afterLoginBottomNavigationJava =new BottomNavigationViewHelper();


    private File cluexGalleryFolder;
    private File imageFile;
    private String CLUEX_GALLERY_LOCATION="cluex_image_gallery";
    private static final int START_CAMERA_REQUEST_CODE_TWO=2;

    //------------------ for Camera - if not work we will delete this code-----------------------//

    private void createClueXImageGallery()
    {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        cluexGalleryFolder=new File(storageDir, CLUEX_GALLERY_LOCATION);
        if(!cluexGalleryFolder.exists())
        {
            cluexGalleryFolder.mkdirs();
        }
    }

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

    private void takePhoto()
    {

        Uri photoURI = null;
        Intent cameraIntent=new Intent();
        cameraIntent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);

        try
        {
            photoURI = FileProvider.getUriForFile(CluexAfterLoginNavigationDrawer.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent,START_CAMERA_REQUEST_CODE_TWO);
    }



    //---------------------------------------------------------end------------------------------------------------------//

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.bottom_navigation_home_alert_xml:

                    Fragment fragmentHomeAlert = new HomeAlertActivity();


                    if(fragmentHomeAlert != null)
                    {
                        FragmentTransaction navToSelectedFragTrans = getSupportFragmentManager().beginTransaction();
                        navToSelectedFragTrans.replace(R.id.after_login_nav_drawer_content_xml, fragmentHomeAlert);
                        navToSelectedFragTrans.commit();

                    }
                    return true;
                case R.id.bottom_navigation_ice_contacts_xml:
                    Fragment fragmentICEContacts = new ICEContactsActivity();


                    if(fragmentICEContacts != null)
                    {
                        FragmentTransaction navToSelectedFragTrans = getSupportFragmentManager().beginTransaction();
                        navToSelectedFragTrans.replace(R.id.after_login_nav_drawer_content_xml, fragmentICEContacts);
                        navToSelectedFragTrans.commit();

                    }

                    return true;
                case R.id.bottom_navigation_newsfeed_xml:
                    Intent intent = new Intent(CluexAfterLoginNavigationDrawer.this, MapsActivity.class);
                    startActivity(intent);
                case R.id.bottom_navigation_maps_xml:
                    Intent CheckIntent = new Intent(CluexAfterLoginNavigationDrawer.this, MapsActivity.class);
                    startActivity(CheckIntent);

                    //          mTextMessage.setText(R.string.title_notifications);


                    return true;
            }
            return false;
        }




    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluex_after_login_navigation_drawer);

        createClueXImageGallery();
        Toolbar afterLoginNavigationDrawerToolbarJava = (Toolbar) findViewById(R.id.after_login_toolbar_xml);
        setSupportActionBar(afterLoginNavigationDrawerToolbarJava);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout afterLoginNavigationDrawerLayoutJava = (DrawerLayout) findViewById(R.id.after_login_drawer_layout_xml);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, afterLoginNavigationDrawerLayoutJava, afterLoginNavigationDrawerToolbarJava, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        afterLoginNavigationDrawerLayoutJava.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.after_login_nav_view_xml);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        afterLoginBottomNavigationJava.disableShiftMode(bottomNavigationView);

        displaySelectedScreen(R.id.after_login_drawer_nav_to_home_alert_activity_xml);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.after_login_drawer_layout_xml);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displaySelectedScreen(int navItemId)
    {
        Fragment fragment=null;
        if(navItemId == R.id.after_login_drawer_nav_to_home_alert_activity_xml)
        {
            fragment = new HomeAlertActivity();
        }

        if(fragment != null)
        {
            FragmentTransaction navToSelectedFragTrans = getSupportFragmentManager().beginTransaction();
            navToSelectedFragTrans.replace(R.id.after_login_nav_drawer_content_xml, fragment);
            navToSelectedFragTrans.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.after_login_drawer_layout_xml);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cluex_after_login_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.after_login_nav_drawer_toolbar_camera_icon) {
            takePhoto();
            //return true;
        }
        if (id == R.id.after_login_nav_drawer_toolbar_logout_icon) {
            SessionManager session;
            // Session manager
            session = new SessionManager(getApplicationContext());
            session.logoutUser();
            //return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.after_login_drawer_nav_to_home_alert_activity_xml) {
            Fragment fragmentHomeAlert = new HomeAlertActivity();


            if(fragmentHomeAlert != null)
            {
                FragmentTransaction navToSelectedFragTrans = getSupportFragmentManager().beginTransaction();
                navToSelectedFragTrans.replace(R.id.after_login_nav_drawer_content_xml, fragmentHomeAlert);
                navToSelectedFragTrans.addToBackStack(null);
                navToSelectedFragTrans.commit();

            }
        } else if (id == R.id.after_login_drawer_nav_to_add_ice_contact_activity_xml) {
            Fragment fragmentICEContacts = new ICEContactsActivity();


            if(fragmentICEContacts != null)
            {
                FragmentTransaction navToSelectedFragTrans = getSupportFragmentManager().beginTransaction();
                navToSelectedFragTrans.replace(R.id.after_login_nav_drawer_content_xml, fragmentICEContacts);
                navToSelectedFragTrans.addToBackStack(null);
                navToSelectedFragTrans.commit();

            }


        }/* else if (id == R.id.after_login_drawer_nav_to_send_sms_activity_xml) {


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        displaySelectedScreen(id);

        return true;
    }
}
