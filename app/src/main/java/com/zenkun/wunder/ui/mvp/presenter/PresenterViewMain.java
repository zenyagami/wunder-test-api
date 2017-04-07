package com.zenkun.wunder.ui.mvp.presenter;

import com.zenkun.wunder.model.Placemark;
import com.zenkun.wunder.ui.mvp.MvpPresenter;
import com.zenkun.wunder.ui.mvp.view.ViewMain;

import java.util.List;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface PresenterViewMain extends MvpPresenter<ViewMain>{

    void loadData();
    void onDataLoaded(List<Placemark> results);
    void onError(Throwable throwable);
}
