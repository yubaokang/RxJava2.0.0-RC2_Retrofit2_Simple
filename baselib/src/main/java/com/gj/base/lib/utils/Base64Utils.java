package com.gj.base.lib.utils;
import android.util.Base64;

/**
 * Created by hank on 2016/1/20/15:15:49
 */
public class Base64Utils {
   //   // 加密传入的数据是byte类型的，并非使用decode方法将原始数据转二进制，String类型的数据 使用 str.getBytes()即可
   //   String str = "Hello!";
   //   // 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
   //   String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
   //   Log.i("Test", "encode >>>" + strBase64);
   //
   //   // 这里 encodeToString 则直接将返回String类型的加密数据
   //   String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
   //   Log.i("Test", "encodeToString >>> " + enToStr);
   //
   //   // 对base64加密后的数据进行解密
   //   Log.i("Test", "decode >>>" + new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT)));

   /**
    * base64加密
    *
    * @param str 需要加密的原文本
    * @return
    */
   public static String encrypt(String str) {
      return new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
   }

   /**
    * 解密
    *
    * @param base64Str base64加密文件
    * @return
    */
   public static String decipher(String base64Str) {
      return new String(Base64.decode(base64Str.getBytes(), Base64.DEFAULT));
   }

}
