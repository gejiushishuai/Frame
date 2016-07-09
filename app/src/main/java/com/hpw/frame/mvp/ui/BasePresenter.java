package com.hpw.frame.mvp.ui;

import android.support.annotation.NonNull;

/**
 * 作者：杭鹏伟
 * 日期：16-7-7 16:19
 * 邮箱：424346976@qq.com
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(@NonNull T view);

    void detachView();
}
