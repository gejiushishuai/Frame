package com.hpw.frame.mvp.ui.main;

import com.hpw.frame.components.injector.component.ApplicationComponent;
import com.hpw.frame.components.injector.module.ActivityModule;
import com.hpw.frame.components.injector.scopes.PerActivity;

import dagger.Component;

/**
 * 作者：杭鹏伟
 * 日期：16-7-7 16:22
 * 邮箱：424346976@qq.com
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
