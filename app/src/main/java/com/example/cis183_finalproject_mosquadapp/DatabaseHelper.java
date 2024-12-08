package com.example.cis183_finalproject_mosquadapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        super(c, DATABASE_NAME, null, 32);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        DB_CreateTables(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
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
        DB_DummyPolygonData();
        DB_DummyReviewData();
        DB_PackagePriceData();
    }
    private void DB_CreateTables(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + POLYGON_DATA_TABLE + " (PolygonID integer primary key autoincrement not null," +
                " Polygon_Latitudes text not null, Polygon_Longitudes text not null);");

        db.execSQL("CREATE TABLE " + YARD_SERVICES_TABLE + " (ServiceID integer primary key autoincrement not null," +
                " Barrier_Treatment integer, All_Natural integer, Special_Event integer, Home_Shield integer, Fly_Control integer," +
                " Invader_Guard integer, Yard_Defender integer);");

        db.execSQL("CREATE TABLE " + SERVICE_ADDRESSES_TABLE + " (AddressID integer primary key autoincrement not null," +
                " Username varchar not null, Street_Address varchar not null, Apt varchar, City varchar not null, State varchar not null," +
                " ZipCode varchar not null, PolygonID integer, Total_Acreage double, ServiceID integer, Single_Treatment_Price double," +
                " Season_Treatment_Price double, foreign key (PolygonID) references " + POLYGON_DATA_TABLE + "(PolygonID)," +
                " foreign key (ServiceID) references " + YARD_SERVICES_TABLE + "(ServiceID));");

        db.execSQL("CREATE TABLE " + REVIEWS_TABLE + " (ReviewID integer primary key autoincrement not null," +
                " Star_Count varchar not null, Review_Text varchar, Review_Date varchar not null);");

        db.execSQL("CREATE TABLE " + PACKAGE_PRICES_TABLE + " (Package_Name varchar primary key not null," +
                " Price double not null);");

        db.execSQL("CREATE TABLE " + USERS_TABLE + " (Username varchar primary key not null, Password varchar not null," +
                " First_Name varchar not null, Last_Name varchar not null, Email varchar not null, Phone_Number varchar not null," +
                " ReviewID integer, foreign key (ReviewID) references " + REVIEWS_TABLE + "(ReviewID));");
    }
    // used to count the number of records in a given table //
    private void DB_DummyUserData()
    {
        if(DB_RecordCount(USERS_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert dummy user data //
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('mEbbs123', 'MikeEbbs123!!!', 'Mike', 'Ebbs', 'MEbbs@gmail.com', '262-255-2244', 1);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('AWhite93', 'AveWhite123!!!', 'Avery', 'White', 'AWhite93@runbox.com', '502-539-4469', 2);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('CMay27', 'CyrMay123!!!', 'Cyrus', 'May', 'CMay27@startmail.com', '839-890-7679', 3);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('AChambers9', 'AngChambers123!!!', 'Angela', 'Chambers', 'AChambers9@startmail.com', '929-549-8259', 4);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('WDuncan65', 'WayDuncan123!!!', 'Waylon', 'Duncan', 'WDuncan65@zoho.com', '347-502-6370', 5);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('CBradley68', 'CorBradley123!!!', 'Corey', 'Bradley', 'CBradley68@zoho.com', '726-995-2452', 6);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('CAllison3', 'CasAllison123!!!', 'Cassidy', 'Allison', 'CAllison3@posteo.com', '606-368-0162', 7);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('LPerry54', 'LogPerry123!!!', 'Logan', 'Perry', 'LPerry54@protonmail.com', '718-762-0340', 8);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('ARodgers45', 'AdrRodgers123!!!', 'Adrian', 'Rodgers', 'ARodgers45@hushmail.com', '629-320-4790', 9);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('AScott83', 'AxeScott123!!!', 'Axel', 'Scott', 'AScott83@mail.com', '785-364-8417', 10);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('LNorris99', 'LogNorris123!!!', 'Logan', 'Norris', 'LNorris99@gmail.com', '469-485-4357', 11);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('JTurner68', 'JacTurner123!!!', 'Jace', 'Turner', 'JTurner68@mailfence.com', '224-431-2903', 12);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('SRobertson65', 'SabRobertson123!!!', 'Sabrina', 'Robertson', 'SRobertson65@runbox.com', '347-648-2164', 13);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('TWeber55', 'TriWeber123!!!', 'Tripp', 'Weber', 'TWeber55@gmx.com', '267-686-5691', 14);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('ISweeney88', 'IsaSweeney123!!!', 'Isabella', 'Sweeney', 'ISweeney88@fastmail.com', '839-871-3555', 15);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('HGill71', 'HudGill123!!!', 'Hudson', 'Gill', 'HGill71@hey.com', '319-592-3310', 16);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('OJensen40', 'OpaJensen123!!!', 'Opal', 'Jensen', 'OJensen40@icloud.com', '270-852-6013', 17);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('ACurtis34', 'AlyCurtis123!!!', 'Alyssa', 'Curtis', 'ACurtis34@apple.com', '929-785-8468', 18);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('ZWallace66', 'ZekWallace123!!!', 'Zeke', 'Wallace', 'ZWallace66@mailfence.com', '901-700-6705', 19);");
            db.execSQL("INSERT INTO " + USERS_TABLE + " (Username, Password, First_Name, Last_Name, Email, Phone_Number, ReviewID) " +
                       "VALUES ('GWallace44', 'GavWallace123!!!', 'Gavin', 'Wallace', 'GWallace44@hey.com', '845-731-0746', 20);");
            db.close();

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
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,1,0,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,1,0,0,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,0,1,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (0,0,0,0,0,1,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,0,0,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,1,0,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,0,0,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (0,0,0,0,0,0,1);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,0,0,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,0,0,0,0,0);");
            db.execSQL("INSERT INTO " + YARD_SERVICES_TABLE +
                    " (Barrier_Treatment, Fly_Control, Home_Shield, Invader_Guard, Yard_Defender, All_Natural, Special_Event)" +
                    " VALUES (1,0,1,0,0,0,0);");
            db.close();
        }
    }
    private void DB_DummyServiceAddressData()
    {
        if(DB_RecordCount(SERVICE_ADDRESSES_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('mEbbs123', '13101  Wanty Rd', null, 'Milan', 'Michigan', '48160', 1, 2.0, 1, 139.00, 973.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('mEbbs123', '236 Austin Ct', null, 'Newport', 'Michigan', '48166', null, 0.25, 2, 154.00, 1078.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('AWhite93', '971 Lotus Dr', null, 'Erie', 'Michigan', '48133', null, 0.5, 3, 114.00, 798.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('CMay27', '517 Kilberry', null, 'Temperance', 'Michigan', '48182', null, 0.25, 4, 89.00, 623.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('AChambers9', '5204 State Line Rd', null, 'Ottawa Lake', 'Michigan', '49267', null, 1, 5, 99.00, 693.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('WDuncan65', '11660 Plank Rd', null, 'Milan', 'Michigan', '48160', null, 1, 6, 99.00, 693.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('CBradley68', '9150 Doty Rd', null, 'Maybee', 'Michigan', '48159', null, 1, 7, 194.00, 1358.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('CAllison3', '9014 Sumpter Rd', null, 'Maybee', 'Michigan', '48159', null, 1, 8, 99.00, 693.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('LPerry54', '8828 Sumpter Rd', null, 'Maybee', 'Michigan', '48159', null, 1, 9, 129.00, null);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('ARodgers45', '9160 Steffas Rd', null, 'Maybee', 'Michigan', '48159', null, 1.5, 10, 119.00, 833.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('AScott83', '1018 Steiner Rd', null, 'Monroe', 'Michigan', '48162', null, 2, 11, 139.00, 973.00);");
            db.execSQL("INSERT INTO " + SERVICE_ADDRESSES_TABLE +
                    " (Username, Street_Address, Apt, City, State, ZipCode, PolygonID, Total_Acreage, ServiceID, Single_Treatment_Price, Season_Treatment_Price) " +
                    "VALUES ('AScott83', '7820 Reinhardt Rd', null, 'Carleton', 'Michigan', '48117', null, 0.5, 12, 174.00, 1218.00);");
            db.close();
        }
    }
    private void DB_DummyPolygonData()
    {
        if(DB_RecordCount(POLYGON_DATA_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + POLYGON_DATA_TABLE +
                    " (Polygon_Latitudes, Polygon_Longitudes) VALUES " +
                    "('41.91844150762183,41.91767810095337,41.917830034577065,41.918666536210814'," +
                    " '-83.46886966377497,-83.46943795681,-83.4705862775445,-83.47018998116255')");
        }
    }
    private void DB_DummyReviewData()
    {
        if(DB_RecordCount(REVIEWS_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('4.0', 'Not a bug in sight for weeks', '2023-06-14');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('3.0', 'Exceptional service! The pest control team did an incredible job eliminating mosquitoes" +
                    " and ticks from our yard. We''ve noticed a huge improvement—no more bites or worries! Highly recommend!', '2022-07-10');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('2.0', 'Not worth the price', '2018-07-13');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('4.0', 'Amazing job by the pest control team! Our yard is now free from mosquitoes and ticks, making" +
                    " outdoor time enjoyable again. Couldn''t be happier with the results!', '2024-06-20');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('4.0', 'Thrilled with the service! Not a single tick or mosquito in sight. " +
                    "It''s been weeks, and our yard is still pest-free. Highly recommend to anyone dealing with bugs.', '2023-05-18');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('3.0', 'Some bugs but better than before.', '2021-06-20');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('2.0', 'Saw a reduction in pests initially, but within a few weeks, mosquitoes returned. Service " +
                    "was decent, but I expected a longer-lasting effect.', '2022-03-12');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('4.0', 'Happy with the results! The mosquitoes are almost gone, and we haven''t seen a tick since. " +
                    "Worth the investment for anyone struggling with these pests.', '2022-09-30');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('3.0', 'Good initial results, though a few bugs are starting to show up again. Overall, much better " +
                    "than before, but might need a follow-up treatment.', '2022-10-04');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('5.0', 'We have been using Mosquito Squad for two years now. They do an excellent job. Trustworthy, " +
                    "great customer service and fast efficient service. Will definitely use this service again.', '2023-11-22');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('4.0', 'Effective service. Haven''t noticed any ticks or mosquitoes since the treatment. Very happy " +
                    "with how it''s working so far.', '2023-08-15');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('5.0', 'Thank you Mosquito Squad, without your service we wouldn''t be able to enjoy our yard. This is our 3rd " +
                    "year using your service and we will be a retuning customer next year.', '2022-07-04');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('2.0', 'Could''ve done better', '2019-05-02');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('4.0', 'Great results! We''ve seen a huge improvement, with hardly any pests left. Considering regular " +
                    "treatments to keep it this way.', '2023-05-09');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('4.0', null, '2023-04-22');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('2.0', 'Effective initially, but pests slowly started coming back. Improvement was noticeable but expected " +
                    "a bit more lasting impact.', '2022-02-10');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('1.0', 'We still have mosquitoes and ticks in our yard. Very disappointed.', '2023-06-05');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('5.0', 'They did a great job!!!', '2021-08-12');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('5.0',null, '2020-07-11');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('5.0', 'No bugs to be found.', '2020-09-06');");
            db.close();
        }
    }
    private void DB_PackagePriceData()
    {
        if(DB_RecordCount(PACKAGE_PRICES_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Barrier_Treatment', 59.00);"); // starts at 1/4 at $59 at 1/2 at $79. after which $4 pre 1/10 acre
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('All_Natural', 59.00);"); // scales like barrier treatment
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Special_Event', 30.00);"); // add the barrier treatment price to this later in the code
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Home_Shield', 95.00);");
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Fly_Control', 35.00);");
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Invader_Guard', 30.00);");
            db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Yard_Defender', 30.00);");
//           db.execSQL("INSERT INTO " + PACKAGE_PRICES_TABLE + "(Package_Name, Price) VALUES ('Per_Quarter', 10.00)");
        }
    }
    public int DB_RecordCount(String tableName)
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
    public boolean DB_UsernameAlreadyExists(String u)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkForUsername = "SELECT username FROM " + USERS_TABLE + " WHERE username = ?";
        Cursor uexists_cursor = db.rawQuery(checkForUsername, new String[]{u});
        if(uexists_cursor.moveToFirst())
        {
            uexists_cursor.close();
            return true;
        }
        uexists_cursor.close();
        return false;
    }
    public boolean DB_CheckingForGoodUserLogin(String uname, String pass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String getUserAcctData = "SELECT * FROM " + USERS_TABLE + " WHERE Username = ?";
        String savedPassword = "";
        Cursor getUserAcctCursor = db.rawQuery(getUserAcctData, new String[]{uname});
        if(getUserAcctCursor.moveToFirst())
        {
            savedPassword = getUserAcctCursor.getString(1);
            if(pass.equals(savedPassword))
            {
                User userData = new User(getUserAcctCursor.getString(0),
                        getUserAcctCursor.getString(1),
                        getUserAcctCursor.getString(2),
                        getUserAcctCursor.getString(3),
                        getUserAcctCursor.getString(4),
                        getUserAcctCursor.getString(5),
                        getUserAcctCursor.getInt(6));
                UserSessionData.SetLoggedInUser(userData);
                DB_GetUserServiceAddresses();
                DB_GetUserReview();
                return true;
            }
        }
        getUserAcctCursor.close();
        db.close();
        return false;
    }
    public void DB_GetUserServiceAddresses()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ServiceAddress> userAddresses = new ArrayList<>();
        String currentUsername = UserSessionData.GetLoggedInUser().getUser_username();
        String getUserAddress = "SELECT * FROM " + SERVICE_ADDRESSES_TABLE + " WHERE Username = ?";
        // need to start using the new String[] method more to pass variables to help against sql injection //
        Cursor userAddressCursor = db.rawQuery(getUserAddress, new String[]{currentUsername});
        if(userAddressCursor.moveToFirst())
        {
            do
            {
                userAddresses.add(new ServiceAddress(
                        userAddressCursor.getInt(0),       // addressID
                        userAddressCursor.getString(1),    // username
                        userAddressCursor.getString(2),    // street address
                        userAddressCursor.getString(3),    // apt           // can be null
                        userAddressCursor.getString(4),    // city
                        userAddressCursor.getString(5),    // state
                        userAddressCursor.getString(6),    // zipcode
                        userAddressCursor.getInt(7),       // polygonID     // can be null
                        userAddressCursor.getDouble(8),    // total acreage // can be null
                        userAddressCursor.getInt(9),       // serviceId     // can be null
                        userAddressCursor.getDouble(10),   // singleT       // can be null
                        userAddressCursor.getDouble(11))); // seasonT       // can be null
            }
            while(userAddressCursor.moveToNext());
        }
        UserSessionData.SetUserAddressData(userAddresses);
        UserSessionData.SetUserAddressCount(userAddresses.size());
        userAddressCursor.close();
        db.close();
    }
    public User DB_GetUserFromReviewID(int reviewID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        User db_tempUser = new User();
        String getUserData = "SELECT * FROM " + USERS_TABLE + " WHERE ReviewID = ?";
        Cursor userData = db.rawQuery(getUserData, new String[]{String.valueOf(reviewID)});
        if(userData.moveToFirst())
        {
            db_tempUser.setUser_username(userData.getString(0));
            db_tempUser.setUser_password(userData.getString(1));
            db_tempUser.setUser_fname(userData.getString(2));
            db_tempUser.setUser_lname(userData.getString(3));
            db_tempUser.setUser_email(userData.getString(4));
            db_tempUser.setUser_phoneNumber(userData.getString(5));
            db_tempUser.setUser_reviewID(userData.getInt(6));
        }
        userData.close();
        db.close();
        return db_tempUser;
    }
    public void DB_AddNewUser(User user)
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
        db.close();
//        UserSessionData.SetLoggedInUser(user); // used to make sure new data has been push to SessionData // might not need later
    }
    public void DB_SaveNewUserPassword(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updatePass = new ContentValues();
        updatePass.put("Password", user.getUser_password());
        db.update(USERS_TABLE, updatePass, "Username = ?", new String[]{user.getUser_username()});
        db.close();
    }
    public void DB_DeleteUserAccount(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        DB_DeleteAllUserAddresses();
        if(user.getUser_reviewID() != 0)
        {
            DB_DeleteUserReview(user.getUser_reviewID(), user.getUser_username());
        }
        db.delete(USERS_TABLE, "Username = ?", new String[]{user.getUser_username()});
        db.close();
    }
    public ArrayList<UserReview> DB_GetListOfReviews(String sortStatement, String order, String aORd)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserReview> db_listOfReviews = new ArrayList<>();
        String getReviewList = "SELECT * FROM " + REVIEWS_TABLE + sortStatement + order + aORd;
        Cursor reviewList = db.rawQuery(getReviewList, null);
        if(reviewList.moveToFirst())
        {
            do
            {
                db_listOfReviews.add(new UserReview(reviewList.getInt(0),
                                                    reviewList.getString(1),
                                                    reviewList.getString(2),
                                                    reviewList.getString(3)));
            }
            while(reviewList.moveToNext());
        }
        reviewList.close();
        db.close();
        return db_listOfReviews;
    }
    public void DB_DeleteUserReview(int reviewID, String uname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues deleteFromUser = new ContentValues();
        String rID = String.valueOf(reviewID);
        deleteFromUser.putNull("ReviewID");
        db.update(USERS_TABLE, deleteFromUser, "Username = ?", new String[]{uname});
        db.delete(REVIEWS_TABLE, "ReviewID = ?", new String[]{rID});
        UserSessionData.SetLoggedInUserReview(null);
        UserSessionData.GetLoggedInUser().setUser_reviewID(0);

    }
    public void DB_AddNewReview(UserReview ur)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addUserReview = new ContentValues();
        addUserReview.put("Star_Count", ur.getUrv_starCount());
        addUserReview.put("Review_Text", ur.getUrv_reviewText());
        addUserReview.put("Review_Date", ur.getUrv_reviewDate());
        db.insert(REVIEWS_TABLE, null, addUserReview);
        DB_AddReviewIDToUserData();
        DB_GetUserReview();
        db.close();

    }
    public void DB_AddReviewIDToUserData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addReviewIDToUser = new ContentValues();
        int reviewId = DB_GetNewestReview().getUrv_reviewID();
        addReviewIDToUser.put("ReviewID", reviewId);
        db.update(USERS_TABLE, addReviewIDToUser, "Username = ?", new String[]{UserSessionData.GetLoggedInUser().getUser_username()});
        UserSessionData.GetLoggedInUser().setUser_reviewID(reviewId);
        db.close();
    }
    private UserReview DB_GetNewestReview()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        UserReview db_tempReview = new UserReview();
        String selectLastReviewID = "SELECT * FROM " + REVIEWS_TABLE + " ORDER BY ReviewID DESC LIMIT 1;";
        Cursor getReviewID = db.rawQuery(selectLastReviewID, null);
        if(getReviewID.moveToFirst())
        {
            db_tempReview.setUrv_reviewID(getReviewID.getInt(0));
            db_tempReview.setUrv_starCount(getReviewID.getString(1));
            db_tempReview.setUrv_reviewText(getReviewID.getString(2));
            db_tempReview.setUrv_reviewDate(getReviewID.getString(3));
        }
        getReviewID.close();
        return db_tempReview;
    }
    public void DB_GetUserReview()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int reviewID = UserSessionData.GetLoggedInUser().getUser_reviewID();
        UserReview db_userReview = new UserReview();
        String selectReviewID = "SELECT * FROM " + REVIEWS_TABLE + " WHERE ReviewID = '" + reviewID + "';";
        Cursor userReview = db.rawQuery(selectReviewID, null);
        if(userReview.moveToFirst())
        {
            db_userReview.setUrv_reviewID(userReview.getInt(0));
            db_userReview.setUrv_starCount(userReview.getString(1));
            if(userReview.getString(2) == null)
            {
                db_userReview.setUrv_reviewText(null);
            }
            else
            {
                db_userReview.setUrv_reviewText(userReview.getString(2));
            }
            db_userReview.setUrv_reviewDate(userReview.getString(3));
        }
        else
        {
            db_userReview = null;
        }
        UserSessionData.SetLoggedInUserReview(db_userReview);
        userReview.close();
        db.close();
    }
    public void DB_UpdateUserReview(UserReview ur)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateUserReview = new ContentValues();
        updateUserReview.put("Star_Count", ur.getUrv_starCount());
        if(ur.getUrv_reviewText() != null)
        {
            updateUserReview.put("Review_Text", ur.getUrv_reviewText());
        }
        db.update(REVIEWS_TABLE, updateUserReview, "ReviewID = ?", new String[]{String.valueOf(ur.getUrv_reviewID())});
        db.close();
    }
    public float DB_GetRatingTotalSum()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String ratingTotalSum = "SELECT Star_Count From " + REVIEWS_TABLE;
        String tempStarCount;
        float totalSum = 0;
        Cursor db_sumCursor = db.rawQuery(ratingTotalSum, null);
        if(db_sumCursor.moveToFirst())
        {
            do
            {
                tempStarCount = db_sumCursor.getString(0);
                totalSum += Float.parseFloat(tempStarCount);
            }
            while(db_sumCursor.moveToNext());
        }
        db_sumCursor.close();
        db.close();
        return totalSum;
    }
    public void DB_AddNewUserAddress(ServiceAddress sa)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addNewAddress = new ContentValues();
        addNewAddress.put("Username", UserSessionData.GetLoggedInUser().getUser_username());
        addNewAddress.put("Street_Address", sa.getSa_streetAddress());
        if(sa.getSa_apt() != null)
        {
            addNewAddress.put("Apt", sa.getSa_apt());
        }
        addNewAddress.put("City", sa.getSa_city());
        addNewAddress.put("State", sa.getSa_state());
        addNewAddress.put("ZipCode", sa.getSa_zipCode());
        db.insert(SERVICE_ADDRESSES_TABLE,null,addNewAddress);
        db.close();
        UserSessionData.SetPassedServiceAddress(DB_GetNewestAddress());
        DB_GetUserServiceAddresses();
    }
    public void DB_UpdateUserAddress(ServiceAddress sa)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updatedAddress = new ContentValues();
        updatedAddress.put("Username", UserSessionData.GetLoggedInUser().getUser_username());
        updatedAddress.put("Street_Address", sa.getSa_streetAddress());
        if(sa.getSa_apt() != null)
        {
            updatedAddress.put("Apt", sa.getSa_apt());
        }
        updatedAddress.put("City", sa.getSa_city());
        updatedAddress.put("State", sa.getSa_state());
        updatedAddress.put("ZipCode", sa.getSa_zipCode());
        db.update(SERVICE_ADDRESSES_TABLE, updatedAddress, "AddressID = ?", new String[]{String.valueOf(sa.getSa_addressID())});
        db.close();
        DB_GetUserServiceAddresses();
    }
    public void DB_DeleteUserAddress(ServiceAddress sa)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SERVICE_ADDRESSES_TABLE,"AddressID = ?", new String[]{String.valueOf(sa.getSa_addressID())});
        db.close();
        if(sa.getSa_polygonID() != 0)
        {
            DB_DeletePolygonData(String.valueOf(sa.getSa_polygonID()));
        }
        if(sa.getSa_serviceID() != 0)
        {
            DB_DeleteYardServiceData(String.valueOf(sa.getSa_serviceID()));
        }
        DB_GetUserServiceAddresses();
    }
    private void DB_DeleteAllUserAddresses()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ServiceAddress> listOfAddresses = UserSessionData.GetUserAddressData();
        String uname = UserSessionData.GetLoggedInUser().getUser_username();
        for(ServiceAddress sa : listOfAddresses)
        {
            if(sa.getSa_polygonID() != 0)
            {
                DB_DeletePolygonData(String.valueOf(sa.getSa_polygonID()));
            }
            if(sa.getSa_serviceID() != 0)
            {
                DB_DeleteYardServiceData(String.valueOf(sa.getSa_serviceID()));
            }
        }
        db.delete(SERVICE_ADDRESSES_TABLE, "Username = ?", new String[]{uname});
    }
    public UserPolygon DB_GetUserPolygonData(String polygonID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectPolygon = "SELECT * FROM " + POLYGON_DATA_TABLE + " WHERE PolygonID = ?";
        UserPolygon db_userPolygon = new UserPolygon();
        Cursor polygonCursor = db.rawQuery(selectPolygon, new String[]{polygonID});
        if(polygonCursor.moveToFirst())
        {
            db_userPolygon.setUp_polygonID(polygonCursor.getInt(0));
            db_userPolygon.setUp_polygonLats(polygonCursor.getString(1));
            db_userPolygon.setUp_polygonLngs(polygonCursor.getString(2));
        }
        else
        {
            db_userPolygon = null;
        }
        polygonCursor.close();
        db.close();
        return db_userPolygon;
    }
    public void DB_AddNewPolygon(UserPolygon upoly)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addUserPolygon = new ContentValues();
        addUserPolygon.put("Polygon_Latitudes", upoly.getUp_polygonLats());
        addUserPolygon.put("Polygon_Longitudes", upoly.getUp_polygonLngs());
        db.insert(POLYGON_DATA_TABLE, null, addUserPolygon);
        db.close();
        DB_AddPolyIDToAddressData();
        DB_GetUserServiceAddresses();
    }
    private void DB_AddPolyIDToAddressData()
    {
        int aID = UserSessionData.GetPassedServiceAddress().getSa_addressID();
        int pID = DB_GetNewestPolygon().getUp_polygonID();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addPolyIDToUser = new ContentValues();
        addPolyIDToUser.put("PolygonID", pID);
        db.update(SERVICE_ADDRESSES_TABLE, addPolyIDToUser, "AddressID = ?", new String[]{String.valueOf(aID)});
        db.close();
        UserSessionData.SetPassedServiceAddress(DB_GetServiceAddressByID(aID));
    }
    private ServiceAddress DB_GetNewestAddress()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ServiceAddress db_tempAddress = new ServiceAddress();
        String newAddress = "SELECT * FROM " + SERVICE_ADDRESSES_TABLE + " ORDER BY AddressID DESC LIMIT 1;";
        Cursor getAddress = db.rawQuery(newAddress, null);
        if(getAddress.moveToFirst())
        {
            db_tempAddress.setSa_addressID(getAddress.getInt(0));          // addressID
            db_tempAddress.setSa_username(getAddress.getString(1));        // username
            db_tempAddress.setSa_streetAddress(getAddress.getString(2));   // street address
            db_tempAddress.setSa_apt(getAddress.getString(3));             // apt           // can be null
            db_tempAddress.setSa_city(getAddress.getString(4));            // city
            db_tempAddress.setSa_state(getAddress.getString(5));           // state
            db_tempAddress.setSa_zipCode(getAddress.getString(6));         // zipcode
            db_tempAddress.setSa_polygonID(getAddress.getInt(7));          // polygonID     // can be null
            db_tempAddress.setSa_totalAcreage(getAddress.getDouble(8));    // total acreage // can be null
            db_tempAddress.setSa_serviceID(getAddress.getInt(9));          // serviceId     // can be null
            db_tempAddress.setSa_singleTreatment(getAddress.getDouble(10));// singleT       // can be null
            db_tempAddress.setSa_seasonTreatment(getAddress.getDouble(11));// seasonT       // can be null
        }
        getAddress.close();
        db.close();
        return db_tempAddress;
    }
    private ServiceAddress DB_GetServiceAddressByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ServiceAddress db_tempAddress = new ServiceAddress();
        String newAddress = "SELECT * FROM " + SERVICE_ADDRESSES_TABLE + " WHERE AddressID = ?;";
        Cursor getAddress = db.rawQuery(newAddress, new String[]{String.valueOf(UserSessionData.GetPassedServiceAddress().getSa_addressID())});
        if(getAddress.moveToFirst())
        {
            db_tempAddress.setSa_addressID(getAddress.getInt(0));          // addressID
            db_tempAddress.setSa_username(getAddress.getString(1));        // username
            db_tempAddress.setSa_streetAddress(getAddress.getString(2));   // street address
            db_tempAddress.setSa_apt(getAddress.getString(3));             // apt           // can be null
            db_tempAddress.setSa_city(getAddress.getString(4));            // city
            db_tempAddress.setSa_state(getAddress.getString(5));           // state
            db_tempAddress.setSa_zipCode(getAddress.getString(6));         // zipcode
            db_tempAddress.setSa_polygonID(getAddress.getInt(7));          // polygonID     // can be null
            db_tempAddress.setSa_totalAcreage(getAddress.getDouble(8));    // total acreage // can be null
            db_tempAddress.setSa_serviceID(getAddress.getInt(9));          // serviceId     // can be null
            db_tempAddress.setSa_singleTreatment(getAddress.getDouble(10));// singleT       // can be null
            db_tempAddress.setSa_seasonTreatment(getAddress.getDouble(11));// seasonT       // can be null
        }
        getAddress.close();
        db.close();
        return db_tempAddress;
    }
    private UserPolygon DB_GetNewestPolygon()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        UserPolygon db_tempPolygon = new UserPolygon();
        String newPolygon = "SELECT * FROM " + POLYGON_DATA_TABLE + " ORDER BY PolygonID DESC LIMIT 1;";
        Cursor getPolygon = db.rawQuery(newPolygon, null);
        if(getPolygon.moveToFirst())
        {
            db_tempPolygon.setUp_polygonID(getPolygon.getInt(0));
            db_tempPolygon.setUp_polygonLats(getPolygon.getString(1));
            db_tempPolygon.setUp_polygonLngs(getPolygon.getString(2));
        }
        getPolygon.close();
        db.close();
        return db_tempPolygon;
    }
    public void DB_UpdatePolygonData(UserPolygon upoly)
    {
        int pID = UserSessionData.GetPassedServiceAddress().getSa_polygonID();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateUserPloygon = new ContentValues();
        updateUserPloygon.put("Polygon_Latitudes", upoly.getUp_polygonLats());
        updateUserPloygon.put("Polygon_Longitudes", upoly.getUp_polygonLngs());
        db.update(POLYGON_DATA_TABLE, updateUserPloygon, "PolygonID = ?", new String[]{String.valueOf(pID)});
        db.close();
    }
    private void DB_DeletePolygonData(String polygonID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POLYGON_DATA_TABLE, "PolygonID = ?", new String[]{polygonID});
    }
    public void DB_UpdateTotalAcreage(double acreage)
    {
        int aID = UserSessionData.GetPassedServiceAddress().getSa_addressID();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addAcreage = new ContentValues();
        addAcreage.put("Total_Acreage", acreage);
        db.update(SERVICE_ADDRESSES_TABLE, addAcreage, "AddressID = ?", new String[]{String.valueOf(aID)});
        db.close();
        DB_GetUserServiceAddresses();
    }
    public void DB_AddNewYardServicesData(YardServices ysData)
    {
        // set to 0 for false and 1 for true //
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addNewYardData = new ContentValues();
        addNewYardData.put("Barrier_Treatment", ysData.getBarrierTreatment());
        addNewYardData.put("All_Natural", ysData.getAllNatural());
        addNewYardData.put("Special_Event", ysData.getSpecialEvent());
        addNewYardData.put("Home_Shield", ysData.getHomeShield());
        addNewYardData.put("Fly_Control", ysData.getFlyControl());
        addNewYardData.put("Invader_Guard", ysData.getInvaderGuard());
        addNewYardData.put("Yard_Defender", ysData.getYardDefender());
        db.insert(YARD_SERVICES_TABLE, null, addNewYardData);
        db.close();
        DB_AddServiceIDtoAddressData();
        DB_GetUserServiceAddresses();
    }
    public void DB_UpdateYardServicesData(YardServices ysData)
    {
        int sID = UserSessionData.GetPassedServiceAddress().getSa_serviceID();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateYardServices = new ContentValues();
        updateYardServices.put("Barrier_Treatment", ysData.getBarrierTreatment());
        updateYardServices.put("All_Natural", ysData.getAllNatural());
        updateYardServices.put("Special_Event", ysData.getSpecialEvent());
        updateYardServices.put("Home_Shield", ysData.getHomeShield());
        updateYardServices.put("Fly_Control", ysData.getFlyControl());
        updateYardServices.put("Invader_Guard", ysData.getInvaderGuard());
        updateYardServices.put("Yard_Defender", ysData.getYardDefender());
        db.update(YARD_SERVICES_TABLE, updateYardServices, "ServiceID = ?", new String[]{String.valueOf(sID)});
        db.close();
    }
    public YardServices DB_GetNewestServicesData()
    {
        SQLiteDatabase db = getReadableDatabase();
        YardServices db_tempServices = new YardServices();
        String newYardServices = "SELECT * FROM " + YARD_SERVICES_TABLE + " ORDER BY ServiceID DESC LIMIT 1;";
        Cursor getServices = db.rawQuery(newYardServices, null);
        if(getServices.moveToFirst())
        {
            db_tempServices.setServiceID(getServices.getInt(0));
            db_tempServices.setBarrierTreatment(getServices.getInt(1));
            db_tempServices.setAllNatural(getServices.getInt(2));
            db_tempServices.setSpecialEvent(getServices.getInt(3));
            db_tempServices.setHomeShield(getServices.getInt(4));
            db_tempServices.setFlyControl(getServices.getInt(5));
            db_tempServices.setInvaderGuard(getServices.getInt(6));
            db_tempServices.setYardDefender(getServices.getInt(7));
        }
        getServices.close();
        db.close();
        return db_tempServices;
    }
    public YardServices DB_GetYardServiceData(String sID)
    {
        SQLiteDatabase db = getReadableDatabase();
        YardServices db_tempServices = new YardServices();
        String newYardServices = "SELECT * FROM " + YARD_SERVICES_TABLE + " Where ServiceID = ?";
        Cursor getServices = db.rawQuery(newYardServices, new String[]{sID});
        if(getServices.moveToFirst())
        {
            db_tempServices.setServiceID(getServices.getInt(0));
            db_tempServices.setBarrierTreatment(getServices.getInt(1));
            db_tempServices.setAllNatural(getServices.getInt(2));
            db_tempServices.setSpecialEvent(getServices.getInt(3));
            db_tempServices.setHomeShield(getServices.getInt(4));
            db_tempServices.setFlyControl(getServices.getInt(5));
            db_tempServices.setInvaderGuard(getServices.getInt(6));
            db_tempServices.setYardDefender(getServices.getInt(7));
        }
        getServices.close();
        db.close();
        return db_tempServices;
    }
    private void DB_AddServiceIDtoAddressData()
    {
        int aID = UserSessionData.GetPassedServiceAddress().getSa_addressID();
        int sID = DB_GetNewestServicesData().getServiceID();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues addServiceID = new ContentValues();
        addServiceID.put("ServiceID", sID);
        db.update(SERVICE_ADDRESSES_TABLE, addServiceID, "AddressID = ?", new String[]{String.valueOf(aID)});
        db.close();
        UserSessionData.SetPassedServiceAddress(DB_GetServiceAddressByID(aID));
    }
    private void DB_DeleteYardServiceData(String sID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(YARD_SERVICES_TABLE, "ServiceID = ?", new String[]{sID});
    }
    public ArrayList<PackagePrice> DB_GetPackagePrices()
    {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PackagePrice> db_tempPackageList = new ArrayList<>();
        String newPackagePrice = "SELECT * FROM " + PACKAGE_PRICES_TABLE;
        Cursor getPackegePrice = db.rawQuery(newPackagePrice, null);
        if(getPackegePrice.moveToFirst())
        {
            do
            {
                db_tempPackageList.add(new PackagePrice(getPackegePrice.getString(0),
                                                        getPackegePrice.getDouble(1)));
            }
            while(getPackegePrice.moveToNext());
        }
        getPackegePrice.close();
        db.close();
        return db_tempPackageList;
    }
    public void DB_AddEstimateTotals(double single, double season)
    {
        int aID = UserSessionData.GetPassedServiceAddress().getSa_addressID();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addTotals = new ContentValues();
        addTotals.put("Single_Treatment_Price", single);
        addTotals.put("Season_Treatment_Price", season);
        db.update(SERVICE_ADDRESSES_TABLE, addTotals, "AddressID = ?", new String[]{String.valueOf(aID)});
        db.close();
        UserSessionData.SetPassedServiceAddress(DB_GetServiceAddressByID(aID));
        DB_GetUserServiceAddresses();
    }
}
