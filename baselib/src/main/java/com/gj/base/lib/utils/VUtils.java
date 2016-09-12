package com.gj.base.lib.utils;

import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class VUtils {

   /**
    * 软键盘显示 隐藏
    *
    * @param editText 触发的输入框
    * @param isShow   true is show
    */
   public static void showHideSoftInput(final EditText editText, boolean isShow) {
      if (editText == null) {
         return;
      }
      if (isShow) {
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               editText.requestFocus();
               InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
               imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
               //imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
            }
         }, 100);
      } else {
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
         }, 100);
      }
   }

   interface keyBoardListener {
      void onKeyIsShow(boolean isShow);
   }
}
