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
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    Flowable<List<TaskEntity>> getAll();

    @Insert
    Completable insert(TaskEntity task);

    // @Query("UPDATE tasks SET text = :text WHERE id = :taskId")
    @Update
    Completable update(TaskEntity task);

    @Delete
    Completable delete(TaskEntity task);
}
