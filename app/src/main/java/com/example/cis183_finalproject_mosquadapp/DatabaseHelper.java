package com.example.cis183_finalproject_mosquadapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "MoSquadApp.db";
    private static final String USERS_TABLE = "Users";
    private static final String SERVICE_ADDRESSES_TABLE = "Service_Addresses";
    private static final String REVIEWS_TABLE = "Reviews";
    private static final String POLYGON_DATA_TABLE = "Polygon_Data";
    private static final String YARD_SERVICES_TABLE = "Yard_Services";
    private static final String PACKAGE_PRICES_TABLE = "Package_Price";

    public DatabaseHelper(Context c)
    {
        super(c, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        DB_CreateTables(db);
    }
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SERVICE_ADDRESSES_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + REVIEWS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + POLYGON_DATA_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + YARD_SERVICES_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + PACKAGE_PRICES_TABLE + ";");
        onCreate(db);
    }

    public void DB_PopulateDummyData()
    {

    }

    private void DB_CreateTables(@NonNull SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + USERS_TABLE + " (Username varchar primary key not null, Password varchar," +
                " First_Name varchar, Last_Name varchar, Email varchar, Phone_Number varchar);");

        db.execSQL("CREATE TABLE " + POLYGON_DATA_TABLE + " (PolygonID integer primary key autoincrement not null," +
                " Polygon_Points varchar, Display_Format varchar);");

        db.execSQL("CREATE TABLE " + YARD_SERVICES_TABLE + " (ServiceID integer primary key autoincrement not null," +
                " Barrier_Treatment integer, Fly_Control integer, Home_Shield integer, Invader_Guard integer, Yard_Defender integer," +
                " All_Natural integer, Special_Event integer);");

        db.execSQL("CREATE TABLE " + SERVICE_ADDRESSES_TABLE + " (AddressID integer primary key autoincrement not null," +
                " Username varchar, Street_Address varchar, Apt varchar, City varchar, State varchar, PolygonID integer," +
                " Total_Acreage double, ServiceID integer, Estimated_Price double, foreign key (Username) references " +
                USERS_TABLE + "(Username), foreign key (PolygonID) references " + POLYGON_DATA_TABLE + "(PolygonID)," +
                " foreign key (ServiceID) references " + YARD_SERVICES_TABLE + "(ServiceID));");

        db.execSQL("CREATE TABLE " + REVIEWS_TABLE + " (ReviewID integer primary key autoincrement not null," +
                " Username varchar, Star_Count integer, Review_Text varchar, Review_Date varchar, foreign key" +
                " (Username) references " + USERS_TABLE + "(Username));");

        db.execSQL("CREATE TABLE " + PACKAGE_PRICES_TABLE + " (Package_Name varchar primary key not null," +
                " Price double);");
    }
}
