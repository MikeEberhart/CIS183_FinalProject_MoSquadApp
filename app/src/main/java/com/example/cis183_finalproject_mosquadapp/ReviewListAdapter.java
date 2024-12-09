package com.example.cis183_finalproject_mosquadapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReviewListAdapter extends BaseAdapter
{
    Context rla_passedContext;
    ArrayList<UserReview> rla_listOfReviews;
    RatingBar rb_jReviewCell_starCount;
    RatingBar rb_jReviewCell_averageRating;
    TextView tv_jReviewCell_nameDisplay;
    TextView tv_jReviewCell_reviewDate;
    TextView tv_jReviewCell_reviewText;
    ImageView iv_jReviewCell_deleteReview;
    ImageView iv_jReviewCell_editReview;
    DatabaseHelper rla_dbHelper;

    public ReviewListAdapter(Context c, ArrayList<UserReview> list, RatingBar rb)
    {
        rla_passedContext = c;
        rla_listOfReviews = list;
        rb_jReviewCell_averageRating = rb;
//        Log.d("listOfReviews", String.valueOf(rla_listOfReviews.get(0).getUrv_reviewID()));
    }

    @Override
    public int getCount()
    {
        return rla_listOfReviews.size();
    }
    @Override
    public Object getItem(int i)
    {
        return rla_listOfReviews.get(i);
    }
    @Override
    public long getItemId(int i)
    {
        return i;
    }
    @Override
    public View getView(int pos, View view, ViewGroup parent)
    {
        if(view == null)
        {
            LayoutInflater listInflater = (LayoutInflater) rla_passedContext.getSystemService(UserReviewsActivity.LAYOUT_INFLATER_SERVICE);
            view = listInflater.inflate(R.layout.listview_review_cell, null);
        }
        rla_dbHelper = new DatabaseHelper(rla_passedContext);
        UserReview rla_userReview = rla_listOfReviews.get(pos);
        User selectedUser = rla_dbHelper.DB_GetUserFromReviewID(rla_userReview.getUrv_reviewID());
        User currentUser = UserSessionData.GetLoggedInUser();
        String flName = selectedUser.getUser_fname() + " " + selectedUser.getUser_lname().charAt(0) + ".";
        RLA_ListOfViews(view);
        if(selectedUser.getUser_username().equals(currentUser.getUser_username()))
        {
            iv_jReviewCell_deleteReview.setVisibility(View.VISIBLE);
            iv_jReviewCell_editReview.setVisibility(View.VISIBLE);
            RLA_OnClickListeners(selectedUser, rla_userReview);
        }
        else
        {
            iv_jReviewCell_deleteReview.setVisibility(View.INVISIBLE);
            iv_jReviewCell_editReview.setVisibility(View.INVISIBLE);
        }
        rb_jReviewCell_starCount.setRating(Float.parseFloat(rla_userReview.getUrv_starCount()));
        tv_jReviewCell_nameDisplay.setText(flName);
        tv_jReviewCell_reviewDate.setText(rla_userReview.getUrv_reviewDate());
        tv_jReviewCell_reviewText.setText(rla_userReview.getUrv_reviewText());
        return view;
    }
    private void RLA_ListOfViews(View v)
    {
        rb_jReviewCell_starCount    = v.findViewById(R.id.rb_vReviewCell_starCount);
        tv_jReviewCell_nameDisplay  = v.findViewById(R.id.tv_vReviewCell_nameDisplay);
        tv_jReviewCell_reviewDate   = v.findViewById(R.id.tv_vReviewCell_reviewDate);
        tv_jReviewCell_reviewText   = v.findViewById(R.id.tv_vReviewCell_reviewText);
        iv_jReviewCell_deleteReview = v.findViewById(R.id.iv_vReviewCell_deleteReview);
        iv_jReviewCell_editReview   = v.findViewById(R.id.iv_vReviewCell_editReview);
    }
    private void RLA_OnClickListeners(User currentUser, UserReview ur)
    {
        iv_jReviewCell_deleteReview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rla_dbHelper.DB_DeleteUserReview(currentUser.getUser_reviewID(), currentUser.getUser_username());
                rla_listOfReviews.remove(ur);
                UserSessionData.SetLoggedInUserReview(null);
                RLA_SettingAverageReview();
                notifyDataSetChanged();
            }
        });
        iv_jReviewCell_editReview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent rla_leaveReviewIntent = new Intent(rla_passedContext, LeaveReviewActivity.class);
                UserSessionData.SetIsPassedFromEditReview(true);
                rla_passedContext.startActivity(rla_leaveReviewIntent);
            }
        });
    }
    private void RLA_SettingAverageReview()
    {
        float tempTotal = rla_dbHelper.DB_GetRatingTotalSum();
        float tempReviewCnt = rla_dbHelper.DB_RecordCount("Reviews");
        float average = tempTotal / tempReviewCnt;
        rb_jReviewCell_averageRating.setRating(average);
    }
}
