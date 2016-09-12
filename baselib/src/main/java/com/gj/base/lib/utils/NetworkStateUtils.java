package com.gj.base.lib.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hank on 2015/7/27.
 * 用于检测网路连接状态
 */
public class NetworkStateUtils {

   public static final int TYPE_NONE = -1;

   public static final int TYPE_MOBILE = 0;

   public static final int TYPE_WIFI = 1;

   private Context context;
   private ConnectivityManager manager;
   private NetworkInfo info;

   public static NetworkStateUtils networkState;

   public NetworkStateUtils(Context context) {
      this.context = context;
      manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
   }

   public static NetworkStateUtils getInstance(Context context) {
      if (networkState == null) {
         networkState = new NetworkStateUtils(context);
      }
      return networkState;
   }

   public int getType() {
      info = manager.getActiveNetworkInfo();
      if (info == null) {
         return TYPE_NONE;
      }
      return info.getType();
   }

   public boolean isConnection() {
      return getType() != TYPE_NONE;
   }

   //判断当前网络是否是wifi网络
   public boolean isWifi(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
      if (activeNetInfo != null
              && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
         return true;
      }
      return false;
   }

   // 网络是否可用
   public boolean isNetworkAvailable(Context context) {
      ConnectivityManager connectivity = (ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivity == null) {

      } else {
         NetworkInfo[] info = connectivity.getAllNetworkInfo();
         if (info != null) {
            for (int i = 0; i < info.length; i++) {
               if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   // 判断当前网络是否是3G网络
   public boolean is3G(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
      if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
         return true;
      }
      return false;
   }
}
