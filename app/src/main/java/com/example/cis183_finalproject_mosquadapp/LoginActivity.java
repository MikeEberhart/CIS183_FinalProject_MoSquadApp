package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity
{
    Intent la_createAccountIntent;
    Intent la_welcomeUserIntent;
    ImageView iv_jLogin_bannerPic;
    EditText et_jLogin_username;
    EditText et_jLogin_password;
    TextView tv_jLogin_usernameError;
    TextView getTv_jLogin_passwordError;
    Button btn_jLogin_login;
    Button btn_jLogin_createAccount;
    Button btn_jSkipLogin;
    Button btn_jSkipLoginTwo;
    DatabaseHelper jLogin_dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        LA_ListOfViews();
        LA_InitData();
        LA_OnClickListeners();
        LA_TextChangeEventListeners();

    }

    private void LA_InitData()
    {
//        UserSessionData.SetLoggedInUser(null);
//        UserSessionData.SetUserAddressData(null);
        la_createAccountIntent = new Intent(this, CreateAccountActivity.class);
        la_welcomeUserIntent   = new Intent(this, WelcomeUserActivity.class);
        jLogin_dbHelper = new DatabaseHelper(this);
        jLogin_dbHelper.DB_PopulateDummyData();
    }

    private void LA_ListOfViews()
    {
        iv_jLogin_bannerPic        = findViewById(R.id.iv_vLogin_bannerPic);
        et_jLogin_username         = findViewById(R.id.et_vLogin_username);
        et_jLogin_password         = findViewById(R.id.et_vLogin_password);
        tv_jLogin_usernameError    = findViewById(R.id.tv_vLogin_usernameError);
        getTv_jLogin_passwordError = findViewById(R.id.tv_vLogin_passwordError);
        btn_jLogin_login           = findViewById(R.id.btn_vLogin_login);
        btn_jLogin_createAccount   = findViewById(R.id.btn_vLogin_createAccount);
        btn_jSkipLogin             = findViewById(R.id.btn_skipLogin);
        btn_jSkipLoginTwo          = findViewById(R.id.btn_skipLoginTwo);
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
                if(jLogin_dbHelper.DB_UsernameAlreadyExists(uname))
                {
                    tv_jLogin_usernameError.setVisibility(View.INVISIBLE);
                    if(jLogin_dbHelper.DB_CheckingForGoodUserLogin(uname, pass))
                    {
                        getTv_jLogin_passwordError.setVisibility(View.INVISIBLE);
                        Log.d("inside btn click", "inside btn click");
                        startActivity(la_welcomeUserIntent);
                    }
                    else
                    {
                        getTv_jLogin_passwordError.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    tv_jLogin_usernameError.setVisibility(View.VISIBLE);
                }
//               MessingWithToast();
            }
        });
        btn_jLogin_createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(la_createAccountIntent);
            }
        });
        btn_jSkipLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(jLogin_dbHelper.DB_CheckingForGoodUserLogin("mEbbs123", "MikeEbbs123!!!"))
                {
                    startActivity(la_welcomeUserIntent);
                    Log.d("login skipped", "login skipped");
                }
            }
        });
        btn_jSkipLoginTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(jLogin_dbHelper.DB_CheckingForGoodUserLogin("TestTest1212", "TestTest12!@"))
                {
                    startActivity(la_welcomeUserIntent);
                    Log.d("login skipped", "login skipped");
                }
            }
        });
    }

    private void LA_TextChangeEventListeners()
    {
        et_jLogin_username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tv_jLogin_usernameError.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_jLogin_password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                getTv_jLogin_passwordError.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s)
            {

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