package com.example.cis183_finalproject_mosquadapp;

import android.util.Log;

import java.util.ArrayList;

public class UserSessionData
{
    private static User loggedInUser;
    private static UserReview currentUsersReview; // might need this to be an array //
    private static ServiceAddress editServiceAddress;
    private static ArrayList<ServiceAddress> userAddressData;
    private static int userAddressCount;
    private static boolean isPassedFromEditReview;
    private static boolean isPassedFromWelcomeUser;

    // GETTERS //
    public static User GetLoggedInUser()
    {
        return loggedInUser;
    }
    public static UserReview GetLoggedInUserReview()
    {
        return currentUsersReview;
    }
    public static ServiceAddress GetPassedServiceAddress()
    {
        return editServiceAddress;
    }
    public static ArrayList<ServiceAddress> GetUserAddressData()
    {
        return userAddressData;
    }
    public static boolean GetIsPassedFromEditReview()
    {
        return isPassedFromEditReview;
    }
    public static boolean GetIsPassedFromWelcomeUser()
    {
        return isPassedFromWelcomeUser;
    }
    // SETTERS //
    public static void SetLoggedInUser(User u)
    {
        loggedInUser = u;
    }
    public static void SetLoggedInUserReview(UserReview ur)
    {
        currentUsersReview = ur;
    }
    public static void SetPassedServiceAddress(ServiceAddress sa)
    {
        editServiceAddress = sa;
    }
    public static void SetUserAddressData(ArrayList<ServiceAddress> addressData)
    {
        userAddressData = addressData;
    }
    public static void SetIsPassedFromEditReview(boolean b)
    {
        isPassedFromEditReview = b;
    }
    public static void SetIsPassedFromWelcomeUser(boolean b)
    {
        isPassedFromWelcomeUser = b;
    }

    // not sure about the addressCount setter/getters here //
    public static int GetUserAddressCount()
    {
        return userAddressCount;
    }
    public static void SetUserAddressCount(int cnt)
    {
        userAddressCount = cnt;
    }
}
