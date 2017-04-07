package com.zenkun.wunder.ui.mvp.presenter;

import com.google.android.gms.maps.model.Marker;
import com.zenkun.wunder.ui.mvp.MvpPresenter;
import com.zenkun.wunder.ui.mvp.view.ViewMap;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface PresenterViewMap extends MvpPresenter<ViewMap>{
    void onMapReady();
    void addMarker(Marker marker);

    void onMarkerClick(Marker marker);
}
