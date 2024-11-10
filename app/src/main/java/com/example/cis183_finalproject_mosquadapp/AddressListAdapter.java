package com.example.cis183_finalproject_mosquadapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AddressListAdapter extends BaseAdapter {
    Context ala_passedContext;
    ArrayList<ServiceAddress> ala_listOfAddresses;
    TextView tv_jAddressCell_streetAddress;
    TextView tv_jAddressCell_aptAddress;
    TextView tv_jAddressCell_city;
    TextView tv_jAddressCell_state;
    TextView tv_jAddressCell_zipCode;
    TextView tv_jAddressCell_acreage;
    TextView tv_jAddressCell_singleTreatment;
    TextView tv_jAddressCell_seasonTreatment;


    public AddressListAdapter(Context c, ArrayList<ServiceAddress> list) {
        ala_passedContext = c;
        ala_listOfAddresses = list;
    }

    @Override
    public int getCount() {
        return ala_listOfAddresses.size();
    }

    @Override
    public Object getItem(int i) {
        return ala_listOfAddresses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater listInflater = (LayoutInflater) ala_passedContext.getSystemService(WelcomeUserActivity.LAYOUT_INFLATER_SERVICE);
            view = listInflater.inflate(R.layout.listview_address_cell, null);
        }
        ALA_ListOfViews(view);
        ServiceAddress ala_address = ala_listOfAddresses.get(i);
        DecimalFormat df = new DecimalFormat("0.00");
        String singlePrice = "$ " + df.format(ala_address.getSa_singleTreatment());
        String seasonPrice = "$ " + df.format(ala_address.getSa_seasonTreatment());
        tv_jAddressCell_streetAddress.setText(ala_address.getSa_streetAddress());
        tv_jAddressCell_city.setText(ala_address.getSa_city());
        tv_jAddressCell_state.setText(ala_address.getSa_state());
        tv_jAddressCell_zipCode.setText(ala_address.getSa_zipCode());
        tv_jAddressCell_acreage.setText(String.valueOf(ala_address.getSa_totalAcreage()));
        tv_jAddressCell_singleTreatment.setText(singlePrice);
        tv_jAddressCell_seasonTreatment.setText(seasonPrice);
        if (ala_address.getSa_apt() == null) {
            tv_jAddressCell_aptAddress.setText("N/A");
        } else {
            tv_jAddressCell_aptAddress.setText(ala_address.getSa_apt());
        }
        return view;
    }

    private void ALA_ListOfViews(@NonNull View v) {
        tv_jAddressCell_streetAddress = v.findViewById(R.id.tv_vAddressCell_streetAddress);
        tv_jAddressCell_aptAddress = v.findViewById(R.id.tv_vAddressCell_aptAddress);
        tv_jAddressCell_city = v.findViewById(R.id.tv_vAddressCell_city);
        tv_jAddressCell_state = v.findViewById(R.id.tv_vAddressCell_state);
        tv_jAddressCell_zipCode = v.findViewById(R.id.tv_vAddressCell_zipCode);
        tv_jAddressCell_acreage = v.findViewById(R.id.tv_vAddressCell_acreage);
        tv_jAddressCell_singleTreatment = v.findViewById(R.id.tv_vAddressCell_singleTreatment);
        tv_jAddressCell_seasonTreatment = v.findViewById(R.id.tv_vAddressCell_seasonTreatment);
    }
}
