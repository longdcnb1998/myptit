package com.example.myptit.view.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myptit.R;
import com.example.myptit.control.cache.CacheManager;
import com.example.myptit.util.Logger;
import com.example.myptit.view.fragment.CategoryFragment;
import com.example.myptit.view.fragment.SplashFragment;

public class MainActivity extends BaseActivity {
    private SplashFragment splashFragment;
    private CategoryFragment categoryFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CacheManager.getInstance().init(this);
        Logger.getInstance().enableAllLog();

        fragmentManager = getSupportFragmentManager();
        addSplashFragment();
    }

    private void addSplashFragment() {
        if (splashFragment == null) {
            splashFragment = SplashFragment.newInstance(() -> {
                addFragmentCat();
                removeSplashFragment();
            });
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.overFragment, splashFragment);
        fragmentTransaction.commit();
    }

    private void removeSplashFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.remove(splashFragment);
        fragmentTransaction.commit();
    }

    private void addFragmentCat() {
        if (categoryFragment == null) {
            categoryFragment = CategoryFragment.newInstance(new CategoryFragment.Callback() {
            });
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.belowFragment, categoryFragment);
        fragmentTransaction.commit();
    }
}
