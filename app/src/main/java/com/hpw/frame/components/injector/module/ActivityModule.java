package com.hpw.frame.components.injector.module;

import android.app.Activity;

import com.hpw.frame.components.injector.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 14:55
 * 邮箱：424346976@qq.com
 */
@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
