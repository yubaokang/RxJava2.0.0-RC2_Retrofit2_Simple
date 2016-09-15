package com.example.yubao.rxjavademo.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.example.yubao.rxjavademo.NotificationShowActivity;
import com.example.yubao.rxjavademo.R;

/**
 * Created by yubao on 2016/9/15.
 */
public class NotificationUtils {
    NotificationCompat.Builder builder;
    NotificationManager manager;
    private int notiId = 1;

    public void notification(Context context, int progress) {
        if (builder == null) {
            builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_head_normal)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_head_normal))
                    .setAutoCancel(false)//禁止用户点击删除按钮删除
                    .setOngoing(true)//禁止滑动删除
                    .setShowWhen(false);//取消右上角的时间显示
        }
        builder.setProgress(100, progress, false);
        if (progress == 100) {
            builder.setContentTitle("完成");
            builder.setOngoing(false);//下载完成可以滑动删除
            Intent intent = new Intent(context, NotificationShowActivity.class);
            intent.putExtra("extra", "通知的内容");
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(NotificationShowActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        } else {
            builder.setContentTitle("下载中..." + progress + "%");
        }
        manager.notify(notiId, builder.build());
    }
}
