package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class WelcomeUserActivity extends AppCompatActivity
{
    Intent jWelcomeUser_loginIntent;
    TextView tv_jWelcomeUser_userFname;
    ListView lv_jWelcomeUser_addressList;
    Button btn_jWelcomeUser_logout;
    Button btn_jWelcomeUser_deleteAddress;
    Button btn_jWelcomeUser_updateAddress;
    Button btn_jWelcomeUser_updateEstimate;
    Button btn_jWelcomeUser_reviews;
    Button btn_jWelcomeUser_userAccount;
    Button btn_jWelcomeUser_newEstimate;
    Button btn_jWelcomeUser_packageDetails;
    AddressListAdapter jWelcomeUser_addressListAdapter;
    ArrayList<String> testArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_user);

        WU_InitData();
        WU_OnClickListeners();
    }

    private void WU_InitData()
    {
        testArray = new ArrayList<>();
        testArray.add("school");
        testArray.add("base camp");
        WU_ListOfViews();
        jWelcomeUser_addressListAdapter = new AddressListAdapter(this, testArray);
        jWelcomeUser_loginIntent = new Intent(this, LoginActivity.class);
        lv_jWelcomeUser_addressList.setAdapter(jWelcomeUser_addressListAdapter);
    }

    private void WU_ListOfViews()
    {
        tv_jWelcomeUser_userFname       = findViewById(R.id.tv_vWelcomeUser_userFname);
        lv_jWelcomeUser_addressList     = findViewById(R.id.lv_vWelcomeUser_addressList);
        btn_jWelcomeUser_logout         = findViewById(R.id.btn_vWelcomeUser_logout);
        btn_jWelcomeUser_deleteAddress  = findViewById(R.id.btn_vWelcomeUser_deleteAddress);
        btn_jWelcomeUser_updateAddress  = findViewById(R.id.btn_vWelcomeUser_updateAddress);
        btn_jWelcomeUser_updateEstimate = findViewById(R.id.btn_vWelcomeUser_updateEstimate);
        btn_jWelcomeUser_reviews        = findViewById(R.id.btn_vWelcomeUser_reviews);
        btn_jWelcomeUser_userAccount    = findViewById(R.id.btn_vWelcomeUser_userAccount);
        btn_jWelcomeUser_newEstimate    = findViewById(R.id.btn_vWelcomeUser_newEstimate);
        btn_jWelcomeUser_packageDetails = findViewById(R.id.btn_vWelcomeUser_packageDetails);
    }

    private void WU_OnClickListeners()
    {
        btn_jWelcomeUser_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(jWelcomeUser_loginIntent);
            }
        });
    }
}