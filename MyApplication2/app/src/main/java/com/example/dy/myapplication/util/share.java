package com.example.dy.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dy on 17-4-11.
 */

public class share {
    public static final String STORAGE_FILE_NAME = ".config";

    private static SharedPreferences getSharedPreferences(Context ctx){
        return ctx.getSharedPreferences(STORAGE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void putString(Context ctx, String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(ctx);
        sharedPreferences.edit().putString(key,value).commit();
    }
    public static void putBoolean(Context ctx, String key, boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences(ctx);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context ctx, String key, String... defaultValue){
        SharedPreferences sharedPreferences  = getSharedPreferences(ctx);
        String dv = "";
        for(String v:defaultValue){
            dv = v;
            break;
        }
        return sharedPreferences.getString(key,dv);
    }
    public static boolean getBoolean(Context ctx, String key, boolean... defaultValue){
        SharedPreferences sharedPreferences  = getSharedPreferences(ctx);
        boolean dv = false;
        for(boolean v:defaultValue){
            dv = v;
            break;
        }
        return sharedPreferences.getBoolean(key,dv);
    }
}
