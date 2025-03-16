package com.example.todolistapp.ui.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;
import com.example.todolistapp.ui.MainActivity;
import com.example.todolistapp.ui.task.TaskListFragment;
import com.example.todolistapp.viewmodel.CategoryViewModel;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CategoryListFragment extends Fragment implements CategoryListener {
    private final static String TAG = "TaskListFragment";
    private RecyclerView mCategoryListRecyclerView;
    private CategoryAdapter mAdapter;
    private CategoryViewModel mCategoryViewModel;
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
        // layoutフォルダのfragment_category_list.xmlを使用する
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryListRecyclerView = (RecyclerView) view.findViewById(R.id.category_list_view);
        mAdapter = new CategoryAdapter();
        mAdapter.setDeleteCategoryListener(this);
        mAdapter.setUpdateCategoryListener(this);
        mCategoryListRecyclerView.setAdapter(mAdapter);

        // 所属している親アクティビティを取得
        MainActivity activity = (MainActivity) getActivity();
        Objects.requireNonNull(activity).setTitle("タスクのカテゴリー");
        // 戻るボタンを非活性にする
        activity.setupBackButton(false);

        return view;
    }

    // 開始時処理
    // @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart(){
        super.onStart();
        // RecyclerViewに対してテキストのリストを渡している
        mDisposable.add(mCategoryViewModel.getCategoryTextList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textList -> mAdapter.setData(textList)
                        , throwable -> Log.e(TAG, "Unable to read tasks.", throwable)));
    }

    // Delete処理
    @Override
    public void onClickDeleteCategory(int position){
        mDisposable.add(mCategoryViewModel.deleteCategory(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}
                        , throwable -> Log.e(TAG, "Unable to delete.", throwable)));
    }

    // Update処理
    @Override
    public void onClickUpdateCategory(int position){
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        UpdateCategoryDialogFragment dialog = new UpdateCategoryDialogFragment();
        dialog.setArguments(args);
        dialog.show(requireActivity().getSupportFragmentManager(), "UpdateCategoryDialogFragment");
    }
}
