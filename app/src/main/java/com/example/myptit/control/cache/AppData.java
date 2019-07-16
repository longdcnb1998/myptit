package com.example.myptit.control.cache;

public interface AppData {
    interface Asset {
        String ROOT = "file:///android_asset/";
    }

    interface Structure {
        String FOLDER_LOGO = "logo";
        String FOLDER_DATA = "data";
        String SPLASH_ROCKET = "Splash.html";
        String DATA_DB = "qas.db";
    }

    interface grantPermission {
        int WRITE_STORAGE = 0x000001;
    }
}
