package com.example.myptit.control.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class CacheManager {
    public AppInfo appInfo;

    public static CacheManager instance;

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public CacheManager() {
        appInfo = new AppInfo();
    }

    public void init(Context context) {
        appInfo.appPackage = context.getPackageName();
        if (Environment.getExternalStorageState() == null) {
            appInfo.appInternalStoragePath = String.format("%s/data/data/%s", Environment.getDataDirectory(), appInfo.appPackage);
        } else {
            appInfo.appInternalStoragePath = String.format("%s/data/data/%s", Environment.getExternalStorageDirectory(), appInfo.appPackage);
        }
        File file = new File(appInfo.appInternalStoragePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
