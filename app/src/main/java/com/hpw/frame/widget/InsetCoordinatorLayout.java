package com.hpw.frame.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import com.hpw.frame.R;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 10:54
 * 邮箱：424346976@qq.com
 */
public class InsetCoordinatorLayout extends CoordinatorLayout implements View.OnApplyWindowInsetsListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    public InsetCoordinatorLayout(Context context) {
        this(context, null);
    }

    public InsetCoordinatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOnApplyWindowInsetsListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        toolbar = (Toolbar) findViewById(R.id.girlToolbar);
        recyclerView = (RecyclerView) findViewById(R.id.girlRecyclerView);
    }

    @Override
    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
        int windowInsetRight = insets.getSystemWindowInsetRight();

        ViewGroup.MarginLayoutParams lpToolbar = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        lpToolbar.topMargin += insets.getSystemWindowInsetTop();
        lpToolbar.rightMargin += windowInsetRight;
        toolbar.setLayoutParams(lpToolbar);

        recyclerView.setPadding(recyclerView.getPaddingLeft(),
                recyclerView.getPaddingTop(),
                recyclerView.getPaddingRight() + windowInsetRight,
                recyclerView.getPaddingBottom());

        setOnApplyWindowInsetsListener(null);
        return insets.consumeSystemWindowInsets();
    }
}
