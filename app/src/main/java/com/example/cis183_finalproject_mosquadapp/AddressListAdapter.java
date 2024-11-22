package com.example.cis183_finalproject_mosquadapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AddressListAdapter extends BaseAdapter {
    Context ala_passedContext;
    ArrayList<ServiceAddress> ala_listOfAddresses;
    Intent ala_enterAddressIntent;
    Intent ala_propertyMapIntent;
    TextView tv_jAddressCell_streetAddress;
    TextView tv_jAddressCell_aptAddress;
    TextView tv_jAddressCell_city;
    TextView tv_jAddressCell_state;
    TextView tv_jAddressCell_zipCode;
    TextView tv_jAddressCell_acreage;
    TextView tv_jAddressCell_singleTreatment;
    TextView tv_jAddressCell_seasonTreatment;
    TextView tv_jAddressCell_passedNoAddrTextview;
    Button btn_jAddressCell_editEstimate;
    Button btn_jAddressCell_editAddress;
    ImageView iv_jAddressCell_deleteEstimate;
    DatabaseHelper ala_dbHelper;
    ServiceAddress ala_address;
    String ala_singlePrice;
    String ala_seasonPrice;
    DecimalFormat ala_dFormat;


    public AddressListAdapter(Context c, ArrayList<ServiceAddress> list, TextView tv)
    {
        ala_passedContext = c;
        ala_listOfAddresses = list;
        tv_jAddressCell_passedNoAddrTextview = tv;
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
        if (view == null) {
            LayoutInflater listInflater = (LayoutInflater) ala_passedContext.getSystemService(WelcomeUserActivity.LAYOUT_INFLATER_SERVICE);
            view = listInflater.inflate(R.layout.listview_address_cell, null);
        }
        ALA_ListOfViews(view);
        ALA_InitData(i);
        ALA_OnClickListeners(ala_address);
        ALA_SetTextViewData();
        return view;
    }

    private void ALA_ListOfViews(@NonNull View v)
    {
        tv_jAddressCell_streetAddress   = v.findViewById(R.id.tv_vAddressCell_streetAddress);
        tv_jAddressCell_aptAddress      = v.findViewById(R.id.tv_vAddressCell_aptAddress);
        tv_jAddressCell_city            = v.findViewById(R.id.tv_vAddressCell_city);
        tv_jAddressCell_state           = v.findViewById(R.id.tv_vAddressCell_state);
        tv_jAddressCell_zipCode         = v.findViewById(R.id.tv_vAddressCell_zipCode);
        tv_jAddressCell_acreage         = v.findViewById(R.id.tv_vAddressCell_acreage);
        tv_jAddressCell_singleTreatment = v.findViewById(R.id.tv_vAddressCell_singleTreatment);
        tv_jAddressCell_seasonTreatment = v.findViewById(R.id.tv_vAddressCell_seasonTreatment);
        iv_jAddressCell_deleteEstimate  = v.findViewById(R.id.iv_vAddressCell_deleteEstimate);
        btn_jAddressCell_editEstimate   = v.findViewById(R.id.btn_vAddressCell_editEstimate);
        btn_jAddressCell_editAddress    = v.findViewById(R.id.btn_vAddressCell_editAddress);
    }
    private void ALA_InitData(int i)
    {
        ala_dbHelper           = new DatabaseHelper(ala_passedContext);
        ala_enterAddressIntent = new Intent(ala_passedContext, EnterAddressActivity.class);
        ala_propertyMapIntent  = new Intent(ala_passedContext, PropertyMapActivity.class);
        ala_address            = ala_listOfAddresses.get(i);
        ala_dFormat            = new DecimalFormat("0.00");
        ala_singlePrice        = "$ " + ala_dFormat.format(ala_address.getSa_singleTreatment());
        ala_seasonPrice        = "$ " + ala_dFormat.format(ala_address.getSa_seasonTreatment());
    }
    private void ALA_OnClickListeners(ServiceAddress sa)
    {
        iv_jAddressCell_deleteEstimate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ala_dbHelper.DB_DeleteUserAddress(sa.getSa_addressID());
                ala_listOfAddresses.remove(sa);
                notifyDataSetChanged();
                if(UserSessionData.GetUserAddressCount() == 0)
                {
                    tv_jAddressCell_passedNoAddrTextview.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv_jAddressCell_passedNoAddrTextview.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_jAddressCell_editEstimate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ala_passedContext.startActivity(ala_propertyMapIntent);
            }
        });
        btn_jAddressCell_editAddress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserSessionData.SetIsPassedFromWelcomeUser(true);
                UserSessionData.SetPassedServiceAddress(sa);

                ala_passedContext.startActivity(ala_enterAddressIntent);
            }
        });
    }
    private void ALA_SetTextViewData()
    {
        tv_jAddressCell_streetAddress.setText(ala_address.getSa_streetAddress());
        tv_jAddressCell_city.setText(ala_address.getSa_city());
        tv_jAddressCell_state.setText(ala_address.getSa_state());
        tv_jAddressCell_zipCode.setText(ala_address.getSa_zipCode());
        tv_jAddressCell_acreage.setText(String.valueOf(ala_address.getSa_totalAcreage()));
        tv_jAddressCell_singleTreatment.setText(ala_singlePrice);
        tv_jAddressCell_seasonTreatment.setText(ala_seasonPrice);
        if (ala_address.getSa_apt() == null)
        {
            tv_jAddressCell_aptAddress.setText("N/A");
        }
        else
        {
            tv_jAddressCell_aptAddress.setText(ala_address.getSa_apt());
        }
    }
}
