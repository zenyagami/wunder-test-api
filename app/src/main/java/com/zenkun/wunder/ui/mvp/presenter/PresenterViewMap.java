package com.zenkun.wunder.ui.mvp.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.zenkun.wunder.ui.mvp.MvpPresenter;
import com.zenkun.wunder.ui.mvp.view.ViewMap;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface PresenterViewMap extends MvpPresenter<ViewMap>{
    void onMapReady(LatLngBounds bounds);
    void addMarker(Marker marker);

    void onMarkerClick(Marker marker);

    void showPartialCars(LatLngBounds latLngBounds);

    void showAllCars();

    void showSingleCarList(LatLngBounds latLngBounds);
}
