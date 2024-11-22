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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PropertyMapActivity extends AppCompatActivity implements OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback
{
    Intent pm_enterAddressIntent;
    Intent pm_welcomeUserIntent;
    Intent pm_packageSelectionIntent;
    GoogleMap map_jPropertyMap_gMap;
    SupportMapFragment mFrag_jPropertyMap_mapView;
    Button btn_jPropertyMap_saveAndContinue;
    Button btn_jPropertyMap_home;
    Button btn_jPropertyMap_back;
    FloatingActionButton fab_jPropertyMap_mapTypeSwich;
    FloatingActionButton fab_jPropertyMap_undoLast;
    FloatingActionButton fab_jPropertyMap_resetAll;
    ArrayList<LatLng> pm_listOfLatLngs;
    ArrayList<LatLng> pm_tempListOfLatLng;
    List<LatLng> pm_polygonPoints;
    PolygonOptions pm_userPolygonOptions;
    Polygon pm_userPolygon;
    MarkerOptions pm_userPointMarkerOption;
    Marker pm_userPointMarker;
    PolylineOptions pm_userPolylineOption;
    Polyline pm_userPolyline;
    LocationManager pm_userLocationManger;
    Location pm_userLocation;
    CameraPosition pm_userCamPos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_map);

        PM_ListOfViews();
        PM_InitData();
        PM_OnClickListeners();

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        map_jPropertyMap_gMap = googleMap;
        map_jPropertyMap_gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        PM_SettingMapLocation();
        PM_MapOnClickListeners();
