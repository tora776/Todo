package com.example.todolistapp;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.todolistapp.db.AppDatabase;
import com.example.todolistapp.db.TaskDao;
import com.example.todolistapp.db.TaskEntity;

import java.io.IOException;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseReadWriteTest {
    private TaskDao taskDao;
    private AppDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        taskDao = db.taskDao();
    }

    @After
    public void closeDb() throws IOException{
        db.close();
    }

    @Test
    public void insertTest() {
        // Context of the app under test.
        TaskEntity task = new TaskEntity();
        task.setId(0);
        task.setText("aaa");
        taskDao.insert(task).blockingAwait();

        taskDao.getAll()
                .test()
                .assertValue(tasks -> tasks.size() == 1);
    }

    @Test
    public void deleteTest(){
        TaskEntity task = new TaskEntity();
        task.setId(1);
        task.setText("aaa");
        taskDao.insert(task).blockingAwait();

        taskDao.delete(task).blockingAwait();

        taskDao.getAll()
                .test()
                .assertValue(tasks -> tasks.isEmpty());
    }
}