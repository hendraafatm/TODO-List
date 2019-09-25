package com.app.todolist.data.room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.app.todolist.data.model.TODOItem;

import java.util.List;

public class TODORepository {

    private String DB_NAME = "db_todo";

    private TODODatabase contentDatabase;

    public TODORepository(Context context) {
        contentDatabase = Room.databaseBuilder(context, TODODatabase.class, DB_NAME).build();
    }

    public void insertTODO(final TODOItem todoItem) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                contentDatabase.daoAccess().insertTodo(todoItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    public void updateTODO(final TODOItem todoItem) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                contentDatabase.daoAccess().updateTodo(todoItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    public void deleteTODO(final TODOItem todoItem) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                contentDatabase.daoAccess().deleteTodo(todoItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    public List<TODOItem> getTODOList() {
        return contentDatabase.daoAccess().fetchAllTODOs();
    }

    public List<TODOItem> getCompletedTODOList() {
        return contentDatabase.daoAccess().fetchCompletedTODOs();
    }

}
