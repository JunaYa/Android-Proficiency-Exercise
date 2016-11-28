package com.junaya.gank;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


/**
 * Created by aya on 2016/11/24.
 */

public class App extends Application {
    private static App mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        initLeakcanary();
        mApp  = (App) getApplicationContext();
    }

    private void initLeakcanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    public static App getInstance(){
        return mApp;
    }
}
