package com.gj.base.lib.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gj.base.lib.loading.VaryViewHelperController;
import com.gj.base.lib.mvp.BaseView;

import butterknife.ButterKnife;


/**
 * Created by Hank on 2016/3/3 17:27.
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    protected VaryViewHelperController mVaryViewHelperController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        if (getCurrentLayout() == null) {
            throw new NullPointerException("getCurrentLayout() 不能返回 null");
        }
        mVaryViewHelperController = new VaryViewHelperController(getCurrentLayout());
        init(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        apiCancel();
    }

    public abstract int getLayoutId();

    public abstract void init(View view, @Nullable Bundle savedInstanceState);

    public abstract void apiCancel();//销毁界面时，取消所有请求

    public abstract View getCurrentLayout();

    @Override
    public void showLoading() {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.showLoading();
        }
    }

    @Override
    public void showNetWork(View.OnClickListener clickListener) {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.showNetworkError(clickListener);
        }
    }

    @Override
    public void showEmpty() {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.showEmpty("没有数据");
        }
    }

    @Override
    public void restore() {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.restore();
        }
    }
}
