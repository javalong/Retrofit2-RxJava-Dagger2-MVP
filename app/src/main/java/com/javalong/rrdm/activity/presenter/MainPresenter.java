package com.javalong.rrdm.activity.presenter;

import android.util.Log;

import com.javalong.rrdm.activity.iview.IMainView;
import com.javalong.rrdm.app.annotation.ActivityScope;
import com.javalong.rrdm.app.iview.MvpView;
import com.javalong.rrdm.app.presenter.AppPresenter;

import javax.inject.Inject;

/**
 * Created by javalong on 16-12-27.
 */

public class MainPresenter extends AppPresenter<IMainView>{

    @Inject
    public MainPresenter(IMainView mvpView) {
        super(mvpView);
    }

    @Override
    public void attach() {

    }

    @Override
    public void detach() {

    }

    public void test() {
        mvpView.showMainMessage();
    }
}
