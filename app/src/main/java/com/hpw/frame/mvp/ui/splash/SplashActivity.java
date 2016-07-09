package com.hpw.frame.mvp.ui.splash;

import android.widget.FrameLayout;

import com.hpw.frame.R;
import com.hpw.frame.mvp.ui.BaseActivity;
import com.hpw.frame.mvp.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：杭鹏伟
 * 日期：16-7-8 16:24
 * 邮箱：424346976@qq.com
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {
    @BindView(R.id.splash)
    FrameLayout splash;
    @Inject
    SplashPresenter mPresenter;

    @Override
    public int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initInjector() {
        DaggerSplashComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mPresenter.initSplash();
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    public void showMainUi() {
        MainActivity.startActivity(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
