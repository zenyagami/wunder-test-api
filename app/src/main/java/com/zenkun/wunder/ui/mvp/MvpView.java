package com.zenkun.wunder.ui.mvp;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public interface MvpView<PresenterType extends MvpPresenter> {

    int getLayoutResourceId();
    PresenterType getPresenterInstance();
    void showError(Throwable throwable);
}