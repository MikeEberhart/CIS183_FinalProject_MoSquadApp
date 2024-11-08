package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity
{
    Intent jLogin_createAccountIntent;
    Intent jLogin_welcomeUserIntent;
    ImageView iv_jLogin_bannerPic;
    EditText et_jLogin_username;
    EditText et_jLogin_password;
    Button btn_jLogin_login;
    Button btn_jLogin_createAccount;
    DatabaseHelper jLogin_dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        LA_InitData();
        LA_OnClickListeners();

    }

    private void LA_InitData()
    {
        LA_ListOfViews();
        jLogin_createAccountIntent = new Intent(this, CreateAccountActivity.class);
        jLogin_welcomeUserIntent   = new Intent(this, WelcomeUserActivity.class);
        jLogin_dbHelper = new DatabaseHelper(this);
        jLogin_dbHelper.DB_PopulateDummyData();
    }

    private void LA_ListOfViews()
    {
        iv_jLogin_bannerPic        = findViewById(R.id.iv_vLogin_bannerPic);
        et_jLogin_username         = findViewById(R.id.et_vLogin_username);
        et_jLogin_password         = findViewById(R.id.et_vLogin_password);
        btn_jLogin_login           = findViewById(R.id.btn_vLogin_login);
        btn_jLogin_createAccount   = findViewById(R.id.btn_vLogin_createAccount);
    }

    private void LA_OnClickListeners()
    {
        btn_jLogin_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String uname = et_jLogin_username.getText().toString();
                String pass = et_jLogin_password.getText().toString();
                if(jLogin_dbHelper.DB_UserLoginGood(uname, pass))
                {
                    Log.d("inside btn click", "inside btn click");
                    startActivity(jLogin_welcomeUserIntent);
                }
//               MessingWithToast();
            }
        });
        btn_jLogin_createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(jLogin_createAccountIntent);
            }
        });
    }

    // make this into its own base adapter instead of using this to show error message //
    private void MessingWithToast()
    {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_toast_error, null);
        Toast cToast = new Toast(getApplicationContext());
        cToast.setGravity(0,0,0);
        cToast.setDuration(Toast.LENGTH_SHORT);
        cToast.setView(customView);
        cToast.show();
    }
}