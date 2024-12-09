package com.example.cis183_finalproject_mosquadapp;

import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private static final String ALLOWED_APT_CHARS = "^[a-zA-Z0-9\\s\\-.,/'&]*$";
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
            tv.setText(R.string.zip_code_error_message);
        }
        return tempBool;
    }
    public boolean FL_IsEmptyCheck(EditText et, TextView tv, String s)
    {
        if(et.getText().toString().isEmpty())
        {
            tv.setVisibility(View.VISIBLE);
            tv.setText(s);
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
            if(!bool)
            {
                return false;
            }
        }
        return true;
    }
    public ArrayList<String> FL_GetStatsArray()
    {
        ArrayList<String> fl_statesArray = new ArrayList<>();
        fl_statesArray.add("Alabama");
        fl_statesArray.add("Alaska");
        fl_statesArray.add("Arizona");
        fl_statesArray.add("Arkansas");
        fl_statesArray.add("California");
        fl_statesArray.add("Colorado");
        fl_statesArray.add("Connecticut");
        fl_statesArray.add("Delaware");
        fl_statesArray.add("Florida");
        fl_statesArray.add("Georgia");
        fl_statesArray.add("Hawaii");
        fl_statesArray.add("Idaho");
        fl_statesArray.add("Illinois");
        fl_statesArray.add("Indiana");
        fl_statesArray.add("Iowa");
        fl_statesArray.add("Kansas");
        fl_statesArray.add("Kentucky");
        fl_statesArray.add("Louisiana");
        fl_statesArray.add("Maine");
        fl_statesArray.add("Maryland");
        fl_statesArray.add("Massachusetts");
        fl_statesArray.add("Michigan");
        fl_statesArray.add("Minnesota");
        fl_statesArray.add("Mississippi");
        fl_statesArray.add("Missouri");
        fl_statesArray.add("Montana");
        fl_statesArray.add("Nebraska");
        fl_statesArray.add("Nevada");
        fl_statesArray.add("New Hampshire");
        fl_statesArray.add("New Jersey");
        fl_statesArray.add("New Mexico");
        fl_statesArray.add("New York");
        fl_statesArray.add("North Carolina");
        fl_statesArray.add("North Dakota");
        fl_statesArray.add("Ohio");
        fl_statesArray.add("Oklahoma");
        fl_statesArray.add("Oregon");
        fl_statesArray.add("Pennsylvania");
        fl_statesArray.add("Rhode Island");
        fl_statesArray.add("South Carolina");
        fl_statesArray.add("South Dakota");
        fl_statesArray.add("Tennessee");
        fl_statesArray.add("Texas");
        fl_statesArray.add("Utah");
        fl_statesArray.add("Vermont");
        fl_statesArray.add("Virginia");
        fl_statesArray.add("Washington");
        fl_statesArray.add("West Virginia");
        fl_statesArray.add("Wisconsin");
        fl_statesArray.add("Wyoming");
        return fl_statesArray;
    }
    public void FL_SetTotalAcreage(TextView tv, ArrayList<LatLng> latlngs)
    {
        double fl_totalAcreage   = (SphericalUtil.computeArea(latlngs) / 4046.86);
        DecimalFormat fl_dFormat = new DecimalFormat("0.00");
        tv.setText(fl_dFormat.format(fl_totalAcreage));
    }
    public String FL_GetTotalAcreage(ArrayList<LatLng> latlngs)
    {
        double fl_totalAcreage   = (SphericalUtil.computeArea(latlngs) / 4046.86);
        DecimalFormat fl_dFormat = new DecimalFormat("0.00");
        return fl_dFormat.format(fl_totalAcreage);
    }
}
