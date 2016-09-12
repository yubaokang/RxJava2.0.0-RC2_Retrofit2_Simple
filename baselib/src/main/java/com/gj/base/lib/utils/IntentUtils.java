package com.gj.base.lib.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {
    //拨号界面
    public static void IntentCall(final Activity activity, final String phone) {
        DialogUtils.showDialog(activity, "是否拨打 " + phone + " ?", "取消", "确定", new DialogUtils.ButtonClickListenerAbstract() {
            @Override
            public void positiveButton() {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    T.show(activity, "设备不支持拨打电话");
                }
            }
        });
    }

    //跳转设置wifi界面
    public static void IntentNetWorkSetting(Activity activity) {
        try {
            Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
            activity.startActivity(wifiSettingsIntent);
        } catch (Exception ignored) {
        }
    }
}
