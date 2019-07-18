package com.example.myptit.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myptit.R;
import com.example.myptit.databinding.ItemCategoryFullBinding;
import com.example.myptit.databinding.ItemCategorySubBinding;
import com.example.myptit.model.enity.Category;
import com.example.myptit.view.activity.QuizActivity;

import java.util.List;

public class CategoryAdapter extends PagerAdapter {
    private List<Category> data;
    private Callback callback;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, Callback callback) {
        this.callback = callback;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Category item = data.get(position);
        ItemCategoryFullBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_category_full, container, false);
        Context context = binding.getRoot().getContext();

        binding.tvTitle.setText(item.title);
        binding.btAll.setOnClickListener(view -> {
            QuizActivity.start(context);
        });

        binding.sub.removeAllViews();
        if (item.sub != null && item.sub.size() > 0) {
            for (int i = 0; i < item.sub.size(); i++) {
                Category sub = item.sub.get(i);
                if (sub == null) {
                    continue;
                }
                ItemCategorySubBinding subBinding = DataBindingUtil.inflate(inflater, R.layout.item_category_sub, container, false);
                subBinding.getRoot().setId(View.generateViewId());

                subBinding.tvTitle.setText(sub.title);

                binding.sub.addView(subBinding.getRoot());
            }
        }

        container.addView(binding.getRoot(), 0);
        return binding.getRoot();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    public interface Callback {
    }
}
