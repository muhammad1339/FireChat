package com.prodev.firechat.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prodev.firechat.data.user.User;

import java.lang.reflect.Type;

public class PrefManager {

    public static void savePref(Object obj, Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<GenericObject<Object>>() {
        }.getType();
        GenericObject<Object> genericEducation = new GenericObject<>();
        genericEducation.setObject(obj);
        String eduJson = gson.toJson(genericEducation, type);
        sharedPreferences.edit().putString(key, eduJson).apply();
    }

    public static User getUserObject(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
        Type type = new TypeToken<GenericObject<User>>() {
        }.getType();
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString(key, "");
        GenericObject<User> userGenericObject = gson.fromJson(userJson, type);
        if (userGenericObject == null) {
            return null;
        }
        return userGenericObject.getObject();
    }

    public static void storeString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getStoredString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static void clearPref(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }
}
