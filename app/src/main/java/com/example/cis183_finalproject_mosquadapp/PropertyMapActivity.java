package com.example.cis183_finalproject_mosquadapp;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
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
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertyMapActivity extends AppCompatActivity implements OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback
{
    Intent pm_enterAddressIntent;
    Intent pm_welcomeUserIntent;
    Intent pm_packageSelectionIntent;
    DatabaseHelper pm_dbHelper;
    DecimalFormat pm_dFormat;
    GoogleMap map_jPropertyMap_gMap;
    SupportMapFragment mFrag_jPropertyMap_mapView;
    Button btn_jPropertyMap_saveAndContinue;
    Button btn_jPropertyMap_home;
    Button btn_jPropertyMap_back;
    TextView tv_jPropertyMap_totalAcreage;
    FloatingActionButton fab_jPropertyMap_mapTypeSwich;
    FloatingActionButton fab_jPropertyMap_undoLast;
    FloatingActionButton fab_jPropertyMap_resetAll;
    ArrayList<LatLng> pm_listOfLatLngs;
    List<LatLng> pm_polygonListOfPoints;
    ArrayList<Marker> pm_userListOfMarkers;
    PolygonOptions pm_userPolygonOptions;
    Polygon pm_userPolygon;
    MarkerOptions pm_userPointMarkerOption;
    Marker pm_userPointMarker;
    LocationManager pm_userLocationManger;
    Location pm_userLocation;
    CameraPosition pm_userCamPos;
    private int pm_markerCount = 0;
    // still messing with these //
    JSONArray listOfPoints;
    private int pm_tCount = 0;

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

    }
    private void PM_ListOfViews()
    {
        mFrag_jPropertyMap_mapView       = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mFrag_vPropertyMap_mapView);
        btn_jPropertyMap_saveAndContinue = findViewById(R.id.btn_vPropertyMap_saveAndContinue);
        btn_jPropertyMap_home            = findViewById(R.id.btn_vPropertyMap_home);
        btn_jPropertyMap_back            = findViewById(R.id.btn_vPropertyMap_back);
        tv_jPropertyMap_totalAcreage     = findViewById(R.id.tv_vPropertyMap_totalAcreage);
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
        pm_dbHelper = new DatabaseHelper(this);
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
            pm_userPointMarkerOption = new MarkerOptions();
            pm_userListOfMarkers     = new ArrayList<>();
            pm_userPolygonOptions    = new PolygonOptions();
            pm_listOfLatLngs         = new ArrayList<>();
            pm_listOfLatLngs.add(latLng);
            pm_userPolygonOptions.fillColor(Color.argb(120,170,174,55));
            pm_userPolygonOptions.strokeColor(Color.rgb(255,0,0));
            pm_userPolygonOptions.strokeWidth(10);
            pm_userPolygonOptions.strokeJointType(JointType.ROUND);
            pm_userPolygonOptions.add(latLng);
            pm_userPointMarkerOption.position(latLng);
            pm_userPointMarkerOption.draggable(true);
            pm_userPolygon = map_jPropertyMap_gMap.addPolygon(pm_userPolygonOptions);
            pm_userPointMarker = map_jPropertyMap_gMap.addMarker(pm_userPointMarkerOption);

            if(pm_userPointMarker != null)
            {
                pm_userPointMarker.setTag(pm_markerCount);
            }
            pm_userListOfMarkers.add(pm_userPointMarker);
        }
        else
        {
            pm_polygonListOfPoints = pm_userPolygon.getPoints();
            pm_polygonListOfPoints.add(latLng);
            if(pm_polygonListOfPoints.size() > 2)
            {
                // used to remove the center or "hidden" point
                // every time a point is placed a second point is automatically made in the same place as the
                // first original point. This causes the polygon to be cut into triangular pieces //
                // doing this removes that "hidden" point so the polygon can look normal //
                pm_polygonListOfPoints.remove(pm_polygonListOfPoints.size() - 2);
            }
            pm_listOfLatLngs.add(latLng);
            pm_userPointMarkerOption.position(latLng);
            pm_userPointMarker = map_jPropertyMap_gMap.addMarker(pm_userPointMarkerOption);
            if(pm_userPointMarker != null)
            {
                pm_userPointMarker.setTag(pm_markerCount);
            }
            pm_userListOfMarkers.add(pm_userPointMarker);
            pm_userPolygon.setPoints(pm_polygonListOfPoints);
        }
        pm_markerCount++;
        PM_SetTotalAcreage();
    }
    @SuppressLint("PotentialBehaviorOverride")
    private void PM_MapOnClickListeners()
    {
        map_jPropertyMap_gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(@NonNull LatLng latLng)
            {
                Log.d("my latlng", String.valueOf(latLng));
                PM_CreatePolygon(latLng);
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
                if(pm_markerCount != 0)
                {
                    pm_markerCount--;
                    Marker tempMarker = pm_userListOfMarkers.get(pm_markerCount);
                    tempMarker.remove();
                    pm_userListOfMarkers.remove(pm_markerCount);
                    pm_polygonListOfPoints.remove(pm_markerCount);
                    pm_listOfLatLngs.remove(pm_markerCount);
                    pm_userPolygon.setPoints(pm_polygonListOfPoints);
                    PM_SetTotalAcreage();
                }
                else
                {
                    pm_polygonListOfPoints = null;
                    map_jPropertyMap_gMap.clear();
                    pm_userPolygon = null;
                    pm_userPointMarker = null;
                    pm_userListOfMarkers = null;
                    pm_listOfLatLngs = null;
                    pm_markerCount = 0;
                    tv_jPropertyMap_totalAcreage.setText("0.00");
                }
            }
        });
        fab_jPropertyMap_resetAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv_jPropertyMap_totalAcreage.setText("0.00");
                map_jPropertyMap_gMap.clear();
                pm_userPolygon = null;
                pm_userPointMarker = null;
                pm_userListOfMarkers = null;
                pm_listOfLatLngs = null;
                pm_markerCount = 0;
            }
        });
        map_jPropertyMap_gMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDrag(@NonNull Marker marker)
            {
                LatLng tempMarkerPos;
                marker.setDraggable(true);
                tempMarkerPos = marker.getPosition();
                int tempTag = Integer.parseInt(Objects.requireNonNull(marker.getTag()).toString());
                pm_polygonListOfPoints.set(tempTag, tempMarkerPos);
                pm_listOfLatLngs.set(tempTag, tempMarkerPos);
                pm_userPolygon.setPoints(pm_polygonListOfPoints);
                PM_SetTotalAcreage();

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
                            // below .target is for testing and the above .target is the actual code //
                            // sets the location to MCCC //
                            .target(new LatLng(41.91844, -83.46907))
                            .zoom(17).build();
                    map_jPropertyMap_gMap.animateCamera(CameraUpdateFactory.newCameraPosition(pm_userCamPos));
                    map_jPropertyMap_gMap.setMyLocationEnabled(true);
                }
            }
        }
    }
    private void PM_SetTotalAcreage()
    {
        double pm_totalAcreage = (SphericalUtil.computeArea(pm_listOfLatLngs) / 4046.86);
        pm_dFormat      = new DecimalFormat("0.00");
        tv_jPropertyMap_totalAcreage.setText(pm_dFormat.format(pm_totalAcreage));
    }
    private void Testing(LatLng ltlg)
    {
//        LatLng tempLatLng = pm_listOfLatLngs
//        ArrayList<JSONObject> point = new ArrayList<>();

//        JSONObject pointSd = new JSONObject();

//        for (LatLng latlng : pm_polygonListOfPoints)
//        {
        JSONObject pointSd = new JSONObject();
        try {
            pointSd.put("id", pm_tCount);
            pointSd.put("lat", ltlg.latitude);
            pointSd.put("lng", ltlg.longitude);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        listOfPoints.put(pointSd);
        try {
            Log.d("JSON", listOfPoints.getJSONObject(pm_tCount).getInt("id") + " - " + listOfPoints.getJSONObject(pm_tCount).getDouble("lat") + " - " + listOfPoints.getJSONObject(pm_tCount).getDouble("lng"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        pm_tCount++;

    }



}