package com.example.myptit.model.enity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "cat")
public class Category {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "id")
    public Integer id;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    public String title;

    @SerializedName("type")
    @Expose
    @ColumnInfo(name = "type")
    public Integer type;

    @Ignore
    public List<Category> sub;
}
