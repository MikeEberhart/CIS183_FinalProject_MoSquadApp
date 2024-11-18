package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PackageDetailsActivity extends AppCompatActivity
{
    Intent pd_welcomeUserIntent;
    Intent pd_enterAddressIntent;
    // add intent for the google map here //
    Button btn_jPackageDetails_home;
    Button btn_jPackageDetails_newEstimate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_package_details);

        PD_ListOfViews();
        PD_InitData();
        PD_OnClickListeners();

    }

    private void PD_InitData()
    {
        pd_welcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
        pd_enterAddressIntent = new Intent(this, EnterAddressActivity.class);
    }
    private void PD_ListOfViews()
    {
        btn_jPackageDetails_home = findViewById(R.id.btn_vPackageDetails_home);
        btn_jPackageDetails_newEstimate = findViewById(R.id.btn_vPackageDetails_newEstimate);
    }
    private void PD_OnClickListeners()
    {
        btn_jPackageDetails_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(pd_welcomeUserIntent);
            }
        });
        btn_jPackageDetails_newEstimate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(pd_enterAddressIntent);
            }
        });
    }
}