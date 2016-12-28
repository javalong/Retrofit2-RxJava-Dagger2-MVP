package com.javalong.rrdm.app;

import android.app.Application;

import com.javalong.rrdm.app.component.AppComponent;
import com.javalong.rrdm.app.component.DaggerAppComponent;
import com.javalong.rrdm.app.module.AppModule;

/**
 * Created by javalong on 16-12-27.
 */

public class AppBaseApplication extends Application {

    private static AppBaseApplication instance;

    public static AppBaseApplication getInstance() {
        return instance;
    }

    AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        }
    }
}
