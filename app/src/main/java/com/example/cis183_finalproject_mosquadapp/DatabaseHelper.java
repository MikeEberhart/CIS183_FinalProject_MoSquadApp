package com.example.cis183_finalproject_mosquadapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

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
        super(c, DATABASE_NAME, null, 6);
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
        DB_DummyReviewData();
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
                " Total_Acreage double, ServiceID integer, Single_Treatment_Price double, Season_Treatment_Price double," +
                " foreign key (Username) references " + USERS_TABLE + "(Username), foreign key (PolygonID) references " +
                POLYGON_DATA_TABLE + "(PolygonID), foreign key (ServiceID) references " + YARD_SERVICES_TABLE + "(ServiceID));");

        db.execSQL("CREATE TABLE " + REVIEWS_TABLE + " (ReviewID integer primary key autoincrement not null," +
                " Username varchar not null, Star_Count float not null, Review_Text varchar not null, Review_Date varchar not null," +
                " foreign key (Username) references " + USERS_TABLE + "(Username));");

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
                       "VALUES ('mEbbs123', '13101  Wanty Rd', null, 'Milan', 'MI', '48160', null, null, 1, 123.323, 1223.233);");
            db.close();
        }
    }
    private void DB_DummyReviewData()
    {
        if(DB_RecordCount(REVIEWS_TABLE) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                       "VALUES ('mEbbs123', 4.5, 'Not a bug in sight for weeks', '06-14-2023');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('AWhite93', 3.5, 'Exceptional service! The pest control team did an incredible job eliminating mosquitoes" +
                    " and ticks from our yard. We''ve noticed a huge improvement—no more bites or worries! Highly recommend!', '07-10-2022');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('CMay27', 4.0, 'Amazing job by the pest control team! Our yard is now free from mosquitoes and ticks, making" +
                    " outdoor time enjoyable again. Couldn''t be happier with the results!', '06-20-2023');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('AChambers9', 4.5, 'Thrilled with the service! Not a single tick or mosquito in sight. " +
                    "It''s been weeks, and our yard is still pest-free. Highly recommend to anyone dealing with bugs.', '05-18-2023');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('WDuncan65', 2.0, 'Saw a reduction in pests initially, but within a few weeks, mosquitoes returned. Service " +
                    "was decent, but I expected a longer-lasting effect.', '03-12-2022');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('CBradley68', 4.0, 'Happy with the results! The mosquitoes are almost gone, and we haven''t seen a tick since. " +
                    "Worth the investment for anyone struggling with these pests.', '09-30-2022');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('CAllison3', 3.5, 'Good initial results, though a few bugs are starting to show up again. Overall, much better " +
                    "than before, but might need a follow-up treatment.', '10-04-2022');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('LPerry54', 5.0, 'We have been using Mosquito Squad for two years now. They do an excellent job. Trustworthy, " +
                    "great customer service and fast efficient service. Will definitely use this service again.', '11-22-2023');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('ARodgers45', 4.0, 'Effective service. Haven''t noticed any ticks or mosquitoes since the treatment. Very happy " +
                    "with how it''s working so far.', '08-15-2023');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('AScott83', 5.0, 'Thank you Mosquito Squad, without your service we wouldn’t be able to enjoy our yard. This is our 3rd " +
                    "year using your service and we will be a retuning customer next year.', '07-04-2022');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('LNorris99', 4.0, 'Great results! We''ve seen a huge improvement, with hardly any pests left. Considering regular " +
                    "treatments to keep it this way.', '05-09-2023');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('JTurner68', 4.5, 'Fantastic! Ticks and mosquitoes are gone as promised. The treatment exceeded our expectations, " +
                    "and we plan to continue with it.', '04-22-2023');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('SRobertson65', 2.5, 'Effective initially, but pests slowly started coming back. Improvement was noticeable but expected " +
                    "a bit more lasting impact.', '02-10-2022');");
            db.execSQL("INSERT INTO " + REVIEWS_TABLE + " (Username, Star_Count, Review_Text, Review_Date) " +
                    "VALUES ('TWeber55', 1.5, 'Unfortunately, the service didn''t work well for us. We still have mosquitoes and ticks in our yard. " +
                    "Very disappointed given the cost of the treatment.', '06-05-2023');");

            db.close();
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
        String getUserAcctData = "SELECT * FROM " + USERS_TABLE + " WHERE Username = '" + uname + "';";
        String getUserPassword = "SELECT Password FROM " + USERS_TABLE + " WHERE Username = '" + uname + "';";
        String savedPassword = "";
        Cursor getUserPassCursor = db.rawQuery(getUserPassword, null);
        Cursor getUserAcctCursor = db.rawQuery(getUserAcctData, null);
        if(getUserAcctCursor.moveToFirst())
        {
            if(getUserPassCursor.moveToFirst())
            {
                savedPassword = getUserPassCursor.getString(0);
                Log.d("good password", savedPassword);
            }
            if(pass.equals(savedPassword))
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
        }
        getUserPassCursor.close();
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
    public String DB_GetUserData(String uname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        User db_tempUser = new User();
        String getUserData = "SELECT First_Name, Last_Name FROM " + USERS_TABLE + " WHERE Username = '" + uname + "';";
        String fname = "";
        String lname = "";
        Cursor userData = db.rawQuery(getUserData, null);
        if(userData.moveToFirst())
        {
            fname = userData.getString(0);
            lname = userData.getString(1);
        }
        userData.close();
        db.close();
        return fname + " " + lname.charAt(0) + ".";
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
        db.execSQL("DELETE FROM " + USERS_TABLE + " WHERE Username='" + user.getUser_username() + "';");
        UserSessionData.SetLoggedInUser(null);
        db.close();
    }
    public ArrayList<UserReview> DB_GetListOfReviews()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserReview> db_listOfReviews = new ArrayList<>();
        String getReviewList = "SELECT * FROM " + REVIEWS_TABLE;
        Cursor reviewList = db.rawQuery(getReviewList, null);
        if(reviewList.moveToFirst())
        {
            do
            {
                db_listOfReviews.add(new UserReview(reviewList.getInt(0),
                                                    reviewList.getString(1),
                                                    reviewList.getFloat(2),
                                                    reviewList.getString(3),
                                                    reviewList.getString(4)));
            }
            while(reviewList.moveToNext());
        }
        reviewList.close();
        db.close();
        return db_listOfReviews;
    }
}
