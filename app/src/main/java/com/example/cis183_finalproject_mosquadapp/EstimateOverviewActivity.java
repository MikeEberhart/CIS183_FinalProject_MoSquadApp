package com.example.cis183_finalproject_mosquadapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

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
    TextView tv_jEstOverview_streetAddress;
    TextView tv_jEstOverview_apt;
    TextView tv_jEstOverview_city;
    TextView tv_jEstOverview_state;
    TextView tv_jEstOverview_zipCode;
    TextView tv_jEstOverview_totalAcreage;
    TextView tv_jEstOverview_selectedPackages;
    TextView tv_jEstOverview_packageList;
    TextView tv_jEstOverview_packageSubTotals;
    TextView tv_jEstOverview_acreageTotal;
    TextView tv_jEstOverview_acreageSubTotal;
    TextView tv_jEstOverview_singleTotal;
    TextView tv_jEstOverview_seasonalTotal;
    DatabaseHelper eo_dbHelper;
    ServiceAddress eo_serviceAddress;
    UserPolygon eo_userPolygon;
    YardServices eo_yardServices;
    FunctionLibrary eo_funcLib;
    List<LatLng> eo_listOfPolyPoints;
    ArrayList<LatLng> eo_listOfLatLngs;
    ArrayList<Marker> eo_listOfMakers;
    MarkerOptions eo_markerOptions;
    Marker eo_marker;
    PolygonOptions eo_polygonOptions;
    Polygon eo_polygon;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estimate_overview);

        EO_ListOfViews();
        EO_InitData();
        EO_OnClickListeners();
        if(UserSessionData.GetIsPassedFromWelcomeUser())
        {
            EO_LoadPassedEstData();
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        map_jEstOverview_gMap = googleMap;
        map_jEstOverview_gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if(UserSessionData.GetIsPassedFromWelcomeUser())
        {


        }
        EO_SettingMapLocation();
    }
    private void EO_InitData()
    {
        eo_welcomeUserIntent = new Intent(this, WelcomeUserActivity.class);
        eo_selectPackageIntent = new Intent(this, SelectPackageActivity.class);
        eo_dbHelper = new DatabaseHelper(this);
        eo_funcLib = new FunctionLibrary();
    }
    private void EO_ListOfViews()
    {
        mFrag_jEstOverview_mapView       = (SupportMapFragment) getSupportFragmentManager().
                                           findFragmentById(R.id.mFrag_vEstOverview_mapView);
        btn_jEstOverview_back            = findViewById(R.id.btn_vEstOverview_back);
        btn_jEstOverview_home            = findViewById(R.id.btn_vEstOverview_home);
        tv_jEstOverview_streetAddress    = findViewById(R.id.tv_vEstOverview_streetAddress);
        tv_jEstOverview_apt              = findViewById(R.id.tv_vEstOverview_apt);
        tv_jEstOverview_city             = findViewById(R.id.tv_vEstOverview_city);
        tv_jEstOverview_state            = findViewById(R.id.tv_vEstOverview_state);
        tv_jEstOverview_zipCode          = findViewById(R.id.tv_vEstOverview_zipCode);
        tv_jEstOverview_totalAcreage     = findViewById(R.id.tv_vEstOverview_totalAcreage);
        tv_jEstOverview_selectedPackages = findViewById(R.id.tv_vEstOverview_selectedPackages);
        tv_jEstOverview_packageList      = findViewById(R.id.tv_vEstOverview_packageList);
        tv_jEstOverview_packageSubTotals = findViewById(R.id.tv_vEstOverview_packageSubTotals);
        tv_jEstOverview_acreageTotal     = findViewById(R.id.tv_vEstOverview_acreageTotal);
        tv_jEstOverview_acreageSubTotal  = findViewById(R.id.tv_vEstOverview_acreageSubTotal);
        tv_jEstOverview_singleTotal      = findViewById(R.id.tv_vEstOverview_singleTotal);
        tv_jEstOverview_seasonalTotal    = findViewById(R.id.tv_vEstOverview_seasonalTotal);
    }
    private void EO_OnClickListeners()
    {
        btn_jEstOverview_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // might need a bool that tells selectPackageActivity to load the last known data/last saved data //
                startActivity(eo_selectPackageIntent);
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
        if(eo_userPolygon != null)
        {
            EO_LoadPolygonData(eo_userPolygon);
        }
        if(eo_yardServices != null)
        {

        }
    }
    private void EO_LoadPolygonData(UserPolygon uPoly)
    {
        LatLng tempLatLngs;
        Log.d("LoadPolygonData", uPoly.toString());
        String[] lats = uPoly.getUp_polygonLats().split(",");
        String[] lngs = uPoly.getUp_polygonLngs().split(",");
        for(int i = 0; i < lats.length; i++)
        {
            tempLatLngs = new LatLng(Double.parseDouble(lats[i]), Double.parseDouble(lngs[i]));
            if(eo_polygon == null)
            {
//            eo_listOfLatLngs  = new ArrayList<>();
//                eo_listOfMakers   = new ArrayList<>();
                eo_markerOptions  = new MarkerOptions();
                eo_polygonOptions = new PolygonOptions();
//            eo_listOfLatLngs.add(tempLatLngs);
                eo_polygonOptions.fillColor(Color.argb(120,170,174,55));
                eo_polygonOptions.strokeColor(Color.rgb(255,0,0));
                eo_polygonOptions.strokeWidth(10);
                eo_polygonOptions.strokeJointType(JointType.ROUND);
                eo_polygonOptions.add(tempLatLngs);
                eo_markerOptions.position(tempLatLngs);
                eo_markerOptions.draggable(false);
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
                eo_markerOptions.position(tempLatLngs);
                eo_marker = map_jEstOverview_gMap.addMarker(eo_markerOptions);
                eo_polygon.setPoints(eo_listOfPolyPoints);
            }
        }
    }
    private void EO_SettingMapLocation()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                LocationManager eo_locationManger = (LocationManager) getSystemService(LOCATION_SERVICE);
                Location eo_userLocation = eo_locationManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(eo_userLocation != null)
                {
                    CameraPosition eo_userCamPos = new CameraPosition.Builder()
//                            .target(new LatLng(pm_userLocation.getLatitude(), pm_userLocation.getLongitude()))
                            // below .target is for testing and the above .target is the actual code //
                            // sets the location to MCCC //
                            .target(new LatLng(41.91844, -83.46907))
                            .zoom(17).build();
                    map_jEstOverview_gMap.animateCamera(CameraUpdateFactory.newCameraPosition(eo_userCamPos));
                    map_jEstOverview_gMap.setMyLocationEnabled(true);
                }
            }
        }
    }

}