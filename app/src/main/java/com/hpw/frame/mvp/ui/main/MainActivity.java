package com.hpw.frame.mvp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.hpw.frame.R;
import com.hpw.frame.mvp.ui.BaseActivity;
import com.hpw.frame.mvp.ui.girl.GirlActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainPresenter mPresenter;
    @BindView(R.id.btNight)
    Button btNight;
    @BindView(R.id.btGirl)
    Button btGirl;

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mPresenter.onNightModelClick(btNight);
        mPresenter.onGirlClick(btGirl);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void showGirlUi() {
        GirlActivity.startActivity(this);
    }
}
