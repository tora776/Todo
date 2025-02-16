package com.example.todolistapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * リストのデータと画面の表示を紐づける
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    // Taskリストの一覧
    private List<String> mData;
    private DeleteTaskListener mListener;

    public TaskAdapter(){
        this.mData = new ArrayList<>();
    }

    public int getItemCount(){
        return mData.size();
    }

    public void setDeleteTaskListener(DeleteTaskListener listener){
        mListener = listener;
    }

    public void setData(List<String> data){
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.getTaskTextView().setText(mData.get(position));
        holder.getDeleteTaskButton().setTag(position);
        holder.getDeleteTaskButton().setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onClickDeleteTask(position);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTaskTextView;
        private final Button mDeleteTaskButton;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mTaskTextView = (TextView) itemView.findViewById(R.id.task_text);
            mDeleteTaskButton = (Button) itemView.findViewById(R.id.delete_task_button);
        }

        public TextView getTaskTextView() {
            return mTaskTextView;
        }

        public Button getDeleteTaskButton() {
            return mDeleteTaskButton;
        }
    }
}
