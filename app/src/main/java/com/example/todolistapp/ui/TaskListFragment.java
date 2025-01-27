package com.example.todolistapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;


public class TaskListFragment extends Fragment implements DeleteTaskListener {
    private RecyclerView mTaskListRecyclerView;
    private TaskAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskListRecyclerView = (RecyclerView) view.findViewById(R.id.task_list_view);
        // TODO:アダプターをRecyclerViewと紐づける
        mAdapter = new TaskAdapter();
        mAdapter.setDeleteTaskListener(this);
        mTaskListRecyclerView.setAdapter(mAdapter);



        return view;
    }

    @Override
    public void onClickDeleteTask(int position){
        // TODO:ViewModelをコールする。
    }
}
