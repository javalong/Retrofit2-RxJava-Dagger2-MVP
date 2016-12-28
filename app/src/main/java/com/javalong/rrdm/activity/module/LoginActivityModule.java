package com.javalong.rrdm.activity.module;

import com.javalong.rrdm.activity.iview.ILoginView;
import com.javalong.rrdm.activity.iview.IMainView;
import com.javalong.rrdm.activity.presenter.LoginPresenter;
import com.javalong.rrdm.app.annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javalong on 16-12-27.
 */
@Module
public class LoginActivityModule {
    private ILoginView loginView;

    public LoginActivityModule(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @ActivityScope
    ILoginView provideILoginView(){
        return loginView;
    }


}
