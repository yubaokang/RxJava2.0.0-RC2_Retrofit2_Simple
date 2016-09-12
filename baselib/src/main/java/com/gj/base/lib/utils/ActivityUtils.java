package com.gj.base.lib.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hank on 2015/11/10/16:16:54
 */
public class ActivityUtils {

   private ActivityUtils() {
   }

   private static ActivityUtils activityUtils;

   public synchronized static ActivityUtils getInstance() {
      if (activityUtils == null) {
         activityUtils = new ActivityUtils();
      }
      return activityUtils;
   }

   //运用list来保存们每一个activity是关键
   private List<Activity> mList = new LinkedList<>();

   // add Activity
   public void addActivity(Activity activity) {
      mList.add(activity);
   }

   //关闭每一个list内的activity
   public void exit() {
      try {
         for (Activity activity : mList) {
            if (activity != null)
               activity.finish();
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         System.exit(0);
      }
   }

   public void removeThis(Activity activity) {
      mList.remove(activity);
   }

   public void removeOther() {
      int num = mList.size();
      if (num <= 0) {
         return;
      }
      for (int i = 0; i < num - 1; i++) {
         mList.get(i).finish();
      }
   }
}
