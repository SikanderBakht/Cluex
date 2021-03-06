package com.example.cluex.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cluex.Helper.ContactHelper;
import com.example.cluex.R;


public class AddContact extends AppCompatActivity implements View.OnClickListener {

    private EditText edtContactName, edtContactNumber;
    private Button btnAddContact, btnCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_contact);

        edtContactName = (EditText) findViewById(R.id.edtContactName);
        edtContactNumber = (EditText) findViewById(R.id.edtContactNumber);

        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edtContactName.getText().toString().equals("")
                        && edtContactNumber.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "ICE Contact has been updated",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    String message = edtContactName.getText().toString();
                    String messege = edtContactNumber.getText().toString();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Message", message);
                    returnIntent.putExtra("Messege", messege);

                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragmentICEContacts = new ICEContactsActivity();


                if(fragmentICEContacts != null)
                {

                    FragmentTransaction navToSelectedFragTrans = getSupportFragmentManager().beginTransaction();
                    navToSelectedFragTrans.replace(R.id.add_new_ice_contact_xml, fragmentICEContacts);
                    navToSelectedFragTrans.addToBackStack(null);
                    navToSelectedFragTrans.commit();
                    finish();

                }

                /*Intent myIntent = new Intent(AddContact.this, ICEContactsActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);*/

            }
        });






    }

/*    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(AddContact.this, ICEContactsActivity.class);
        startActivity(intent);
    }
*/
    public void onClick(View v) {

        //Intent intent = new Intent(this, HomeAlertActivity.class);
        if (v.getId() == R.id.btnAddContact)
        {
            Fragment fragmentHomeAlert = new HomeAlertActivity();


            if(fragmentHomeAlert != null)
            {
                FragmentTransaction navToSelectedFragTrans = getSupportFragmentManager().beginTransaction();
                navToSelectedFragTrans.replace(R.id.add_new_ice_contact_xml, fragmentHomeAlert);
                navToSelectedFragTrans.addToBackStack(null);
                ContactHelper.insertContact(getContentResolver(), edtContactName.getText().toString(), edtContactNumber.getText().toString());
                edtContactName.setText("");
                edtContactNumber.setText("");
                navToSelectedFragTrans.commit();

            }
            /*if (edtContactName.getText().toString().equals("") && edtContactNumber.getText().toString().equals(""))
            {
                Toast.makeText(this, "Please fill both fields...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ContactHelper.insertContact(getContentResolver(), edtContactName.getText().toString(), edtContactNumber.getText().toString());
                edtContactName.setText("");
                edtContactNumber.setText("");
                startActivity(intent);
            }*/

        }
    }

}

