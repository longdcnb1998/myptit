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
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.myptit.R;
import com.example.myptit.control.viewmodel.SplashViewModel;
import com.example.myptit.databinding.FragmentSplashBinding;

public class SplashFragment extends BaseFragment {
    private SplashViewModel viewModel;
    private FragmentSplashBinding binding;
    private Callback callback;

    public static SplashFragment newInstance(Callback callback) {
        SplashFragment fragment = new SplashFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        WebSettings settings = binding.wvLogo.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        binding.wvLogo.loadUrl(Asset.ROOT + Structure.FOLDER_LOGO + "/" + Structure.SPLASH_ROCKET);

        viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        viewModel.init(getContext());
        viewModel.getLiveLoadingContent().observe(this, data -> {
            binding.tvLoading.setText(data);
        });
        viewModel.getLiveLoadingProgress().observe(this, data -> {
            if (data == -1) {
                // TODO : need retry
            } else if (data == 100) {
                // start app, close fragment
                binding.pbLoading.setProgress(data);
                if (callback != null) {
                    handler.postDelayed(() -> callback.complete(), 1500);
                }
            } else {
                binding.pbLoading.setProgress(data);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                viewModel.loadData(context);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, grantPermission.WRITE_STORAGE);
            }
        } else {
            viewModel.loadData(context);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case grantPermission.WRITE_STORAGE:
                    viewModel.loadData(getContext());
                    break;
                default:
                    break;
            }
        }
    }

    public interface Callback {
        void complete();
    }
}
