package com.zenkun.wunder.helpers;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zenkun.wunder.net.LatLngDeserializer;
import com.zenkun.wunder.net.LatLngSerializer;

import java.util.Date;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class HelperGson {
    private Gson gson;
    private static HelperGson INSTANCE;

    public static HelperGson get() {
        return INSTANCE;
    }

    public static HelperGson init() {
        INSTANCE = new HelperGson();

        INSTANCE.gson = new GsonBuilder()
                .registerTypeAdapter(LatLng.class, new LatLngDeserializer())
                .registerTypeAdapter(LatLng.class, new LatLngSerializer())
                .create();

        return INSTANCE;
    }


    public Gson getGson() {
        return gson;
    }
}
