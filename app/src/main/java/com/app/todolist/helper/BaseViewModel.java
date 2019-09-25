package com.app.todolist.helper;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;


public abstract class BaseViewModel extends AndroidViewModel {

    private final WeakReference<Context> mContext;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mContext = new WeakReference<>(application.getApplicationContext());
    }


}
