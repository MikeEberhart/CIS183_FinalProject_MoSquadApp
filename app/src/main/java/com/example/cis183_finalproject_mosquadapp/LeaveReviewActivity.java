package com.example.cis183_finalproject_mosquadapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
@SuppressLint("NewApi")
public class LeaveReviewActivity extends AppCompatActivity
{
    Intent lr_userReviewIntent;
    Intent lr_welcomeUserIntent;
    RatingBar rb_jLeaveReview_starCount;
    EditText et_jLeaveReview_reviewText;
    TextView tv_jLeaveReview_zeroStarsError;
    Button btn_jLeaveReview_back;
    Button btn_jLeaveReview_home;
    Button btn_jLeaveReview_postReview;
    DatabaseHelper lr_dbHelper;
    UserReview lr_userReview;
    private boolean lr_firstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leave_review);

        LR_ListOfViews();
        LR_InitData();
        LR_OnClickListeners();
        if(UserSessionData.GetIsPassedFromEditReview()) {
            lr_userReview = UserSessionData.GetLoggedInUserReview();
            rb_jLeaveReview_starCount.setRating(Float.parseFloat(lr_userReview.getUrv_starCount()));
            if (lr_userReview.getUrv_reviewText() != null)
            {
                lr_firstRun = false;
                et_jLeaveReview_reviewText.setText(lr_userReview.getUrv_reviewText());
                et_jLeaveReview_reviewText.setGravity(Gravity.TOP);
                et_jLeaveReview_reviewText.setGravity(Gravity.START);
            }
        }
    }
    private void LR_InitData()
    {
        lr_firstRun = true;
        lr_dbHelper = new DatabaseHelper(this);
        lr_userReviewIntent = new Intent(this, UserReviewsActivity.class);
        lr_welcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
    }
    private void LR_ListOfViews()
    {
        rb_jLeaveReview_starCount   = findViewById(R.id.rb_vLeaveReview_starCount);
        et_jLeaveReview_reviewText  = findViewById(R.id.et_vLeaverReview_reviewText);
        btn_jLeaveReview_back       = findViewById(R.id.btn_vLeaveReview_back);
        btn_jLeaveReview_home       = findViewById(R.id.btn_vLeaveReview_home);
        btn_jLeaveReview_postReview = findViewById(R.id.btn_vLeaveReview_postReview);
        tv_jLeaveReview_zeroStarsError = findViewById(R.id.tv_vLeaveReview_zeroStarsError);
    }

    private void LR_OnClickListeners()
    {
        btn_jLeaveReview_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserSessionData.SetIsPassedFromEditReview(false);
                startActivity(lr_userReviewIntent);
            }
        });
        btn_jLeaveReview_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserSessionData.SetIsPassedFromEditReview(false);
                startActivity(lr_welcomeUserIntent);
            }
        });
        btn_jLeaveReview_postReview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(rb_jLeaveReview_starCount.getRating() != 0)
                {
                    tv_jLeaveReview_zeroStarsError.setVisibility(View.INVISIBLE);
                    if(UserSessionData.GetIsPassedFromEditReview())
                    {
                        LR_UpdateUserReview();
                        UserSessionData.SetIsPassedFromEditReview(false);
                        startActivity(lr_userReviewIntent);
                    }
                    else
                    {
                        LR_AddNewUserReview();
                        UserSessionData.SetIsPassedFromEditReview(false);
                        startActivity(lr_userReviewIntent);
                    }
                }
                else
                {
                    tv_jLeaveReview_zeroStarsError.setVisibility(View.VISIBLE);
                }
            }
        });
        et_jLeaveReview_reviewText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus && lr_firstRun)
                {
                    lr_firstRun = false;
                    et_jLeaveReview_reviewText.setText("");
                    et_jLeaveReview_reviewText.setGravity(Gravity.TOP);
                    et_jLeaveReview_reviewText.setGravity(Gravity.START);
                }
            }
        });
    }
    private void LR_AddNewUserReview()
    {
        Log.d("current user", UserSessionData.GetLoggedInUser().getUser_username());
        User uname = UserSessionData.GetLoggedInUser();
        String reviewText;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = df.format(new Date());
        UserReview lr_currentUserReview = new UserReview();
        float tempStarCnt = 0;
        if(et_jLeaveReview_reviewText.getText().toString().isEmpty() || et_jLeaveReview_reviewText.getText().toString().equals("*Optional*"))
        {
            tempStarCnt = Float.parseFloat(String.valueOf(rb_jLeaveReview_starCount.getRating()));
            lr_currentUserReview.setUrv_starCount(String.valueOf(tempStarCnt));
            lr_currentUserReview.setUrv_reviewText(null);
            lr_currentUserReview.setUrv_reviewDate(date);
        }
        else
        {
            tempStarCnt = Float.parseFloat(String.valueOf(rb_jLeaveReview_starCount.getRating()));
            reviewText = et_jLeaveReview_reviewText.getText().toString();
            lr_currentUserReview.setUrv_starCount(String.valueOf(tempStarCnt));
            lr_currentUserReview.setUrv_reviewText(reviewText);
            lr_currentUserReview.setUrv_reviewDate(date);
        }
        lr_dbHelper.DB_AddNewUserReview(lr_currentUserReview, uname.getUser_username());
    }
    private void LR_UpdateUserReview()
    {
        String reviewText;
        if(et_jLeaveReview_reviewText.getText().toString().isEmpty() || et_jLeaveReview_reviewText.getText().toString().equals("*Optional*"))
        {
            lr_userReview.setUrv_starCount(String.valueOf(rb_jLeaveReview_starCount.getRating()));
            lr_userReview.setUrv_reviewText(null);
        }
        else
        {
            reviewText = et_jLeaveReview_reviewText.getText().toString();
            lr_userReview.setUrv_starCount(String.valueOf(rb_jLeaveReview_starCount.getRating()));
            lr_userReview.setUrv_reviewText(reviewText);
        }
        lr_dbHelper.DB_UpdateLoggedInUserReview(lr_userReview);
    }
}