package com.example.cis183_finalproject_mosquadapp;

public class PackagePrice
{
    private String pp_packageName;
    private double pp_packagePrice;

    PackagePrice()
    {

    }
    PackagePrice(String pn, double pp)
    {
        pp_packageName = pn;
        pp_packagePrice = pp;
    }
    // GETTERS //
    public String getPp_packageName()
    {
        return pp_packageName;
    }
    public double getPp_packagePrice()
    {
        return pp_packagePrice;
    }
    // SETTERS //
    public void setPp_packageName(String pp_packageName)
    {
        this.pp_packageName = pp_packageName;
    }
    public void setPp_packagePrice(double pp_packagePrice)
    {
        this.pp_packagePrice = pp_packagePrice;
    }
}
