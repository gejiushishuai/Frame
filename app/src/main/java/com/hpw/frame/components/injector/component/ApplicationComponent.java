package com.hpw.frame.components.injector.component;

import android.content.Context;

import com.hpw.frame.MyApplication;
import com.hpw.frame.components.injector.module.ApplicationModule;
import com.hpw.frame.mvp.ui.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 作者：杭鹏伟
 * 日期：16-7-7 18:00
 * 邮箱：424346976@qq.com
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MyApplication myApplication);

    void inject(BaseActivity mBaseActivity);

    Context getContext();//构造函数里的参数使用dependencies = ApplicationComponent.class依赖过来查找方式，然后ApplicationModule提供
}
