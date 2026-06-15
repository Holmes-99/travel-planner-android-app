package com.example.a1231279_1230239_courseproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreManager {

    private static final String SHARED_PREF_NAME = "TravelAppSharedPref";
    private static SharedPreManager myInstance=null;
    private static SharedPreferences sharedPreferences=null;
    private SharedPreferences.Editor editor=null;
    private static SharedPreManager getInstance(Context context){
        if(myInstance==null){
            myInstance=new SharedPreManager(context);
        }
        return myInstance;
        }

        private SharedPreManager(Context context){
        sharedPreferences=context.getSharedPreferences(
                SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
    public boolean writeString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public int readInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public boolean writeInt(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean writeBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }




    }

