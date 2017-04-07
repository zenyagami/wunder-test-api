package com.zenkun.wunder.ui.mvp.view.impl;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.zenkun.wunder.R;
import com.zenkun.wunder.adapter.AdapterCars;
import com.zenkun.wunder.model.Placemark;
import com.zenkun.wunder.ui.mvp.MvpViewBaseActivity;
import com.zenkun.wunder.ui.mvp.presenter.PresenterViewMain;
import com.zenkun.wunder.ui.mvp.presenter.impl.PresenterViewMainImpl;
import com.zenkun.wunder.ui.mvp.view.ViewMain;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class ViewMainImpl extends MvpViewBaseActivity<PresenterViewMain> implements ViewMain {


    @BindView(R.id.rv_car_list) RecyclerView rv;
    @BindView(R.id.pb_loading)
    View loading;

    private AdapterCars adapterCar;
    @Override
    public int getLayoutResourceId() {
        return R.layout.view_main;
    }

    @Override
    public PresenterViewMain getPresenterInstance() {
        return new PresenterViewMainImpl();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterCar=new AdapterCars(null);
        rv.setAdapter(adapterCar);
        mPresenter.loadData();
    }

    @Override
    public void showError(Throwable throwable) {
        super.showError(throwable);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showResults(List<Placemark> results) {
        loading.setVisibility(View.GONE);
        adapterCar.changeDataSet(results);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_map_view:
                showMapView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMapView() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        startActivity(new Intent(getApplicationContext(),ViewMapImpl.class));
                    } else {
                        showMessage(R.string.permissions_denied);
                    }
                });

    }
}
