package com.example.myptit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myptit.R;
import com.example.myptit.control.viewmodel.QuizViewModel;
import com.example.myptit.databinding.ActivityQuizBinding;
import com.example.myptit.databinding.ItemQuizAnsLeftBinding;
import com.example.myptit.databinding.ItemQuizAnsRightBinding;
import com.example.myptit.model.enity.QA;
import com.example.myptit.util.Helper;

import java.util.zip.Inflater;

public class QuizActivity extends BaseActivity {
    private ActivityQuizBinding binding;
    private QuizViewModel viewModel;

    public static void start(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);

        WebSettings settings = binding.wvBackground.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        binding.wvBackground.loadUrl(Asset.ROOT + Structure.FOLDER_LOGO + "/" + Structure.BACKGROUND_STAR);

        viewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        viewModel.init(this);
        viewModel.getLiveQas().observe(this, data -> {
            try {
                QA qa = data.get(0);
                fillQuestion(qa, 0, data.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        viewModel.loadData(this);
    }

    private void fillQuestion(QA qa, int position, int total) {
//        binding.tvTitle.setText(String.format(resources.getString(R.string.quiz_title), (position + 1) + "", total + ""));

        binding.tvQuestion.setText(qa.question);

        binding.layoutAnswer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = Helper.convertDip2Pixels(this, 16);
        LayoutInflater inflater = getLayoutInflater();
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String text = i == 0 ? qa.a : i == 1 ? qa.b : i == 2 ? qa.c : qa.d;
            if (!TextUtils.isEmpty(text)) {
                if (count % 2 == 0) {
                    ItemQuizAnsLeftBinding leftBinding = DataBindingUtil.inflate(inflater, R.layout.item_quiz_ans_left, null, false);
                    leftBinding.tvContent.setText(text);
                    binding.layoutAnswer.addView(leftBinding.getRoot(), params);
                } else {
                    ItemQuizAnsRightBinding rightBinding = DataBindingUtil.inflate(inflater, R.layout.item_quiz_ans_right, null, false);
                    rightBinding.tvContent.setText(text);
                    binding.layoutAnswer.addView(rightBinding.getRoot(), params);
                }
                count++;
            }
        }
    }
}
