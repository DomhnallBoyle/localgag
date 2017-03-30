package com.example.domhnallboyle.localgag.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by DomhnallBoyle on 07/08/2016.
 */
public class DeviceStorage {

    public static void setValueSharedPreferences(Context context, String key, Object value)
    {
        SharedPreferences preferences = context.getSharedPreferences("Device Storage", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        if (value.getClass().equals(Integer.class))
        {
            editor.putInt(key, (Integer)value);
        }
        else
        {
            editor.putString(key, (String)value);
        }
        editor.apply();
    }

    public static int getIntegerSharedPreferences(Context context, String key, int defaultValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("Device Storage", Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    public static String getStringSharedPreferences(Context context, String key, String defaultValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("Device Storage", Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static void removeValueSharedPreferences(Context context, String key, Object value)
    {
        SharedPreferences preferences = context.getSharedPreferences("Device Storage", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

}
