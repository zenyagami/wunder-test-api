package com.zenkun.wunder.ui.mvp.presenter.impl;

import android.os.Bundle;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zenkun.wunder.Constant;
import com.zenkun.wunder.R;
import com.zenkun.wunder.helpers.HelperLocation;
import com.zenkun.wunder.helpers.HelperPreference;
import com.zenkun.wunder.model.Placemark;
import com.zenkun.wunder.ui.mvp.MvpPresenterBase;
import com.zenkun.wunder.ui.mvp.presenter.PresenterViewMap;
import com.zenkun.wunder.ui.mvp.view.ViewMap;
import com.zenkun.wunder.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class PresenterViewMapImpl extends MvpPresenterBase<ViewMap> implements PresenterViewMap {

    //hashmap to detect what marker was clicked...
    private ArrayList<Marker> markerList= new ArrayList<>();
    private boolean showOneMarker=false;

    @Override
    public void onMapReady() {
        //map ready, read data from...
        //TODO read data async for HUGE data...
        List<Placemark> list= HelperPreference.get().getMarkerList(Constant.KEY.PLACE_MARK);
        //if is empty... we need call maybe the  service again...
        if(list!=null && list.size()>0)
        {
            //maybe block the map or something until we finish....
            //TODO show markers based on the map screen, load many markers will lag..... too much lag
            int size =list.size();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i=0;i<size;i++)
            {
                Placemark placemark = list.get(i);

                if(placemark.getCoordinates()==null)
                {
                    Logger.d("null value "+placemark.getVin());
                    continue;
                }
                builder.include(placemark.getCoordinates());
                getView().loadMapMarker(new MarkerOptions()
                        .position(placemark.getCoordinates())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_car))
                        .title(placemark.getName()));
            }
            getView().updateZoomMap(builder);
        }


    }

    @Override
    public void addMarker(Marker marker) {
        markerList.add(marker);
    }

    @Override
    public void onMarkerClick(Marker marker) {
        //hide all otherMarkers, better than "clean" and add marker again :P
        marker.showInfoWindow();
        for (Marker current : markerList)
        {

            if(current.getId().equalsIgnoreCase(marker.getId()))
            {


                continue;
            }
            current.setVisible(showOneMarker);
        }
        showOneMarker=!showOneMarker;

    }

    @Override
    public void onDettach() {
        super.onDettach();
        markerList.clear();
    }
}
