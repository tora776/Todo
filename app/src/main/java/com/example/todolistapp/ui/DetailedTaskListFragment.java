package com.example.todolistapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todolistapp.R;



public class DetailedTaskListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_task_list, container, false);
        /*
        // 戻るボタンを活性化
        MainActivity activity = (MainActivity) getActivity();
        activity.setTitle("タスク");
        // 戻るボタンを活性化
        activity.setupBackButton(true);
        // この記述でフラグメントにてアクションバーメニューが使えるようになる
        setHasOptionsMenu(true);

         */

        // 値を受け取る
        int position = requireArguments().getInt("POSITION", 0);
        String categoryName = requireArguments().getString("DETAILED_TASK");
        TextView textView = view.findViewById(R.id.category_text);
        textView.setText(categoryName);
        // Inflate the layout for this fragment
        return view;
    }

    // アクションバーを押したときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            // android.R.id.homeで戻るボタン「←」を押したときの動作を検知
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}