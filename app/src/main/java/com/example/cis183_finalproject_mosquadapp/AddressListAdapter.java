package com.example.cis183_finalproject_mosquadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class AddressListAdapter extends BaseAdapter
{
    Context ala_passedContext;
    ArrayList<String> ala_listOfAddresses;

    public AddressListAdapter(Context c, ArrayList<String> list)
    {
        ala_passedContext = c;
        ala_listOfAddresses = list;
    }

    @Override
    public int getCount()
    {
        return ala_listOfAddresses.size();
    }

    @Override
    public Object getItem(int i)
    {
        return ala_listOfAddresses.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent)
    {
        if(view == null)
        {
            LayoutInflater listInflater = (LayoutInflater) ala_passedContext.getSystemService(WelcomeUserActivity.LAYOUT_INFLATER_SERVICE);
            view = listInflater.inflate(R.layout.listview_address_cell, null);
        }
        return view;
    }
}
