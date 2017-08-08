package com.example.cluex.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cluex.Helper.AlertAdapter;
import com.example.cluex.Helper.AlertIconsDetails;
import com.example.cluex.Helper.AppController;
import com.example.cluex.Helper.BottomNavigationViewHelper;
import com.example.cluex.R;
import com.example.cluex.Helper.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class HomeAlertActivity extends AppCompatActivity {

    //  private TextView mTextMessage;
    private GridLayoutManager lLayout;
    private SessionManager session;
    String username;
    String RegisteredUser_ID;
    private BottomNavigationViewHelper bb =new BottomNavigationViewHelper();

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

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setLogo(R.drawable.logo);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

             SessionManager session;

            // Session manager
               session = new SessionManager(getApplicationContext());

               session.logoutUser();



        }
        if (id == R.id.action_refresh) {
            Toast.makeText(HomeAlertActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, SignUPActivity.class);
            startActivity(intent);

            Toast.makeText(HomeAlertActivity.this, "Create Text", Toast.LENGTH_LONG).show();
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

