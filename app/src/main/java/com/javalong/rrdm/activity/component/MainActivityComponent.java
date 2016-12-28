package com.javalong.rrdm.activity.component;

import com.javalong.rrdm.activity.MainActivity;
import com.javalong.rrdm.activity.iview.IMainView;
import com.javalong.rrdm.activity.module.MainActivityModule;
import com.javalong.rrdm.app.annotation.ActivityScope;
import com.javalong.rrdm.app.component.AppComponent;

import dagger.Component;

/**
 * Created by javalong on 16-12-27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = MainActivityModule.class)
public interface MainActivityComponent{
    /**
     * @param mainView
     * 这里必须使用MainActivity,不能使用IMain接口
     * 否则会造成@Inject注入失败
     */
    void inject(MainActivity mainView);
}
