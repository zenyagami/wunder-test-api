package com.zenkun.wunder.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zenkun.wunder.Constant;
import com.zenkun.wunder.model.Placemark;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class HelperPreference {
    private static HelperPreference INSTANCE;
    private static Gson mGson;
    private SharedPreferences mSharedPreferences;

    public static HelperPreference get()
    {
        return INSTANCE;
    }
    public static HelperPreference init(Context context, Gson gson) {
        INSTANCE = new HelperPreference();
        INSTANCE.mSharedPreferences = context.getSharedPreferences(Constant.KEY.PREFS_FILENAME_GENERAL, Context.MODE_PRIVATE);
        mGson = gson;

        return INSTANCE;
    }
    public void save(String name, String value) {
        mSharedPreferences
                .edit()
                .putString(name, value)
                .apply();
    }
    public void saveObject(String name, Object value) {
        save(name, mGson.toJson(value));
    }
    //TODO generic save
    public List<Placemark> getMarkerList(String name)
    {
        return  getObject(name,new TypeToken<ArrayList<Placemark>>() {}.getType());
    }
    public <T> T getObject(String name, Type type) {
        String value = getString(name);
        if (!TextUtils.isEmpty(value)) {
            return mGson.fromJson(value, type);
        }
        return null;
    }
    public String getString(String name) {
        return mSharedPreferences.getString(name, null);
    }

}
