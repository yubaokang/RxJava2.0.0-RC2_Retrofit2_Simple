package com.gj.base.lib.adapter.my;

import android.content.Context;

import com.gj.base.lib.adapter.CommonAdapter;
import com.gj.base.lib.adapter.MultiItemTypeAdapter;
import com.gj.base.lib.adapter.base.ViewHolder;
import com.gj.base.lib.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * LoadMoreAdapter = CommonAdapter + LoadMoreWrapper ; // 加载更多
 * Created by yubaokang on 2016/8/31.
 */
public abstract class LoadMoreAdapter<T> {
    private CommonAdapter<T> adapter;
    private LoadMoreWrapper loadMoreWrapper;

    public LoadMoreAdapter(Context context, int layoutId, List<T> datas) {
        adapter = new CommonAdapter<T>(context, layoutId, datas) {
            @Override
            protected void convert(ViewHolder holder, T t, int position) {
                LoadMoreAdapter.this.convert(holder, t, position);
            }
        };
        loadMoreWrapper = new LoadMoreWrapper(adapter);
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

    public abstract void convert(ViewHolder holder, T t, int position);
}
