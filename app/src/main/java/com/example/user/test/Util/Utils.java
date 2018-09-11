package com.example.user.test.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import com.example.user.test.Handler.XYHandler;

public class Utils {

    public static String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    public static int getDialgWidth(Context context) {
        int width = -1;
        if (context instanceof Activity) {
            int ori = context.getResources().getConfiguration().orientation;
            int windowWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();

            if (Configuration.ORIENTATION_PORTRAIT == ori) { // 竖屏
                width = (int) (windowWidth * 0.9);

            }
            else if (windowWidth > 799) {
                width = 533 * windowWidth / 800;
            }

        }
        return 1 > width ? 440 : width;
    }

    /***
     * 获取各种 view 的大小
     *
     * @param context
     * @return
     */
    public static int getViewWidth(Context context) {
        int width = -1;
        if (context instanceof Activity) {

            int windowWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
            int windowHight = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();


            width = (int) (26 * Math.min(windowWidth, windowHight) / 480);
        }
        return 1 > width ? 24 : width;
    }

    /**
     * 异步耗时操作,直接在主线程引用
     */
    private  void DelayClos(){
        XYHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },20000);
    }
}
