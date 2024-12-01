package com.example.cis183_finalproject_mosquadapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SelectPackageActivity extends AppCompatActivity
{
    Intent sp_propertyMapIntent;
    Intent sp_welcomeUserIntent;
    Intent sp_estimateOverviewIntent;
    CheckBox cBox_jSelectPackage_barrierTreatment;
    CheckBox cBox_jSelectPackage_allNatural;
    CheckBox cBox_jSelectPackage_specialEvent;
    CheckBox cBox_jSelectPackage_homeShield;
    CheckBox cBox_jSelectPackage_flyControl;
    CheckBox cBox_jSelectPackage_invaderGuard;
    CheckBox cBox_jSelectPackage_yardDefender;
    Button btn_jSelectPackage_back;
    Button btn_jSelectPackage_home;
    Button btn_jSelectPackage_saveAndContinue;
    TextView tv_jSelectPackage_errorText;
    DatabaseHelper sp_dbHelper;
    YardServices sp_userYardServices;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_package);

        SP_ListOfViews();
        SP_InitData();
        SP_OnClickListeners();
        if(UserSessionData.GetIsPassedFromWelcomeUser())
        {
            SP_LoadYardServiceData();
        }
    }
    private void SP_ListOfViews()
    {
        cBox_jSelectPackage_barrierTreatment = findViewById(R.id.cBox_vSelectPackage_barrierTreatment);
        cBox_jSelectPackage_allNatural       = findViewById(R.id.cBox_vSelectPackage_allNatural);
        cBox_jSelectPackage_specialEvent     = findViewById(R.id.cBox_vSelectPackage_specialEvent);
        cBox_jSelectPackage_homeShield       = findViewById(R.id.cBox_vSelectPackage_homeShield);
        cBox_jSelectPackage_flyControl       = findViewById(R.id.cBox_vSelectPackage_flyControl);
        cBox_jSelectPackage_invaderGuard     = findViewById(R.id.cBox_vSelectPackage_invaderGuard);
        cBox_jSelectPackage_yardDefender     = findViewById(R.id.cBox_vSelectPackage_yardDefender);
        btn_jSelectPackage_back              = findViewById(R.id.btn_vSelectPackage_back);
        btn_jSelectPackage_home              = findViewById(R.id.btn_vSelectPackage_home);
        btn_jSelectPackage_saveAndContinue   = findViewById(R.id.btn_vSelectPackage_saveAndContinue);
        tv_jSelectPackage_errorText          = findViewById(R.id.tv_vSelectPackage_errorText);
    }
    private void SP_InitData()
    {
        sp_propertyMapIntent      = new Intent(this, PropertyMapActivity.class);
        sp_welcomeUserIntent      = new Intent(this, WelcomeUserActivity.class);
        sp_estimateOverviewIntent = new Intent(this, EstimateOverviewActivity.class);
        sp_dbHelper               = new DatabaseHelper(this);
    }
    private void SP_OnClickListeners()
    {
        btn_jSelectPackage_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(sp_propertyMapIntent);
            }
        });
        btn_jSelectPackage_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(sp_welcomeUserIntent);
            }
        });
        btn_jSelectPackage_saveAndContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SP_SaveNewYardServices();
                startActivity(sp_estimateOverviewIntent);
            }
        });
        cBox_jSelectPackage_barrierTreatment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    Log.d("SelectPackage bTreatment", "is clicked");
                    cBox_jSelectPackage_allNatural.setChecked(false);
                    cBox_jSelectPackage_specialEvent.setChecked(false);
                }

            }
        });
        cBox_jSelectPackage_allNatural.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    Log.d("SelectPackage all natural", "is clicked");
                    cBox_jSelectPackage_barrierTreatment.setChecked(false);
                    cBox_jSelectPackage_specialEvent.setChecked(false);
                }

            }
        });
        cBox_jSelectPackage_specialEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    Log.d("SelectPackage spEvent", "is clicked");
                    cBox_jSelectPackage_barrierTreatment.setChecked(false);
                    cBox_jSelectPackage_allNatural.setChecked(false);
                }
            }
        });
        cBox_jSelectPackage_homeShield.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // might not need/use //
            }
        });
        cBox_jSelectPackage_flyControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // might not need/use //
            }
        });
        cBox_jSelectPackage_invaderGuard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // might not need/use //
            }
        });
        cBox_jSelectPackage_yardDefender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // might not need/use //
            }
        });
    }
    private void SP_SaveNewYardServices()
    {
        sp_userYardServices = new YardServices();
        sp_userYardServices.setBarrierTreatment(SP_ConfirmedIfChecked(cBox_jSelectPackage_barrierTreatment));
        sp_userYardServices.setAllNatural(SP_ConfirmedIfChecked(cBox_jSelectPackage_allNatural));
        sp_userYardServices.setSpecialEvent(SP_ConfirmedIfChecked(cBox_jSelectPackage_specialEvent));
        sp_userYardServices.setHomeShield(SP_ConfirmedIfChecked(cBox_jSelectPackage_homeShield));
        sp_userYardServices.setFlyControl(SP_ConfirmedIfChecked(cBox_jSelectPackage_flyControl));
        sp_userYardServices.setInvaderGuard(SP_ConfirmedIfChecked(cBox_jSelectPackage_invaderGuard));
        sp_userYardServices.setYardDefender(SP_ConfirmedIfChecked(cBox_jSelectPackage_yardDefender));
        if(UserSessionData.GetIsPassedFromWelcomeUser())
        {
            sp_dbHelper.DB_UpdateYardServicesData(sp_userYardServices);
        }
        else
        {
            sp_dbHelper.DB_AddNewYardServicesData(sp_userYardServices);
        }
    }
    private void SP_LoadYardServiceData()
    {
        ServiceAddress sp_serviceAddress = UserSessionData.GetPassedServiceAddress();
        sp_userYardServices = sp_dbHelper.DB_GetYardServiceData(String.valueOf(sp_serviceAddress.getSa_serviceID()));
        Log.d("LoadYardData", String.valueOf(sp_userYardServices.getServiceID()));
        if(sp_userYardServices != null)
        {
            SP_SetCheckBox(sp_userYardServices.getBarrierTreatment(), cBox_jSelectPackage_barrierTreatment);
            SP_SetCheckBox(sp_userYardServices.getAllNatural(), cBox_jSelectPackage_allNatural);
            SP_SetCheckBox(sp_userYardServices.getSpecialEvent(), cBox_jSelectPackage_specialEvent);
            SP_SetCheckBox(sp_userYardServices.getHomeShield(), cBox_jSelectPackage_homeShield);
            SP_SetCheckBox(sp_userYardServices.getFlyControl(), cBox_jSelectPackage_flyControl);
            SP_SetCheckBox(sp_userYardServices.getInvaderGuard(), cBox_jSelectPackage_invaderGuard);
            SP_SetCheckBox(sp_userYardServices.getYardDefender(), cBox_jSelectPackage_yardDefender);
        }
    }
    private int SP_ConfirmedIfChecked(CheckBox cb)
    {
        if(cb.isChecked())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    private void SP_SetCheckBox(int sel, CheckBox cb)
    {
        if(sel == 1)
        {
            cb.setChecked(true);
        }
        else
        {
            cb.setChecked(false);
        }
    }
}