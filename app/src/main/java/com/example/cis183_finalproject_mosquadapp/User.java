package com.example.cis183_finalproject_mosquadapp;

public class User
{
    String user_username;
    String user_password;
    String user_fname;
    String user_lname;
    String user_email;
    String user_phoneNumber;

    public User()
    {

    }
    public User(String u, String p, String f, String l, String e, String pn)
    {
        user_username = u;
        user_password = p;
        user_fname = f;
        user_lname = l;
        user_email = e;
        user_phoneNumber = pn;
    }

    // GETTERS //
    public String getUser_username()
    {
        return user_username;
    }
    public String getUser_password()
    {
        return user_password;
    }
    public String getUser_fname()
    {
        return user_fname;
    }
    public String getUser_lname()
    {
        return user_lname;
    }
    public String getUser_email()
    {
        return user_email;
    }
    public String getUser_phoneNumber()
    {
        return user_phoneNumber;
    }

    // SETTERS //
    public void setUser_username(String user_username)
    {
        this.user_username = user_username;
    }
    public void setUser_password(String user_password)
    {
        this.user_password = user_password;
    }
    public void setUser_fname(String user_fname)
    {
        this.user_fname = user_fname;
    }
    public void setUser_lname(String user_lname)
    {
        this.user_lname = user_lname;
    }
    public void setUser_email(String user_email)
    {
        this.user_email = user_email;
    }
    public void setUser_phoneNumber(String user_phoneNumber)
    {
        this.user_phoneNumber = user_phoneNumber;
    }
}
