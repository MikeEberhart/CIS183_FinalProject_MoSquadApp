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

public class CreateAccountActivity extends AppCompatActivity
{
    Intent ca_loginIntent;
//    Intent ca_WelcomeUserIntent; might not use this
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
    FunctionLibrary ca_funcLib;
    DatabaseHelper ca_dbHelper;
    private User ca_newUser;
    private boolean[] ca_inputIsGood;

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
//        CA_ResetErrors();
        ca_funcLib = new FunctionLibrary();
        ca_dbHelper = new DatabaseHelper(this);
        ca_loginIntent = new Intent(this, LoginActivity.class);
//        ca_WelcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
        ca_inputIsGood = new boolean[7];
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
                startActivity(ca_loginIntent);
            }
        });
        btn_jCreateAcct_createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("OnClick", "OnClick");
                String tempUsername = et_jCreateAcct_username.getText().toString();
//                    if(ca_goodUsername && ca_goodPassword && ca_goodConfirmPass && ca_goodFname && ca_goodLname &&
//                            ca_goodEmail && ca_goodPhoneNum && !ca_dbHelper.DB_UsernameExists(tempUsername)) // maybe put these bools in an array too //
                if(!CA_InputIsEmptyCheck() && ca_funcLib.FL_InputIsGood(ca_inputIsGood) && !ca_dbHelper.DB_UsernameAlreadyExists(tempUsername))
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
                        startActivity(ca_loginIntent);
                    }
                    else
                    {
                        Log.d("not equal pass", "not equal pass");
                        tv_jCreateAcct_confirmPassError.setVisibility(View.VISIBLE);
                        tv_jCreateAcct_confirmPassError.setText(R.string.password_dont_match_message);
                    }
                }
                else if(ca_dbHelper.DB_UsernameAlreadyExists(tempUsername))
                {
                    tv_jCreateAcct_usernameError.setVisibility(View.VISIBLE);
                    tv_jCreateAcct_usernameError.setText(R.string.username_alread_exists);
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
                ca_inputIsGood[0] = ca_funcLib.FL_UsernameInputValidation(tv_jCreateAcct_usernameError, s);
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
                ca_inputIsGood[1] = ca_funcLib.FL_PasswordInputValidation(tv_jCreateAcct_passwordError, s);
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
                ca_inputIsGood[2] = ca_funcLib.FL_PasswordInputValidation(tv_jCreateAcct_confirmPassError, s);
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
                ca_inputIsGood[3] = ca_funcLib.FL_NameInputValidation(tv_jCreateAcct_fnameError, s);
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
                ca_inputIsGood[4] = ca_funcLib.FL_NameInputValidation(tv_jCreateAcct_lnameError, s);
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
                ca_inputIsGood[5] = ca_funcLib.FL_EmailInputValidation(tv_jCreateAcct_emailError, s);
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
                ca_inputIsGood[6] = ca_funcLib.FL_PhoneNumberValidation(tv_jCreateAcct_phoneNumError, s);
            }
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
    private void CA_ResetErrors()
    {
        tv_jCreateAcct_usernameError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_passwordError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_confirmPassError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_fnameError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_lnameError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_emailError.setVisibility(View.INVISIBLE);
        tv_jCreateAcct_phoneNumError.setVisibility(View.INVISIBLE);

    }
    private boolean CA_InputIsEmptyCheck()
    {
        boolean[] ca_emptyInput = new boolean[7];
        ca_emptyInput[0] = ca_funcLib.FL_IsInputEmptyCheck(et_jCreateAcct_username, tv_jCreateAcct_usernameError, getString(R.string.username_blank));
        ca_emptyInput[1] = ca_funcLib.FL_IsInputEmptyCheck(et_jCreateAcct_password, tv_jCreateAcct_passwordError, getString(R.string.password_blank));
        ca_emptyInput[2] = ca_funcLib.FL_IsInputEmptyCheck(et_jCreateAcct_confirmpass, tv_jCreateAcct_confirmPassError, getString(R.string.password_blank));
        ca_emptyInput[3] = ca_funcLib.FL_IsInputEmptyCheck(et_jCreateAcct_fname, tv_jCreateAcct_fnameError, getString(R.string.first_name_blank));
        ca_emptyInput[4] = ca_funcLib.FL_IsInputEmptyCheck(et_jCreateAcct_lname, tv_jCreateAcct_lnameError, getString(R.string.last_name_blank));
        ca_emptyInput[5] = ca_funcLib.FL_IsInputEmptyCheck(et_jCreateAcct_email, tv_jCreateAcct_emailError, getString(R.string.email_blank));
        ca_emptyInput[6] = ca_funcLib.FL_IsInputEmptyCheck(et_jCreateAcct_phoneNumber, tv_jCreateAcct_phoneNumError, getString(R.string.phone_number_blank));
        return ca_funcLib.FL_InputIsGood(ca_emptyInput);
    }
    private void CA_SaveNewUser()
    {
        ca_newUser = new User();
        ca_newUser.setUser_username(et_jCreateAcct_username.getText().toString());
        ca_newUser.setUser_password(et_jCreateAcct_password.getText().toString());
        ca_newUser.setUser_fname(et_jCreateAcct_fname.getText().toString());
        ca_newUser.setUser_lname(et_jCreateAcct_lname.getText().toString());
        ca_newUser.setUser_email(et_jCreateAcct_email.getText().toString());
        ca_newUser.setUser_phoneNumber(et_jCreateAcct_phoneNumber.getText().toString());
        Log.d("username", ca_newUser.getUser_username());
        Log.d("password", ca_newUser.getUser_password());
        Log.d("fname", ca_newUser.getUser_fname());
        Log.d("lname", ca_newUser.getUser_lname());
        Log.d("email", ca_newUser.getUser_email());
        Log.d("phoneNum", ca_newUser.getUser_phoneNumber());
        ca_dbHelper.DB_AddNewUser(ca_newUser);
    }
}