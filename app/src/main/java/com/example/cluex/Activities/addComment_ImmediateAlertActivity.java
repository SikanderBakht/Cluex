package com.example.cluex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.cluex.R;


public class addComment_ImmediateAlertActivity extends AppCompatActivity {

    EditText comment_editText;
    String alert_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment__immediate_alert);


        setTitle(null);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setLogo(R.drawable.logo);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        comment_editText=(EditText) findViewById(R.id.comment_editText_id);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        alert_type= extras.getString("alertType");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }


    @Override
    public void onBackPressed() {

        String str_comment=comment_editText.getText().toString();


        Intent intent=new Intent();
        intent.putExtra("COMMENT",str_comment);
        setResult(2,intent);
        finish();//finishing activity


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.back_id){


          /*  Intent intent=new Intent(this,ImmediateAlert.class);
            intent.putExtra("alertType",alert_type);
            startActivity(intent);
            */

          String str_comment=comment_editText.getText().toString();


            Intent intent=new Intent();
            intent.putExtra("COMMENT",str_comment);
            setResult(2,intent);
            finish();//finishing activity
            


        }
        if(id == R.id.delete_id){

            comment_editText.setText("");

        }
        return super.onOptionsItemSelected(item);
    }

}
