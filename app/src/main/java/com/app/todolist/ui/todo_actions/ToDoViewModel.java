package com.app.todolist.ui.todo_actions;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.app.todolist.data.model.TODOItem;
import com.app.todolist.data.room.TODORepository;
import com.app.todolist.helper.BaseViewModel;

import java.lang.ref.WeakReference;

public class ToDoViewModel extends BaseViewModel {

    private final WeakReference<Context> mContext;
    TODORepository todoRepository;

    public ToDoViewModel(@NonNull Application application) {
        super(application);

        mContext = new WeakReference<>(application.getApplicationContext());
        todoRepository = new TODORepository(mContext.get());
    }

    public void addTODO(TODOItem todoItem) {
        todoRepository.insertTODO(todoItem);
    }

    public void updateTODO(TODOItem todoItem) {
        todoRepository.updateTODO(todoItem);
    }
}
