package com.zenkun.wunder.ui.mvp;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface MvpPresenter<ViewType extends MvpView> {

    void onCreate(ViewType view);

    void onAttach();

    void onDettach();

    void onPause();

    ViewType getView();

    boolean isViewAttached();

}
