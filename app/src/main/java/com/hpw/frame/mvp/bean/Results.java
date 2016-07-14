package com.hpw.frame.mvp.bean;

import java.util.List;

import retrofit2.adapter.rxjava.Result;
import rx.functions.Func1;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 10:04
 * 邮箱：424346976@qq.com
 */
public class Results {
    public static Func1<Result<?>, Boolean> DATA_FUNC =
            result -> !result.isError() && result.response().isSuccessful();

    public static Func1<Result<?>, Boolean> isSuccess() {
        return DATA_FUNC;
    }

    public static Func1<List<?>, Boolean> IMAGE_FUNC =
            images -> images.size() != 0 && images != null;

    public static Func1<List<?>, Boolean> isNull() {
        return IMAGE_FUNC;
    }

    private Results() {
        throw new AssertionError("no instances ");
    }
}
