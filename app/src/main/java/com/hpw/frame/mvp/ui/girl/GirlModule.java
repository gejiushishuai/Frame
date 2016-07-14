package com.hpw.frame.mvp.ui.girl;

import dagger.Module;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 13:26
 * 邮箱：424346976@qq.com
 */
@Module
public class GirlModule {
    private GirlActivity mActivity;

    public GirlModule(GirlActivity mActivity) {
        this.mActivity = mActivity;
    }
}
