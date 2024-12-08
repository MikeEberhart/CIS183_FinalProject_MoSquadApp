package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    Intent wu_loginIntent;
    Intent wu_userAcctIntent;
    Intent wu_userReviewsIntent;
    Intent wu_packageDetailsIntent;
    Intent wu_enterAddressIntent;
    Intent wu_estimateOverview;
    TextView tv_jWelcomeUser_userFname;
    TextView tv_jWelcomeUser_noSavedEstimates;
    ListView lv_jWelcomeUser_addressList;
    Button btn_jWelcomeUser_logout;
    Button btn_jWelcomeUser_reviews;
    Button btn_jWelcomeUser_userAccount;
    Button btn_jWelcomeUser_newEstimate;
    Button btn_jWelcomeUser_packageDetails;
    AddressListAdapter wu_addressListAdapter;
    User wu_currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_user);

        WU_ListOfViews();
        WU_InitData();
        WU_OnClickListeners();
    }
    private void WU_InitData()
    {
        wu_loginIntent           = new Intent(this, LoginActivity.class);
        wu_userAcctIntent        = new Intent(this, UserAccountActivity.class);
        wu_userReviewsIntent     = new Intent(this, UserReviewsActivity.class);
        wu_enterAddressIntent    = new Intent(this, EnterAddressActivity.class);
        wu_packageDetailsIntent  = new Intent(this, PackageDetailsActivity.class);
        wu_estimateOverview      = new Intent(this, EstimateOverviewActivity.class);
        wu_currentUser           = UserSessionData.GetLoggedInUser();
        UserSessionData          .SetIsPassedFromWelcomeUser(false);
        UserSessionData          .SetIsPassedFromBack(false);
        String welcomeMessage     = "Welcome " + wu_currentUser.getUser_fname();
        tv_jWelcomeUser_userFname.setText(welcomeMessage);
        if(UserSessionData       .GetUserAddressCount() != 0)
        {
            tv_jWelcomeUser_noSavedEstimates.setVisibility(View.INVISIBLE);
            wu_addressListAdapter = new AddressListAdapter(this, UserSessionData.GetUserAddressData(), tv_jWelcomeUser_noSavedEstimates); // need to double check if this will be ok when deleting addresses
            lv_jWelcomeUser_addressList.setAdapter(wu_addressListAdapter);                                // if so then maybe could be use something like this with the other listviews
        }
        else
        {
            tv_jWelcomeUser_noSavedEstimates.setVisibility(View.VISIBLE);
        }
    }
    private void WU_ListOfViews()
    {
        tv_jWelcomeUser_userFname        = findViewById(R.id.tv_vWelcomeUser_userFname);
        tv_jWelcomeUser_noSavedEstimates = findViewById(R.id.tv_vWelcomeUser_noSavedEstimates);
        lv_jWelcomeUser_addressList      = findViewById(R.id.lv_vWelcomeUser_addressList);
        btn_jWelcomeUser_logout          = findViewById(R.id.btn_vWelcomeUser_logout);
        btn_jWelcomeUser_reviews         = findViewById(R.id.btn_vWelcomeUser_reviews);
        btn_jWelcomeUser_userAccount     = findViewById(R.id.btn_vWelcomeUser_userAccount);
        btn_jWelcomeUser_newEstimate     = findViewById(R.id.btn_vWelcomeUser_newEstimate);
        btn_jWelcomeUser_packageDetails  = findViewById(R.id.btn_vWelcomeUser_packageDetails);
    }
    private void WU_OnClickListeners()
    {
        btn_jWelcomeUser_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(wu_loginIntent);
            }
        });
        btn_jWelcomeUser_reviews.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(wu_userReviewsIntent);
            }
        });
        btn_jWelcomeUser_newEstimate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(wu_enterAddressIntent);
            }
        });
        btn_jWelcomeUser_userAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(wu_userAcctIntent);
            }
        });
        btn_jWelcomeUser_packageDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(wu_packageDetailsIntent);
            }
        });
//        lv_jWelcomeUser_addressList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
//        {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                startActivity(wu_estimateOverview);
//                return false;
//            }
//        });
    }
}