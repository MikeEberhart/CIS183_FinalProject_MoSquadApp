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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity
{
    Intent jCreateAcct_loginIntent;
    Intent jCreateAcct_WelcomeUserIntent;
    TextView tv_jCreateAcct_usernameError;
    TextView tv_jCreateAcct_passwordError;
    TextView tv_jCreateAcct_confirmPassError;
    TextView tv_jCreateAcct_fnameError;
    TextView tv_jCreateAcct_lnameError;
    TextView tv_jCreateAcct_emailError;
    TextView tv_jCreateAcct_phoneNumError;
    EditText et_jCreateAcct_username;
    EditText et_jCreateAcct_password;
    EditText et_jCreateAcct_confirmpass;
    EditText et_jCreateAcct_fname;
    EditText et_jCreateAcct_lname;
    EditText et_jCreateAcct_email;
    EditText et_jCreateAcct_phoneNumber;
    Button btn_jCreateAcct_back;
    Button btn_jCreateAcct_createAccount;
    ArrayList<EditText> etView; // maybe use an array of textviews and edittext to pass to function library to use there //
    FunctionLibrary funcLib;
    DatabaseHelper ca_dbHelper;
    ConstraintLayout cl;
    private boolean ca_goodUsername;
    private boolean ca_goodPassword;
    private boolean ca_goodConfirmPass;
    private boolean ca_goodFname;
    private boolean ca_goodLname;
    private boolean ca_goodEmail;
    private boolean ca_goodPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        CA_InitData();
        CA_OnClickListeners();
        CA_TextChangeEventListener();
    }

    private void CA_InitData()
    {
        CA_ListOfViews();
        CA_ResetErrorsAndBools();
        funcLib = new FunctionLibrary();
        ca_dbHelper = new DatabaseHelper(this);
        jCreateAcct_loginIntent = new Intent(this, LoginActivity.class);
        jCreateAcct_WelcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
    }

