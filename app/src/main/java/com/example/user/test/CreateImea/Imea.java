package com.example.user.test.CreateImea;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.telephony.TelephonyManager;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;


import static android.content.Context.TELEPHONY_SERVICE;

public class Imea {

    /**
     * Android6.0和7.0需要主动拉起权限申请，获取手机imea码
     * @param activity
     */
    public void getImea(final Activity activity){
        AndPermission.with(activity)
                .runtime()
                .permission(Permission.READ_PHONE_STATE)
                .onGranted(new Action<List<String>>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onAction(List<String> data) {
                        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE);
                        String imei = telephonyManager.getDeviceId();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                    }
                })
                .start();
    }

}
