package com.example.cis183_finalproject_mosquadapp;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// used to house all the function that will be used across multiple activities //
// and a few other error checking functions //
public class FunctionLibrary
{
    private static final String ALLOWED_USERNAME_CHARS = "^[a-zA-Z0-9_.-]{12,}$";
    private static final String ALLOWED_PASSWORD_CHARS = "^(?=.+[a-z])(?=.+[A-Z])(?=.+[0-9])(?=.+[!@#$%^&*()_\\-=+{}\\[\\]|;:\"'<>,.?/~]).{12,}$";
    private static final String ALLOWED_NAME_CHARS = "^[a-zA-Z\\s]+$";
    private static final String ALLOWED_EMAIL_CHARS = "^[a-zA-Z]([a-zA-Z0-9_.-]+[a-zA-Z0-9])@([a-zA-Z]+[a-zA-Z.]+[a-zA-Z])(\\.com|\\.gov|\\.edu)$";
    private static final String ALLOWED_PHONE_NUMBERS = "^[2-9]{3}-[2-9]{3}-[0-9]{4}$";
    private static final String ALLOWED_STREET_CHARS = "^[a-zA-Z0-9\\s\\-.,/'&]+$";
    private static final String ALLOWED_APT_CHARS = "^[a-zA-Z0-9\\s\\-.,/'&]*$"
            ;
    private static final String ALLOWED_CITY_CHARS = "^[a-zA-Z\\s\\-.']+$";
    private static final String ALLOWED_ZIP_CHARS = "^[0-9]{5}$";

    public FunctionLibrary()
    {

    }
    public boolean FL_UsernameInputValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_USERNAME_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(!tempBool)
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.username_error_message);
        }
        else
        {
            tv.setVisibility(View.INVISIBLE);
        }
        return tempBool;
    }
    public boolean FL_PasswordInputValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_PASSWORD_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(!tempBool)
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.password_error_message);
        }
        else
        {
            tv.setVisibility(View.INVISIBLE);
        }
        return tempBool;
    }
    public boolean FL_NameInputValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_NAME_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(tempBool)
        {
            tv.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.name_error_message);
        }
        return tempBool;
    }
    public boolean FL_EmailInputValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_EMAIL_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(tempBool)
        {
            tv.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.email_error_message);
        }
        return tempBool;
    }
    public boolean FL_PhoneNumberValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_PHONE_NUMBERS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(tempBool)
        {
            tv.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.phone_error_message);
        }
        return tempBool;
    }
    public boolean FL_StreetAddressValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_STREET_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(tempBool)
        {
            tv.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.street_address_error_message);
        }
        return tempBool;
    }
    public boolean FL_AptAddressValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_APT_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(tempBool)
        {
            tv.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.street_address_error_message);
        }
        return tempBool;
    }
    public boolean FL_CityValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_CITY_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(tempBool)
        {
            tv.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.city_error_message);
        }
        return tempBool;
    }
    public boolean FL_ZipCodeValidation(TextView tv, CharSequence cs)
    {
        Pattern goodChars = Pattern.compile(ALLOWED_ZIP_CHARS);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean tempBool = checkingChars.find();
        if(tempBool)
        {
            tv.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText("");
        }
        return tempBool;
    }
    public boolean FL_IsInputEmptyCheck(EditText et, TextView tv, String s)
    {
        if(et.getText().toString().isEmpty())
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(s);
            Log.d("is empty input check", et.toString());
            return true;
        }
        tv.setVisibility(View.INVISIBLE);
        return false;
    }
    public boolean FL_InputIsGood(boolean[] b)
    {
        int cnt = 0;
        for (boolean bool : b)
        {
            cnt++;
            Log.d("start of loop", bool + "");
            if(!bool)
            {
                Log.d("input is false", bool + " - " + cnt);

                return false;
            }
        }
        Log.d("input is true", true + " - " + cnt);
        return true;
    }

}
