package com.example.cis183_finalproject_mosquadapp;

public class YardServices
{
    private int serviceID;
    private int barrierTreatment;
    private int allNatural;
    private int specialEvent;
    private int homeShield;
    private int flyControl;
    private int invaderGuard;
    private int yardDefender;

    public YardServices()
    {

    }
    public YardServices(int id, int bt, int an, int se, int hs, int fc, int ig, int yd)
    {
        serviceID = id;
        barrierTreatment = bt;
        allNatural = an;
        specialEvent = se;
        homeShield = hs;
        flyControl = fc;
        invaderGuard = ig;
        yardDefender = yd;
    }

    // GETTERS //
    public int getServiceID()
    {
        return serviceID;
    }
    public int getBarrierTreatment()
    {
        return barrierTreatment;
    }
    public int getAllNatural()
    {
        return allNatural;
    }
    public int getSpecialEvent()
    {
        return specialEvent;
    }
    public int getHomeShield()
    {
        return homeShield;
    }
    public int getFlyControl()
    {
        return flyControl;
    }
    public int getInvaderGuard()
    {
        return invaderGuard;
    }
    public int getYardDefender()
    {
        return yardDefender;
    }

    // SETTERS //
    public void setServiceID(int serviceID)
    {
        this.serviceID = serviceID;
    }
    public void setBarrierTreatment(int barrierTreatment)
    {
        this.barrierTreatment = barrierTreatment;
    }
    public void setAllNatural(int allNatural)
    {
        this.allNatural = allNatural;
    }
    public void setSpecialEvent(int specialEvent)
    {
        this.specialEvent = specialEvent;
    }
    public void setHomeShield(int homeShield)
    {
        this.homeShield = homeShield;
    }
    public void setFlyControl(int flyControl)
    {
        this.flyControl = flyControl;
    }
    public void setInvaderGuard(int invaderGuard)
    {
        this.invaderGuard = invaderGuard;
    }
    public void setYardDefender(int yardDefender)
    {
        this.yardDefender = yardDefender;
    }
}
