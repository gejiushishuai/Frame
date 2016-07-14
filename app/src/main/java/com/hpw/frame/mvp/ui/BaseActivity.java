package com.hpw.frame.mvp.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.hpw.frame.AppManager;
import com.hpw.frame.MyApplication;
import com.hpw.frame.components.injector.component.ApplicationComponent;
import com.hpw.frame.components.injector.module.ActivityModule;
import com.hpw.frame.utils.ResourceUtil;
import com.hpw.frame.utils.SettingPrefUtil;
import com.hpw.frame.utils.StatusBarUtil;
import com.hpw.frame.utils.ThemeUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * 作者：杭鹏伟
 * 日期：16-7-7 16:19
 * 邮箱：424346976@qq.com
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        setTranslucentStatus(isApplyStatusBarTranslucency());
        setStatusBarColor(isApplyStatusBarColor());
        initInjector();
        initUiAndListener();
        AppManager.getAppManager().addActivity(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);添加友盟等公共操作
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    private void initTheme() {
        int theme;
//        try {
//            theme = getPackageManager().getActivityInfo(getComponentName(), 0).theme;
//        } catch (PackageManager.NameNotFoundException e) {
//            return;
//        }
//        if (theme != R.style.AppThemeLaunch) {
            theme = ThemeUtil.themeArr[SettingPrefUtil.getThemeIndex(this)][
                    SettingPrefUtil.getNightModel(this) ? 1 : 0];
//        }
        setTheme(theme);
    }

    /**
     * 设置view
     */
    public abstract int initContentView();

    /**
     * 注入Injector
     */
    public abstract void initInjector();

    /**
     * init UI && Listener
     */
    public abstract void initUiAndListener();

    /**
     * is applyStatusBarTranslucency
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * set status bar translucency
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected abstract boolean isApplyStatusBarColor();

    /**
     * use SystemBarTintManager
     */
    public void setStatusBarColor(boolean on) {
        if (on) {
            StatusBarUtil.setColor(this, ResourceUtil.getThemeColor(this), 0);
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public int getStatusBarHeight() {
        return StatusBarUtil.getStatusBarHeight(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}