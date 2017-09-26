package com.lizi.fast_fragment.core;

import android.app.Application;

/**
 * Created by lizi on 2016/11/4.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppStatusTracker.init(this);
    }
}
