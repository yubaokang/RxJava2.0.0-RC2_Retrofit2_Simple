package com.gj.base.lib.widgets;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gj.base.lib.R;

/**
 * 说明：若要显示默认空view,RecycleEmptyErrorView的父View须为FrameLayout，若其父View为刷新，则父父View为FrameLayout
 * Created by tujiajie on 16/4/23.
 */
public class RecycleEmptyErrorView extends RecyclerView {

    private View mEmptyView;

    private View mErrorView;

    private boolean isError;

    private int mVisibility;

    private boolean isFirstSetAdapter = true;//是否第一次设置Adapter

    private int otherItemCount = 1;
    private boolean isSetOtherItemCount;//是否调用过setOtherItemCount()方法；

    /**
     * @param otherItemCount 用于显示空数据做判断；除了数据之外的item个数，：比如加载更多的footer item,header item；默认是一个
     */
    public void setOtherItemCount(int otherItemCount) {
        this.otherItemCount = otherItemCount;
        isSetOtherItemCount = true;//是否调用过setOtherItemCount()方法；
    }

    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateEmptyView();
        }
    };

    public RecycleEmptyErrorView(Context context) {
        super(context);
        mVisibility = getVisibility();
    }

    public RecycleEmptyErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mVisibility = getVisibility();
        mEmptyView = new ImageView(context);
        ((ImageView) mEmptyView).setImageDrawable(getResources().getDrawable(R.mipmap.bg_empty));
        ((ImageView) mEmptyView).setScaleType(ImageView.ScaleType.CENTER);
        post(new Runnable() {
            @Override
            public void run() {
                if (getParent() instanceof FrameLayout) {
                    ((ViewGroup) getParent()).addView(mEmptyView);
                } else if (getParent() != null && getParent().getParent() instanceof FrameLayout) {
                    ((ViewGroup) getParent().getParent()).addView(mEmptyView);
                }
            }
        });
    }

    public RecycleEmptyErrorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVisibility = getVisibility();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
        }
        updateEmptyView();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        mVisibility = visibility;
        updateErrorView();
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (!isSetOtherItemCount) {
            throw new NullPointerException("RecycleEmptyErrorView 必须调用 setOtherItemCount 方法");
        }
        if (isFirstSetAdapter) {
            isFirstSetAdapter = false;
            mEmptyView.setVisibility(GONE);
            super.setVisibility(VISIBLE);
            return;
        }
        if (mEmptyView != null && getAdapter() != null) {
            boolean isShowEmptyView = getAdapter().getItemCount() <= otherItemCount;//footer为1个item
            mEmptyView.setVisibility(isShowEmptyView && !shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
            super.setVisibility(!isShowEmptyView && !shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
        }
    }

    private void updateErrorView() {
        if (mErrorView != null) {
            mErrorView.setVisibility(shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
        }
    }

    private boolean shouldShowErrorView() {
        return mErrorView != null && isError;
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        updateEmptyView();
    }

    public void setErrorView(View errorView) {
        mErrorView = errorView;
        updateErrorView();
        updateEmptyView();
    }

    public void showErrorView() {
        isError = true;
        updateErrorView();
        updateEmptyView();
    }

    public void hideErrorView() {
        isError = false;
        updateErrorView();
        updateEmptyView();
    }
}
