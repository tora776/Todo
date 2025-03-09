package com.example.todolistapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tasks",
        foreignKeys = @ForeignKey(
                entity = CategoryEntity.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int mTaskId;

    @ColumnInfo(name = "task_text")
    private String mTaskText;

    @ColumnInfo(name = "category_id", index = true)
    private int mCategoryId;

    public int getTaskId() {
        return mTaskId;
    }

    public void setTaskId(int mTaskId) {
        this.mTaskId = mTaskId;
    }

    public String getTaskText() {
        return mTaskText;
    }

    public void setTaskText(String mTaskText) {
        this.mTaskText = mTaskText;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int mCategoryId) {
        this.mCategoryId = mCategoryId;
    }
}
