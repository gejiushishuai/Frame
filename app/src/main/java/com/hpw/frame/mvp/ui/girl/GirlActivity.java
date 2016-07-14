package com.hpw.frame.mvp.ui.girl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.hpw.frame.R;
import com.hpw.frame.mvp.ui.BaseActivity;
import com.hpw.frame.utils.NetUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 10:42
 * 邮箱：424346976@qq.com
 */
public class GirlActivity extends BaseActivity implements GirlContract.View {
    @Inject
    GirlPresenter mPresenter;
    @Inject
    GirlAdapter mGirlAdapter;
    @BindView(R.id.girlToolbar)
    Toolbar girlToolbar;
    @BindView(R.id.girlRecyclerView)
    RecyclerView girlRecyclerView;
    @BindView(R.id.girlRefreshLayout)
    SwipeRefreshLayout girlRefreshLayout;

    @Override
    public int initContentView() {
        return R.layout.activity_girl;
    }

    @Override
    public void initInjector() {
        DaggerGirlComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .girlModule(new GirlModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mPresenter.init(this, mGirlAdapter, girlRecyclerView, girlRefreshLayout);
        mPresenter.onClickFlyToTop(girlToolbar);
        mPresenter.onRefresh(girlRefreshLayout);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, GirlActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (!NetUtils.checkNet(this)) {
            Snackbar.make(girlRecyclerView, "无网络不能获取美女哦!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("知道了", v -> {
                    })
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void flyToTop() {
        girlRecyclerView.smoothScrollToPosition(0);
    }
}
