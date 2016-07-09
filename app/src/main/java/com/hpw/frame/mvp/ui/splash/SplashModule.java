package com.hpw.frame.mvp.ui.splash;

import dagger.Module;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 17:41
 * 邮箱：424346976@qq.com
 */
@Module
public class SplashModule {
    private SplashActivity mActivity;

    public SplashModule(SplashActivity mActivity) {
        this.mActivity = mActivity;
    }
}
