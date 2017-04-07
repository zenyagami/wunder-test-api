package com.zenkun.wunder.ui.mvp;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public abstract class MvpPresenterBase<ViewType extends MvpView>
        implements MvpPresenter<ViewType> {

    private ViewType mView;

    //onResume
    @Override public void onAttach() {

    }

    //onPostCreate
    @Override public void onCreate(ViewType view) {
        mView = view;
    }

    //onpause
    @Override
    public void onPause() {

    }

    //call in on destroy...
    @Override public void onDettach() {
        mView = null;
    }

    @Override public ViewType getView() {
        if (mView == null) {
            throw new IllegalStateException("Presenter view is null");
        }
        return mView;
    }

    @Override public boolean isViewAttached() {
        return mView != null;
    }
}