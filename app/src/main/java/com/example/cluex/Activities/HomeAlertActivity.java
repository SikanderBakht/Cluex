package com.example.cluex.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cluex.BuildConfig;
import com.example.cluex.Helper.AlertAdapter;
import com.example.cluex.Helper.AlertIconsDetails;
import com.example.cluex.Helper.AppController;
import com.example.cluex.Helper.BottomNavigationViewHelper;
import com.example.cluex.R;
import com.example.cluex.Helper.SessionManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





public class HomeAlertActivity extends Fragment {

    private AppController app;

    //  private TextView mTextMessage;
    private GridLayoutManager lLayout;
    private SessionManager session;
    String username;
    String RegisteredUser_ID;
    //private BottomNavigationViewHelper afterLoginBottomNavigationJava =new BottomNavigationViewHelper();







    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Alert Home Page");


        session = new SessionManager(getActivity());
        session.checkLogin();

        // session = new SessionManager(getApplicationContext());
        RegisteredUser_ID = session.getUsername(username);
        app = (AppController) getActivity().getApplicationContext();
        app.ICEContactFillUp(RegisteredUser_ID);




        //   mTextMessage = (TextView) findViewById(R.id.message);
       /* BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
*/






        List<AlertIconsDetails> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(getActivity(), 2);

        RecyclerView rView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        AlertAdapter rcAdapter = new AlertAdapter(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);

        /*BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.navigation);

        afterLoginBottomNavigationJava.disableShiftMode(bottomNavigationView);*/

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_home_alert, container, false);
    }





 /*   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.bottom_navigation_home_alert_xml:

                    //         mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.bottom_navigation_ice_contacts_xml:
                    Intent AddContactIntent = new Intent(getActivity(), ICEContactsActivity.class);
                    startActivity(AddContactIntent);
                    //         mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.bottom_navigation_newsfeed_xml:
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    startActivity(intent);
                case R.id.bottom_navigation_maps_xml:
                    Intent CheckIntent = new Intent(getActivity(), MapsActivity.class);
                    startActivity(CheckIntent);

                    //          mTextMessage.setText(R.string.title_notifications);


                    return true;
            }
            return false;
        }




    };

*/


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        createClueXImageGallery();
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        // session = new SessionManager(getApplicationContext());
        RegisteredUser_ID = session.getUsername(username);
        app = (AppController) getApplicationContext();
        app.ICEContactFillUp(RegisteredUser_ID);

        setContentView(R.layout.activity_home_alert);


        //   mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        setTitle(null);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.home_alert_toolbar_xml);
        setSupportActionBar(topToolBar);


        List<AlertIconsDetails> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(HomeAlertActivity.this, 2);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        AlertAdapter rcAdapter = new AlertAdapter(HomeAlertActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bb.disableShiftMode(bottomNavigationView);




    }*/


    //////////////////////////////////////////////////////
    /////////////////////////////////////////////////////

 /*   @Override
    public void onBackPressed() {

        // finish();
        Intent intent = new Intent(HomeAlertActivity.this, HomeAlertActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_alert_menu_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_page_alert_toolbar_camera_icon) {


            takePhoto();




        }
        if (id == R.id.home_page_alert_toolbar_logout_icon) {
            SessionManager session;
            // Session manager
            session = new SessionManager(getApplicationContext());
            session.logoutUser();

        }


        return super.onOptionsItemSelected(item);
    }*/

    private List<AlertIconsDetails> getAllItemList() {

        List<AlertIconsDetails> allItems = new ArrayList<AlertIconsDetails>();
        allItems.add(new AlertIconsDetails("Roberry", R.drawable.roberry));
        allItems.add(new AlertIconsDetails("Accident", R.drawable.accident));
        allItems.add(new AlertIconsDetails("Medical", R.drawable.medical));
        allItems.add(new AlertIconsDetails("Fire", R.drawable.fire));

        allItems.add(new AlertIconsDetails("Natural Disaster", R.drawable.natrual_disater));
        allItems.add(new AlertIconsDetails("Harassment", R.drawable.harassment));
        allItems.add(new AlertIconsDetails("Kidnapping", R.drawable.kidnapping));
        allItems.add(new AlertIconsDetails("Terrorist Attack", R.drawable.terrorist_attack));


        return allItems;
    }




}



