package com.app.todolist.ui.main.tabs.completed;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.todolist.R;
import com.app.todolist.data.model.TODOItem;
import com.app.todolist.ui.main.tabs.adapters.ToDoListAdapter;
import com.app.todolist.ui.main.tabs.todo.TodosViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompletedTODOsFragment extends Fragment {

    private CompletedTodosViewModel mViewModel;

    @BindView(R.id.rvTODOs)
    RecyclerView rvToDo;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private ArrayList<TODOItem> todoList;
    private ToDoListAdapter toDoListAdapter;

    public static CompletedTODOsFragment newInstance() {
        return new CompletedTODOsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_todos, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CompletedTodosViewModel.class);
        // TODO: Use the ViewModel

        bindUI();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void bindUI() {
        todoList = new ArrayList<>();

        toDoListAdapter = new ToDoListAdapter(todoList, null);
        rvToDo.setAdapter(toDoListAdapter);

        mViewModel.getTodoList();
        mViewModel.getTodoLiveData().observe(this, this::onCompletedToDoListReceived);
    }

    private void onCompletedToDoListReceived(List<TODOItem> todoItems) {
        if (!todoItems.isEmpty()) {
            todoList.addAll(todoItems);
            toDoListAdapter.notifyDataSetChanged();
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    // here to listen on todoitem change to complete
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToDoItemReceived(TODOItem todoItem) {
        if (todoItem.isCompleted()) {
            todoList.add(todoItem);
            toDoListAdapter.notifyDataSetChanged();

            tvNoData.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

}
