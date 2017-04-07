package com.zenkun.wunder.net;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.zenkun.wunder.utils.UtilLocation;

import java.lang.reflect.Type;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class LatLngDeserializer implements JsonDeserializer<LatLng> {
    @Override
    public LatLng deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null ||
                json.isJsonNull()) {
            return null;
        }
        //if json.isJsonArray() is from the restAPI
        if(json.isJsonArray()) {
            return UtilLocation.toLatLng(((JsonArray) json).get(1).getAsString(), ((JsonArray) json).get(0).getAsString());
        }
        else
        {
            //TODO change serialize/deserialize same as the Rest API
            //is deserializer from preferences.. because we are using same GSON...
            return UtilLocation.toLatLng(json.getAsString());
        }
    }
}
