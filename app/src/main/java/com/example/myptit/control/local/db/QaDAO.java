package com.example.myptit.control.local.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myptit.model.enity.QA;

import java.util.List;

@Dao
public interface QaDAO {
    @Insert
    void insert(QA item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserts(List<QA> items);

    @Query("SELECT * from qa")
    List<QA> get();

    @Query("SELECT * from qa WHERE s=:filter")
    List<QA> getByType(String filter);
}
