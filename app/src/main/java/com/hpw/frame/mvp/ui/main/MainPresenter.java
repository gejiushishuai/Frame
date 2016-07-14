package com.hpw.frame.mvp.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.hpw.frame.utils.SettingPrefUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * 作者：杭鹏伟
 * 日期：16-7-7 16:24
 * 邮箱：424346976@qq.com
 */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mMainView;
    private Context mContext;

    @Inject
    public MainPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void attachView(@NonNull MainContract.View view) {
        mMainView = view;
    }

    @Override
    public void detachView() {
        mMainView = null;
    }

    @Override
    public void onNightModelClick(View view) {
        RxView.clicks(view)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .compose(((MainActivity) mMainView).<Void>bindToLifecycle())
                .subscribe(aVoid -> {
                    SettingPrefUtil.setNightModel(mContext, !SettingPrefUtil.getNightModel(mContext));
                    mMainView.reload();
                });
    }

    @Override
    public void onGirlClick(View view) {
        RxView.clicks(view)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .compose(((MainActivity) mMainView).<Void>bindToLifecycle())
                .subscribe(aVoid -> {
                    mMainView.showGirlUi();
                });
    }
}
