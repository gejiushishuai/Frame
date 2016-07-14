package com.hpw.frame.mvp.ui.girl;

import com.hpw.frame.components.injector.component.ApplicationComponent;
import com.hpw.frame.components.injector.module.ActivityModule;
import com.hpw.frame.components.injector.scopes.PerActivity;

import dagger.Component;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 11:19
 * 邮箱：424346976@qq.com
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GirlModule.class})
public interface GirlComponent {
    void inject(GirlActivity activity);
}