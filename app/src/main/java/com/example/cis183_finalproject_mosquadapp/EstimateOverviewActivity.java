package com.example.cis183_finalproject_mosquadapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EstimateOverviewActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback
{
    GoogleMap map_jEstOverview_gMap;
    Intent eo_welcomeUserIntent;
    Intent eo_selectPackageIntent;
    Button btn_jEstOverview_home;
    Button btn_jEstOverview_back;
    SupportMapFragment mFrag_jEstOverview_mapView;
    View mFrag_jEstOverview_mFrag;
    TextView tv_jEstOverview_streetAddress;
    TextView tv_jEstOverview_apt;
    TextView tv_jEstOverview_city;
    TextView tv_jEstOverview_state;
    TextView tv_jEstOverview_zipCode;
    TextView tv_jEstOverview_totalAcreage;
    TextView tv_jEstOverview_packageList;
    TextView tv_jEstOverview_packageSubTotals;
    TextView tv_jEstOverview_acreageSubTotal;
    TextView tv_jEstOverview_singleTotal;
    TextView tv_jEstOverview_seasonalTotal;
    TextView tv_jEstOverview_priceAfterAcreage;
    TextView tv_jEstOverview_pricePerQuarter;
    TextView tv_jEstOverview_subTotal;
    TextView tv_jEstOverview_total;
    TextView tv_jEstOverview_taxes;
    TextView tv_jEstOverview_singleTotalDisplay;
    TextView tv_jEstOverview_seasonTotalDisplay;
    TextView tv_jEstOverview_missingEstError;
    TextView tv_jEstOverview_selPackDisplay;
    TextView tv_jEstOverview_mapNotSet;
    DatabaseHelper eo_dbHelper;
    ServiceAddress eo_serviceAddress;
    UserPolygon eo_userPolygon;
    YardServices eo_yardServices;
    FunctionLibrary eo_funcLib;
    DecimalFormat eo_dFormat;
    List<LatLng> eo_listOfPolyPoints;
    ArrayList<LatLng> eo_listOfLatLngs;
    MarkerOptions eo_markerOptions;
    Marker eo_marker;
    PolygonOptions eo_polygonOptions;
    Polygon eo_polygon;
    LocationManager eo_locationManger;
    Location eo_userLocation;
    CameraPosition eo_userCamPos;
    private int[] eo_serviceData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estimate_overview);

        EO_ListOfViews();
        EO_InitData();
        EO_OnClickListeners();
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        map_jEstOverview_gMap = googleMap;
        map_jEstOverview_gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        EO_LoadPassedEstData();
        if(eo_userPolygon != null)
        {
            tv_jEstOverview_mapNotSet.setVisibility(View.INVISIBLE);
            mFrag_jEstOverview_mFrag.setVisibility(View.VISIBLE);
            EO_LoadPolygonData(eo_userPolygon);
            EO_SetMapToPropertyLocation();
            if(eo_yardServices != null)
            {
                EO_LoadingServiceSelection();
            }
            else
            {
                EO_ClearText();
            }
        }
        else
        {
            tv_jEstOverview_mapNotSet.setVisibility(View.VISIBLE);
            mFrag_jEstOverview_mFrag.setVisibility(View.INVISIBLE);
            EO_ClearText();
        }

    }
    private void EO_InitData()
    {
        eo_welcomeUserIntent   = new Intent(this, WelcomeUserActivity.class);
        eo_selectPackageIntent = new Intent(this, SelectPackageActivity.class);
        eo_dbHelper            = new DatabaseHelper(this);
        eo_funcLib             = new FunctionLibrary();
        eo_serviceData         = new int[7];
        eo_dFormat             = new DecimalFormat("0.00");
    }
    private void EO_ListOfViews()
    {
        mFrag_jEstOverview_mapView         = (SupportMapFragment) getSupportFragmentManager().
                                             findFragmentById(R.id.mFrag_vEstOverview_mapView);
        mFrag_jEstOverview_mFrag           = findViewById(R.id.mFrag_vEstOverview_mapView);
        btn_jEstOverview_back              = findViewById(R.id.btn_vEstOverview_back);
        btn_jEstOverview_home              = findViewById(R.id.btn_vEstOverview_home);
        tv_jEstOverview_streetAddress      = findViewById(R.id.tv_vEstOverview_streetAddress);
        tv_jEstOverview_apt                = findViewById(R.id.tv_vEstOverview_apt);
        tv_jEstOverview_city               = findViewById(R.id.tv_vEstOverview_city);
        tv_jEstOverview_state              = findViewById(R.id.tv_vEstOverview_state);
        tv_jEstOverview_zipCode            = findViewById(R.id.tv_vEstOverview_zipCode);
        tv_jEstOverview_totalAcreage       = findViewById(R.id.tv_vEstOverview_totalAcreage);
        tv_jEstOverview_packageList        = findViewById(R.id.tv_vEstOverview_packageList);
        tv_jEstOverview_packageSubTotals   = findViewById(R.id.tv_vEstOverview_packageSubTotals);
        tv_jEstOverview_pricePerQuarter    = findViewById(R.id.tv_vEstOverview_pricePerQuarter);
        tv_jEstOverview_acreageSubTotal    = findViewById(R.id.tv_vEstOverview_acreageSubTotal);
        tv_jEstOverview_singleTotal        = findViewById(R.id.tv_vEstOverview_singleTotal);
        tv_jEstOverview_seasonalTotal      = findViewById(R.id.tv_vEstOverview_seasonalTotal);
        tv_jEstOverview_subTotal           = findViewById(R.id.tv_vEstOverview_subTotal);
        tv_jEstOverview_total              = findViewById(R.id.tv_vEstOverview_total);
        tv_jEstOverview_taxes              = findViewById(R.id.tv_vEstOverview_taxes);
        tv_jEstOverview_singleTotalDisplay = findViewById(R.id.tv_vEstOverview_singleTotalDisplay);
        tv_jEstOverview_seasonTotalDisplay = findViewById(R.id.tv_vEstOverview_seasonTotalDisplay);
        tv_jEstOverview_priceAfterAcreage  = findViewById(R.id.tv_vEstOverview_priceAfterAcreage);
        tv_jEstOverview_missingEstError    = findViewById(R.id.tv_vEstOverview_missingEstError);
        tv_jEstOverview_selPackDisplay     = findViewById(R.id.tv_vEstOverview_selPackDisplay);
        tv_jEstOverview_mapNotSet          = findViewById(R.id.tv_vEstOverview_mapNotSet);
        if(mFrag_jEstOverview_mapView != null)
        {
            mFrag_jEstOverview_mapView.getMapAsync(this);
        }
    }
    private void EO_OnClickListeners()
    {
        btn_jEstOverview_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(UserSessionData.GetIsPassedFromOverview())
                {
                    startActivity(eo_welcomeUserIntent);
                }
                else
                {
                    UserSessionData.SetIsPassedFromBack(true);
                    startActivity(eo_selectPackageIntent);
                }
            }
        });
        btn_jEstOverview_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(eo_welcomeUserIntent);
            }
        });
    }
    private void EO_LoadPassedEstData()
    {
        eo_serviceAddress = UserSessionData.GetPassedServiceAddress();
        eo_userPolygon    = eo_dbHelper.DB_GetUserPolygonData(String.valueOf(eo_serviceAddress.getSa_polygonID()));
        eo_yardServices   = eo_dbHelper.DB_GetYardServiceData(String.valueOf(eo_serviceAddress.getSa_serviceID()));
        tv_jEstOverview_streetAddress.setText(eo_serviceAddress.getSa_streetAddress());
        if(eo_serviceAddress.getSa_apt() != null)
        {
            tv_jEstOverview_apt.setText(eo_serviceAddress.getSa_apt());
        }
        tv_jEstOverview_city.setText(eo_serviceAddress.getSa_city());
        tv_jEstOverview_state.setText(eo_serviceAddress.getSa_state());
        tv_jEstOverview_zipCode.setText(eo_serviceAddress.getSa_zipCode());
    }
    private void EO_ClearText()
    {
        tv_jEstOverview_missingEstError.setVisibility(View.VISIBLE);
        tv_jEstOverview_subTotal.setVisibility(View.INVISIBLE);
        tv_jEstOverview_total.setVisibility(View.INVISIBLE);
        tv_jEstOverview_taxes.setVisibility(View.INVISIBLE);
        tv_jEstOverview_pricePerQuarter.setVisibility(View.INVISIBLE);
        tv_jEstOverview_singleTotalDisplay.setVisibility(View.INVISIBLE);
        tv_jEstOverview_seasonTotalDisplay.setVisibility(View.INVISIBLE);
        tv_jEstOverview_priceAfterAcreage.setVisibility(View.INVISIBLE);
        tv_jEstOverview_selPackDisplay.setVisibility(View.INVISIBLE);
        tv_jEstOverview_packageList.setText("");
        tv_jEstOverview_packageSubTotals.setText("");
        tv_jEstOverview_acreageSubTotal.setText("");
        tv_jEstOverview_singleTotal.setText("");
        tv_jEstOverview_seasonalTotal.setText("");
    }
    private void EO_LoadPolygonData(UserPolygon uPoly)
    {
        LatLng tempLatLngs;
        String[] lats = uPoly.getUp_polygonLats().split(",");
        String[] lngs = uPoly.getUp_polygonLngs().split(",");
        for(int i = 0; i < lats.length; i++)
        {
            tempLatLngs = new LatLng(Double.parseDouble(lats[i]), Double.parseDouble(lngs[i]));
            if(eo_polygon == null)
            {
                Circle cir;
                CircleOptions cop = new CircleOptions();
                eo_listOfLatLngs  = new ArrayList<>();
                eo_markerOptions  = new MarkerOptions();
                eo_polygonOptions = new PolygonOptions();
                eo_listOfLatLngs.add(tempLatLngs);
                eo_polygonOptions.fillColor(Color.argb(120,170,174,55));
                eo_polygonOptions.strokeColor(Color.rgb(255,0,0));
                eo_polygonOptions.strokeWidth(10);
                eo_polygonOptions.strokeJointType(JointType.ROUND);
                eo_polygonOptions.add(tempLatLngs);
                eo_markerOptions.position(tempLatLngs);
                eo_markerOptions.draggable(false);
                Bitmap markerImage = BitmapFactory.decodeResource(getResources(), R.drawable.location_marker01);
                markerImage = Bitmap.createScaledBitmap(markerImage, 80,80,false);
                eo_markerOptions.icon(BitmapDescriptorFactory.fromBitmap(markerImage));
                eo_polygon = map_jEstOverview_gMap.addPolygon(eo_polygonOptions);
                eo_marker = map_jEstOverview_gMap.addMarker(eo_markerOptions);
            }
            else
            {
                eo_listOfPolyPoints = eo_polygon.getPoints();
                eo_listOfPolyPoints.add(tempLatLngs);
                if(eo_listOfPolyPoints.size() > 2)
                {
                    eo_listOfPolyPoints.remove(eo_listOfPolyPoints.size() - 2);
                }
                eo_listOfLatLngs.add(tempLatLngs);
                eo_markerOptions.position(tempLatLngs);
                eo_marker = map_jEstOverview_gMap.addMarker(eo_markerOptions);
                eo_polygon.setPoints(eo_listOfPolyPoints);
            }
        }
        eo_funcLib.FL_SetTotalAcreage(tv_jEstOverview_totalAcreage, eo_listOfLatLngs);
    }
    private void EO_SetMapToPropertyLocation()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                eo_locationManger = (LocationManager) getSystemService(LOCATION_SERVICE);
                eo_userLocation = eo_locationManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(eo_userLocation != null)
                {
                    eo_userCamPos = new CameraPosition.Builder()
                            .target(eo_listOfLatLngs.get(0))
                            .zoom(17).build();
                    map_jEstOverview_gMap.animateCamera(CameraUpdateFactory.newCameraPosition(eo_userCamPos));
                    map_jEstOverview_gMap.getUiSettings().setZoomControlsEnabled(true);
                }
            }
        }
    }
    private void EO_LoadingServiceSelection()
    {
        tv_jEstOverview_missingEstError.setVisibility(View.INVISIBLE);
        tv_jEstOverview_subTotal.setVisibility(View.VISIBLE);
        tv_jEstOverview_total.setVisibility(View.VISIBLE);
        tv_jEstOverview_taxes.setVisibility(View.VISIBLE);
        tv_jEstOverview_pricePerQuarter.setVisibility(View.VISIBLE);
        tv_jEstOverview_singleTotalDisplay.setVisibility(View.VISIBLE);
        tv_jEstOverview_seasonTotalDisplay.setVisibility(View.VISIBLE);
        tv_jEstOverview_priceAfterAcreage.setVisibility(View.VISIBLE);
        tv_jEstOverview_selPackDisplay.setVisibility(View.VISIBLE);
        ArrayList<PackagePrice> tempPackage = eo_dbHelper.DB_GetPackagePrices();
        String tempPackageList = "";
        String tempPriceList = "";
        double tempPackSubTotal = 0;
        eo_serviceData[0] = eo_yardServices.getBarrierTreatment();
        eo_serviceData[1] = eo_yardServices.getAllNatural();
        eo_serviceData[2] = eo_yardServices.getSpecialEvent();
        eo_serviceData[3] = eo_yardServices.getHomeShield();
        eo_serviceData[4] = eo_yardServices.getFlyControl();
        eo_serviceData[5] = eo_yardServices.getInvaderGuard();
        eo_serviceData[6] = eo_yardServices.getYardDefender();
        int cnt = 0;
        for (int i : eo_serviceData)
        {
            if(i == 1)
            {
                tempPackageList = tempPackageList + tempPackage.get(cnt).getPp_packageName() + "\n";
                tempPriceList = tempPriceList + "$" + eo_dFormat.format(tempPackage.get(cnt).getPp_packagePrice()) + "\n";
                tempPackSubTotal = tempPackSubTotal + tempPackage.get(cnt).getPp_packagePrice();
            }
            cnt++;
        }
        tv_jEstOverview_packageList.setText(tempPackageList);
        tv_jEstOverview_packageSubTotals.setText(tempPriceList);
        CalculateTotal(tempPackSubTotal);
    }
    private void CalculateTotal(double packSubTotal)
    {
        double tempTotalAcreage = Double.parseDouble(tv_jEstOverview_totalAcreage.getText().toString());
        // subtract the first 1/4 acreage since pricing start at (1/4 = $59.00) //
        double tempAcreageMinusInit;
        if(tempTotalAcreage > 0.25)
        {
            tempAcreageMinusInit = tempTotalAcreage - 0.25;
        }
        else
        {
            tempAcreageMinusInit = tempTotalAcreage;
        }
        double tempTotalPrice = ((tempAcreageMinusInit / 0.25) * 10) + packSubTotal; // instead of 59 use a var //
        double tempSingleAfterTax = (tempTotalPrice * 0.06) + tempTotalPrice;
        double tempSeasonAfterTax = (tempTotalPrice * 7) + ((tempTotalPrice * 7) * 0.06);
        String tempBeforeTaxTotal = "$" + eo_dFormat.format(tempTotalPrice);
        String tempSingleTotal = "$" + eo_dFormat.format(tempSingleAfterTax);
        String tempSeansonTotal = "$" + eo_dFormat.format(tempSeasonAfterTax);
        tv_jEstOverview_acreageSubTotal.setText(tempBeforeTaxTotal);
        tv_jEstOverview_singleTotal.setText(tempSingleTotal);
        tv_jEstOverview_seasonalTotal.setText(tempSeansonTotal);
        // save estimate price here //
        eo_dbHelper.DB_AddEstimateTotals(Double.parseDouble(eo_dFormat.format(tempSingleAfterTax)), Double.parseDouble(eo_dFormat.format(tempSeasonAfterTax)));

    }
}