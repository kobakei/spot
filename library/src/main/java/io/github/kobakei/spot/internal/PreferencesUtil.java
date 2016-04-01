package io.github.kobakei.spot.internal;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by keisukekobayashi on 16/03/31.
 */
public final class PreferencesUtil {

    public static int getInt(Context context, String name, String key, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    public static void putInt(Context context, String name, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String name, String key, long defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getLong(key, defValue);
    }

    public static void putLong(Context context, String name, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String name, String key, float defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defValue);
    }

    public static void putFloat(Context context, String name, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defValue);
    }

    public static void putBoolean(Context context, String name, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(Context context, String name, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static void putString(Context context, String name, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static Set<String> getStringSet(Context context, String name, String key, Set<String> defValue) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getStringSet(key, defValue);
    }

    public static void putStringSet(Context context, String name, String key, Set<String> value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }
}
