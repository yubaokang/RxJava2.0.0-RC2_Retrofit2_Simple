package com.gj.base.lib.mvp;

import android.view.View;

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showLoading();

    void showNetWork(View.OnClickListener clickListener);

    void showEmpty();

    void restore();
}
