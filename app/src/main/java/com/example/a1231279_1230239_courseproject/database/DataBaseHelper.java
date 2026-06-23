package com.example.a1231279_1230239_courseproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    private String encryptPassword(String password) {
        try {
            java.security.MessageDigest md =
                    java.security.MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            return password;
        }
    }
    public DataBaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TRIPS");
        db.execSQL("DROP TABLE IF EXISTS FAVOURITES");
        db.execSQL("DROP TABLE IF EXISTS RESERVATIONS");
        db.execSQL("DROP TABLE IF EXISTS USERS");
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE USERS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIRSTNAME TEXT," +
                "LASTNAME TEXT," +
                "MAJOR TEXT," +
                "PHONE TEXT," +
                "EMAIL TEXT," +
                "PASSWORD TEXT," +
                "GENDER TEXT," +
                "ROLE TEXT," +
                "PROFILEPIC TEXT)");
        db.execSQL("CREATE TABLE TRIPS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "COUNTRY TEXT," +
                "DESTINATION TEXT NOT NULL," +
                "DURATIONDAYS INTEGER," +
                "PRICE REAL," +
                "RATING REAL," +
                "DESCRIPTION TEXT," +
                "IMAGE TEXT,"+
                "UNIQUE(COUNTRY, DESTINATION))");
        db.execSQL("CREATE TABLE RESERVATIONS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TRIPID INTEGER," +
                "USERID INTEGER," +
                "TYPE TEXT," +
                "DATE TEXT," +
                "STATUS TEXT," +
                "TRIPDESTINATION TEXT," +
                "QUANTITY INTEGER)");

        db.execSQL("CREATE TABLE FAVOURITES(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TRIPID INTEGER," +
                "USERID INTEGER)");

        ContentValues admin = new ContentValues();

        admin.put("FIRSTNAME", "Admin");
        admin.put("LASTNAME", "Admin");
        admin.put("EMAIL", "admin@admin.com");
        admin.put("PASSWORD", encryptPassword("Admin123!"));
        admin.put("ROLE", "admin");

        db.insert("USERS", null, admin);
    }

    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FIRSTNAME", user.getFirstName());
        values.put("LASTNAME", user.getLastName());
        values.put("MAJOR", user.getMajor());

        //save user role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            values.put("ROLE", "user");
        } else {
            values.put("ROLE", user.getRole());
        }        values.put("PHONE", user.getPhone());
        values.put("EMAIL", user.getEmail());
        values.put("GENDER", user.getGender());
        values.put("PASSWORD", user.getPassword());
        values.put("PROFILEPIC", user.getprofilepic());
        return db.insert("USERS", null, values);
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FIRSTNAME", user.getFirstName());
        values.put("LASTNAME", user.getLastName());
        values.put("PHONE", user.getPhone());
        values.put("EMAIL", user.getEmail());
        values.put("GENDER", user.getGender());
        values.put("PASSWORD", user.getPassword());
        values.put("PROFILEPIC", user.getprofilepic());
        return db.update("USERS", values, "ID=?",
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

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM USERS", null);

    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM USERS WHERE EMAIL=?", new String[]{email});
    }

    //trips
    public long insertTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("COUNTRY", trip.getCountry());
        values.put("DESTINATION", trip.getDestination());
        values.put("DURATIONDAYS", trip.getDurationDays());
        values.put("PRICE", trip.getPrice());
        values.put("RATING", trip.getRating());
        values.put("DESCRIPTION", trip.getDescription());
        values.put("IMAGE", trip.getImage());
       return  db.insertWithOnConflict("TRIPS", null, values, SQLiteDatabase.CONFLICT_IGNORE);

    }

    public int updateTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("COUNTRY", trip.getCountry());
        values.put("DESTINATION", trip.getDestination());
        values.put("DURATIONDAYS", trip.getDurationDays());
        values.put("PRICE", trip.getPrice());
        values.put("RATING", trip.getRating());
        values.put("DESCRIPTION", trip.getDescription());
        values.put("IMAGE", trip.getImage());

        return db.update(
                "TRIPS",
                values,
                "ID = ?",
                new String[]{String.valueOf(trip.getId())}
        );
    }

    public int deleteTrip(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("TRIPS", "ID=?",
                new String[]{
                        String.valueOf(id)
                });
    }

    public Cursor getAllTrips() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM TRIPS", null);
    }



    //reservations

    public long insertReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TRIPID", reservation.getTripId());
        values.put("USERID", reservation.getUserId());
        values.put("TYPE", reservation.getType());
        values.put("DATE", reservation.getDate());
        values.put("STATUS", reservation.getStatus());
        values.put("TRIPDESTINATION", reservation.getTripDestination());
        values.put("QUANTITY", reservation.getQuantity());
        return db.insert("RESERVATIONS", null, values);

    }


    public Cursor getReservationByUserId(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM RESERVATIONS WHERE USERID=?",
                new String[]{
                        String.valueOf(userId)});

    }

    public Cursor getAllReservations(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM RESERVATIONS",null);

    }



    //favourites

    public long insertFavourite(int userId, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USERID", userId);
        values.put("TRIPID", tripId);
        return db.insert("FAVOURITES", null, values);

    }
    public boolean isFavourite(int userId, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FAVOURITES WHERE USERID=? AND TRIPID=?",
                new String[]{
                        String.valueOf(userId),
                        String.valueOf(tripId)
                });
        boolean isFavourite = cursor.moveToFirst();
        cursor.close();
        return isFavourite;
    }

    public int deleteFavourite(int userId, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("FAVOURITES", "USERID=? AND TRIPID=?",
                new String[]{
                        String.valueOf(userId),
                        String.valueOf(tripId)
                });
    }

    public Cursor getAllFavourites() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM FAVOURITES", null);

    }

}









