package com.gj.base.lib.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gj.base.lib.loading.VaryViewHelperController;
import com.gj.base.lib.mvp.BaseView;

import butterknife.ButterKnife;


/**
 * Created by ybk on 2016/3/1.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    protected VaryViewHelperController mVaryViewHelperController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (getCurrentLayout() == null) {
            throw new NullPointerException("getCurrentLayout() 不能返回 null");
        }
        mVaryViewHelperController = new VaryViewHelperController(getCurrentLayout());
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiCancel();
    }

    public abstract void init(Bundle savedInstanceState);//初始化操作

    public abstract int getLayoutId();//获取布局id

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
