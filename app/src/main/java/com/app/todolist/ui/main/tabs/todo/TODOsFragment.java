package com.app.todolist.ui.main.tabs.todo;

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
import com.app.todolist.ui.todo_actions.TODOActivity;
import com.app.todolist.ui.main.tabs.adapters.ToDoListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TODOsFragment extends Fragment implements TODOCallback {

    @BindView(R.id.rvTODOs)
    RecyclerView rvToDo;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.btnAddToDO)
    FloatingActionButton btnAddToDO;

    private TodosViewModel mViewModel;
    private ArrayList<TODOItem> todoList;
    private ToDoListAdapter toDoListAdapter;

    public static TODOsFragment newInstance() {
        return new TODOsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TodosViewModel.class);
        // TODO: Use the ViewModel


        bindUI();

        btnAddToDO.setOnClickListener(v -> {
            TODOActivity.launch(getActivity(), null);
        });

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    private void bindUI() {
        todoList = new ArrayList<>();

        toDoListAdapter = new ToDoListAdapter(todoList, this);
        rvToDo.setAdapter(toDoListAdapter);

        mViewModel.getTodoList();
        mViewModel.getTodoLiveData().observe(this, this::onToDoListReceived);
    }

    private void onToDoListReceived(List<TODOItem> todoItems) {
        if (!todoItems.isEmpty()) {
            todoList.clear();
            todoList.addAll(todoItems);
            toDoListAdapter.notifyDataSetChanged();
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemComplete(int position) {
        todoList.get(position).setCompleted(true);
        mViewModel.updateToDo(todoList.get(position));

        // notify completed fragment to add item as completed
        EventBus.getDefault().post(todoList.get(position));

        todoList.remove(position);
        toDoListAdapter.notifyItemRemoved(position);

        if (todoList.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemEdit(int position) {
        TODOActivity.launch(getActivity(), todoList.get(position));
    }

    @Override
    public void onItemDelete(int position) {
        mViewModel.deleteToDo(todoList.get(position));
        todoList.remove(position);
        toDoListAdapter.notifyItemRemoved(position);

        if (todoList.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToDoItemReceived(TODOItem todoItem) {
        mViewModel.getTodoList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}
