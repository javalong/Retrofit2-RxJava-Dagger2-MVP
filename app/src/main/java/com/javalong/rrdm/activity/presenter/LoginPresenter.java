package com.javalong.rrdm.activity.presenter;

import com.javalong.rrdm.activity.iview.ILoginView;
import com.javalong.rrdm.app.presenter.AppPresenter;
import com.javalong.rrdm.retrofit.ServerApi;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by javalong on 16-12-27.
 */

public class LoginPresenter extends AppPresenter<ILoginView> {

    @Inject
    ServerApi serverApi;

    @Inject
    public LoginPresenter(ILoginView mvpView) {
        super(mvpView);
    }

    @Override
    public void attach() {
    }

    @Override
    public void detach() {
    }

    public void  login(){
        serverApi.requestUrl("login")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                    }
                });
    }
}
