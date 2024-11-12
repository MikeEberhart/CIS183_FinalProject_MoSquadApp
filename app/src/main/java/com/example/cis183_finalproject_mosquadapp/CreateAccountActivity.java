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
    User ca_tempUser;
    FunctionLibrary ca_funcLib;
    DatabaseHelper ca_dbHelper;
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
        CA_ResetErrorsAndBools();
        ca_funcLib = new FunctionLibrary();
        ca_dbHelper = new DatabaseHelper(this);
        jCreateAcct_loginIntent = new Intent(this, LoginActivity.class);
        jCreateAcct_WelcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
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
                startActivity(jCreateAcct_loginIntent);
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
                if(!CA_InputIsEmptyCheck() && ca_funcLib.FL_InputIsGood(ca_inputIsGood) && !ca_dbHelper.DB_UsernameExists(tempUsername))
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
    private void CA_ResetErrorsAndBools()
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
        boolean[] ca_emptyTextBox = new boolean[7];
        ca_emptyTextBox[0] = ca_funcLib.FL_IsEmptyInputCheck(et_jCreateAcct_username, tv_jCreateAcct_usernameError, getString(R.string.username_blank));
        ca_emptyTextBox[1] = ca_funcLib.FL_IsEmptyInputCheck(et_jCreateAcct_password, tv_jCreateAcct_passwordError, getString(R.string.password_blank));
        ca_emptyTextBox[2] = ca_funcLib.FL_IsEmptyInputCheck(et_jCreateAcct_confirmpass, tv_jCreateAcct_confirmPassError, getString(R.string.password_blank));
        ca_emptyTextBox[3] = ca_funcLib.FL_IsEmptyInputCheck(et_jCreateAcct_fname, tv_jCreateAcct_fnameError, getString(R.string.first_name_blank));
        ca_emptyTextBox[4] = ca_funcLib.FL_IsEmptyInputCheck(et_jCreateAcct_lname, tv_jCreateAcct_lnameError, getString(R.string.last_name_blank));
        ca_emptyTextBox[5] = ca_funcLib.FL_IsEmptyInputCheck(et_jCreateAcct_email, tv_jCreateAcct_emailError, getString(R.string.email_blank));
        ca_emptyTextBox[6] = ca_funcLib.FL_IsEmptyInputCheck(et_jCreateAcct_phoneNumber, tv_jCreateAcct_phoneNumError, getString(R.string.phone_number_blank));
        return ca_funcLib.FL_InputIsGood(ca_emptyTextBox);
    }
    private void CA_SaveNewUser()
    {
        ca_tempUser = new User(et_jCreateAcct_username.getText().toString(),
                               et_jCreateAcct_password.getText().toString(),
                               et_jCreateAcct_fname.getText().toString(),
                               et_jCreateAcct_lname.getText().toString(),
                               et_jCreateAcct_email.getText().toString(),
                               et_jCreateAcct_phoneNumber.getText().toString());
        ca_dbHelper.DB_AddNewUser(ca_tempUser);
    }
}