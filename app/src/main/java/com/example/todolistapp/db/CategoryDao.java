package com.example.todolistapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

/*
    タスクのエンティティにアクセスする
    読み取り、追加、削除
 */

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Categories")
    Flowable<List<CategoryEntity>> getAll();

    @Insert
    Completable insert(CategoryEntity category);

    @Update
    Completable update(CategoryEntity category);

    @Delete
    Completable delete(CategoryEntity category);
}
