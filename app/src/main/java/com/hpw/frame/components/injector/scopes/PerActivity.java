package com.hpw.frame.components.injector.scopes;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：杭鹏伟
 * 日期：16-7-7 15:29
 * 邮箱：424346976@qq.com
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
