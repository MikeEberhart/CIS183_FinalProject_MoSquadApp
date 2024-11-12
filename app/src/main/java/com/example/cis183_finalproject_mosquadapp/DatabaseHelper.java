package com.example.cis183_finalproject_mosquadapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

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
        super(c, DATABASE_NAME, null, 4);
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
        DB_DummyUserData();
        DB_DummyYardServiceData();
        DB_DummyServiceAddressData();
        DB_PackagePriceData();
    }

    private void DB_CreateTables(@NonNull SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + USERS_TABLE + " (Username varchar primary key not null, Password varchar not null," +
                " First_Name varchar not null, Last_Name varchar not null, Email varchar not null, Phone_Number varchar not null);");

        db.execSQL("CREATE TABLE " + POLYGON_DATA_TABLE + " (PolygonID integer primary key autoincrement not null," +
                " Polygon_Points varchar not null, Display_Format varchar not null);");

        db.execSQL("CREATE TABLE " + YARD_SERVICES_TABLE + " (ServiceID integer primary key autoincrement not null," +
                " Barrier_Treatment integer, Fly_Control integer, Home_Shield integer, Invader_Guard integer, Yard_Defender integer," +
                " All_Natural integer, Special_Event integer);");

        db.execSQL("CREATE TABLE " + SERVICE_ADDRESSES_TABLE + " (AddressID integer primary key autoincrement not null," +
                " Username varchar not null, Street_Address varchar not null, Apt varchar, City varchar not null," +
                " State varchar not null, ZipCode varchar not null, PolygonID integer," +
                " Total_Acreage double, ServiceID integer, Single_Treatment_Price double, Season_Treatment_Price double, " +
                "foreign key (Username) references " + USERS_TABLE + "(Username), foreign key (PolygonID) references " +
                POLYGON_DATA_TABLE + "(PolygonID), foreign key (ServiceID) references " + YARD_SERVICES_TABLE + "(ServiceID));");

        db.execSQL("CREATE TABLE " + REVIEWS_TABLE + " (ReviewID integer primary key autoincrement not null," +
                " Username varchar not null, Star_Count integer not null, Review_Text varchar not null, Review_Date varchar not null, " +
                "foreign key (Username) references " + USERS_TABLE + "(Username));");

        db.execSQL("CREATE TABLE " + PACKAGE_PRICES_TABLE + " (Package_Name varchar primary key not null," +
                " Price double not null);");
    }
    // used to count the number of records in a given table //
    private int DB_RecordCount(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String rowCnt = "SELECT COUNT(*) FROM " + tableName + ";";
        int count = 0;
        Cursor db_rowCnt = db.rawQuery(rowCnt, null);
        if(db_rowCnt.moveToFirst())
        {
            count = db_rowCnt.getInt(0);
            db_rowCnt.close();
            db.close();
        }
        return count;
    }
    private void DB_DummyUserData()
    {
        if(DB_RecordCount(USERS_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert dummy user data //
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('mEbbs123', 'MikeEbbs123!!!', 'Mike', 'Ebbs', 'MEbbs@gmail.com', '262-255-2244');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('AWhite93', 'AveWhite123!!!', 'Avery', 'White', 'AWhite93@runbox.com', '502-539-4469');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('CMay27', 'CyrMay123!!!', 'Cyrus', 'May', 'CMay27@startmail.com', '839-890-7679');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('AChambers9', 'AngChambers123!!!', 'Angela', 'Chambers', 'AChambers9@startmail.com', '929-549-8259');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('WDuncan65', 'WayDuncan123!!!', 'Waylon', 'Duncan', 'WDuncan65@zoho.com', '347-502-6370');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('CBradley68', 'CorBradley123!!!', 'Corey', 'Bradley', 'CBradley68@zoho.com', '726-995-2452');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('CAllison3', 'CasAllison123!!!', 'Cassidy', 'Allison', 'CAllison3@posteo.com', '606-368-0162');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('LPerry54', 'LogPerry123!!!', 'Logan', 'Perry', 'LPerry54@protonmail.com', '718-762-0340');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('ARodgers45', 'AdrRodgers123!!!', 'Adrian', 'Rodgers', 'ARodgers45@hushmail.com', '629-320-4790');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('AScott83', 'AxeScott123!!!', 'Axel', 'Scott', 'AScott83@mail.com', '785-364-8417');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('LNorris99', 'LogNorris123!!!', 'Logan', 'Norris', 'LNorris99@gmail.com', '469-485-4357');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('JTurner68', 'JacTurner123!!!', 'Jace', 'Turner', 'JTurner68@mailfence.com', '224-431-2903');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('SRobertson65', 'SabRobertson123!!!', 'Sabrina', 'Robertson', 'SRobertson65@runbox.com', '347-648-2164');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('TWeber55', 'TriWeber123!!!', 'Tripp', 'Weber', 'TWeber55@gmx.com', '267-686-5691');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('ISweeney88', 'IsaSweeney123!!!', 'Isabella', 'Sweeney', 'ISweeney88@fastmail.com', '839-871-3555');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('HGill71', 'HudGill123!!!', 'Hudson', 'Gill', 'HGill71@hey.com', '319-592-3310');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('OJensen40', 'OpaJensen123!!!', 'Opal', 'Jensen', 'OJensen40@icloud.com', '270-852-6013');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('ACurtis34', 'AlyCurtis123!!!', 'Alyssa', 'Curtis', 'ACurtis34@apple.com', '929-785-8468');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('ZWallace66', 'ZekWallace123!!!', 'Zeke', 'Wallace', 'ZWallace66@mailfence.com', '901-700-6705');");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number) " +
                       "VALUES ('GWallace44', 'GavWallace123!!!', 'Gavin', 'Wallace', 'GWallace44@hey.com', '845-731-0746');");

        }
    }
    private void DB_DummyYardServiceData()
    {
        if(DB_RecordCount(YARD_SERVICES_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,0,0,0,0,0);");
        }
    }
    private void DB_DummyServiceAddressData()
    {
        if(DB_RecordCount(SERVICE_ADDRESSES_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                       " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                       "VALUES ('mEbbs123', '13101  Wanty Rd', null, 'Milan', 'MI', '48160', null, null, 1, 123.323, 1223.233);");
        }
    }
    private void DB_PackagePriceData()
    {
        if(DB_RecordCount(PACKAGE_PRICES_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Barrier_Treatment', 59.00);"); // starts at 1/4 at $59 at 1/2 at $79. after which $4 pre 1/10 acre
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Fly_Control', 35.00);");
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Home_Shield', 95.00);");
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Invader_Guard', 30.00);");
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Yard_Defender', 30.00);");
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('All_Natural', 59.00);"); // scales like barrier treatment
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Special_Event', 30.00);"); // add the barrier treatment price to this later in the code
        }
    }
    public boolean DB_UsernameExists(String u)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkForUsername = "SELECT username FROM " + USERS_TABLE + " WHERE username = '" + u + "';";
        Cursor uexists_cursor = db.rawQuery(checkForUsername, null);
        if(uexists_cursor.moveToFirst())
        {
            uexists_cursor.close();
            return true;
        }
        uexists_cursor.close();
        return false;
    }
    public boolean DB_UserLoginGood(String uname, String pass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkForUsername = "SELECT Password FROM " + USERS_TABLE + " WHERE Username = '" + uname + "';";
        String getUserAcctData = "SELECT * FROM " + USERS_TABLE + " WHERE Username = '" + uname + "';";
        String savedPassword = "";
        Cursor checkForUserCursor = db.rawQuery(checkForUsername, null);
        Cursor getUserAcctCursor = db.rawQuery(getUserAcctData, null);
        if(checkForUserCursor.moveToFirst())
        {
            savedPassword = checkForUserCursor.getString(0);
            Log.d("saved password", savedPassword);
        }
        if(pass.equals(savedPassword) && getUserAcctCursor.moveToFirst())
        {
            User userData = new User(getUserAcctCursor.getString(0),
                                     getUserAcctCursor.getString(1),
                                     getUserAcctCursor.getString(2),
                                     getUserAcctCursor.getString(3),
                                     getUserAcctCursor.getString(4),
                                     getUserAcctCursor.getString(5));
            UserSessionData.SetLoggedInUser(userData);
            DB_GetUserServiceAddresses();
            Log.d("saved password", "pass = savedpass");
            return true;
        }
        checkForUserCursor.close();
        getUserAcctCursor.close();
        db.close();
        return false;
    }
    public void DB_GetUserServiceAddresses()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ServiceAddress> userAddresses = new ArrayList<>();
        String currentUsername = UserSessionData.GetLoggedInUser().getUser_username();
        String getUserAddress = "SELECT * FROM " + SERVICE_ADDRESSES_TABLE + " WHERE Username = '" + currentUsername + "';";
        Cursor userAddressCursor = db.rawQuery(getUserAddress, null);
        if(userAddressCursor.moveToFirst())
        {
            do
            {
                userAddresses.add(new ServiceAddress(
                        userAddressCursor.getInt(0),       // addressID
                        userAddressCursor.getString(1),    // username
                        userAddressCursor.getString(2),    // street address
                        userAddressCursor.getString(3),    // apt
                        userAddressCursor.getString(4),    // city
                        userAddressCursor.getString(5),    // state
                        userAddressCursor.getString(6),    // zipcode
                        userAddressCursor.getInt(7),       // polygonID
                        userAddressCursor.getDouble(8),    // total acreage
                        userAddressCursor.getInt(9),       // serviceId
                        userAddressCursor.getDouble(10),   // singleT
                        userAddressCursor.getDouble(11))); // seasonT
            }
            while(userAddressCursor.moveToNext());
        }
        UserSessionData.SetUserAddressData(userAddresses);
        UserSessionData.SetUserAddressCount(userAddresses.size());
        userAddressCursor.close();
        db.close();
//        return userAddresses;
    }
    public void DB_AddNewUser(User user) //String un, String p, String f, String l, String e, String pn)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addUser = new ContentValues();
        addUser.put("Username", user.getUser_username());
        addUser.put("Password", user.getUser_password());
        addUser.put("First_Name", user.getUser_fname());
        addUser.put("Last_Name", user.getUser_lname());
        addUser.put("Email", user.getUser_email());
        addUser.put("Phone_Number", user.getUser_phoneNumber());
        db.insert(USERS_TABLE, null, addUser);
        db.close();
    }

    public void DB_UpdateUserData(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateUser = new ContentValues();
        updateUser.put("First_Name", user.getUser_fname());
        updateUser.put("Last_Name", user.getUser_lname());
        updateUser.put("Email", user.getUser_email());
        updateUser.put("Phone_Number", user.getUser_phoneNumber());
        db.update(USERS_TABLE, updateUser, "Username = ?", new String[]{user.getUser_username()});
//        UserSessionData.SetLoggedInUser(user); // used to make sure new data has been push to SessionData // might not need later
    }

    public void DB_DeleteUserAccount(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + USERS_TABLE + " WHERE Username='" + user.getUser_username() + "';");
        UserSessionData.SetLoggedInUser(null);
        db.close();
    }
}
