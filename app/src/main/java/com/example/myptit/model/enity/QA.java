package com.example.myptit.model.enity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "qa")
public class QA {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "id")
    public Integer id;

    @SerializedName("question")
    @Expose
    @ColumnInfo(name = "question")
    public String question;

    @SerializedName("s")
    @Expose
    @ColumnInfo(name = "s")
    public Integer s;

    @SerializedName("a")
    @Expose
    @ColumnInfo(name = "a")
    public String a;

    @SerializedName("b")
    @Expose
    @ColumnInfo(name = "b")
    public String b;

    @SerializedName("c")
    @Expose
    @ColumnInfo(name = "c")
    public String c;

    @SerializedName("d")
    @Expose
    @ColumnInfo(name = "d")
    public String d;

    @SerializedName("answer")
    @Expose
    @ColumnInfo(name = "answer")
    public String answer;
}
