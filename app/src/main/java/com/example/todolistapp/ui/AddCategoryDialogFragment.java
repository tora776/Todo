package com.example.todolistapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.todolistapp.R;
import com.example.todolistapp.viewmodel.CategoryViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddCategoryDialogFragment extends DialogFragment {
    private final static String TAG = "AddCategoryDialogFragment";
    private CategoryViewModel mCategoryViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_category_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_add_category_dialog, null));

        builder.setMessage("カテゴリーの追加")
                .setPositiveButton("OK", (dialog, id) ->{
                    EditText editText = (EditText) getDialog().findViewById(R.id.add_category_text);
                    if(editText != null){
                        mDisposable.add(mCategoryViewModel.insertCategory(editText.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {}, throwable -> Log.e(TAG, "Unable to insert.", throwable)));
                    } else {
                        Log.e(TAG, "No text.");
                    }
                })
                .setNegativeButton("キャンセル", (dialog, id) -> {});

        return builder.create();
    }

    @Override
    public void onStop(){
        super.onStop();
        mDisposable.clear();
    }
}