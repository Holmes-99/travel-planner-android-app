package com.example.a1231279_1230239_courseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }


    @Override //create all tables
        public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE USERS("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "FIRSTNAME TEXT,"+
                "LASTNAME TEXT,"+
                "MAJOR TEXT,"+
                "PHONE TEXT,"+
                "EMAIL TEXT,"+
                "PASSWORD TEXT,"+
                "GENDER TEXT,"+
                "ROLE TEXT,"+
                "PROFILEPIC TEXT)");
        db.execSQL("CREATE TABLE TRIPS("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "COUNTRY TEXT,"+
                "DESTINATION TEXT,"+
                "DURATIONDAYS INTEGER,"+
                "PRICE REAL,"+
                "RATING REAL,"+
                "DESCRIPTION TEXT,"+
                "IMAGE TEXT)");
        db.execSQL("CREATE TABLE RESERVATIONS("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "TRIPID INTEGER,"+
                "USERID INTEGER,"+
                "TYPE TEXT,"+
                "DATE TEXT,"+
                "STATUS TEXT,"+
                "TRIPDESTINATION TEXT,"+
                "QUANTITY INTEGER)");

        db.execSQL("CREATE TABLE FAVOURITES("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "TRIPID INTEGER,"+
                "USERID INTEGER)");


        //admin account
        ContentValues admin = new ContentValues();

        admin.put("FIRSTNAME","Admin");
        admin.put("LASTNAME","Admin");
        admin.put("EMAIL","admin@admin.com");
        admin.put("PASSWORD","Admin123!");
        admin.put("ROLE","admin");

        db.insert("USERS",null, admin);
    }

    public long insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FIRSTNAME",user.getFirstName());
        values.put("LASTNAME",user.getLastName());
        values.put("MAJOR",user.getMajor());
        values.put("ROLE","user");//alwayes saved as user -no one registers as admin-
        values.put("PHONE",user.getPhone());
        values.put("EMAIL",user.getEmail());
        values.put("GENDER",user.getGender());
        values.put("PASSWORD",user.getPassword());
        values.put("PROFILEPIC",user.getprofilepic());
        return db.insert("USERS",null,values);
    }

    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FIRSTNAME",user.getFirstName());
        values.put("LASTNAME",user.getLastName());
        values.put("PHONE",user.getPhone());
        values.put("EMAIL",user.getEmail());
        values.put("GENDER",user.getGender());
        values.put("PASSWORD",user.getPassword());
        values.put("PROFILEPIC",user.getprofilepic());
        return db.update("USERS",values,"ID=?",
                new String[]{
                        String.valueOf(user.getId())
        });

    }
    public int deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("USERS", "ID=?",
                new String[]{
                        String.valueOf(id)
                });
    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM USERS",null);

    }
    public Cursor getUserByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM USERS WHERE EMAIL=?",new String[]{email});
    }









