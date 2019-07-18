package com.example.myptit.view.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myptit.control.cache.AppData;

public class BaseActivity extends AppCompatActivity implements AppData {
    protected Resources resources;
    protected Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        init();
    }

    private void init() {
        if (resources == null) {
            resources = getResources();
        }
        if (handler == null) {
            handler = new Handler();
        }
    }
}
