package com.hpw.frame.mvp.ui.splash;

import com.hpw.frame.mvp.ui.BasePresenter;
import com.hpw.frame.mvp.ui.BaseView;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 16:25
 * 邮箱：424346976@qq.com
 */
public interface SplashContract {
    interface View extends BaseView {
        void showMainUi();
    }

    interface Presenter extends BasePresenter<View> {
        void initSplash();
    }
}
