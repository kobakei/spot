package io.github.kobakei.spot;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by keisukekobayashi on 16/03/31.
 */
public class PreferencesUtil {

    public static int getInt(Context context, String name, String key, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    public static void putInt(Context context, String name, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getString(Context context, String name, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static void putString(Context context, String name, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
