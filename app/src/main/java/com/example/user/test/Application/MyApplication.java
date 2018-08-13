package com.example.user.test.Application;

import android.app.Application;

import com.example.user.test.CrashColectl.CrashHandler;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
