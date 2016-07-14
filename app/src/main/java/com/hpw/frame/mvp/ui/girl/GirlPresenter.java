package com.hpw.frame.mvp.ui.girl;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.hpw.frame.mvp.api.girl.GirlApi;
import com.hpw.frame.mvp.bean.GirlData;
import com.hpw.frame.mvp.bean.Image;
import com.hpw.frame.mvp.bean.PrettyGirl;
import com.hpw.frame.mvp.bean.Results;
import com.hpw.frame.utils.ConfigurationUtils;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 10:46
 * 邮箱：424346976@qq.com
 */
public class GirlPresenter implements GirlContract.Presenter {

    private GirlContract.View mGirlView;
    private Context mContext;
    private int mPage = 1;
    private boolean refreshing;
    private List<Image> mImages = new ArrayList<>();

    private GirlAdapter girlAdapter;
    private RecyclerView girlRecyclerView;
    private SwipeRefreshLayout girlRefreshLayout;

    @Inject
    GirlApi mGirlApi;

    @Inject
    public GirlPresenter() {

    }

    @Override
    public void attachView(@NonNull GirlContract.View view) {
        mGirlView = view;
    }

    @Override
    public void detachView() {
        mGirlView = null;
    }

    @Override
    public void onClickFlyToTop(Toolbar girlToolbar) {
        RxView.clicks(girlToolbar)
                .subscribe(aVoid -> {
                    mGirlView.flyToTop();
                });
    }

    public void getGirlData(boolean clean) {
        Observable<List<Image>> results = mGirlApi.getPrettyGirl(mPage)
                .compose(((GirlActivity) mGirlView).<Result<GirlData>>bindToLifecycle())
                .filter(Results.isSuccess())
                .map(girlDataResult -> girlDataResult.response().body())
                .flatMap(imageFetcher)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share();

        results.filter(Results.isNull())
                .doOnNext(images -> {
                    initRecyclerView();
                    if (clean) images.clear();
                })
                .doOnCompleted(() -> girlRefreshLayout.setRefreshing(false))
                .subscribe(girlAdapter, dataError);
    }

    private final Func1<GirlData, Observable<List<Image>>> imageFetcher = girlData -> {
        List<PrettyGirl> results = girlData.results;
        for (PrettyGirl girl : results) {
            try {
                Bitmap bitmap = Picasso.with(mContext).load(girl.url)
                        .get();
                Image image = new Image();
                image.width = bitmap.getWidth();
                image.height = bitmap.getHeight();
                image.url = girl.url;
                mImages.add(image);
            } catch (IOException e) {
                e.printStackTrace();
                return Observable.error(e);
            }
        }
        return Observable.just(mImages);
    };

    private Action1<Throwable> dataError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            throwable.printStackTrace();
            girlRefreshLayout.setRefreshing(false);
            Snackbar.make(girlRecyclerView, throwable.getMessage(), Snackbar.LENGTH_LONG)
                    .show();
        }
    };

    @Override
    public void onRefresh(SwipeRefreshLayout girlRefreshLayout) {
        RxSwipeRefreshLayout.refreshes(girlRefreshLayout)
                .compose(((GirlActivity) mGirlView).<Void>bindToLifecycle())
                .subscribe(aVoid -> {
                    mPage = 1;
                    refreshing = true;
                    getGirlData(true);
                });
    }

    @Override
    public void init(Context context, GirlAdapter mGirlAdapter, RecyclerView mGirlRecyclerView, SwipeRefreshLayout mGirlRefreshLayout) {

        this.mContext = context;
        this.girlAdapter = mGirlAdapter;
        this.girlRecyclerView = mGirlRecyclerView;
        this.girlRefreshLayout = mGirlRefreshLayout;
        getGirlData(false);
        girlRecyclerView.setAdapter(girlAdapter);
        int spanCount = 2;
        if (ConfigurationUtils.isOrientationPortrait(mContext)) spanCount = 2;
        else if (ConfigurationUtils.isOrientationLandscape(mContext)) spanCount = 3;

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                spanCount, StaggeredGridLayoutManager.VERTICAL);
        girlRecyclerView.setLayoutManager(layoutManager);
        RxRecyclerView.scrollEvents(girlRecyclerView)
                .compose(((GirlActivity) mGirlView).<RecyclerViewScrollEvent>bindUntilEvent(ActivityEvent.DESTROY))
                .filter(recyclerViewScrollEvent -> {
                    boolean isBottom = false;
                    if (ConfigurationUtils.isOrientationPortrait(mContext)) {
                        isBottom = layoutManager.findLastCompletelyVisibleItemPositions(
                                new int[2])[1] >= mImages.size() - 4;
                    } else if (ConfigurationUtils.isOrientationLandscape(mContext)) {
                        isBottom = layoutManager.findLastCompletelyVisibleItemPositions(
                                new int[3])[2] >= mImages.size() - 4;
                    }
                    return !girlRefreshLayout.isRefreshing() && isBottom;
                })
                .subscribe(recyclerViewScrollEvent -> {
                    //这么做的目的是一旦下拉刷新，RxRecyclerView scrollEvents 也会被触发，mPage就会加一
                    //所以要将mPage设为0，这样下拉刷新才能获取第一页的数据
                    if (refreshing) {
                        mPage = 0;
                        refreshing = false;
                    }
                    mPage += 1;
                    girlRefreshLayout.setRefreshing(true);
                    getGirlData(false);
                });
    }

    public void initRecyclerView() {
        girlAdapter.bind(mContext, mImages);
    }

}
