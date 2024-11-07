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
    Button btn_jWelcomeUser_logout;
    TextView tv_jWelcomeUser_userFname;
    ListView lv_jWelcomeUser_addressList;
    AddressListAdapter jWelcomeUser_addressListAdapter;
    ArrayList<String> testArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_user);

        WU_InitData();
    }

    private void WU_InitData()
    {
        testArray = new ArrayList<>();
        testArray.add("school");
        testArray.add("base camp");
        jWelcomeUser_addressListAdapter = new AddressListAdapter(this, testArray);
        tv_jWelcomeUser_userFname = findViewById(R.id.tv_vWelcomeUser_userFname);
        btn_jWelcomeUser_logout = findViewById(R.id.btn_vWelcomeUser_logout);
        lv_jWelcomeUser_addressList = findViewById(R.id.lv_vWelcomeUser_addressList);
        lv_jWelcomeUser_addressList.setAdapter(jWelcomeUser_addressListAdapter);
        jWelcomeUser_loginIntent = new Intent(this, LoginActivity.class);
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