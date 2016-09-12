package com.gj.base.lib.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2015/8/6.
 */
public class MD5Utils {

   public static  String getMD5Str(String md5Str){
      byte[] hash;
      try {
         hash = MessageDigest.getInstance("MD5").digest(md5Str.getBytes("UTF-8"));
      } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException("Huh, MD5 should be supported?", e);
      } catch (UnsupportedEncodingException e) {
         throw new RuntimeException("Huh, UTF-8 should be supported?", e);
      }

      StringBuilder hex = new StringBuilder(hash.length * 2);
      for (byte b : hash) {
         if ((b & 0xFF) < 0x10)
            hex.append("0");
         hex.append(Integer.toHexString(b & 0xFF));
      }
      return hex.toString();
   }

}
