package com.example.myptit.control.viewmodel;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;

import com.example.myptit.control.local.db.AppDatabase;
import com.example.myptit.control.local.db.CategoryDAO;
import com.example.myptit.control.local.db.QaDAO;
import com.example.myptit.model.enity.Category;
import com.example.myptit.model.enity.QA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizViewModel extends BaseViewModel {
    private final String TAG = QuizViewModel.class.getSimpleName();

    private MediatorLiveData<List<QA>> liveQas;

    public MediatorLiveData<List<QA>> getLiveQas() {
        if (liveQas == null) {
            liveQas = new MediatorLiveData<>();
        }
        return liveQas;
    }

    public QuizViewModel() {
        super();
    }

    public void loadData(final Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        QaDAO dao = db.qaDAO();
        List<QA> qas = dao.get();
        if (qas != null && qas.size() > 0) {
            liveQas.postValue(qas);
        }
    }
}
