package com.example.myptit.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.myptit.R;
import com.example.myptit.control.viewmodel.CategoryViewModel;
import com.example.myptit.databinding.FragmentCategoryBinding;
import com.example.myptit.util.Helper;
import com.example.myptit.view.adapter.CategoryAdapter;
import com.example.myptit.view.adapter.CategoryAdapterTransformer;

public class CategoryFragment extends BaseFragment {
    private CategoryViewModel viewModel;
    private FragmentCategoryBinding binding;
    private Callback callback;

    public static CategoryFragment newInstance(Callback callback) {
        CategoryFragment fragment = new CategoryFragment();
        fragment.init(callback);
        return fragment;
    }

    public void init(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        WebSettings settings = binding.wvBackground.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        binding.wvBackground.loadUrl(Asset.ROOT + Structure.FOLDER_LOGO + "/" + Structure.SPLASH_ROCKET);

        CategoryAdapter adapter = new CategoryAdapter(context, new CategoryAdapter.Callback() {
        });
        CategoryAdapterTransformer transformer = new CategoryAdapterTransformer(context);
        binding.vpCategory.setPageMargin(Helper.convertDip2Pixels(context, 24));
        binding.vpCategory.setAdapter(adapter);
        binding.vpCategory.setPageTransformer(false, transformer);

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        viewModel.init(getContext());
        viewModel.getLiveCategories().observe(this, data -> {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
            binding.vpCategory.setCurrentItem(1);
            binding.vpCategory.setOffscreenPageLimit(data.size());
        });
        viewModel.loadData(context);
    }

    public interface Callback {
    }
}
