package com.example.myptit.control.local.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myptit.model.enity.Category;
import com.example.myptit.model.enity.QA;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT * from cat")
    List<Category> get();
}