//        Testing();

    }
    private void PM_ListOfViews()
    {
        mFrag_jPropertyMap_mapView       = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mFrag_vPropertyMap_mapView);
        btn_jPropertyMap_saveAndContinue = findViewById(R.id.btn_vPropertyMap_saveAndContinue);
        btn_jPropertyMap_home            = findViewById(R.id.btn_vPropertyMap_home);
        btn_jPropertyMap_back            = findViewById(R.id.btn_vPropertyMap_back);
        fab_jPropertyMap_mapTypeSwich    = findViewById(R.id.fab_vPropertyMap_mapTypeSwitch);
        fab_jPropertyMap_undoLast        = findViewById(R.id.fab_vPropertyMap_undoLast);
        fab_jPropertyMap_resetAll        = findViewById(R.id.fab_vPropertyMap_resetAll);
        if(mFrag_jPropertyMap_mapView != null)
        {
            mFrag_jPropertyMap_mapView.getMapAsync(this);
        }

    }
    private void PM_InitData()
    {
        pm_enterAddressIntent = new Intent(this, EnterAddressActivity.class);
        pm_welcomeUserIntent  = new Intent(this, WelcomeUserActivity.class);
        pm_listOfLatLngs      = new ArrayList<>();
        pm_userPolygonOptions = new PolygonOptions();
//        PM_SetPolygonOptions();
    }
    private void PM_OnClickListeners()
    {
        btn_jPropertyMap_saveAndContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                startActivity(pm_packageSelectionIntent);
            }
        });
        btn_jPropertyMap_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(pm_welcomeUserIntent);
            }
        });
        btn_jPropertyMap_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(pm_enterAddressIntent);
            }
        });


    }
    private void PM_CreatePolygon(LatLng latLng)
    {
        if(pm_userPolygon == null)
        {
            pm_userPolygonOptions = new PolygonOptions();
            pm_userPolygonOptions.fillColor(Color.rgb(255,0,0));
            pm_userPolygonOptions.strokeColor(Color.rgb(0,0,0));
            pm_userPolygonOptions.strokeWidth(10);
            pm_userPolygonOptions.strokeJointType(JointType.ROUND);
            pm_userPolygonOptions.add(latLng);
            pm_userPointMarkerOption = new MarkerOptions();
            pm_userPointMarkerOption.position(latLng);
//            pm_userPointMarkerOption.anchor(0,0);
            pm_userPointMarkerOption.draggable(true);
            pm_userPolygon = map_jPropertyMap_gMap.addPolygon(pm_userPolygonOptions);
            pm_userPointMarker = map_jPropertyMap_gMap.addMarker(pm_userPointMarkerOption);
        }
        else
        {
            pm_polygonPoints = pm_userPolygon.getPoints();
            pm_polygonPoints.add(latLng);
            if(pm_polygonPoints.size() > 2)
            {
                pm_polygonPoints.remove(pm_polygonPoints.size() - 2);
            }
            pm_userPointMarkerOption.position(latLng);
            pm_userPointMarker = map_jPropertyMap_gMap.addMarker(pm_userPointMarkerOption);
            pm_userPolygon.setPoints(pm_polygonPoints);
        }


//        LatLng startingPoint = pm_listOfLatLngs.get(0);
//        LatLng endingPoing = pm_listOfLatLngs.get(pm_listOfLatLngs.size()-1);
//        double spLat = startingPoint.latitude;
//        double spLong = startingPoint.longitude;
//        double epLat = endingPoing.latitude;
//        double epLong = endingPoing.longitude;
//
//        pm_tempListOfLatLng = new ArrayList<>(pm_listOfLatLngs);
//        pm_userPolygonOptions.addAll(pm_tempListOfLatLng);
//        pm_userPolygonOptions.fillColor(Color.argb(95,170,174,55));
//        pm_userPolygonOptions.strokeColor(Color.argb(95,170,174,55));
//        pm_userPolygonOptions.strokeWidth(10);
//        pm_userPolygon = map_jPropertyMap_gMap.addPolygon(pm_userPolygonOptions);
//        pm_userPolygon.setPoints(pm_listOfLatLngs);
    }
    private void PM_MapOnClickListeners()
    {
        map_jPropertyMap_gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(@NonNull LatLng latLng)
            {
                Log.d("my latlng", String.valueOf(latLng));

//                pm_listOfLatLngs.add(latLng);
                PM_CreatePolygon(latLng);
//                PM_SetPolygonOptions();
            }
        });
        fab_jPropertyMap_mapTypeSwich.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(map_jPropertyMap_gMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
                {
                    fab_jPropertyMap_mapTypeSwich.setImageResource(R.drawable.folding_map_icon);
                    map_jPropertyMap_gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else
                {
                    fab_jPropertyMap_mapTypeSwich.setImageResource(R.drawable.sat_icon_white);
                    map_jPropertyMap_gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });
        fab_jPropertyMap_undoLast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pm_listOfLatLngs.remove(pm_listOfLatLngs.size() - 1);
                PM_SetPolygonOptions();
            }
        });
        fab_jPropertyMap_resetAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pm_listOfLatLngs = new ArrayList<>();
                PM_SetPolygonOptions();
            }
        });
        map_jPropertyMap_gMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDrag(@NonNull Marker marker)
            {
                // need to get the point the marker is linked with so the LatLng of the point changes with the markers //
                marker.setDraggable(true);
            }
            @Override
            public void onMarkerDragEnd(@NonNull Marker marker)
            {

            }
            @Override
            public void onMarkerDragStart(@NonNull Marker marker)
            {

            }
        });

    }
    private void PM_SetPolygonOptions()
    {
        ArrayList<LatLng> temp = pm_listOfLatLngs;
//        PolylineOptions tempOptions = new PolylineOptions();
//        tempOptions.color(Color.rgb(255,0,0));
//        tempOptions.jointType(JointType.ROUND);
//        temp = pm_listOfLatLngs;
//        tempOptions = pm_userPolylineOption;
//        tempOptions.addAll(temp);
        pm_userPolygonOptions = new PolygonOptions();
        pm_userPolygonOptions.fillColor(Color.rgb(255,0,0));
        pm_userPolygonOptions.strokeColor(Color.rgb(255,0,0));
        pm_userPolygonOptions.strokeWidth(10);
        pm_userPolygonOptions.strokeJointType(JointType.ROUND);
        pm_userPolygonOptions.addAll(temp);
        pm_userPolygon = map_jPropertyMap_gMap.addPolygon(pm_userPolygonOptions);

//        pm_userPolylineOption = new PolylineOptions();
//        pm_userPolylineOption.color(Color.rgb(255,0,0));
//        pm_userPolylineOption.jointType(JointType.ROUND);
//        pm_userPolylineOption.addAll(temp);
//        pm_userPolyline = map_jPropertyMap_gMap.addPolyline(pm_userPolylineOption);
    }
    private void PM_SettingMapLocation()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                pm_userLocationManger = (LocationManager) getSystemService(LOCATION_SERVICE);
                pm_userLocation = pm_userLocationManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(pm_userLocation != null)
                {
                    pm_userCamPos = new CameraPosition.Builder()
//                            .target(new LatLng(pm_userLocation.getLatitude(), pm_userLocation.getLongitude()))
                            .target(new LatLng(41.91106, -83.41131))
                            .zoom(18).build();
                    map_jPropertyMap_gMap.animateCamera(CameraUpdateFactory.newCameraPosition(pm_userCamPos));
                    map_jPropertyMap_gMap.setMyLocationEnabled(true);
                }
            }
        }
    }
    private void Testing()
    {
        // Instantiates a new Polygon object and adds points to define a rectangle
        LatLng testLatLng = new LatLng(41.91106, -83.41131);
        LatLng startLatLng = new LatLng(41.91106, -83.41131);
        double tlat = testLatLng.latitude;
        double tlong = testLatLng.longitude;
        double slat = startLatLng.latitude;
        double slong = startLatLng.longitude;

        // not 100% about all this yet // maybe a loop would be better? or some other way //
        if(slat / tlat >= 0.90 && slat / tlat <= 1.10 || slat / (tlat + 0.00001) >= 0.90 && slat / (tlat + 0.00001) <= 1.10 || slat / (tlat - 0.00001) >= 0.90 && slat / (tlat - 0.00001) <= 1.10)
        {
            if(slong / tlong >= 0.90 && slong / tlong <= 1.10 || slong / (tlong + 0.00001) >= 0.90 && slong / (tlong + 0.00001) <= 1.10 || slong / (tlong - 0.00001) >= 0.90 && slong / (tlong - 0.00001) <= 1.10)
            {
                testLatLng = startLatLng;
            }
        }
        pm_userPolygonOptions = new PolygonOptions()
                .add(startLatLng,
                        new LatLng(41.91119, -83.41158),
                        new LatLng(41.91105, -83.41169),
                        new LatLng(41.91090, -83.41143),
                        testLatLng);


        pm_userPolygon = map_jPropertyMap_gMap.addPolygon(pm_userPolygonOptions);

    }


}