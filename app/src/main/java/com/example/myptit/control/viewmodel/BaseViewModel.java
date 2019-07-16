package com.example.myptit.control.viewmodel;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.example.myptit.control.cache.AppData;
import com.example.myptit.control.cache.CacheManager;
import com.example.myptit.control.local.other.PreferenceManager;

public class BaseViewModel extends ViewModel implements AppData {
    protected Resources resources;
    protected CacheManager cacheManager;
    protected PreferenceManager preferenceManager;

    public void init(Context context) {
        resources = context.getResources();
        cacheManager = CacheManager.getInstance();
        preferenceManager = new PreferenceManager(context);
    }
}
