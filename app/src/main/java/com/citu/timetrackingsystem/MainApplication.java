package com.citu.timetrackingsystem;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.citu.timetrackingsystem.manager.SessionManager;

public class MainApplication extends Application implements
        Application.ActivityLifecycleCallbacks,
        LifecycleObserver {

    public SessionManager mSessionManager;


    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        prepareManagers();
        mSessionManager.createAdminUser();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void prepareManagers() {
        mSessionManager = SessionManager.getInstance(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
