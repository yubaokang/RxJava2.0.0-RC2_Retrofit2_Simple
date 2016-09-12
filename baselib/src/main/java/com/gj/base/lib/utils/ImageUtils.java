package com.gj.base.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gj.base.lib.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by hank on 2015/7/6.
 */
public class ImageUtils {

    /**
     * 一般形式，一般默认图
     *
     * @param url
     * @param imageView
     */
    public static void showImage(String url, ImageView imageView) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.bg_def_normal)
                .crossFade()
                .into(imageView);
    }

    /**
     * 自定义默认图
     *
     * @param url         图片地址
     * @param imageView
     * @param placeHolder 默认图
     */
    public static void showImage(String url, ImageView imageView, int placeHolder) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeHolder)
                .crossFade()
                .into(imageView);
    }

    /**
     * 自定义默认图,资源图片模式FIX_CENTER,区分占位图模式
     *
     * @param url         图片地址
     * @param imageView
     * @param placeHolder 默认图
     */
    public static void showImageFitCenter(String url, ImageView imageView, int placeHolder) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeHolder)
                .crossFade()
                .fitCenter()
                .into(imageView);
    }

    /**
     * 没有默认图
     *
     * @param url       图片地址
     * @param imageView
     */
    public static void showImageNoPlaceHolder(String url, ImageView imageView) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(url)
                .crossFade()
                .into(imageView);
    }

    /**
     * 一般默认图的圆角imageView
     *
     * @param url
     * @param imageView
     * @param radius    圆角半径
     */
    public static void showImageCircle(String url, ImageView imageView, int radius) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.bg_def_normal)
                .crossFade()
                .bitmapTransform(new RoundedCornersTransformation(imageView.getContext(), radius, 0))
                .into(imageView);
    }

    /**
     * 带头像默认图的圆形imageView
     * 头像默认图 圆形
     *
     * @param url
     * @param imageView
     */
    public static void showImageCircleHead(String url, ImageView imageView, int headImgId) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_head_normal)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }

    /**
     * 加载本地的，一般默认图
     *
     * @param drawableInt 本地drawable图片资源
     * @param imageView
     */
    public static void showImage(int drawableInt, ImageView imageView) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(drawableInt)
                .placeholder(R.mipmap.bg_def_normal)
                .crossFade()
                .into(imageView);
    }

    /**
     * 加载本地的
     *
     * @param drawableInt 本地drawable图片资源
     * @param imageView
     * @param placeHolder 占位图
     */
    public static void showImage(int drawableInt, ImageView imageView, int placeHolder) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load(drawableInt)
                .placeholder(placeHolder)
                .crossFade()
                .into(imageView);
    }

    /**
     * @param filePath  图片sdcard 路径
     * @param imageView
     */
    public static void showImageFilePath(String filePath, ImageView imageView) {
        if (imageView == null) return;
        Context context = imageView.getContext();
        if (context == null) return;
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                Activity activity = (Activity) ((ContextWrapper) context).getBaseContext();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                    return;
                }
            }
        }
        Glide.with(context)
                .load("file://" + filePath)
                .placeholder(R.mipmap.bg_def_normal)
                .crossFade()
                .into(imageView);
    }


//    load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
//    load assets资源：load("file:///android_asset/f003.gif")
//    load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
//    load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
//    load ContentProvider资源：load("content://media/external/images/media/139469")
}
