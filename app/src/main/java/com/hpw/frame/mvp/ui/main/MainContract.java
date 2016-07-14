package com.hpw.frame.mvp.ui.main;

import com.hpw.frame.mvp.ui.BasePresenter;
import com.hpw.frame.mvp.ui.BaseView;

/**
 * 作者：杭鹏伟
 * 日期：16-7-7 16:22
 * 邮箱：424346976@qq.com
 */
public interface MainContract {
    interface View extends BaseView {
        void showGirlUi();
        void reload();
    }

    interface Presenter extends BasePresenter<View> {
        void onNightModelClick(android.view.View view);
        void onGirlClick(android.view.View view);
    }
}
