package com.example.myptit.util;

import android.text.TextUtils;
import android.util.Log;

public class Logger {
    private final String TAG = Logger.class.getSimpleName();
    private Type logType = Type.ALL;

    private static Logger instance;

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public Logger() {
    }

    public void setLogType(Type logType) {
        this.logType = logType;
    }

    public void enableAllLog() {
        this.logType = Type.ALL;
    }

    public void disableAllLog() {
        this.logType = Type.OFF;
    }

    public void d(String msg) {
        d(TAG, msg);
    }

    public void d(String tag, String msg) {
        if (logType.getLevel() >= Type.ALL.getLevel() &&
                !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public void v(String msg) {
        v(TAG, msg);
    }

    public void v(String tag, String msg) {
        if (logType.getLevel() >= Type.SEVERE.getLevel() &&
                !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }

    public void i(String msg) {
        i(TAG, msg);
    }

    public void i(String tag, String msg) {
        if (logType.getLevel() >= Type.FINE.getLevel() &&
                !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public void w(String msg) {
        w(TAG, msg);
    }

    public void w(String tag, String msg) {
        if (logType.getLevel() >= Type.WARNING.getLevel() &&
                !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public void e(String msg) {
        e(TAG, msg);
    }

    public void e(String tag, String msg) {
        if (logType.getLevel() >= Type.ERROR.getLevel() &&
                !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }


    public enum Type {
        ALL(5),
        SEVERE(4),
        FINE(3),
        WARNING(2),
        ERROR(1),
        OFF(0);

        private int level;

        Type(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
