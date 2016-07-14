package com.hpw.frame.components.injector.module;

import com.hpw.frame.mvp.api.girl.GirlApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * 作者：杭鹏伟
 * 日期：16-7-12 18:04
 * 邮箱：424346976@qq.com
 */
@Module
public class ApiModule {
    @Provides
    @Singleton
    public GirlApi providesGirlApi(OkHttpClient okHttpClient) {
        return new GirlApi(okHttpClient);
    }
}
