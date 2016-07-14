package com.hpw.frame.mvp.api.girl;

import com.hpw.frame.mvp.bean.GirlData;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 作者：杭鹏伟
 * 日期：16-7-12 17:18
 * 邮箱：424346976@qq.com
 */
public class GirlApi {
    private static final String GANK_URL = "http://gank.io/api/";
    private GirlService mGirlService;

    public GirlApi(OkHttpClient mOkHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GANK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        mGirlService = retrofit.create(GirlService.class);
    }

    public Observable<Result<GirlData>> getPrettyGirl(int page) {
        return mGirlService.getPrettyGirl(page);
    }
}
