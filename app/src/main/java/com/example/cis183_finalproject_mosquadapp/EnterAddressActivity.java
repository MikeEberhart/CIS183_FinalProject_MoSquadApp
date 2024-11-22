package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EnterAddressActivity extends AppCompatActivity
{
    String[] ea_statesArray = {"AL - Alabama", "AK - Alaska", "AZ - Arizona", "AR - Arkansas", "CA - California",
            "CO - Colorado", "CT - Connecticut", "DE - Delaware", "FL - Florida", "GA - Georgia", "HI - Hawaii",
            "ID - Idaho", "IL - Illinois", "IN - Indiana", "IA - Iowa", "KS - Kansas", "KY - Kentucky",
            "LA - Louisiana", "ME - Maine", "MD - Maryland", "MA - Massachusetts", "MI - Michigan",
            "MN - Minnesota", "MS - Mississippi", "MO - Missouri", "MT - Montana", "NE - Nebraska",
            "NV - Nevada", "NH - New Hampshire", "NJ - New Jersey", "NM - New Mexico", "NY - New York",
            "NC - North Carolina", "ND - North Dakota", "OH - Ohio", "OK - Oklahoma", "OR - Oregon",
            "PA - Pennsylvania", "RI - Rhode Island", "SC - South Carolina", "SD - South Dakota",
            "TN - Tennessee", "TX - Texas", "UT - Utah", "VT - Vermont", "VA - Virginia", "WA - Washington",
            "WV - West Virginia", "WI - Wisconsin", "WY - Wyoming"};
//    String[] ea_statesArray = {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS",
//            "KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK",
//            "OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};

    Intent ea_welcomeUserIntent;
    Intent ea_propertyMapIntent;
    TextView tv_jEnterAddress_headerText;
    TextView tv_jEnterAddress_streetAddressError;
    TextView tv_jEnterAddress_aptOtherError;
    TextView tv_jEnterAddress_cityError;
    TextView tv_jEnterAddress_zipCodeError;
    TextView tv_jEnterAddress_stateError;
    EditText et_jEnterAddress_streetAddress;
    EditText et_jEnterAddress_aptOther;
    EditText et_jEnterAddress_city;
    EditText et_jEnterAddress_zipCode;
    Spinner sp_jEnterAddress_states;
    Button btn_jEnterAddress_saveAndContinue;
    Button btn_jEnterAddress_home;
    Button btn_jEnterAddress_back;
    DatabaseHelper ea_dbHelper;
    FunctionLibrary ea_funcLib;
    ArrayAdapter<String> ea_listOfStatesAdapter;
    ServiceAddress ea_userServiceAddress;
    String ea_tempStateName;
    private boolean[] ea_inputIsGood;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter_address);

        EA_ListOfViews();
        EA_InitData();
        EA_OnClickListeners();
        EA_TextChangeEventListeners();
        if(UserSessionData.GetIsPassedFromWelcomeUser())
        {
            EA_LoadUserAddressData();
            btn_jEnterAddress_saveAndContinue.setText("Save");
        }
    }
    private void EA_InitData()
    {
        ea_funcLib = new FunctionLibrary();
        ea_dbHelper = new DatabaseHelper(this);
        ea_welcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
        ea_propertyMapIntent = new Intent(this, PropertyMapActivity.class);
        ea_listOfStatesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ea_statesArray);
        sp_jEnterAddress_states.setAdapter(ea_listOfStatesAdapter);
        ea_inputIsGood = new boolean[4];
    }
    private void EA_ListOfViews()
    {
        tv_jEnterAddress_headerText         = findViewById(R.id.tv_vEnterAddress_headerText);
        tv_jEnterAddress_streetAddressError = findViewById(R.id.tv_vEnterAddress_streetAddressError);
        tv_jEnterAddress_aptOtherError      = findViewById(R.id.tv_vEnterAddress_apOtherError);
        tv_jEnterAddress_cityError          = findViewById(R.id.tv_vEnterAddress_cityError);
        tv_jEnterAddress_zipCodeError       = findViewById(R.id.tv_vEnterAddress_zipCodeError);
        tv_jEnterAddress_stateError         = findViewById(R.id.tv_vEnterAddress_stateError);
        et_jEnterAddress_streetAddress      = findViewById(R.id.et_vEnterAddress_streetAddress);
        et_jEnterAddress_aptOther           = findViewById(R.id.et_vEnterAddress_aptOther);
        et_jEnterAddress_city               = findViewById(R.id.et_vEnterAddress_city);
        et_jEnterAddress_zipCode            = findViewById(R.id.et_vEnterAddress_zipCode);
        sp_jEnterAddress_states             = findViewById(R.id.sp_vEnterAddress_states);
        btn_jEnterAddress_back              = findViewById(R.id.btn_vEnterAddress_back);
        btn_jEnterAddress_home              = findViewById(R.id.btn_vEnterAddress_home);
        btn_jEnterAddress_saveAndContinue   = findViewById(R.id.btn_vEnterAddress_saveAndContinue);
    }
    private void EA_OnClickListeners()
    {
        btn_jEnterAddress_saveAndContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(et_jEnterAddress_aptOther.getText().toString().isEmpty())
                {
                    ea_inputIsGood[1] = true;
                }
                if(UserSessionData.GetIsPassedFromWelcomeUser())
                {
                    if(!EA_InputIsEmptyCheck() && ea_funcLib.FL_InputIsGood(ea_inputIsGood))
                    {
                        EA_UpdateUserAddress();
                        UserSessionData.SetIsPassedFromWelcomeUser(false);
                        startActivity(ea_welcomeUserIntent);
                    }
                }
                else
                {
                    if(!EA_InputIsEmptyCheck() && ea_funcLib.FL_InputIsGood(ea_inputIsGood))
                    {
                        EA_SaveNewAddress();
                        UserSessionData.SetIsPassedFromWelcomeUser(false);
                        startActivity(ea_propertyMapIntent);
                    }
                }
            }
        });
        btn_jEnterAddress_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserSessionData.SetIsPassedFromWelcomeUser(false);
                startActivity(ea_welcomeUserIntent);
            }
        });
        btn_jEnterAddress_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
        sp_jEnterAddress_states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
               ea_tempStateName = ea_statesArray[pos];
               ea_tempStateName = ea_tempStateName.substring(0,2);
               Log.d("ea_tempStateName", ea_tempStateName);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }
    private void EA_TextChangeEventListeners()
    {
        et_jEnterAddress_streetAddress.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                ea_inputIsGood[0] = ea_funcLib.FL_StreetAddressValidation(tv_jEnterAddress_streetAddressError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jEnterAddress_aptOther.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                ea_inputIsGood[1] = ea_funcLib.FL_AptAddressValidation(tv_jEnterAddress_aptOtherError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jEnterAddress_city.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                ea_inputIsGood[2] = ea_funcLib.FL_CityValidation(tv_jEnterAddress_cityError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jEnterAddress_zipCode.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                ea_inputIsGood[3] = ea_funcLib.FL_ZipCodeValidation(tv_jEnterAddress_zipCodeError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
    private boolean EA_InputIsEmptyCheck()
    {
        boolean[] ea_emptyInput = new boolean[3];
        ea_emptyInput[0] = ea_funcLib.FL_IsEmptyCheck(et_jEnterAddress_streetAddress, tv_jEnterAddress_streetAddressError, getString(R.string.street_address_blank));
//        ea_emptyInput[1] = ea_funcLib.FL_IsEmptyCheck(et_jEnterAddress_aptOther, tv_jEnterAddress_aptOtherError, getString(R.string.street_address_blank));
        ea_emptyInput[1] = ea_funcLib.FL_IsEmptyCheck(et_jEnterAddress_city, tv_jEnterAddress_cityError, getString(R.string.city_blank));
        ea_emptyInput[2] = ea_funcLib.FL_IsEmptyCheck(et_jEnterAddress_zipCode, tv_jEnterAddress_zipCodeError, getString(R.string.zip_code_blank));
        for(boolean b : ea_emptyInput)
        {
            Log.d("for loop in input is good", String.valueOf(b));
        }
        return ea_funcLib.FL_InputIsGood(ea_emptyInput);
    }
    private void EA_SaveNewAddress()
    {
        Log.d("EA_SaveNewAddress", "EA_SaveNewAddress");
        ea_userServiceAddress = new ServiceAddress();
        ea_userServiceAddress.setSa_streetAddress(et_jEnterAddress_streetAddress.getText().toString());
        if(!et_jEnterAddress_aptOther.getText().toString().isEmpty())
        {
            ea_userServiceAddress.setSa_apt(et_jEnterAddress_aptOther.getText().toString());
        }
        else
        {
            ea_userServiceAddress.setSa_apt(null);
        }
        ea_userServiceAddress.setSa_city(et_jEnterAddress_city.getText().toString());
        ea_userServiceAddress.setSa_zipCode(et_jEnterAddress_zipCode.getText().toString());
        ea_userServiceAddress.setSa_state(ea_tempStateName);
        ea_dbHelper.DB_AddNewUserAddress(ea_userServiceAddress);

    }
    private void EA_UpdateUserAddress()
    {
        ea_userServiceAddress.setSa_streetAddress(et_jEnterAddress_streetAddress.getText().toString());
        if(!et_jEnterAddress_aptOther.getText().toString().isEmpty())
        {
            ea_userServiceAddress.setSa_apt(et_jEnterAddress_aptOther.getText().toString());
        }
        else
        {
            ea_userServiceAddress.setSa_apt(null);
        }
        ea_userServiceAddress.setSa_city(et_jEnterAddress_city.getText().toString());
        ea_userServiceAddress.setSa_zipCode(et_jEnterAddress_zipCode.getText().toString());
        ea_userServiceAddress.setSa_state(ea_tempStateName);
        ea_dbHelper.DB_UpdateUserAddress(ea_userServiceAddress);
    }
    private void EA_LoadUserAddressData()
    {
        ea_userServiceAddress = UserSessionData.GetPassedServiceAddress();
        et_jEnterAddress_streetAddress.setText(ea_userServiceAddress.getSa_streetAddress());
        if(ea_userServiceAddress.getSa_apt() != null)
        {
            et_jEnterAddress_aptOther.setText(ea_userServiceAddress.getSa_apt());
        }
        et_jEnterAddress_city.setText(ea_userServiceAddress.getSa_city());
        et_jEnterAddress_zipCode.setText(ea_userServiceAddress.getSa_zipCode());
        for(int i = 0; i < ea_statesArray.length; i++)
        {
            if(ea_statesArray[i].substring(0,2).equals(ea_userServiceAddress.getSa_state()))
            {
                sp_jEnterAddress_states.setSelection(i);
            }
        }
    }
}