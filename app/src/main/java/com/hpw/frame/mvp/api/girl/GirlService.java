package com.hpw.frame.mvp.api.girl;

import com.hpw.frame.mvp.bean.GirlData;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 作者：杭鹏伟
 * 日期：16-7-12 17:48
 * 邮箱：424346976@qq.com
 */
public interface GirlService {
    @GET("data/福利/10/{page}")
    Observable<Result<GirlData>> getPrettyGirl(@Path("page") int page);
}
