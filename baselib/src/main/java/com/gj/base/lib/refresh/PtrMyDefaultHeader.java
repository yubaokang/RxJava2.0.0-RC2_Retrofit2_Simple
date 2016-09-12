package com.gj.base.lib.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gj.base.lib.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by yubaokang on 16-6-23.
 */
public class PtrMyDefaultHeader extends FrameLayout implements PtrUIHandler {
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;

    public PtrMyDefaultHeader(Context context) {
        super(context);
        initViews();
    }

    public PtrMyDefaultHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrMyDefaultHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    protected void initViews() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.my_default_header, this);
        textView = (TextView) header.findViewById(R.id.textView);
        imageView = (ImageView) header.findViewById(R.id.imageView);
        progressBar = (ProgressBar) header.findViewById(R.id.progressBar);
    }

    //step 1 下拉-未达到释放刷新的位置
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        textView.setText("下拉刷新");
        imageView.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch();
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch();
            }
        }
    }

    //step 2 下拉-达到了释放刷新的位置
    private void crossRotateLineFromTopUnderTouch() {
        textView.setText("释放刷新");
        imageView.startAnimation(getAnim1());
    }

    //step 2 达到释放刷新的位置继续往上移动到不可释放刷新的位置
    private void crossRotateLineFromBottomUnderTouch() {
        textView.setText("下拉刷新");
        imageView.startAnimation(getAnim2());
    }

    //step 3 正在刷新
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        textView.setText("正在刷新");
        imageView.clearAnimation();
        imageView.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    //step 4 刷新完成
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        textView.setText("刷新完成");
        progressBar.setVisibility(GONE);
    }

    //step 5 刷新完成之后，Header消失
    @Override
    public void onUIReset(PtrFrameLayout frame) {
    }

    RotateAnimation anim1;
    RotateAnimation anim2;

    public Animation getAnim1() {
        if (anim1 == null) {
            anim1 = new RotateAnimation(0.0f, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim1.setDuration(200);
            anim1.setFillAfter(true);
        }
        return anim1;
    }

    public Animation getAnim2() {
        if (anim2 == null) {
            anim2 = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim2.setDuration(200);
            anim2.setFillAfter(true);
        }
        return anim2;
    }
}
