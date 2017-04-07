package com.zenkun.wunder.utils;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class UtilLocation {

    public static String latLngToString(LatLng src) {
        if(src==null)return null;
        return src.latitude+","+src.longitude;
    }

    public static LatLng toLatLng(String latitude,String longitude) {
        if(TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude))return null;

        return new LatLng(Double.valueOf(latitude),Double.valueOf(longitude));
    }
    public static LatLng toLatLng(Location location) {
        if(location==null)return null;
        return new LatLng(location.getLatitude(),location.getLongitude());
    }
    public static LatLng toLatLng(String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        String[] pieces = value.split(",");
        float lat = Float.valueOf(pieces[0]);
        float lon = Float.valueOf(pieces[1]);
        return new LatLng(lat, lon);
    }
}
