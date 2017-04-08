package com.zenkun.wunder.ui.mvp.presenter.impl;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zenkun.wunder.Constant;
import com.zenkun.wunder.R;
import com.zenkun.wunder.helpers.HelperPreference;
import com.zenkun.wunder.model.Placemark;
import com.zenkun.wunder.ui.mvp.MvpPresenterBase;
import com.zenkun.wunder.ui.mvp.presenter.PresenterViewMap;
import com.zenkun.wunder.ui.mvp.view.ViewMap;
import com.zenkun.wunder.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class PresenterViewMapImpl extends MvpPresenterBase<ViewMap> implements PresenterViewMap {

    //hashmap to detect what marker was clicked...
    private ArrayList<Marker> markerList= new ArrayList<>();
    private boolean showOneMarker=false;
    //display the maximum markers on map to avoid lag :S
    private static final long MAX_MARKERS_ON_MAP=25;
    private List<Placemark> list;
    //boolean to not update "on idle"
    private boolean isAllCarsVisible=false;

    @Override
    public void onAttach() {
        super.onAttach();
        list= HelperPreference.get().getMarkerList(Constant.KEY.PLACE_MARK);
    }

    @Override
    public void onMapReady(LatLngBounds bounds) {
        //map ready, read data from...
        //TODO read data async for HUGE data...

        //if is empty... we need call maybe the  service again...
        showPartialCars(bounds);
        getView().enableListeners();
    }
    private LatLngBounds.Builder showMarkers(List<Placemark> data)
    {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if(data!=null && data.size()>0)
        {
            //maybe block the map or something until we finish....
            //TODO show markers based on the map screen, load many markers will lag..... too much lag
            int size =data.size();

            for (int i=0;i<size;i++)
            {
                Placemark placemark = data.get(i);

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


        }
        return builder;
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
    public void showPartialCars(LatLngBounds latLngBounds) {
        //TODO maybe "lock" this process if the  user moves again...

        //if is displaying one marker we return.. we dont want lose the "selected marker ;)"
        if(showOneMarker || isAllCarsVisible)return;
        getView().clearMap();
        //we reset the markers
        showOneMarker=false;
        //
        markerList.clear();
        //show only some markers
        List<Placemark> data = Stream.of(list)
                .filter(value -> latLngBounds.contains(value.getCoordinates()))
                .limit(MAX_MARKERS_ON_MAP)
                .collect(Collectors.toList());
        //show markers
        showMarkers(data);
    }

    @Override
    public void showAllCars() {
        isAllCarsVisible =true;
        clearFlags();
        LatLngBounds.Builder bounds =showMarkers(list);
        getView().updateZoomMap(bounds);
    }
    private void clearFlags()
    {
        showOneMarker=false;
        markerList.clear();
    }

    @Override
    public void showSingleCarList(LatLngBounds latLngBounds) {
        isAllCarsVisible=false;
        clearFlags();
        showPartialCars(latLngBounds);
    }

    @Override
    public void onDettach() {
        super.onDettach();
        markerList.clear();
    }
}
