package com.javalong.rrdm.activity.module;

import com.javalong.rrdm.activity.iview.IMainView;
import com.javalong.rrdm.activity.presenter.MainPresenter;
import com.javalong.rrdm.app.annotation.ActivityScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javalong on 16-12-27.
 */
@Module
public class MainActivityModule  {

    private IMainView mainView;

    public MainActivityModule(IMainView mainView) {
        this.mainView = mainView;
    }

    @Provides @ActivityScope IMainView provideIMainView(){
        return mainView;
    }

    /**
     * 这种方式,和直接在MainPresenter构造方法上添加@Inject是一样的
     * 二选一即可.
     */
//    @Provides @ActivityScope MainPresenter provideMainPresenter(IMainView mainView){
//        return new MainPresenter(mainView);
//    }
}
