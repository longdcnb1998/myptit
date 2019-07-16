package com.example.myptit.control.local.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myptit.control.cache.AppData;
import com.example.myptit.control.cache.CacheManager;
import com.example.myptit.model.enity.QA;
import com.example.myptit.util.Logger;

@Database(entities = {QA.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();

    public abstract QaDAO QaDAO();

    private static volatile AppDatabase instance;

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    String path = CacheManager.getInstance().appInfo.appInternalStoragePath;
                    String name = path + "/" + AppData.Structure.DATA_DB;
                    instance = Room.databaseBuilder(context, AppDatabase.class, name)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Logger.getInstance().d(TAG, "AppDatabase onCreate");
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    Logger.getInstance().d(TAG, "AppDatabase onOpen");
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }
}