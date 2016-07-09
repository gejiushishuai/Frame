package com.hpw.frame.mvp.ui.splash;

import android.support.annotation.NonNull;

import com.hpw.frame.components.injector.scopes.PerActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 17:20
 * 邮箱：424346976@qq.com
 */
@PerActivity
public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mSplashView;

    @Inject
    public SplashPresenter() {

    }

    @Override
    public void attachView(@NonNull SplashContract.View view) {
        mSplashView = view;
    }

    @Override
    public void detachView() {
        mSplashView = null;
    }

    @Override
    public void initSplash() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((SplashActivity) mSplashView).<Long>bindToLifecycle())
                .subscribe(aVoid -> {
                    mSplashView.showMainUi();
                });
    }
}
