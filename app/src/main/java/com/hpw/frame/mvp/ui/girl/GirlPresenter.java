package com.hpw.frame.mvp.ui.girl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.hpw.frame.widget.PictureActivity;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 10:46
 * 邮箱：424346976@qq.com
 */
public class GirlPresenter implements GirlContract.Presenter {

    private GirlContract.View mGirlView;
    private List<Image> mImages = new ArrayList<>();
    private int mPage = 1;
    private boolean refreshing;

    @Inject
    GirlApi mGirlApi;
    private Context mContext;

    @Inject
    public GirlPresenter(Context mContext) {
        this.mContext = mContext;
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

        mGirlView.showUi(clean, results);
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
    public void onRecyclerViewScroll(StaggeredGridLayoutManager layoutManager, RecyclerView girlRecyclerView, SwipeRefreshLayout girlRefreshLayout) {
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

    @Override
    public void onImageClick(GirlAdapter girlAdapter) {
        girlAdapter.setOnTouchListener((v, image) ->
                Picasso.with(mContext).load(image.url).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent((GirlActivity) mGirlView, PictureActivity.class);
                        intent.putExtra("url", image.url);
                        ActivityOptionsCompat compat =
                                ActivityOptionsCompat.makeSceneTransitionAnimation((GirlActivity) mGirlView,
                                        v, "girl");
                        ActivityCompat.startActivity((GirlActivity) mGirlView, intent, compat.toBundle());
                    }

                    @Override
                    public void onError() {
                    }
                }));
    }
}
