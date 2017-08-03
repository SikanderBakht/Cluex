package com.example.cluex.Helper;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

//import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by sikander on 6/8/17.
 */

public class inputValidation {
    private Context context;

    public inputValidation(Context context){
        this.context = context;
    }

    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()){
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;

        }else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message ){
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)){
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        }else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, String textInputString, TextInputLayout textInputLayout, String message ){
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputString;
        if (!value1.contentEquals(value2)){
            textInputLayout.setError(message);
            //hideKeyboardFrom(textInputEditText2);
            return false;
        }else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }


/*    public boolean isSpinnerSelected(MaterialSpinner spinner)
    {
        String value=(String)spinner.getSelectedItem();
        if(value.equals(spinner.getHint()))
        {
            return false;
        }
        return true;
    }*/

    public boolean isValidPassword(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message)
    {
        String value = textInputEditText.getText().toString();
        if (/*value.length()<6 || */(!value.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"))){

            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;

        }else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isValidPassword(EditText textInputEditText, Context appContext)
    {
        String value = textInputEditText.getText().toString();
        if (/*value.length()<6 || */(!value.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"))){
            Toast.makeText(appContext,"Your Password must contain one Alphabet and one Digit",Toast.LENGTH_SHORT).show();

            hideKeyboardFrom(textInputEditText);
            return false;

        }
        return true;
    }

    public boolean isValidContact(EditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString();
        if(value.length()!=11)
        {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }
        else
        {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }


    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



}
