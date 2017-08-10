package com.example.cluex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cluex.R;


public class addComment_ImmediateAlertActivity extends AppCompatActivity {

    private EditText comment_editText;
    private String alert_type;
    private Button addImmediateAlertDetailsBtnJava;
    private Button cancelImmedaiteAlertButtonJava;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment__immediate_alert);

        addImmediateAlertDetailsBtnJava = (Button) findViewById(R.id.add_immediate_alert_detail_btn_xml);
        cancelImmedaiteAlertButtonJava = (Button) findViewById(R.id.cancel_immediate_alert_detail_btn_xml);

        addImmediateAlertDetailsBtnJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_comment=comment_editText.getText().toString();


                Intent intent=new Intent();
                intent.putExtra("COMMENT",str_comment);
                setResult(2,intent);
                finish();//finishing activity
            }
        });

        cancelImmedaiteAlertButtonJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(addComment_ImmediateAlertActivity.this, ImmediateAlert.class);
                startActivity(backIntent);
                finish();
            }
        });

        setTitle(null);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.add_commentimmediate_alert_toolbar_xml);
        setSupportActionBar(topToolBar);
      //  topToolBar.setLogo(R.drawable.logo);
      //  topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        comment_editText=(EditText) findViewById(R.id.comment_editText_id);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        alert_type= extras.getString("alertType");




    }









}
