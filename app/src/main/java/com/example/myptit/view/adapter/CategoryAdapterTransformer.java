package com.example.myptit.view.adapter;

import android.content.Context;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.myptit.util.Helper;

public class CategoryAdapterTransformer implements ViewPager.PageTransformer {
    private ViewPager viewPager;
    private int maxTranslateOffsetX;

    public CategoryAdapterTransformer(Context context) {
        maxTranslateOffsetX = Helper.convertDip2Pixels(context, 180);
    }

    @Override
    public void transformPage(View view, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }

        int leftInScreen = view.getLeft() - viewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);

        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
            //ViewCompat.setElevation(view, 0.0f);
        }
        ViewCompat.setElevation(view, scaleFactor);
    }
}