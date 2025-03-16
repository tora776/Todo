package com.example.todolistapp.ui.task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistapp.R;
import com.example.todolistapp.viewmodel.TaskViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateTaskDialogFragment extends DialogFragment {
    private final static String TAG = "AddTaskDialogFragment";
    private TaskViewModel mTaskViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        // Inflate the layout for this fragment
        // TODO:ダイアログ名修正(Addしていないため)
        return inflater.inflate(R.layout.fragment_input_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 値を受け取る
        int position = requireArguments().getInt("POSITION", 0);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_input_dialog, null));

        builder.setMessage("タスクの更新")

                .setPositiveButton("OK", (dialog, id) ->{
                    EditText editText = (EditText) getDialog().findViewById(R.id.add_category_text);
                    // TODO:ダイアログ表示時、事前にTask名を入力しておく
                    // editText.setText(getResources().getResourceEntryName(R.id.task_text));
                    if(editText != null){
                        mDisposable.add(mTaskViewModel.updateTask(position, editText.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {}, throwable -> Log.e(TAG, "Unable to update.", throwable)));
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