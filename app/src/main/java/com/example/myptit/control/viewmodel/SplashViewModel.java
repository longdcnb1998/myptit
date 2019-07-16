package com.example.myptit.control.viewmodel;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.lifecycle.MediatorLiveData;

import com.example.myptit.R;
import com.example.myptit.control.local.db.AppDatabase;
import com.example.myptit.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashViewModel extends BaseViewModel {
    private final String TAG = SplashViewModel.class.getSimpleName();
    private final int STEP_COUNT = 5;

    private MediatorLiveData<String> liveLoadingContent;
    private MediatorLiveData<Integer> liveLoadingProgress;

    public MediatorLiveData<String> getLiveLoadingContent() {
        if (liveLoadingContent == null) {
            liveLoadingContent = new MediatorLiveData<>();
        }
        return liveLoadingContent;
    }

    public MediatorLiveData<Integer> getLiveLoadingProgress() {
        if (liveLoadingProgress == null) {
            liveLoadingProgress = new MediatorLiveData<>();
        }
        return liveLoadingProgress;
    }

    public SplashViewModel() {
        super();
    }

    public void loadData(final Context context) {
        new Thread(() -> {
            try {
                float wave = 100 / STEP_COUNT;

                // start
                Logger.getInstance().d(TAG, "Initialize : Start");
                liveLoadingContent.postValue("");

                // check copy data to internal storage
                liveLoadingContent.postValue(resources.getString(R.string.splash_notice_init_data));
                liveLoadingProgress.postValue(1);
                int currentVersion = preferenceManager.getDataVersion();
                if (currentVersion == -1) {
                    Logger.getInstance().d(TAG, "Initialize : Need copy file from asset");
                    AssetManager assetManager = resources.getAssets();
                    String[] files = new String[]{
                            Structure.FOLDER_DATA + "/" + Structure.DATA_DB
                    };

                    Logger.getInstance().d(TAG, "Initialize : Clear file if exists");
                    File outDir = new File(cacheManager.appInfo.appInternalStoragePath);
                    if (outDir.isDirectory()) {
                        String[] children = outDir.list();
                        for (int i = 0; i < children.length; i++) {
                            new File(outDir, children[i]).delete();
                        }
                    }

                    Logger.getInstance().d(TAG, "Initialize : Copying...");
                    for (String file : files) {
                        InputStream in = null;
                        OutputStream out = null;
                        try {
                            in = assetManager.open(file);
                            File outFile = new File(cacheManager.appInfo.appInternalStoragePath, Structure.DATA_DB);
                            out = new FileOutputStream(outFile);
                            byte[] buffer = new byte[1024];
                            int read;
                            while ((read = in.read(buffer)) != -1) {
                                out.write(buffer, 0, read);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    Logger.getInstance().d(TAG, "Initialize : Save local version");
                    preferenceManager.saveDataVersion(currentVersion);
                } else {
                    Logger.getInstance().d(TAG, "Initialize : Already copy file from asset");
                }

                // TODO : check version current in internal storage
                liveLoadingContent.postValue(resources.getString(R.string.splash_notice_check_new_version));
                liveLoadingProgress.postValue((int) (wave * 1));
                Logger.getInstance().d(TAG, "Initialize : Query to get config");
                int newestVersion = currentVersion; // Request http get config, replace for this sentence
                if (newestVersion > currentVersion) {
                    // TODO : download new version if exists
                    Logger.getInstance().d(TAG, "Initialize : Download newest version");
                    liveLoadingContent.postValue(resources.getString(R.string.splash_notice_download_new_version));
                    liveLoadingProgress.postValue((int) (wave * 2));

                    // TODO : replace data with new version if exists
                    Logger.getInstance().d(TAG, "Initialize : Replace with newest data");
                    liveLoadingContent.postValue(resources.getString(R.string.splash_notice_init_new_version));
                    liveLoadingProgress.postValue((int) (wave * 3));

                    Logger.getInstance().d(TAG, "Initialize : Save local version");
                    preferenceManager.saveDataVersion(currentVersion);
                } else {
                    Logger.getInstance().d(TAG, "Initialize : Already newest version");
                }

                Logger.getInstance().d(TAG, "Initialize : Preparing data ...");
                liveLoadingContent.postValue(resources.getString(R.string.splash_notice_prepare));
                liveLoadingProgress.postValue((int) (wave * 4));
                AppDatabase.getDatabase(context);

                Logger.getInstance().d(TAG, "Initialize : Complete");
                liveLoadingProgress.postValue(100);
            } catch (Exception e) {
                e.printStackTrace();
                liveLoadingProgress.postValue(-1);
            }
        }).run();
    }
}
