package com.example.cis183_finalproject_mosquadapp;

public class UserSessionData
{
    private static User loggedInUser;
    private static ServiceAddress addressData;


    public static User getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(User u)
    {
        loggedInUser = u;
    }
}
