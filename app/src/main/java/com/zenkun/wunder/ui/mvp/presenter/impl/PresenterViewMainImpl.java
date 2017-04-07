package com.zenkun.wunder.ui.mvp.presenter.impl;

import com.zenkun.wunder.Constant;
import com.zenkun.wunder.helpers.HelperPreference;
import com.zenkun.wunder.model.ModelPlaceMarks;
import com.zenkun.wunder.model.Placemark;
import com.zenkun.wunder.net.ApiClient;
import com.zenkun.wunder.ui.mvp.MvpPresenterBase;
import com.zenkun.wunder.ui.mvp.presenter.PresenterViewMain;
import com.zenkun.wunder.ui.mvp.view.ViewMain;
import com.zenkun.wunder.utils.UtilRx;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class PresenterViewMainImpl extends MvpPresenterBase<ViewMain> implements PresenterViewMain{

    private Subscription networkSubscription;
    @Override
    public void loadData() {
        //start networkConnection
        //rxjava2 is disposable :p
        //start networkConnection
        networkSubscription =ApiClient.get()
                .getWunderApi()
                .getLocations()
                .subscribeOn(Schedulers.io())
                .map(ModelPlaceMarks::getPlacemarks)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataLoaded,this::onError);
        //TODO read data from disk or a place...

    }

    @Override
    public void onDataLoaded(List<Placemark> results) {
        getView().showResults(results);
        //TODO saveData to Disk in background if is huge load...
        HelperPreference.get().saveObject(Constant.KEY.PLACE_MARK,results);
    }

    @Override
    public void onError(Throwable throwable) {
        //error happened maybe on map==null or network issue :(
        getView().showError(throwable);
    }

    @Override
    public void onDettach() {
        UtilRx.unsubscribe(networkSubscription);
        super.onDettach();
    }
}
