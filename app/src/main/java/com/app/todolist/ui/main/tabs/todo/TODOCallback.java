package com.app.todolist.ui.main.tabs.todo;

public interface TODOCallback {

    void onItemComplete(int position);

    void onItemEdit(int position);

    void onItemDelete(int position);
}
