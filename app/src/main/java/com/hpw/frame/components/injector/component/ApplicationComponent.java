package com.hpw.frame.components.injector.component;

import com.hpw.frame.MyApplication;
import com.hpw.frame.components.injector.module.ApplicationModule;

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
}
