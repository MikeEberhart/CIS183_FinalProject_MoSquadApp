package com.example.cis183_finalproject_mosquadapp;

import android.util.Log;

import java.util.ArrayList;

public class UserSessionData
{
    private static User loggedInUser;
    private static ArrayList<ServiceAddress> userAddressData;
    private static int userAddressCount;


    public static User GetLoggedInUser()
    {
        return loggedInUser;
    }
    public static void SetLoggedInUser(User u)
    {
        loggedInUser = u;
    }


    // should this be here? Or in the database class as a function to get each address with said username//
    public static ArrayList<ServiceAddress> GetUserAddressData()
    {
        return userAddressData;
    }
    public static void SetUserAddressData(ArrayList<ServiceAddress> addressData)
    {
        userAddressData = addressData;

    }

    public static int GetUserAddressCount()
    {
        return userAddressCount;
    }
    public static void SetUserAddressCount(int cnt)
    {
        userAddressCount = cnt;
        Log.d("address count", userAddressCount + "");
    }
}
