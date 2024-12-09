package com.example.cis183_finalproject_mosquadapp;

public class UserReview
{
    private int urv_reviewID;
    String urv_starCount;
    String urv_reviewText;
    String urv_reviewDate;

    public UserReview()
    {

    }
    public UserReview(int rID, String sc, String rt, String rd)
    {
        urv_reviewID = rID;
        urv_starCount = sc;
        urv_reviewText = rt;
        urv_reviewDate = rd;
    }
    // GETTERS //
    public int getUrv_reviewID()
    {
        return urv_reviewID;
    }
    public String getUrv_starCount()
    {
        return urv_starCount;
    }
    public String getUrv_reviewText()
    {
        return urv_reviewText;
    }
    public String getUrv_reviewDate()
    {
        return urv_reviewDate;
    }
    // SETTERS //
    public void setUrv_reviewID(int urv_reviewID)
    {
        this.urv_reviewID = urv_reviewID;
    }
    public void setUrv_starCount(String urv_starCount)
    {
        this.urv_starCount = urv_starCount;
    }
    public void setUrv_reviewText(String urv_reviewText)
    {
        this.urv_reviewText = urv_reviewText;
    }
    public void setUrv_reviewDate(String urv_reviewDate)
    {
        this.urv_reviewDate = urv_reviewDate;
    }
}
