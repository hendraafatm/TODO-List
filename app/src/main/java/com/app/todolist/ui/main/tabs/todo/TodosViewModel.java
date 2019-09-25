package com.app.todolist.ui.main.tabs.todo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;

import com.app.todolist.data.model.TODOItem;
import com.app.todolist.data.room.TODORepository;
import com.app.todolist.helper.BaseViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class TodosViewModel extends BaseViewModel {

    private final WeakReference<Context> mContext;
    MutableLiveData<List<TODOItem>> todoLiveData;
    TODORepository todoRepository;

    public TodosViewModel(@NonNull Application app) {
        super(app);
        mContext = new WeakReference<>(app.getApplicationContext());
        todoLiveData = new MutableLiveData<>();

        todoRepository = new TODORepository(mContext.get());
    }

    public void getTodoList() {
        new AsyncTask<Void, Void, List<TODOItem>>() {

            @Override
            protected List<TODOItem> doInBackground(Void... voids) {
                return todoRepository.getTODOList();
            }

            @Override
            protected void onPostExecute(List<TODOItem> todoItems) {
                super.onPostExecute(todoItems);

                todoLiveData.postValue(todoItems);

            }
        }.execute();
    }

    public void deleteToDo(TODOItem todoItem) {
        todoRepository.deleteTODO(todoItem);
    }

    public void updateToDo(TODOItem todoItem) {
        todoRepository.updateTODO(todoItem);
    }


    public MutableLiveData<List<TODOItem>> getTodoLiveData() {
        return todoLiveData;
    }
}
