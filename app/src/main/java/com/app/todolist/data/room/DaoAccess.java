package com.app.todolist.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.app.todolist.data.model.TODOItem;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertTodo(TODOItem todoItem);

    @Update
    void updateTodo(TODOItem todoItem);

    @Delete
    void deleteTodo(TODOItem todoItem);

    @Query("SELECT * FROM TODOS WHERE isCompleted = 0")
    List<TODOItem> fetchAllTODOs();

    @Query("SELECT * FROM TODOS WHERE isCompleted = 1")
    List<TODOItem> fetchCompletedTODOs();
}
