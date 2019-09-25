package com.app.todolist.ui.main.tabs.completed;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.app.todolist.data.model.TODOItem;
import com.app.todolist.data.room.TODORepository;
import com.app.todolist.helper.BaseViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class CompletedTodosViewModel extends BaseViewModel {

    private final WeakReference<Context> mContext;
    MutableLiveData<List<TODOItem>> todoLiveData;
    TODORepository todoRepository;

    public CompletedTodosViewModel(@NonNull Application application) {
        super(application);

        mContext = new WeakReference<>(application.getApplicationContext());
        todoLiveData = new MutableLiveData<>();

        todoRepository = new TODORepository(mContext.get());
    }

    public void getTodoList() {
        new AsyncTask<Void, Void, List<TODOItem>>() {

            @Override
            protected List<TODOItem> doInBackground(Void... voids) {
                return todoRepository.getCompletedTODOList();
            }

            @Override
            protected void onPostExecute(List<TODOItem> todoItems) {
                super.onPostExecute(todoItems);

                todoLiveData.postValue(todoItems);

            }
        }.execute();
    }


    public MutableLiveData<List<TODOItem>> getTodoLiveData() {
        return todoLiveData;
    }
}
