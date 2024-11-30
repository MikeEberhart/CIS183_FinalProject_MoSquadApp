package com.example.cis183_finalproject_mosquadapp;

public class UserPolygon
{
    private int up_polygonID;
    private String up_polygonLats;
    private String up_polygonLngs;

    UserPolygon()
    {

    }
    UserPolygon(int id, String lats, String lngs)
    {
        up_polygonID = id;
        up_polygonLats = lats;
        up_polygonLngs = lngs;
    }

    // GETTERS //
    public int getUp_polygonID()
    {
        return up_polygonID;
    }
    public String getUp_polygonLats()
    {
        return up_polygonLats;
    }
    public String getUp_polygonLngs()
    {
        return up_polygonLngs;
    }
    // SETTERS //
    public void setUp_polygonID(int up_polygonID)
    {
        this.up_polygonID = up_polygonID;
    }
    public void setUp_polygonLats(String up_polygonLats)
    {
        this.up_polygonLats = up_polygonLats;
    }
    public void setUp_polygonLngs(String up_polygonLngs)
    {
        this.up_polygonLngs = up_polygonLngs;
    }
}
