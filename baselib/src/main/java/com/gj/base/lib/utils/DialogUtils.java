package com.gj.base.lib.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 弹框-统一样式：PositiveButton 橙色，AppTheme已经设置，NegativeButton #494949色
 * Created by yubaokang on 16-7-23.
 */
public class DialogUtils {

    /**
     * @param context
     * @param title       标题
     * @param negativeTxt 否定性质的操作
     * @param positiveTxt 确定性质的操作
     * @param listener
     */
    public static void showDialog(Context context, String title, String negativeTxt, String positiveTxt, final ButtonClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(title)
                .setCancelable(true)
                .setNegativeButton(negativeTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        listener.negativeButton();
                    }
                })
                .setPositiveButton(positiveTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        listener.positiveButton();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        //AlertDialog创建之后才可以修改Button信息
//        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color._494949));
    }


    public interface ButtonClickListener {
        void positiveButton();//操作按钮

        void negativeButton();//取消操作按钮
    }

    public static abstract class ButtonClickListenerAbstract implements ButtonClickListener {
        @Override
        public void negativeButton() {
        }
    }
}
