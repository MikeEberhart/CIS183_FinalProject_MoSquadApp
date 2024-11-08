package com.example.cis183_finalproject_mosquadapp;

public class ServiceAddress
{
    int sa_addressID;
    String sa_username;
    String sa_streetAddress;
    String sa_apt;
    String sa_city;
    int sa_polygonID;
    double sa_totalAcreage;
    int sa_serviceID;
    double sa_estimatePrice;

    public ServiceAddress()
    {

    }
    public ServiceAddress(int aID, String u, String sa, String a, String c, int pID, double ta, int sID, double ep)
    {
        sa_addressID = aID;
        sa_username = u;
        sa_streetAddress = sa;
        sa_apt = a;
        sa_city = c;
        sa_polygonID = pID;
        sa_totalAcreage = ta;
        sa_serviceID = sID;
        sa_estimatePrice = ep;
    }

    // GETTERS //
    public int getSa_addressID()
    {
        return sa_addressID;
    }
    public String getSa_username()
    {
        return sa_username;
    }
    public String getSa_streetAddress()
    {
        return sa_streetAddress;
    }
    public String getSa_apt()
    {
        return sa_apt;
    }
    public String getSa_city()
    {
        return sa_city;
    }
    public int getSa_polygonID()
    {
        return sa_polygonID;
    }
    public double getSa_totalAcreage()
    {
        return sa_totalAcreage;
    }
    public int getSa_serviceID()
    {
        return sa_serviceID;
    }
    public double getSa_estimatePrice()
    {
        return sa_estimatePrice;
    }

    // SETTERS //
    public void setSa_addressID(int sa_addressID)
    {
        this.sa_addressID = sa_addressID;
    }
    public void setSa_username(String sa_username)
    {
        this.sa_username = sa_username;
    }
    public void setSa_streetAddress(String sa_streetAddress)
    {
        this.sa_streetAddress = sa_streetAddress;
    }
    public void setSa_apt(String sa_apt)
    {
        this.sa_apt = sa_apt;
    }
    public void setSa_city(String sa_city)
    {
        this.sa_city = sa_city;
    }
    public void setSa_polygonID(int sa_polygonID)
    {
        this.sa_polygonID = sa_polygonID;
    }
    public void setSa_totalAcreage(double sa_totalAcreage)
    {
        this.sa_totalAcreage = sa_totalAcreage;
    }
    public void setSa_serviceID(int sa_serviceID)
    {
        this.sa_serviceID = sa_serviceID;
    }
    public void setSa_estimatePrice(double sa_estimatePrice)
    {
        this.sa_estimatePrice = sa_estimatePrice;
    }
}
