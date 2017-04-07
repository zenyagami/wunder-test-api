package com.zenkun.wunder.ui.mvp.view.impl;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zenkun.wunder.Constant;
import com.zenkun.wunder.R;
import com.zenkun.wunder.helpers.HelperLocation;
import com.zenkun.wunder.ui.mvp.MvpViewBaseActivity;
import com.zenkun.wunder.ui.mvp.presenter.PresenterViewMap;
import com.zenkun.wunder.ui.mvp.presenter.impl.PresenterViewMapImpl;
import com.zenkun.wunder.ui.mvp.view.ViewMap;
import com.zenkun.wunder.utils.UtilLocation;


import butterknife.BindView;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class ViewMapImpl extends MvpViewBaseActivity<PresenterViewMap> implements ViewMap, OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {
    private MapView mMapView;
    @BindView(R.id.map_container)
    ViewGroup mMapContainer;
    private GoogleMap mGoogleMap;
    HelperLocation helperLocation = HelperLocation.get();
    //private LatLng mLastLocation;

    @Override
    public int getLayoutResourceId() {
        return R.layout.view_map;
    }

    @Override
    public PresenterViewMap getPresenterInstance() {
        return new PresenterViewMapImpl();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        boolean isGooglePlayServicesAvailable= result == ConnectionResult.SUCCESS;
        if(!isGooglePlayServicesAvailable)
        {
            GoogleApiAvailability.getInstance().showErrorDialogFragment (this,result,1,null);
        }else
        {
            GoogleMapOptions options = new GoogleMapOptions();
            LatLng mLastLocation =helperLocation.getBestLastKnowLocation();
            if (mLastLocation != null) {
                options.camera(CameraPosition
                        .fromLatLngZoom(mLastLocation, Constant.UI.MAP_DEFAULT_ZOOM));
            }

            mMapView = new MapView(getApplicationContext(),options);
            mMapView.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);

            mMapContainer.addView(mMapView);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap==null)return;
        mGoogleMap =googleMap;

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);
        mPresenter.onMapReady();
        //TODO change google fused location, seems myLocation not workin very welll.
       // Location location=mGoogleMap.getMyLocation();
        LatLng latLng =helperLocation.getBestLastKnowLocation();
        if(latLng!=null)
        {
            zoomMap(latLng);
        }else {
            //TODO use new GOOGLE API fused location...
            LocationManager lm =helperLocation.getLocationManager();
            lm.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,null);
        }

    }

    private void zoomMap(LatLng location) {
        if(location==null)return;
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, Constant.UI.MAP_DEFAULT_ZOOM));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMapView!=null)
            mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mMapView!=null)
            mMapView.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //release resources..
        if (mGoogleMap != null) {
            mGoogleMap.setMyLocationEnabled(false);
            mGoogleMap.setTrafficEnabled(false);
            mGoogleMap.clear();
            mGoogleMap = null;
        }
        if(mMapView!=null)
            mMapView.onDestroy();

        helperLocation.getLocationManager().removeUpdates(this);

    }

  @Override
    public void onLocationChanged(Location location) {
      zoomMap(UtilLocation.toLatLng(location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void loadMapMarker(MarkerOptions markerOptions) {
        Marker marker =mGoogleMap.addMarker(markerOptions);
        mPresenter.addMarker(marker);
    }

    @Override
    public void updateZoomMap(LatLngBounds.Builder builder) {
        //include current position???
        /*LatLng position =helperLocation.getBestLastKnowLocation();
        if(position!=null)
            builder.include(position);*/

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(),
                100);
        try {
            mGoogleMap.animateCamera(cu);
        } catch (IllegalStateException e) {
            // layout not yet initialized
            if (mMapView.getViewTreeObserver().isAlive()) {
                mMapView.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @SuppressWarnings("deprecation")
                            @SuppressLint("NewApi")
                            // We check which build version we are using.
                            @Override
                            public void onGlobalLayout() {
                                mMapView.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                                mGoogleMap.animateCamera(cu);
                            }
                        });
            }
        }


    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        mPresenter.onMarkerClick(marker);
        return true;
    }

}
