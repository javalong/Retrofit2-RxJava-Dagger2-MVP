package com.javalong.rrdm.activity.component;

import android.util.Log;

import com.javalong.rrdm.activity.LoginActivity;
import com.javalong.rrdm.activity.module.LoginActivityModule;
import com.javalong.rrdm.activity.presenter.LoginPresenter;
import com.javalong.rrdm.app.annotation.ActivityScope;
import com.javalong.rrdm.app.component.AppComponent;
import com.javalong.rrdm.app.module.AppModule;
import com.javalong.rrdm.retrofit.ServerApi;

import dagger.Component;

/**
 * Created by javalong on 16-12-27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {LoginActivityModule.class})
public interface LoginActivityComponent  {
    void inject(LoginActivity loginView);
    void inject(LoginPresenter loginPresenter);
}
