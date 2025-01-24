package com.example.todolistapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*
  データベースを管理する
 */
@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;
    public static final String DATABASE_NAME = "mydb";

    public abstract TaskDao taskDao();

    public static AppDatabase getInstance(final Context context){
        if(sInstance == null){
            synchronized(AppDatabase.class){
                if(sInstance == null)
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
                }
            }

        return sInstance;
        }
    }

