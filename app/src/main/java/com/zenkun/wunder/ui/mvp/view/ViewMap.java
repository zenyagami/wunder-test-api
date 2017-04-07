package com.zenkun.wunder.ui.mvp.view;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zenkun.wunder.model.Placemark;
import com.zenkun.wunder.ui.mvp.MvpView;
import com.zenkun.wunder.ui.mvp.presenter.PresenterViewMap;

import java.util.List;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface ViewMap extends MvpView<PresenterViewMap> {
    void loadMapMarker(MarkerOptions markerOptions);

    void
    updateZoomMap(LatLngBounds.Builder builder);
}
