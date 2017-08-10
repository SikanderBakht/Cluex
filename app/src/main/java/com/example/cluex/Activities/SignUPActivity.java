package com.example.cluex.Activities;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cluex.Helper.AppConfig;
import com.example.cluex.Helper.AppController;
import com.example.cluex.Helper.ConnectionStatus;
import com.example.cluex.Helper.inputValidation;
import com.example.cluex.R;
import com.example.cluex.SQL.SQLiteHandler;
import com.example.cluex.Helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class SignUPActivity extends AppCompatActivity {

    private static final String TAG = SignUPActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private TextInputEditText inputUsername;
    private TextInputLayout signUpUsernameTextInputLayoutJava;
    private TextInputEditText inputEmail;
    private TextInputLayout signUpEmailTextInputLayoutJava;
    private TextInputEditText inputContact;
    private TextInputLayout signUpContactTextInputLayoutJava;
    private TextInputEditText inputPassword;
    private TextInputLayout signUpPasswordTextInputLayoutJava;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private ConnectionStatus connectionStatusObj;

    private inputValidation inputValidationSignup;

    private void initializeSignupObjects()
    {
        inputUsername = (TextInputEditText) findViewById(R.id.username_signup_edt_txt_xml);
        signUpUsernameTextInputLayoutJava=(TextInputLayout) findViewById(R.id.user_name_signup_text_input_layout_xml);
        inputEmail = (TextInputEditText) findViewById(R.id.signup_email_txt_input_edt_txt_xml);
        signUpEmailTextInputLayoutJava=(TextInputLayout) findViewById(R.id.signup_email_text_input_layout_xml);
        inputPassword = (TextInputEditText) findViewById(R.id.signup_pswrd_edt_txt_xml);
        signUpPasswordTextInputLayoutJava=(TextInputLayout)findViewById(R.id.signup_pswrd_text_input_layout_xml);
        inputContact=(TextInputEditText) findViewById(R.id.signup_contact_edit_txt_xml);
        signUpContactTextInputLayoutJava=(TextInputLayout) findViewById(R.id.signup_cntct_no_text_input_layout_xml);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        connectionStatusObj=new ConnectionStatus(this);



        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        inputValidationSignup = new inputValidation(SignUPActivity.this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        initializeSignupObjects();
        // Progress dialog




        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignUPActivity.this,
                    HomeAlertActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
          /*      String name = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String contact=inputContact.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !contact.isEmpty()) {
                    registerUser(name, email, password,contact);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                } */

                if(connectionStatusObj.checkInternetConnection())
                    attemptSignUp();
                else {
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection please! ", Toast.LENGTH_LONG).show();
                }



            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email, final String password ,final String contact)
    {
        // Reset errors.
        inputUsername.setError(null);
        inputEmail.setError(null);
        inputPassword.setError(null);
        inputContact.setError(null);



        boolean cancel = false;
        View focusView = null;

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>()
        {
            boolean cancel = false;
            View focusView = null;

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error)
                    {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        // String uid = jObj.getString("uid");

                        // JSONObject user = jObj.getJSONObject("user");
                        //  String name = user.getString("name");
                        //  String email = user.getString("email");
                        //  String created_at = user.getString("created_at");

                        // Inserting row in users table
                        // db.addUser(name, email, uid, created_at);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        String date = df.format(Calendar.getInstance().getTime());

                      //  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                      //  String currentDateandTime = sdf.format(new Date());


                //        db.deleteUsers();
                //        db.addUser(name,email,date);

                        Toast.makeText(getApplicationContext(),name+" Sucessfully Registered!", Toast.LENGTH_SHORT).show();

                    //    Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(SignUPActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {


                        // Error occurred in registration. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        if( errorMsg.contains("username  and email"))
                        {
                            inputEmail.setError(getString(R.string.error_already_exist_email));
                            focusView = inputEmail;

                            inputUsername.setError(getString(R.string.error_invalid_username));
                            focusView = inputUsername;
                            cancel = true;

                        } 
                        else if (errorMsg.contains("username"))
                        {
                            inputUsername.setError(getString(R.string.error_invalid_username));
                            focusView = inputUsername;
                            cancel = true;
                        }
                        else if(errorMsg.contains("email"))
                        {
                            inputEmail.setError(getString(R.string.error_already_exist_email));
                            focusView = inputEmail;
                            cancel = true;

                        }

                        if(cancel)
                        {
                             focusView.requestFocus();
                        } else {


                         //   Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                            Toast.makeText(getApplicationContext(), name +" can not registered, Please try Again!!! ", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
            //    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Sign up Failed, Please try Again !!! ", Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("contact",contact);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    private void attemptSignUp() {




        // Reset errors.
        /*inputUsername.setError(null);
        inputEmail.setError(null);
        inputPassword.setError(null);
        inputContact.setError(null);*/






        if (!inputValidationSignup.isInputEditTextFilled(inputUsername,signUpUsernameTextInputLayoutJava, "Enter Username.")) {
            return;
        }
        if (!inputValidationSignup.isInputEditTextFilled(inputEmail,signUpEmailTextInputLayoutJava, "Enter Email.")) {
            return;
        }
        if (!inputValidationSignup.isInputEditTextFilled(inputPassword,signUpPasswordTextInputLayoutJava, "Enter Password.")) {
            return;
        }
        if (!inputValidationSignup.isInputEditTextFilled(inputContact,signUpContactTextInputLayoutJava, "Enter Contact Number.")) {
            return;
        }
        if (!inputValidationSignup.isInputEditTextEmail(inputEmail,signUpEmailTextInputLayoutJava, "Enter Valid Email.")) {
            return;
        }
        if (!inputValidationSignup.isValidPassword(inputPassword, signUpPasswordTextInputLayoutJava, "Enter Valid Password")) {
            return;
        }
        if (!inputValidationSignup.isValidContact(inputContact,signUpContactTextInputLayoutJava, "Enter Valid Contact Number.")) {
            return;
        }


/*

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            inputPassword.setError(getString(R.string.error_invalid_password));
            focusView = inputPassword;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            inputUsername.setError(getString(R.string.error_field_required));
            focusView = inputUsername;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.error_field_required));
            focusView = inputEmail;
            cancel = true;
        }

        else if (!isEmailValid(email)) {
            inputEmail.setError(getString(R.string.error_invalid_email));
            focusView = inputEmail;
            cancel = true;
            }


        if (TextUtils.isEmpty(contact)) {
            inputContact.setError(getString(R.string.error_field_required));
            focusView = inputContact;
            cancel = true;
        }

        else if (!isContactValid(contact)) {
            inputContact.setError(getString(R.string.error_invalid_contact));
            focusView = inputContact;
            cancel = true;
        }

*/

        // Store values at the time of the login attempt.
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String username= inputUsername.getText().toString();
        String contact= inputContact.getText().toString();



        registerUser(username, email,password,contact);


            //////////////////////////////////////////////////////////////////////////////////

    }




}
