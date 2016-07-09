package com.hpw.frame.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;

import com.hpw.frame.R;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 16:02
 * 邮箱：424346976@qq.com
 */
public class ResourceUtil {
    public static int getThemeColor(@NonNull Context context) {
        return getThemeAttrColor(context, R.attr.colorPrimary);
    }

    public static int getThemeAttrColor(@NonNull Context context, @AttrRes int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }
}
