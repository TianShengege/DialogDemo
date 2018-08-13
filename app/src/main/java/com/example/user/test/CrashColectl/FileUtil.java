package com.example.user.test.CrashColectl;

import android.os.Environment;

public class FileUtil {
    public static boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
