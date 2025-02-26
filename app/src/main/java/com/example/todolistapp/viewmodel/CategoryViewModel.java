package com.example.todolistapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.todolistapp.db.TaskDao;
import com.example.todolistapp.db.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


/*
    タスクのデータをデータレイヤーから取得して保持する
    UIに対して公開する
*/
public class CategoryViewModel extends AndroidViewModel {
    private TaskDao mTaskDao;
    private Flowable<List<TaskEntity>> mTasks;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mTaskDao = ((AppComponent) application).getDatabase().taskDao();
        mTasks = mTaskDao.getAll(); // Flowable でタスクのリストを管理
    }

    public Flowable<List<String>> getCategoryTextList() {
        return mTasks
                .map(tasks -> tasks.stream()
                        .map(TaskEntity::getText)
                        .collect(Collectors.toList()));
    }

    public Completable insertCategory(String text) {
        TaskEntity task = new TaskEntity();
        task.setText(text);
        return mTaskDao.insert(task);
    }

    public Completable updateCategory(int position, String text) {
        return mTasks.firstOrError() // 最新のタスクリストを取得
                .flatMapCompletable(tasks -> {
                    if (position < 0 || position >= tasks.size()) {
                        return Completable.error(new IndexOutOfBoundsException("Invalid position"));
                    }
                    TaskEntity task = tasks.get(position);
                    task.setText(text);
                    return mTaskDao.update(task);
                });
    }

    public Completable deleteCategory(int position) {
        return mTasks.firstOrError()
                .flatMapCompletable(tasks -> {
                    if (position < 0 || position >= tasks.size()) {
                        return Completable.error(new IndexOutOfBoundsException("Invalid position"));
                    }
                    return mTaskDao.delete(tasks.get(position));
                });
    }
}