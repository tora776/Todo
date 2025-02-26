package com.example.todolistapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.todolistapp.db.CategoryDao;
import com.example.todolistapp.db.CategoryEntity;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


/*
    タスクのデータをデータレイヤーから取得して保持する
    UIに対して公開する
*/
public class CategoryViewModel extends AndroidViewModel {
    private CategoryDao mCategoryDao;
    private Flowable<List<CategoryEntity>> mCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mCategoryDao = ((AppComponent) application).getDatabase().categoryDao();
        mCategories = mCategoryDao.getAll(); // Flowable でタスクのリストを管理
    }

    public Flowable<List<String>> getCategoryTextList() {
        return mCategories
                .map(categories -> categories.stream()
                        .map(CategoryEntity::getText)
                        .collect(Collectors.toList()));
    }

    public Completable insertCategory(String text) {
        CategoryEntity category = new CategoryEntity();
        category.setText(text);
        return mCategoryDao.insert(category);
    }

    public Completable updateCategory(int position, String text) {
        return mCategories.firstOrError() // 最新のタスクリストを取得
                .flatMapCompletable(categories -> {
                    if (position < 0 || position >= categories.size()) {
                        return Completable.error(new IndexOutOfBoundsException("Invalid position"));
                    }
                    CategoryEntity category = categories.get(position);
                    category.setText(text);
                    return mCategoryDao.update(category);
                });
    }

    public Completable deleteCategory(int position) {
        return mCategories.firstOrError()
                .flatMapCompletable(categories -> {
                    if (position < 0 || position >= categories.size()) {
                        return Completable.error(new IndexOutOfBoundsException("Invalid position"));
                    }
                    return mCategoryDao.delete(categories.get(position));
                });
    }
}