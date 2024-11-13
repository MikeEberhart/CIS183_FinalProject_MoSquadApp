package com.example.cis183_finalproject_mosquadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewListAdapter extends BaseAdapter
{
    Context rla_passedContext;
    ArrayList<UserReview> rla_listOfReviews;
    RatingBar rb_jReviewCell_starCount;
    TextView tv_jReviewCell_nameDisplay;
    TextView tv_jReviewCell_reviewDate;
    TextView tv_jReviewCell_reviewText;
    DatabaseHelper rla_dbHelper;

    public ReviewListAdapter(Context c, ArrayList<UserReview> list)
    {
        rla_passedContext = c;
        rla_listOfReviews = list;
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
        UserReview rla_userReview = rla_listOfReviews.get(pos);
        rla_dbHelper = new DatabaseHelper(rla_passedContext);
        String flName = rla_dbHelper.DB_GetUserData(rla_userReview.getUrv_username());
        RLA_ListOfViews(view);
        rb_jReviewCell_starCount.setRating(rla_userReview.getUrv_starCount());
        tv_jReviewCell_nameDisplay.setText(flName);
        tv_jReviewCell_reviewDate.setText(rla_userReview.getUrv_reviewDate());
        tv_jReviewCell_reviewText.setText(rla_userReview.getUrv_reviewText());
        return view;
    }
    private void RLA_ListOfViews(View v)
    {
        rb_jReviewCell_starCount = v.findViewById(R.id.rb_vReviewCell_starCount);
        tv_jReviewCell_nameDisplay = v.findViewById(R.id.tv_vReviewCell_nameDisplay);
        tv_jReviewCell_reviewDate = v.findViewById(R.id.tv_vReviewCell_reviewDate);
        tv_jReviewCell_reviewText = v.findViewById(R.id.tv_vReviewCell_reviewText);
    }

}
