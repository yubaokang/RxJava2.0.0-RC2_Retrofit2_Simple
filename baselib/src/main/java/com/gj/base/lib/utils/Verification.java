package com.gj.base.lib.utils;
import android.app.Activity;
import android.text.TextUtils;

/**
 * 公共校验类
 */
public class Verification {

   private Activity activity;

   public Verification(Activity activity) {
      this.activity = activity;
   }

   /**
    * 校验手机号码
    *
    * @param mobileNo
    * @return true:验证失败
    */
   public static boolean isMobile(String mobileNo) {
      if (TextUtils.isEmpty(mobileNo)) {
         return false;
      }
      if (mobileNo.matches("^(1)\\d{10}$")) {
         return true;
      }
      return false;
   }

   /**
    * 校验身份证号码
    *
    * @param idCard
    * @return true:验证失败
    */
   public static boolean isIdCard(String idCard) {
      if (TextUtils.isEmpty(idCard)) {
         return true;
      }
      if (!idCard.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
         return true;
      }
      return false;
   }
}
