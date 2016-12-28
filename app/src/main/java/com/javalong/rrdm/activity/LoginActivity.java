package com.javalong.rrdm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.javalong.rrdm.R;
import com.javalong.rrdm.activity.component.DaggerLoginActivityComponent;
import com.javalong.rrdm.activity.component.LoginActivityComponent;
import com.javalong.rrdm.activity.iview.ILoginView;
import com.javalong.rrdm.activity.module.LoginActivityModule;
import com.javalong.rrdm.activity.presenter.LoginPresenter;
import com.javalong.rrdm.app.AppBaseActivity;

/**
 * Created by javalong on 16-12-27.
 */

public class LoginActivity extends AppBaseActivity<LoginPresenter> implements ILoginView{

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.bt_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });
    }

    @Override
    protected void initInjects() {
        LoginActivityComponent component = DaggerLoginActivityComponent.builder()
                .appComponent(getAppComponent())
                .loginActivityModule(new LoginActivityModule(this))
                .build();
        component.inject(this);
        component.inject(mPresenter);
    }

}
