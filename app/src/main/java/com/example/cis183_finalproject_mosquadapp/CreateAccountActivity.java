package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccountActivity extends AppCompatActivity
{
    Intent jCreateAcct_loginIntent;
    EditText et_jCreateAcct_username;
    EditText et_jCreateAcct_password;
    EditText et_jCreateAcct_confirmpass;
    EditText et_jCreateAcct_fname;
    EditText et_jCreateAcct_lname;
    EditText et_jCreateAcct_email;
    EditText et_jCreateAcct_phoneNumber;
    Button btn_jCreateAcct_back;
    Button btn_jCreateAcct_createAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        CA_InitData();
        CA_OnClickListeners();
    }

    private void CA_InitData()
    {
        CA_ListOfViews();
        jCreateAcct_loginIntent       = new Intent(this, LoginActivity.class);
    }

    private void CA_ListOfViews()
    {
        et_jCreateAcct_username       = findViewById(R.id.et_vCreateAcct_username);
        et_jCreateAcct_password       = findViewById(R.id.et_vCreateAcct_password);
        et_jCreateAcct_confirmpass    = findViewById(R.id.et_vCreateAcct_confirmPass);
        et_jCreateAcct_fname          = findViewById(R.id.et_vCreateAcct_fname);
        et_jCreateAcct_lname          = findViewById(R.id.et_vCreateAcct_lname);
        et_jCreateAcct_email          = findViewById(R.id.et_vCreateAcct_email);
        et_jCreateAcct_phoneNumber    = findViewById(R.id.et_vCreateAcct_phoneNumber);
        btn_jCreateAcct_back          = findViewById(R.id.btn_vCreateAcct_back);
        btn_jCreateAcct_createAccount = findViewById(R.id.btn_vCreateAcct_createAccount);
    }

    private void CA_OnClickListeners()
    {
        btn_jCreateAcct_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(jCreateAcct_loginIntent);
            }
        });
        btn_jCreateAcct_createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(jCreateAcct_loginIntent);
            }
        });
    }
}