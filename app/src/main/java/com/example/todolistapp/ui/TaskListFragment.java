package com.example.todolistapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;
import com.example.todolistapp.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class TaskListFragment extends Fragment implements TaskListener {
    private final static String TAG = "TaskListFragment";
    private RecyclerView mTaskListRecyclerView;
    private TaskAdapter mAdapter;
    private TaskViewModel mTaskViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    // 初期化メソッド
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    // ビューを生成する
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // layoutフォルダのfragment_task_list.xmlを使用する
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskListRecyclerView = (RecyclerView) view.findViewById(R.id.task_list_view);
        mAdapter = new TaskAdapter();
        mAdapter.setDeleteTaskListener(this);
        mAdapter.setUpdateTaskListener(this);
        mTaskListRecyclerView.setAdapter(mAdapter);

        return view;
    }

    // @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart(){
        super.onStart();
        // RecyclerViewに対してテキストのリストを渡している
        mDisposable.add(mTaskViewModel.getTaskTextList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textList -> mAdapter.setData(textList)
                        , throwable -> Log.e(TAG, "Unable to read tasks.", throwable)));
    }

    @Override
    public void onClickDeleteTask(int position){
        mDisposable.add(mTaskViewModel.deleteTask(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}
                        , throwable -> Log.e(TAG, "Unable to delete.", throwable)));

    }

    // 動作確認のため削除処理を記載。TODO:Update処理に変更(ダイアログ表示してテキスト変更)
    @Override
    public void onClickUpdateTask(int position){
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        UpdateTaskDialogFragment dialog = new UpdateTaskDialogFragment();
        dialog.setArguments(args);
        dialog.show(requireActivity().getSupportFragmentManager(), "UpdateTaskDialogFragment");
    }
}
