package com.zenkun.wunder;

import android.app.Application;

import com.zenkun.wunder.helpers.HelperGson;
import com.zenkun.wunder.helpers.HelperLocation;
import com.zenkun.wunder.helpers.HelperPreference;
import com.zenkun.wunder.net.ApiClient;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HelperGson.init();
        HelperPreference.init(getApplicationContext(),HelperGson.get().getGson());
        ApiClient.get().init(HelperGson.get().getGson());
        HelperLocation.init(getApplicationContext());
    }
}
