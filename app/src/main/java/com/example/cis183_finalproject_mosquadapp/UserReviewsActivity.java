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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserReviewsActivity extends AppCompatActivity
{
    Intent ur_welcomeUserIntent;
    Intent ur_leaveReviewIntent;
    TextView tv_jUserReview_headerMessage;
    Button btn_jUserReview_back;
    Button btn_jUserReview_home;
    Button btn_jUserReview_leaveReview;
    ListView lv_jUserReview_listOfReviews;
    ReviewListAdapter ur_userReviewsListAdapter;
    DatabaseHelper ur_dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_reviews);

        UR_ListOfViews();
        UR_InitData();


//        SimpleDateFormat df;
//        df = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
//        String str = df.format(new Date());
//        tv_jUserReview_headerMessage.setText(str);
    }

    private void UR_ListOfViews()
    {
        tv_jUserReview_headerMessage = findViewById(R.id.tv_vUserReviews_headerMessage);
        btn_jUserReview_back = findViewById(R.id.btn_vUserReviews_back);
        btn_jUserReview_home = findViewById(R.id.btn_vUserReview_home);
        btn_jUserReview_leaveReview = findViewById(R.id.btn_vUserReviews_leaveReview);
        lv_jUserReview_listOfReviews = findViewById(R.id.lv_vUserReviews_listOfReviews);
    }
    private void UR_InitData()
    {
        ur_dbHelper = new DatabaseHelper(this);
        ur_welcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
        ur_leaveReviewIntent = new Intent(this, LeaveReviewActivity.class);
        ur_userReviewsListAdapter = new ReviewListAdapter(this, ur_dbHelper.DB_GetListOfReviews());
        lv_jUserReview_listOfReviews.setAdapter(ur_userReviewsListAdapter);
    }
    private void UR_OnClickListeners()
    {
        btn_jUserReview_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
        btn_jUserReview_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(ur_welcomeUserIntent);
            }
        });
        btn_jUserReview_leaveReview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(ur_leaveReviewIntent);
            }
        });
    }
}