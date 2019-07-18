package com.example.myptit.control.viewmodel;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;

import com.example.myptit.control.local.db.AppDatabase;
import com.example.myptit.control.local.db.CategoryDAO;
import com.example.myptit.model.enity.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryViewModel extends BaseViewModel {
    private final String TAG = CategoryViewModel.class.getSimpleName();

    private MediatorLiveData<List<Category>> liveCategories;

    public MediatorLiveData<List<Category>> getLiveCategories() {
        if (liveCategories == null) {
            liveCategories = new MediatorLiveData<>();
        }
        return liveCategories;
    }

    public CategoryViewModel() {
        super();
    }

    public void loadData(final Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        CategoryDAO dao = db.categoryDAO();
        List<Category> categories = dao.get();
        if (categories != null && categories.size() > 0) {
            Map<Integer, Category> result = new HashMap<>();
            for (int i = 0; i < categories.size(); i++) {
                Category item = categories.get(i);
                if (item == null) {
                    continue;
                }
                int index = item.type / 100;
                int sub = item.type % 100;
                if (result.containsKey(index)) {
                    Category temp = result.get(index);
                    if (temp.sub == null) {
                        temp.sub = new ArrayList<>();
                    }
                    temp.sub.add(item);
                    result.put(index, temp);
                } else {
                    result.put(index, item);
                }
            }
            liveCategories.postValue(new ArrayList(result.values()));
        }
    }
}
