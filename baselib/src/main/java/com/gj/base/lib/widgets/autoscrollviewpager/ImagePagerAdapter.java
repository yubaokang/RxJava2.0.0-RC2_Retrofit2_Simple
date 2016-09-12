/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.gj.base.lib.widgets.autoscrollviewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gj.base.lib.utils.ListUtil;

import java.util.List;

/**
 * 开启无限循环
 * Created by Hank on 2016/4/30.
 */
public abstract class ImagePagerAdapter<T> extends RecyclingPagerAdapter {

    private Context context;
    private List<T> listDatas;

    private int size;
    private boolean isInfiniteLoop;//是否开启无限循环
    private ImageView.ScaleType scaleType;

    /**
     * @param context
     * @param listDatas
     */
    public ImagePagerAdapter(Context context, List<T> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
        this.size = ListUtil.getCount(listDatas);
        //大于1时，启动轮播
        isInfiniteLoop = !(ListUtil.isNotEmpty(listDatas) && listDatas.size() == 1);
    }

    public ImagePagerAdapter(Context context, List<T> listDatas, ImageView.ScaleType scaleType) {
        this.context = context;
        this.listDatas = listDatas;
        this.size = ListUtil.getCount(listDatas);
        this.scaleType = scaleType;
        //大于1时，启动轮播
        isInfiniteLoop = !(ListUtil.isNotEmpty(listDatas) && listDatas.size() == 1);
    }

    /**
     * @param context
     * @param listDatas
     * @param isInfiniteLoop 是否开启无限循环  默认>1时开启
     */
    public ImagePagerAdapter(Context context, List<T> listDatas, boolean isInfiniteLoop) {
        this.context = context;
        this.listDatas = listDatas;
        this.size = ListUtil.getCount(listDatas);
        this.isInfiniteLoop = isInfiniteLoop;
    }

    public List<T> getListDatas() {
        return listDatas;
    }

    public int getSize() {
        return size;
    }

    public void setListDatas(List<T> listDatas) {
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtil.getCount(listDatas);
    }

    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            if (scaleType != null) {
                holder.imageView.setScaleType(scaleType);
            } else {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        setImageView(holder.imageView, listDatas.get(getPosition(position)), position);
        return view;
    }

    public abstract void setImageView(ImageView imageView, T t, int position);

    private static class ViewHolder {
        ImageView imageView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
