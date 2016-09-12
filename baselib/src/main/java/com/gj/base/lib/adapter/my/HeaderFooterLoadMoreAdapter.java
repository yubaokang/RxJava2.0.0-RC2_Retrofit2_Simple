package com.gj.base.lib.adapter.my;

import android.content.Context;
import android.view.View;

import com.gj.base.lib.adapter.CommonAdapter;
import com.gj.base.lib.adapter.MultiItemTypeAdapter;
import com.gj.base.lib.adapter.base.ViewHolder;
import com.gj.base.lib.adapter.wrapper.HeaderAndFooterWrapper;
import com.gj.base.lib.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * HeaderFooterLoadMoreAdapter = CommonAdapter + HeaderAndFooterWrapper + LoadMoreWrapper ; //header + footer + 加载更多(没有更多)
 * Created by yubaokang on 2016/8/31.
 */
public abstract class HeaderFooterLoadMoreAdapter<T> {
    private CommonAdapter<T> adapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private LoadMoreWrapper loadMoreWrapper;

    public HeaderFooterLoadMoreAdapter(Context context, int layoutId, List<T> datas) {
        adapter = new CommonAdapter<T>(context, layoutId, datas) {
            @Override
            protected void convert(ViewHolder holder, T t, int position) {
                HeaderFooterLoadMoreAdapter.this.convert(holder, t, position);
            }
        };
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        loadMoreWrapper = new LoadMoreWrapper(headerAndFooterWrapper);
    }

    public LoadMoreWrapper adapter() {
        return loadMoreWrapper;
    }

    public void setOnItemClickListener(MultiItemTypeAdapter.OnItemClickListener<T> itemClickListener) {
        adapter.setOnItemClickListener(itemClickListener);
    }

    public void setOnItemLongClickListener(MultiItemTypeAdapter.OnItemLongClickListener<T> itemLongClickListener) {
        adapter.setOnItemLongClickListener(itemLongClickListener);
    }

    public void setOnLoadMoreListener(LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener) {
        loadMoreWrapper.setOnLoadMoreListener(onLoadMoreListener);
    }

    public void noMore(boolean isNoMore) {
        loadMoreWrapper.noMore(isNoMore);
    }

    public void notifyDataSetChanged() {
        loadMoreWrapper.notifyDataSetChanged();
    }

    public void addHeaderView(View view) {
        headerAndFooterWrapper.addHeaderView(view);
    }

    public void addFootView(View view) {
        headerAndFooterWrapper.addFootView(view);
    }

    public abstract void convert(ViewHolder holder, T t, int position);
}