//    private void testingLoop() // used to get all the textviews in the constraintlayout to later pass to function library //
//    {
//        for(int i = 0; i < cl.getChildCount(); i++)
//        {
//            View v = cl.getChildAt(i);
//            if(v instanceof TextView)
//            {
//
//            }
//        }
//    }

    private void CA_ListOfViews()
    {
        cl = findViewById(R.id.main);
        tv_jCreateAcct_usernameError    = findViewById(R.id.tv_vCreateAcct_usernameError);
        tv_jCreateAcct_passwordError    = findViewById(R.id.tv_vCreateAcct_passwordError);
        tv_jCreateAcct_confirmPassError = findViewById(R.id.tv_vCreateAcct_confirmPassError);
        tv_jCreateAcct_fnameError       = findViewById(R.id.tv_vCreateAcct_fnameError);
        tv_jCreateAcct_lnameError       = findViewById(R.id.tv_vCreateAcct_lnameError);
        tv_jCreateAcct_emailError       = findViewById(R.id.tv_vCreateAcct_emailError);
        tv_jCreateAcct_phoneNumError    = findViewById(R.id.tv_vCreateAcct_phoneNumError);
        et_jCreateAcct_username         = findViewById(R.id.et_vCreateAcct_username);
        et_jCreateAcct_password         = findViewById(R.id.et_vCreateAcct_password);
        et_jCreateAcct_confirmpass      = findViewById(R.id.et_vCreateAcct_confirmPass);
        et_jCreateAcct_fname            = findViewById(R.id.et_vCreateAcct_fname);
        et_jCreateAcct_lname            = findViewById(R.id.et_vCreateAcct_lname);
        et_jCreateAcct_email            = findViewById(R.id.et_vCreateAcct_email);
        et_jCreateAcct_phoneNumber      = findViewById(R.id.et_vCreateAcct_phoneNumber);
        btn_jCreateAcct_back            = findViewById(R.id.btn_vCreateAcct_back);
        btn_jCreateAcct_createAccount   = findViewById(R.id.btn_vCreateAcct_createAccount);
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
                if(!CA_EmptyEditTextInput())
                {
                    Log.d("OnClick", "OnClick");
                    String tempUsername = et_jCreateAcct_username.getText().toString();
                    if(ca_goodUsername && ca_goodPassword && ca_goodConfirmPass && ca_goodFname && ca_goodLname &&
                            ca_goodEmail && ca_goodPhoneNum && !ca_dbHelper.DB_UsernameExists(tempUsername))
                    {
                        Log.d("before IF", "before IF");
                        String pass = et_jCreateAcct_password.getText().toString();
                        String confirmPass = et_jCreateAcct_confirmpass.getText().toString();
                        if(pass.equals(confirmPass))
                        {
                            CA_SaveNewUser();
                            Toast.makeText(CreateAccountActivity.this, "New Account Create", Toast.LENGTH_SHORT).show();
//                            Toast toast = new Toast();
//                            toast.
                            Log.d("equal pass", "equal pass");
                            startActivity(jCreateAcct_loginIntent);
                        }
                        else
                        {
                            Log.d("not equal pass", "not equal pass");
                            tv_jCreateAcct_confirmPassError.setVisibility(View.VISIBLE);
                            tv_jCreateAcct_confirmPassError.setText(R.string.password_dont_match_message);
                        }
                    }
                    else if(ca_dbHelper.DB_UsernameExists(tempUsername))
                    {
                        tv_jCreateAcct_usernameError.setVisibility(View.VISIBLE);
                        tv_jCreateAcct_usernameError.setText(R.string.username_alread_exists);
                    }
                }
            }
        });
    }

    private void CA_TextChangeEventListener()
    {
        et_jCreateAcct_username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                ca_goodUsername = funcLib.FL_UsernameInputValidation(tv_jCreateAcct_usernameError, s);
                Log.d("1", ca_goodUsername + "");
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jCreateAcct_password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("2", ca_goodPassword + "");
                ca_goodPassword = funcLib.FL_PasswordInputValidation(tv_jCreateAcct_passwordError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jCreateAcct_confirmpass.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("3", ca_goodConfirmPass + "");
                ca_goodConfirmPass = funcLib.FL_PasswordInputValidation(tv_jCreateAcct_confirmPassError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jCreateAcct_fname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("4", ca_goodFname +"");
                ca_goodFname = funcLib.FL_NameInputValidation(tv_jCreateAcct_fnameError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jCreateAcct_lname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("5", ca_goodLname + "");
                ca_goodLname = funcLib.FL_NameInputValidation(tv_jCreateAcct_lnameError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jCreateAcct_email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("6", ca_goodEmail + "");
                ca_goodEmail = funcLib.FL_EmailInputValidation(tv_jCreateAcct_emailError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_jCreateAcct_phoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.d("7", ca_goodPhoneNum + "");
                ca_goodPhoneNum = funcLib.FL_PhoneNumberValidation(tv_jCreateAcct_phoneNumError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
    private void CA_ResetErrorsAndBools()
    {
        tv_jCreateAcct_usernameError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_passwordError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_confirmPassError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_fnameError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_lnameError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_emailError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_phoneNumError.setVisibility(View.INVISIBLE);
        ca_goodUsername = false;
        ca_goodPassword = false;
        ca_goodConfirmPass = false;
        ca_goodFname = false;
        ca_goodLname = false;
        ca_goodEmail = false;
        ca_goodPhoneNum = false;
    }
    private boolean CA_EmptyEditTextInput()
    {
        boolean ca_emptyTextBox = false;
        if(et_jCreateAcct_username.getText().toString().isEmpty())
        {
            tv_jCreateAcct_usernameError.setVisibility(View.VISIBLE);
            tv_jCreateAcct_usernameError.setText("Must Enter Username");
            ca_emptyTextBox = true;
        }
        if(et_jCreateAcct_password.getText().toString().isEmpty())
        {
            tv_jCreateAcct_passwordError.setVisibility(View.VISIBLE);
            tv_jCreateAcct_passwordError.setText("Must Enter a Password");
            ca_emptyTextBox = true;
        }
        if(et_jCreateAcct_confirmpass.getText().toString().isEmpty())
        {
            tv_jCreateAcct_confirmPassError.setVisibility(View.VISIBLE);
            tv_jCreateAcct_confirmPassError.setText("Must Enter a Password");
            ca_emptyTextBox = true;
        }
        if(et_jCreateAcct_fname.getText().toString().isEmpty())
        {
            tv_jCreateAcct_fnameError.setVisibility(View.VISIBLE);
            tv_jCreateAcct_fnameError.setText("Must Enter a First Name");
            ca_emptyTextBox = true;
        }
        if(et_jCreateAcct_lname.getText().toString().isEmpty())
        {
            tv_jCreateAcct_lnameError.setVisibility(View.VISIBLE);
            tv_jCreateAcct_lnameError.setText("Must Enter a Last Name");
            ca_emptyTextBox = true;
        }
        if(et_jCreateAcct_email.getText().toString().isEmpty())
        {
            tv_jCreateAcct_emailError.setVisibility(View.VISIBLE);
            tv_jCreateAcct_emailError.setText("Must Enter a Email");
            ca_emptyTextBox = true;
        }
        if(et_jCreateAcct_phoneNumber.getText().toString().isEmpty())
        {
            tv_jCreateAcct_phoneNumError.setVisibility(View.VISIBLE);
            tv_jCreateAcct_phoneNumError.setText("Must Enter a Phone Number");
            ca_emptyTextBox = true;
        }
        Log.d("empty text", "empty text");
        return ca_emptyTextBox;
    }
    private void CA_SaveNewUser()
    {
        String uname = et_jCreateAcct_username.getText().toString();
        String pass = et_jCreateAcct_password.getText().toString();
        String fname = et_jCreateAcct_fname.getText().toString();
        String lname = et_jCreateAcct_lname.getText().toString();
        String email = et_jCreateAcct_email.getText().toString();
        String phoneNum = et_jCreateAcct_phoneNumber.getText().toString();
        ca_dbHelper.DB_AddNewUser(uname, pass, fname, lname, email, phoneNum);
    }
}