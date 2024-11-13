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

import java.util.Arrays;

public class UserAccountActivity extends AppCompatActivity
{
    Intent ua_welcomeUserIntent;
    Intent ua_changePassIntent;
    Intent ua_loginIntent;
    TextView tv_jUserAcct_userAcctHeader;
    TextView tv_jUserAcct_fnameError;
    TextView tv_jUserAcct_lnameError;
    TextView tv_jUserAcct_emailError;
    TextView tv_jUserAcct_phoneNumError;
    EditText et_jUserAcct_fname;
    EditText et_jUserAcct_lname;
    EditText et_jUserAcct_email;
    EditText et_jUserAcct_phoneNum;
    Button btn_jUserAcct_editAcct;
    Button btn_jUserAcct_saveAcct;
    Button btn_jUserAcct_changePass;
    Button btn_jUserAcct_deleteAcct;
    Button btn_jUserAcct_home;
    DatabaseHelper ua_dbHelper;
    FunctionLibrary ua_funcLib;
    private User ua_currentUser;
    private boolean[] ua_inputIsGood;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_account);

        UA_ListOfViews();
        UA_InitData();
        UA_OnClickListeners();
        UA_TextChangeEventListeners();
    }
    private void UA_ListOfViews()
    {
        tv_jUserAcct_userAcctHeader = findViewById(R.id.tv_vUserAcct_userAcctHeader);
        tv_jUserAcct_fnameError     = findViewById(R.id.tv_vUserAcct_fnameError);
        tv_jUserAcct_lnameError     = findViewById(R.id.tv_vUserAcct_lnameError);
        tv_jUserAcct_emailError     = findViewById(R.id.tv_vUserAcct_emailError);
        tv_jUserAcct_phoneNumError  = findViewById(R.id.tv_vUserAcct_phoneNumError);
        et_jUserAcct_fname          = findViewById(R.id.et_vUserAcct_fname);
        et_jUserAcct_lname          = findViewById(R.id.et_vUserAcct_lname);
        et_jUserAcct_email          = findViewById(R.id.et_vUserAcct_email);
        et_jUserAcct_phoneNum       = findViewById(R.id.et_vUserAcct_phoneNum);
        btn_jUserAcct_editAcct      = findViewById(R.id.btn_vUserAcct_editAcct);
        btn_jUserAcct_saveAcct      = findViewById(R.id.btn_vUserAcct_saveAcct);
        btn_jUserAcct_changePass    = findViewById(R.id.btn_vUserAcct_changePass);
        btn_jUserAcct_deleteAcct    = findViewById(R.id.btn_vUserAcct_deleteAcct);
        btn_jUserAcct_home          = findViewById(R.id.btn_vUserAcct_home);
    }
    private void UA_InitData()
    {
        ua_currentUser = null;
        ua_inputIsGood = new boolean[4];
        // setting bools to true since data loaded into the editTextboxes is good/true //
        Arrays.fill(ua_inputIsGood, true);
        ua_welcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
        ua_changePassIntent = new Intent(this, ChangePasswordActivity.class);
        ua_loginIntent  = new Intent(this, LoginActivity.class);
        ua_dbHelper = new DatabaseHelper(this);
        ua_funcLib = new FunctionLibrary();
        ua_currentUser = UserSessionData.GetLoggedInUser();
        tv_jUserAcct_userAcctHeader.setText(ua_currentUser.getUser_username() + "'s Account");
        et_jUserAcct_fname.setText(ua_currentUser.getUser_fname());
        et_jUserAcct_lname.setText(ua_currentUser.getUser_lname());
        et_jUserAcct_email.setText(ua_currentUser.getUser_email());
        et_jUserAcct_phoneNum.setText(ua_currentUser.getUser_phoneNumber());
        UA_ToggleEditText(false);
    }
    private void UA_OnClickListeners()
    {
        btn_jUserAcct_editAcct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UA_ToggleEditText(true);
                btn_jUserAcct_saveAcct.setVisibility(View.VISIBLE);
            }
        });
        btn_jUserAcct_saveAcct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("save acct click", "save acct click");
                if(!UA_InputIsEmptyCheck() && ua_funcLib.FL_InputIsGood(ua_inputIsGood))
                {
                    Log.d("save acct", "save acct");
                    UA_ToggleEditText(false);
                    // function to save the updated info to the database //
                    UA_UpdatingUserData();
                    btn_jUserAcct_saveAcct.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_jUserAcct_changePass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(ua_changePassIntent);
            }
        });
        btn_jUserAcct_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(ua_welcomeUserIntent);
            }
        });
        btn_jUserAcct_deleteAcct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ua_dbHelper.DB_DeleteUserAccount(ua_currentUser);
                startActivity(ua_loginIntent);
            }
        });
    }
    private void UA_TextChangeEventListeners()
    {
        et_jUserAcct_fname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
//                ua_goodFname
                ua_inputIsGood[0] = ua_funcLib.FL_NameInputValidation(tv_jUserAcct_fnameError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jUserAcct_lname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
//                ua_goodLname
                ua_inputIsGood[1] = ua_funcLib.FL_NameInputValidation(tv_jUserAcct_lnameError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jUserAcct_email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
//                ua_goodEmail
                ua_inputIsGood[2] = ua_funcLib.FL_EmailInputValidation(tv_jUserAcct_emailError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jUserAcct_phoneNum.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
//                ua_goodPhoneNum
                ua_inputIsGood[3] = ua_funcLib.FL_PhoneNumberValidation(tv_jUserAcct_phoneNumError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
    private void UA_ToggleEditText(boolean enable)
    {
        et_jUserAcct_fname.setEnabled(enable);
        et_jUserAcct_lname.setEnabled(enable);
        et_jUserAcct_email.setEnabled(enable);
        et_jUserAcct_phoneNum.setEnabled(enable);
    }
    private void UA_UpdatingUserData()
    {
        ua_currentUser.setUser_fname(et_jUserAcct_fname.getText().toString());
        ua_currentUser.setUser_lname(et_jUserAcct_lname.getText().toString());
        ua_currentUser.setUser_email(et_jUserAcct_email.getText().toString());
        ua_currentUser.setUser_phoneNumber(et_jUserAcct_phoneNum.getText().toString());
        ua_dbHelper.DB_UpdateUserData(ua_currentUser);
        Log.d("user data", ua_currentUser.getUser_fname() + " - " + ua_currentUser.getUser_lname() + " - " +
                ua_currentUser.getUser_email() + " - " + ua_currentUser.getUser_phoneNumber());
    }
    private boolean UA_InputIsEmptyCheck()
    {
        boolean[] ua_emptyTextBox = new boolean[4];
        ua_emptyTextBox[0] = ua_funcLib.FL_IsInputEmptyCheck(et_jUserAcct_fname, tv_jUserAcct_fnameError, getString(R.string.first_name_blank));
        ua_emptyTextBox[1] = ua_funcLib.FL_IsInputEmptyCheck(et_jUserAcct_lname, tv_jUserAcct_lnameError, getString(R.string.last_name_blank));
        ua_emptyTextBox[2] = ua_funcLib.FL_IsInputEmptyCheck(et_jUserAcct_email, tv_jUserAcct_emailError, getString(R.string.email_blank));
        ua_emptyTextBox[3] = ua_funcLib.FL_IsInputEmptyCheck(et_jUserAcct_phoneNum, tv_jUserAcct_phoneNumError, getString(R.string.phone_number_blank));
        return ua_funcLib.FL_InputIsGood(ua_emptyTextBox);
    }
}