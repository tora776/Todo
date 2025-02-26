package com.example.todolistapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/*
  データベースを管理する
 */
@Database(entities = {CategoryEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;
    public static final String DATABASE_NAME = "mydb";
    public abstract CategoryDao categoryDao();

    public static AppDatabase getInstance(final Context context){
        if(sInstance == null){
            synchronized(AppDatabase.class) {
                sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
        }
    }