/*
public class HomeAlertActivity extends AppCompatActivity {

    //  private TextView mTextMessage;
    private GridLayoutManager lLayout;
    private SessionManager session;
    String username;
    String RegisteredUser_ID;
    private BottomNavigationViewHelper bb =new BottomNavigationViewHelper();


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
        /*imageFile = File.createTempFile(imageFileName, */ /* prefix *//*".jpg",         *//* suffix *//*cluexGalleryFolder     */ /* directory */ /*);

        // Save a file: path for use with ACTION_VIEW intents
        //mImageFileLocation = image.getAbsolutePath();// unCommented By Sikander for temporary use
        return imageFile;
    }

    private void takePhoto()
    {

        Uri photoURI = null;
        Intent  cameraIntent=new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        try
        {
            photoURI = FileProvider.getUriForFile(HomeAlertActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent,START_CAMERA_REQUEST_CODE_TWO);
    }



    //---------------------------------------------------------end------------------------------------------------------//


    private AppController app;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {




        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.navigation_home:

                            //         mTextMessage.setText(R.string.title_home);
                            return true;
                        case R.id.navigation_dashboard:
                            Intent AddContactIntent = new Intent(getApplicationContext(), ICEContactsActivity.class);
                            startActivity(AddContactIntent);
                            //         mTextMessage.setText(R.string.title_dashboard);
                            return true;
                        case R.id.navigation_notifications:
                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            startActivity(intent);
                        case R.id.navigation_newsfeed:
                            Intent CheckIntent = new Intent(getApplicationContext(), MapsActivity.class);
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



        createClueXImageGallery();
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

       // session = new SessionManager(getApplicationContext());
          RegisteredUser_ID = session.getUsername(username);
         app = (AppController) getApplicationContext();
        app.ICEContactFillUp(RegisteredUser_ID);

        setContentView(R.layout.activity_home_alert);


        //   mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        setTitle(null);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.home_alert_toolbar_xml);
        setSupportActionBar(topToolBar);


        List<AlertIconsDetails> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(HomeAlertActivity.this, 2);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        AlertAdapter rcAdapter = new AlertAdapter(HomeAlertActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bb.disableShiftMode(bottomNavigationView);




    }


    @Override
    public void onBackPressed() {

        // finish();
        Intent intent = new Intent(HomeAlertActivity.this, HomeAlertActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_alert_menu_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_page_alert_toolbar_camera_icon) {


            takePhoto();




        }
        if (id == R.id.home_page_alert_toolbar_logout_icon) {
            SessionManager session;
            // Session manager
            session = new SessionManager(getApplicationContext());
            session.logoutUser();

        }


        return super.onOptionsItemSelected(item);
    }

    private List<AlertIconsDetails> getAllItemList() {

        List<AlertIconsDetails> allItems = new ArrayList<AlertIconsDetails>();
        allItems.add(new AlertIconsDetails("Roberry", R.drawable.roberry));
        allItems.add(new AlertIconsDetails("Accident", R.drawable.accident));
        allItems.add(new AlertIconsDetails("Medical", R.drawable.medical));
        allItems.add(new AlertIconsDetails("Fire", R.drawable.fire));

        allItems.add(new AlertIconsDetails("Natural Disaster", R.drawable.natrual_disater));
        allItems.add(new AlertIconsDetails("Harassment", R.drawable.harassment));
        allItems.add(new AlertIconsDetails("Kidnapping", R.drawable.kidnapping));
        allItems.add(new AlertIconsDetails("Terrorist Attack", R.drawable.terrorist_attack));


        return allItems;
    }




    }

*/