package com.javalong.rrdm.app.component;

import android.content.Context;

import com.javalong.rrdm.app.module.AppModule;
import com.javalong.rrdm.retrofit.ServerApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javalong on 16-12-27.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
    /**这里必须加上这个,因为在LoginPresenter里面我@Inject ServerApi,
        这里不加的话,会造成这个ServerApi找不到
     */
    ServerApi getServerApi();
}
