package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class ChangePasswordActivity extends AppCompatActivity
{
    Intent cp_welcomeUserIntent;
    Intent cp_userAcctIntent;
    TextView tv_jChangePass_headerMessage;
    TextView tv_jChangePass_oldPassError;
    TextView tv_jChangePass_newPassError;
    TextView tv_jChangePass_confirmPassError;
    EditText et_jChangePass_oldPass;
    EditText et_jChangePass_newPass;
    EditText et_jChangePass_confirmPass;
    Button btn_jChangePass_back;
    Button btn_jChangePass_home;
    Button btn_jChangePass_saveNewPass;
    DatabaseHelper cp_dbHelper;
    FunctionLibrary cp_funcLib;
    private User cp_currentUser;
    private boolean[] cp_inputIsGood;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        CP_ListOfViews();
        CP_InitData();
        CP_OnClickListeners();
        CP_TextChangeEventListeners();

    }
    private void CP_ListOfViews()
    {
        tv_jChangePass_headerMessage = findViewById(R.id.tv_vChangePass_headerMessage);
        tv_jChangePass_oldPassError = findViewById(R.id.tv_vChangePass_oldPassError);
        tv_jChangePass_newPassError = findViewById(R.id.tv_vChangePass_newPassError);
        tv_jChangePass_confirmPassError = findViewById(R.id.tv_vChangePass_confirmPassError);
        et_jChangePass_oldPass = findViewById(R.id.et_vChangePass_oldPass);
        et_jChangePass_newPass = findViewById(R.id.et_vChangePass_newPass);
        et_jChangePass_confirmPass = findViewById(R.id.et_vChangePass_confirmPass);
        btn_jChangePass_back = findViewById(R.id.btn_vChangePass_back);
        btn_jChangePass_home = findViewById(R.id.btn_vChangePass_home);
        btn_jChangePass_saveNewPass = findViewById(R.id.btn_vChangePass_saveNewPass);
    }
    private void CP_InitData()
    {
        cp_inputIsGood = new boolean[3];
        cp_dbHelper = new DatabaseHelper(this);
        cp_funcLib = new FunctionLibrary();
        cp_currentUser = UserSessionData.GetLoggedInUser();
        cp_welcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
        cp_userAcctIntent = new Intent(this, UserAccountActivity.class);
    }
    private void CP_OnClickListeners()
    {
        btn_jChangePass_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(cp_userAcctIntent);
            }
        });
        btn_jChangePass_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(cp_welcomeUserIntent);
            }
        });
        btn_jChangePass_saveNewPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("onclick", cp_currentUser.getUser_password());
                String cp_oldPass = et_jChangePass_oldPass.getText().toString();
                String cp_newPass = et_jChangePass_newPass.getText().toString();
                String cp_confirmPass = et_jChangePass_confirmPass.getText().toString();
                if(!CP_InputIsEmptyCheck() && cp_funcLib.FL_InputIsGood(cp_inputIsGood))
                {
                    if(!cp_oldPass.equals(cp_currentUser.getUser_password()))
                    {
                        tv_jChangePass_oldPassError.setVisibility(View.VISIBLE);
                        tv_jChangePass_oldPassError.setText(R.string.old_pass_dont_match);
                    }
                    else if(cp_newPass.equals(cp_oldPass))
                    {
                        tv_jChangePass_newPassError.setVisibility(View.VISIBLE);
                        tv_jChangePass_newPassError.setText(R.string.new_pass_matches_old);
                    }
                    else if(!cp_newPass.equals(cp_confirmPass))
                    {
                        tv_jChangePass_confirmPassError.setVisibility(View.VISIBLE);
                        tv_jChangePass_confirmPassError.setText(R.string.password_dont_match_message);
                    }
                    else
                    {
                        cp_currentUser.setUser_password(cp_newPass);
                        Log.d("saving new pass", cp_currentUser.getUser_password());
                        cp_dbHelper.DB_SaveNewUserPassword(cp_currentUser);
                        startActivity(cp_userAcctIntent);
                    }
                }
            }
        });
    }
    private void CP_TextChangeEventListeners()
    {
        et_jChangePass_oldPass.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // might not need this // might delete later //
                cp_inputIsGood[0] = cp_funcLib.FL_PasswordInputValidation(tv_jChangePass_oldPassError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jChangePass_newPass.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                cp_inputIsGood[1] = cp_funcLib.FL_PasswordInputValidation(tv_jChangePass_newPassError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jChangePass_confirmPass.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                cp_inputIsGood[2] = cp_funcLib.FL_PasswordInputValidation(tv_jChangePass_confirmPassError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
    private boolean CP_InputIsEmptyCheck()
    {
        boolean[] cp_emptyInput = new boolean[3];
        cp_emptyInput[0] = cp_funcLib.FL_IsInputEmptyCheck(et_jChangePass_oldPass, tv_jChangePass_oldPassError, getString(R.string.password_blank));
        cp_emptyInput[1] = cp_funcLib.FL_IsInputEmptyCheck(et_jChangePass_newPass, tv_jChangePass_newPassError, getString(R.string.password_blank));
        cp_emptyInput[2] = cp_funcLib.FL_IsInputEmptyCheck(et_jChangePass_confirmPass, tv_jChangePass_confirmPassError, getString(R.string.password_blank));
        return cp_funcLib.FL_InputIsGood(cp_emptyInput);
    }
}