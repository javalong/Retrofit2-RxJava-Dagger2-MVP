package com.javalong.rrdm.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.javalong.rrdm.app.component.AppComponent;
import com.javalong.rrdm.app.presenter.AppPresenter;
import com.javalong.rrdm.retrofit.ServerApi;

import javax.inject.Inject;

/**
 * Created by javalong on 16-12-27.
 */

public abstract class AppBaseActivity<T extends AppPresenter> extends AppCompatActivity {
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjects();
        mPresenter.attach();
    }

    protected abstract void initInjects();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    protected AppComponent getAppComponent(){
        return AppBaseApplication.getInstance().getAppComponent();
    }
}
