package com.example.todolistapp.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
public class TaskViewModel extends AndroidViewModel {
    private TaskDao mTaskDao;
    private List<TaskEntity> mTasks;
    public TaskViewModel(@NonNull Application application){
        super(application);
        mTaskDao = ((AppComponent)application).getDatabase().taskDao();
    }

    public Flowable<List<String>> getTaskTextList(){
        return mTaskDao.getAll()
                // taskのリストをstringのリストに変換する
                .map(tasks -> {
                    mTasks = tasks;
                    return tasks.stream()
                            .map(task -> task.getText())
                            .collect(Collectors.toList());
                });
    }

    /**
        @param text タスクのテキスト
    */
    public Completable insertTask(String text){
        TaskEntity task = new TaskEntity();
        task.setText(text);
        return mTaskDao.insert(task);
    }

    // TODO:updateされない
    public Completable updateTask(int position, String text){
        TaskEntity task = new TaskEntity();
        task.setId(position);
        task.setText(text);
        System.out.print(task.getText());
        return mTaskDao.update(task);
    }

    /**
        @param position 削除したいタスクのリスト内のインデックス
     */
    public Completable deleteTask(int position){
        return mTaskDao.delete(mTasks.get(position));
    }

}
