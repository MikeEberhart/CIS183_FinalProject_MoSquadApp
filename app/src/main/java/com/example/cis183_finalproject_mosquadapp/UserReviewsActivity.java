package com.example.cis183_finalproject_mosquadapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintProperties;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintsChangedListener;

import java.util.ArrayList;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class UserReviewsActivity extends AppCompatActivity
{
    Intent ur_welcomeUserIntent;
    Intent ur_leaveReviewIntent;
    ConstraintLayout cl_vUserReviews_sortingSpinners;
    ConstraintLayout cl_vUserReviews_main;
    ConstraintSet tempConstraint;
    TextView tv_jUserReviews_headerMessage;
    TextView tv_jUserReviews_reviewErrorText;
    TextView tv_jUserReviews_sortingBtnDisplay;
    RatingBar rb_jUserReviews_averageRating;
    Button btn_jUserReviews_home;
    Button btn_jUserReviews_leaveReview;
    Button btn_jUserReviews_toggleSortingView;
    Button btn_jUserReviews_resetSort;
    Switch sw_jUserReviews_ascOrDescToggle;
    Switch sw_jUserReviews_showAllOrUserReviews;
    Switch sw_jUserReviews_sortByRating;
    ListView lv_jUserReviews_listOfReviews;
    Spinner sp_jUserReviews_sortByStarCount;
    Spinner sp_jUserReviews_sortByYear;
    Spinner sp_jUserReviews_sortByMonth;
    ReviewListAdapter ur_userReviewsListAdapter;
    ReviewListAdapter ur_userReviewsUserAdapter;
    DatabaseHelper ur_dbHelper;
    ArrayList<UserReview> ur_listOfReviews;
    ArrayList<UserReview> ur_UserOnlyReview;
    ArrayAdapter<String> ur_listOfStarCntsAdapter;
    ArrayAdapter<String> ur_listOfYearsAdapter;
    ArrayAdapter<String> ur_listOfMonthsAdapter;
    UserReview ur_currentUserReview;
    String[] ur_starCountArray;
    String[] ur_yearsArray;
    String[] ur_monthsArray;
    String ur_byStarCountSpinnerPos = "0";
    String ur_byYearSpinnerPos      = "0";
    String ur_byMonthSpinnerPos     = "0";
    String ur_sortAscOrDesc         = "DESC";
    String ur_sortByRating          = " ORDER BY Star_Count ";
    String ur_sortingStatement      = " ";
    private boolean ur_userHasReview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_reviews);

        UR_ListOfViews();
        UR_InitData();
        UR_OnClickListeners();
    }
    private void UR_ListOfViews()
    {
        cl_vUserReviews_main                 = findViewById(R.id.cl_vUserReviews_main);
        cl_vUserReviews_sortingSpinners      = findViewById(R.id.cl_vUserReviews_sortingSpinners);
        tv_jUserReviews_headerMessage        = findViewById(R.id.tv_vUserReviews_headerMessage);
        tv_jUserReviews_reviewErrorText      = findViewById(R.id.tv_vUserReviews_sortingErrorText);
        tv_jUserReviews_sortingBtnDisplay    = findViewById(R.id.tv_vUserReviews_sortingBtnDisplay);
        btn_jUserReviews_home                = findViewById(R.id.btn_vUserReviews_home);
        btn_jUserReviews_leaveReview         = findViewById(R.id.btn_vUserReviews_leaveReview);
        btn_jUserReviews_toggleSortingView   = findViewById(R.id.btn_vUserReviews_toggleSortingView);
        btn_jUserReviews_resetSort           = findViewById(R.id.btn_vUserReviews_resetSort);
        lv_jUserReviews_listOfReviews        = findViewById(R.id.lv_vUserReviews_listOfReviews);
        sw_jUserReviews_ascOrDescToggle      = findViewById(R.id.sw_vUserReviews_sortByAscOrDesc);
        sw_jUserReviews_showAllOrUserReviews = findViewById(R.id.sw_vUserReviews_showAllorUserReviews);
        sw_jUserReviews_sortByRating         = findViewById(R.id.sw_vUserReviews_sortByRating);
        sp_jUserReviews_sortByStarCount      = findViewById(R.id.sp_vUserReviews_sortByStarCount);
        sp_jUserReviews_sortByYear           = findViewById(R.id.sp_vUserReviews_sortByYear);
        sp_jUserReviews_sortByMonth          = findViewById(R.id.sp_vUserReviews_sortByMonth);
        rb_jUserReviews_averageRating        = findViewById(R.id.rb_vUserReviews_averageRating);

    }
    private void UR_InitData()
    {
        ur_dbHelper                    = new DatabaseHelper(this);
//        tempConstraint = new ConstraintSet(); // need to work on this later to get the listView to adjust height depending on if the cl_vUserReview_sortingDisplay is visible //
//        tempConstraint.connect(ConstraintProperties.BOTTOM, R.id.lv_vUserReviews_listOfReviews, ConstraintProperties.TOP, R.id.btn_vUserReviews_home);
//        tempConstraint.margin(ConstraintProperties.BOTTOM,0);
//        tempConstraint.clone(cl_vUserReviews_main);
//        tempConstraint.connect(R.id.lv_vUserReviews_listOfReviews, ConstraintSet.BOTTOM, R.id.btn_vUserReviews_home, ConstraintSet.TOP);
//        tempConstraint.applyTo(cl_vUserReviews_main);


        ur_listOfReviews               = ur_dbHelper.DB_GetListOfReviews(ur_sortingStatement, ur_sortByRating, ur_sortAscOrDesc);
        UR_CreateArraysForSpinners();
        UR_SettingAverageReview();
        ur_welcomeUserIntent           = new Intent(this, WelcomeUserActivity.class);
        ur_leaveReviewIntent           = new Intent(this, LeaveReviewActivity.class);
        ur_userReviewsListAdapter      = new ReviewListAdapter(this, ur_listOfReviews, rb_jUserReviews_averageRating);
        ur_listOfStarCntsAdapter       = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ur_starCountArray);
        ur_listOfYearsAdapter          = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ur_yearsArray);
        ur_listOfMonthsAdapter         = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ur_monthsArray);
        lv_jUserReviews_listOfReviews  .setAdapter(ur_userReviewsListAdapter);
        sp_jUserReviews_sortByStarCount.setAdapter(ur_listOfStarCntsAdapter);
        sp_jUserReviews_sortByYear     .setAdapter(ur_listOfYearsAdapter);
        sp_jUserReviews_sortByMonth    .setAdapter(ur_listOfMonthsAdapter);
        sp_jUserReviews_sortByStarCount.setSelection(0);
        sp_jUserReviews_sortByYear     .setSelection(0);
        sp_jUserReviews_sortByMonth    .setSelection(0);
        cl_vUserReviews_sortingSpinners.setVisibility(View.INVISIBLE);
    }
    private void UR_OnClickListeners()
    {
        btn_jUserReviews_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(ur_welcomeUserIntent);
            }
        });
        btn_jUserReviews_leaveReview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ur_currentUserReview = UserSessionData.GetLoggedInUserReview();
                if(ur_currentUserReview == null)
                {
                    ur_userHasReview = false;
                    startActivity(ur_leaveReviewIntent);
                }
                else
                {
                    ur_userHasReview = true;
                    tv_jUserReviews_reviewErrorText.setVisibility(View.VISIBLE);
                    tv_jUserReviews_reviewErrorText.setText("Only 1 review allowed. Delete or Edit your current review.");
                }
            }
        });
        btn_jUserReviews_toggleSortingView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cl_vUserReviews_sortingSpinners.getVisibility() == View.INVISIBLE)
                {
                    cl_vUserReviews_sortingSpinners.setVisibility(View.VISIBLE);

                }
                else
                {
                    cl_vUserReviews_sortingSpinners.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_jUserReviews_resetSort.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sp_jUserReviews_sortByStarCount.setSelection(0);
                sp_jUserReviews_sortByYear.setSelection(0);
                sp_jUserReviews_sortByMonth.setSelection(0);
                sw_jUserReviews_sortByRating.setChecked(false);
                sw_jUserReviews_showAllOrUserReviews.setChecked(false);
                sw_jUserReviews_ascOrDescToggle.setChecked(false);
                sw_jUserReviews_ascOrDescToggle.setText("DESC");
                sw_jUserReviews_sortByRating.setText("By Rating");
                ur_sortByRating = " ORDER BY Star_Count ";
                ur_sortAscOrDesc = "DESC";
                UR_GetSortingParams();
            }
        });
        sw_jUserReviews_showAllOrUserReviews.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(sw_jUserReviews_showAllOrUserReviews.isChecked())
                {
                    if(UserSessionData.GetLoggedInUserReview() != null)
                    {

                        btn_jUserReviews_toggleSortingView.setVisibility(View.INVISIBLE);
                        cl_vUserReviews_sortingSpinners.setVisibility(View.INVISIBLE);
                        tv_jUserReviews_sortingBtnDisplay.setVisibility(View.INVISIBLE);
                        ur_UserOnlyReview = new ArrayList<>();
                        ur_UserOnlyReview.add(UserSessionData.GetLoggedInUserReview());
                        ur_userReviewsUserAdapter = new ReviewListAdapter(UserReviewsActivity.this, ur_UserOnlyReview, rb_jUserReviews_averageRating);
                        sw_jUserReviews_showAllOrUserReviews.setText("My Review");
                        lv_jUserReviews_listOfReviews.setAdapter(ur_userReviewsUserAdapter);
                        ur_userReviewsUserAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    tv_jUserReviews_sortingBtnDisplay.setVisibility(View.VISIBLE);
                    btn_jUserReviews_toggleSortingView.setVisibility(View.VISIBLE);
                    sw_jUserReviews_showAllOrUserReviews.setText("Show All");
                    ur_listOfReviews = ur_dbHelper.DB_GetListOfReviews(ur_sortingStatement, ur_sortByRating, ur_sortAscOrDesc);
                    ur_userReviewsListAdapter = new ReviewListAdapter(UserReviewsActivity.this, ur_listOfReviews, rb_jUserReviews_averageRating);
                    lv_jUserReviews_listOfReviews.setAdapter(ur_userReviewsListAdapter);
                    ur_userReviewsListAdapter.notifyDataSetChanged();
                }
            }
        });
        sw_jUserReviews_ascOrDescToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(sw_jUserReviews_ascOrDescToggle.isChecked())
                {
                    sw_jUserReviews_ascOrDescToggle.setText("ASC");
                    ur_sortAscOrDesc = "ASC";
                    UR_GetSortingParams();
                }
                else
                {
                    sw_jUserReviews_ascOrDescToggle.setText("DESC");
                    ur_sortAscOrDesc = "DESC";
                    UR_GetSortingParams();
                }
            }
        });
        sw_jUserReviews_sortByRating.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(sw_jUserReviews_sortByRating.isChecked())
                {
                    sw_jUserReviews_sortByRating.setText("By Date");
                    ur_sortByRating = " ORDER BY Review_Date ";
                    UR_GetSortingParams();
                }
                else
                {
                    sw_jUserReviews_sortByRating.setText("By Rating");
                    ur_sortByRating = " ORDER BY Star_Count ";
                    UR_GetSortingParams();
                }
            }
        });
        sp_jUserReviews_sortByStarCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                ur_byStarCountSpinnerPos = pos + ".0";
                UR_GetSortingParams();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        sp_jUserReviews_sortByYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                if(pos != 0)
                {
                    ur_byYearSpinnerPos = ur_yearsArray[pos];
                    Log.d("sortByYear", ur_yearsArray[pos]);
                }
                else
                {
                    ur_byYearSpinnerPos = "0";
                }
                UR_GetSortingParams();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        sp_jUserReviews_sortByMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                if(pos != 0)
                {
                    Log.d("sortByMonth", ur_monthsArray[pos].substring(0,2));
                    ur_byMonthSpinnerPos = ur_monthsArray[pos].substring(0,2);
                }
                else
                {
                    ur_byMonthSpinnerPos = "0";
                }
                UR_GetSortingParams();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        cl_vUserReviews_main.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ur_userHasReview)
                {
                    tv_jUserReviews_reviewErrorText.setVisibility(View.INVISIBLE);
                }
            }
        });
        lv_jUserReviews_listOfReviews.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(ur_userHasReview)
                {
                    tv_jUserReviews_reviewErrorText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private void UR_CreateArraysForSpinners()
    {
        ur_starCountArray = new String[]{"All", "1 - Star", "2 - Stars", "3 - Stars", "4 - Stars", "5 - Stars"};
        ur_yearsArray = new String[]{"-Select a Year-", "2024", "2023", "2022", "2021", "2020", "2019", "2018"};
        ur_monthsArray = new String[]{"-Select a Month-", "01 - Jan", "02 - Feb", "03 - Mar", "04 - Apr", "05 - May", "06 - Jun", "07 - Jul",
                                      "08 - Aug", "09 - Sep", "10 - Oct", "11 - Nov", "12 - Dec"};
    }
    private void UR_GetSortingParams()
    {
        String tempStarCnt = "is not null";
        String tempDate = "Review_Date is not null";
        String tempYear = "";
        String tempMonth = "";
        String tempSortStatement = "";
        boolean yearIsEntered = false;
        boolean monthIsEntered = false;
        if(!sw_jUserReviews_showAllOrUserReviews.isChecked())
        {
            if(!ur_byStarCountSpinnerPos.equals("0.0"))
            {
                tempStarCnt = " = '" + ur_byStarCountSpinnerPos + "'";
            }
            if (!ur_byYearSpinnerPos.equals("0"))
            {
                tempYear = ur_byYearSpinnerPos;
                yearIsEntered = true;
            }
            if(!ur_byMonthSpinnerPos.equals("0"))
            {
                tempMonth = ur_byMonthSpinnerPos;
                monthIsEntered = true;
            }
            if(yearIsEntered && monthIsEntered)
            {
                tempDate = "SUBSTR(Review_Date,1,7) = '" + tempYear + "-" + tempMonth + "'";
            }
            else
            {
                if(yearIsEntered)
                {
                    tempDate = "SUBSTR(Review_Date,1,4) = '" + tempYear + "'";
                }
                if(monthIsEntered)
                {
                    tempDate = "SUBSTR(Review_Date,6,2) = '" + tempMonth + "'";
                }
            }
            tempSortStatement = " WHERE Star_Count " + tempStarCnt + " AND  " + tempDate;
            ur_sortingStatement       = tempSortStatement;
            ur_listOfReviews          = ur_dbHelper.DB_GetListOfReviews(ur_sortingStatement, ur_sortByRating, ur_sortAscOrDesc);
            ur_userReviewsListAdapter = new ReviewListAdapter(this, ur_listOfReviews, rb_jUserReviews_averageRating);
            lv_jUserReviews_listOfReviews.setAdapter(ur_userReviewsListAdapter);
            ur_userReviewsListAdapter.notifyDataSetChanged();
            Log.d("tempSortStatement", tempSortStatement);
        }
    }
    private void UR_SettingAverageReview()
    {
        float tempTotal = ur_dbHelper.DB_GetRatingTotalSum();
        float tempReviewCnt = ur_dbHelper.DB_RecordCount("Reviews");
        float average = tempTotal / tempReviewCnt;
        rb_jUserReviews_averageRating.setRating(average);
    }
}