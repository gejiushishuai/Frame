package com.hpw.frame.mvp.ui.girl;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.hpw.frame.mvp.bean.Image;
import com.hpw.frame.mvp.ui.BasePresenter;
import com.hpw.frame.mvp.ui.BaseView;

import java.util.List;

import rx.Observable;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 10:45
 * 邮箱：424346976@qq.com
 */
public interface GirlContract {
    interface View extends BaseView {
        void flyToTop();

        void showUi(boolean clean, Observable<List<Image>> results);
    }

    interface Presenter extends BasePresenter<View> {
        void onClickFlyToTop(Toolbar girlToolbar);

        void onRefresh(SwipeRefreshLayout girlRefreshLayout);

        void onRecyclerViewScroll(StaggeredGridLayoutManager layoutManager, RecyclerView girlRecyclerView, SwipeRefreshLayout girlRefreshLayout);

        void onImageClick(GirlAdapter girlAdapter);
    }
}
