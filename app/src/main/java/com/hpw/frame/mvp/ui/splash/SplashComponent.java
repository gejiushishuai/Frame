package com.hpw.frame.mvp.ui.splash;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 17:40
 * 邮箱：424346976@qq.com
 */

import com.hpw.frame.components.injector.component.ApplicationComponent;
import com.hpw.frame.components.injector.module.ActivityModule;
import com.hpw.frame.components.injector.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, SplashModule.class})
public interface SplashComponent {
    void inject(SplashActivity activity);
}
