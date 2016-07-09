package com.hpw.frame.components.injector.component;

import com.hpw.frame.components.injector.module.ActivityModule;
import com.hpw.frame.components.injector.scopes.PerActivity;

import dagger.Component;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 14:54
 * 邮箱：424346976@qq.com
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
}
