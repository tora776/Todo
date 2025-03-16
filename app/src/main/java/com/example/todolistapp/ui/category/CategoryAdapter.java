package com.example.todolistapp.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * リストのデータと画面の表示を紐づける
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    // Taskリストの一覧
    private List<String> mData;
    private CategoryListener mDeleteListener;
    private CategoryListener mUpdateListener;

    public CategoryAdapter(){
        this.mData = new ArrayList<>();
    }

    public int getItemCount(){
        return mData.size();
    }

    public void setDeleteCategoryListener(CategoryListener listener){
        mDeleteListener = listener;
    }

    public void setUpdateCategoryListener(CategoryListener listener){ mUpdateListener = listener; }

    public void setData(List<String> data){
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.getCategoryTextView().setText(mData.get(position));
        // 削除ボタン押下時
        holder.getDeleteCategoryButton().setTag(position);
        holder.getDeleteCategoryButton().setOnClickListener(v -> {
            if (mDeleteListener != null) {
                mDeleteListener.onClickDeleteCategory(position);
            }
        });
        // 更新ボタン押下時
        holder.getUpdateCategoryButton().setTag(position);
        holder.getUpdateCategoryButton().setOnClickListener(v -> {
            if (mUpdateListener != null){
                mUpdateListener.onClickUpdateCategory(position);
            }
        });
        // タスク押下時
        holder.itemView.setOnClickListener(v -> {
            // ContextからActivityを取得
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            // タスクのテキストを渡す準備
            Bundle args = new Bundle();
            args.putInt("POSITION", position);
            args.putString("DETAILED_TASK", mData.get(position));
            // Fragmentのインスタンスを作成
            DetailedCategoryListFragment fragment = new DetailedCategoryListFragment();
            // タスクのテキストを渡す
            fragment.setArguments(args);
            // Fragmentを追加または置換
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.task_list_fragment_view, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCategoryTextView;
        private final Button mDeleteTaskButton;
        private final Button mUpdateCategoryButton;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mCategoryTextView = (TextView) itemView.findViewById(R.id.category_text);
            mDeleteTaskButton = (Button) itemView.findViewById(R.id.delete_category_button);
            mUpdateCategoryButton = (Button) itemView.findViewById(R.id.update_category_button);
        }

        public TextView getCategoryTextView() {
            return mCategoryTextView;
        }

        public Button getDeleteCategoryButton() {
            return mDeleteTaskButton;
        }

        public Button getUpdateCategoryButton() {
            return mUpdateCategoryButton;
        }
    }
}
