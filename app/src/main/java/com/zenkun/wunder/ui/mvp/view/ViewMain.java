package com.zenkun.wunder.ui.mvp.view;

import com.zenkun.wunder.model.Placemark;
import com.zenkun.wunder.ui.mvp.MvpView;
import com.zenkun.wunder.ui.mvp.presenter.PresenterViewMain;

import java.util.List;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface ViewMain extends MvpView<PresenterViewMain>{
    void showResults(List<Placemark> results);
}
