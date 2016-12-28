package com.javalong.rrdm.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.javalong.rrdm.R;
import com.javalong.rrdm.activity.component.DaggerMainActivityComponent;
import com.javalong.rrdm.activity.iview.IMainView;
import com.javalong.rrdm.activity.module.MainActivityModule;
import com.javalong.rrdm.activity.presenter.MainPresenter;
import com.javalong.rrdm.app.AppBaseActivity;
import com.javalong.rrdm.retrofit.ServerApi;

import java.util.Timer;

import javax.inject.Inject;

public class MainActivity extends AppBaseActivity<MainPresenter> implements IMainView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.test();
    }

    @Override
    protected void initInjects() {
        DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void showMainMessage() {
        ((TextView)findViewById(R.id.tv_test))
                .setText("你好 dagger");
        findViewById(R.id.bt_login)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(MainActivity.this);
                MainActivity.this.finish();
            }
        });
    }
}
