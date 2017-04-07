package com.zenkun.wunder.ui.mvp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.zenkun.wunder.utils.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */
public abstract class MvpViewBaseActivity<PresenterType extends MvpPresenter>
        extends AppCompatActivity
        implements MvpView<PresenterType> {

    protected PresenterType mPresenter;
    private Unbinder unbinder;


    // FINAL IS INTENTIONAL.
    // We use this method to always check for permissions. Because we can not
    // return out of the subclass method, we need to avoid the subclass to
    // keep executing code inside onCreate, because view might not be ready
    // due to the lack of permissions.
    @Override protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (PresenterType) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = getPresenterInstance();
        }
        setContentView(getLayoutResourceId());
        unbinder =ButterKnife.bind(this);

    }

    @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mPresenter.onCreate(this);
    }

    @Override public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override protected void onResume() {
        super.onResume();
        mPresenter.onAttach();
    }

    @Override protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override protected void onDestroy() {
        if(mPresenter!=null)
            mPresenter.onDettach();
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showError(Throwable throwable) {
        Logger.d(throwable);
        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    protected void showMessage(int messageResource)
    {
        Toast.makeText(getApplicationContext(),messageResource,Toast.LENGTH_SHORT).show();
    }



}
