package com.gj.base.lib.adapter.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gj.base.lib.adapter.base.ViewHolder;
import com.gj.base.lib.adapter.utils.WrapperUtils;
import com.gj.base.lib.utils.L;


/**
 * Created by zhy on 16/6/23.
 */
public class EmptyWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;

    private boolean isFirstSetAdapter = true;//是否第一次设置Adapter

    private int otherItemCount;
    private boolean isSetOtherItemCount;//是否调用过setOtherItemCount()方法；

    /**
     * @param otherItemCount 用于显示空数据做判断；除了数据之外的item个数，：比如加载更多的footer item,header item；默认是一个
     */
    public void setOtherItemCount(int otherItemCount) {
        this.otherItemCount = otherItemCount;
        isSetOtherItemCount = true;//是否调用过setOtherItemCount()方法；
    }

    public EmptyWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean isEmpty() {
        if (!isSetOtherItemCount) {
            throw new NullPointerException("RecycleEmptyErrorView 必须调用 setOtherItemCount 方法");
        }
        L.i("=========--------1");
        if (isFirstSetAdapter) {
            L.i("=========--------2");
            isFirstSetAdapter = false;
        }
        L.i("=========--------3");
            return false;
//        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() <= otherItemCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()) {
            L.i("=========--------4");
            ViewHolder holder;
            if (mEmptyView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        L.i("=========--------5");
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        L.i("=========--------6");
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isEmpty()) {
                    L.i("=========--------7");
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                L.i("=========--------8");
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        L.i("=========--------9");
        if (isEmpty()) {
            L.i("=========--------10");
            WrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        L.i("=========--------11");
        if (isEmpty()) {
            L.i("=========--------12");
            return ITEM_TYPE_EMPTY;
        }
        L.i("=========--------13");
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        L.i("=========--------14");
        if (isEmpty()) {
            L.i("=========--------15");
            return;
        }
        L.i("=========--------16");
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
////        if (isEmpty()) return 1;
        return mInnerAdapter.getItemCount();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }
}
