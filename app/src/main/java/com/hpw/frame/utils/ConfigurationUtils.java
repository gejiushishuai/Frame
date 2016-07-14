package com.hpw.frame.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 13:30
 * 邮箱：424346976@qq.com
 */
public class ConfigurationUtils {

    public static boolean isOrientationPortrait(Context context) {
        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }
        return false;
    }

    public static boolean isOrientationLandscape(Context context) {
        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        }
        return false;
    }
}
