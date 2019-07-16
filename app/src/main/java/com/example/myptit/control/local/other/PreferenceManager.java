package com.example.myptit.control.local.other;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private String preferenceName;
    private SharedPreferences shared;

    public PreferenceManager(Context context) {
        preferenceName = context.getPackageName();
        shared = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public void saveDataVersion(int data) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("DataVersion", data);
        editor.commit();
    }

    public int getDataVersion() {
        return shared.getInt("DataVersion", -1);
    }
}
