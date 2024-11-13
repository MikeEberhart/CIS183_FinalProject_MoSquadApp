package com.example.cis183_finalproject_mosquadapp;

public class ServiceAddress
{
    private int sa_addressID;
    private String sa_username;
    private String sa_streetAddress;
    private String sa_apt;
    private String sa_city;
    private String sa_state;
    private String sa_zipCode;
    private int sa_polygonID;
    private double sa_totalAcreage;
    private int sa_serviceID;
    private double sa_singleTreatment;
    private double sa_seasonTreatment;

    public ServiceAddress()
    {

    }
    public ServiceAddress(int aID, String u, String sa, String a, String c, String st, String z, int pID, double ta, int sID, double sigT, double seaT)
    {
        sa_addressID = aID;
        sa_username = u;
        sa_streetAddress = sa;
        sa_apt = a;
        sa_city = c;
        sa_state = st;
        sa_zipCode = z;
        sa_polygonID = pID;
        sa_totalAcreage = ta;
        sa_serviceID = sID;
        sa_singleTreatment = sigT;
        sa_seasonTreatment = seaT;
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
    public String getSa_state()
    {
        return sa_state;
    }
    public String getSa_zipCode()
    {
        return sa_zipCode;
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
    public double getSa_singleTreatment()
    {
        return sa_singleTreatment;
    }
    public double getSa_seasonTreatment()
    {
        return sa_seasonTreatment;
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
    public void setSa_state(String sa_state)
    {
        this.sa_state = sa_state;
    }
    public void setSa_zipCode(String sa_zipCode)
    {
        this.sa_zipCode = sa_zipCode;
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
    public void setSa_singleTreatment(double sa_singleTreatment)
    {
        this.sa_singleTreatment = sa_singleTreatment;
    }
    public void setSa_seasonTreatment(double sa_seasonTreatment)
    {
        this.sa_seasonTreatment = sa_seasonTreatment;
    }
}
