package com.hpw.frame.mvp.ui.girl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.hpw.frame.R;
import com.hpw.frame.mvp.bean.Image;
import com.hpw.frame.mvp.bean.Results;
import com.hpw.frame.mvp.ui.BaseActivity;
import com.hpw.frame.utils.ConfigurationUtils;
import com.hpw.frame.utils.NetUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 10:42
 * 邮箱：424346976@qq.com
 */
public class GirlActivity extends BaseActivity implements GirlContract.View {
    @Inject
    GirlPresenter mPresenter;
    @Inject
    GirlAdapter girlAdapter;
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
        int spanCount = 2;
        if (ConfigurationUtils.isOrientationPortrait(this)) spanCount = 2;
        else if (ConfigurationUtils.isOrientationLandscape(this)) spanCount = 3;

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                spanCount, StaggeredGridLayoutManager.VERTICAL);
        girlRecyclerView.setLayoutManager(layoutManager);
        mPresenter.onClickFlyToTop(girlToolbar);
        mPresenter.onRecyclerViewScroll(layoutManager, girlRecyclerView, girlRefreshLayout);
        mPresenter.onRefresh(girlRefreshLayout);
        mPresenter.onImageClick(girlAdapter);
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
        } else {
            mPresenter.getGirlData(false);
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

    @Override
    public void showUi(boolean clean, Observable<List<Image>> results) {


        results.filter(Results.isNull())
                .doOnNext(images -> {
                    girlAdapter.bind(this, images);
                    girlRecyclerView.setAdapter(girlAdapter);
                    if (clean) images.clear();
                })
                .doOnCompleted(() -> girlRefreshLayout.setRefreshing(false))
                .subscribe(girlAdapter, dataError);
    }

    private Action1<Throwable> dataError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            throwable.printStackTrace();
            girlRefreshLayout.setRefreshing(false);
            Snackbar.make(girlRecyclerView, throwable.getMessage(), Snackbar.LENGTH_LONG)
                    .show();
        }
    };
}
