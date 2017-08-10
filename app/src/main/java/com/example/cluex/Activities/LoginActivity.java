package com.example.cluex.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = SignUPActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private TextInputEditText inputUserName;
    private TextInputLayout inputUserNameTextInputLayout;
    private TextInputEditText inputPassword;
    private TextInputLayout inputPasswordTextInputLayout;
    private ProgressDialog pDialog;
    private SessionManager session;
    private inputValidation inputValidationLogin;
    private ConnectionStatus connectionStatusObj;


    private void initializeObjects()
    {
        inputUserName = (TextInputEditText) findViewById(R.id.username_id);
        inputUserNameTextInputLayout=(TextInputLayout) findViewById(R.id.user_name_text_input_layout_xml);
        inputPassword = (TextInputEditText) findViewById(R.id.pwd_id);
        inputPasswordTextInputLayout=(TextInputLayout) findViewById(R.id.pswrd_text_input_layout_xml);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        connectionStatusObj=new ConnectionStatus(this);

        //for validation to focus view
        /*mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);*/

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);




        // Session manager
        session = new SessionManager(getApplicationContext());

        inputValidationLogin = new inputValidation(LoginActivity.this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initializeObjects();

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this,HomeAlertActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                if(connectionStatusObj.checkInternetConnection())
                    attemptLogin();
                else {
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection please! ", Toast.LENGTH_LONG).show();
                }


            }


        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SignUPActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        session.createLoginSession(email);


                        // user successfully logged in
                        // Create login session
                   /*

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);
*/
                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                HomeAlertActivity.class);
                        Toast.makeText(getApplicationContext(),
                                "Logged in Successfuly!", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                   //     Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Login Failed, Please try Again !!! ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(), "Login Failed, Please try Again !!! ", Toast.LENGTH_LONG).show();
                 //   Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
            //    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Login Failed, Please try Again !!! ", Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email);
                params.put("password", password);

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



    private void attemptLogin() {


        if (!inputValidationLogin.isInputEditTextFilled(inputUserName, inputUserNameTextInputLayout, "Enter Username.")) {
            return;
        }
        if (!inputValidationLogin.isInputEditTextFilled(inputPassword, inputPasswordTextInputLayout, "Enter Password.")) {
            return;
        }
        if (!inputValidationLogin.isValidPassword(inputPassword, inputPasswordTextInputLayout, "Enter Valid Password")) {
            return;
        }





        // Store values at the time of the login attempt.
        String userName = inputUserName.getText().toString();
        String password = inputPassword.getText().toString();

        checkLogin(userName, password);


    }




}
