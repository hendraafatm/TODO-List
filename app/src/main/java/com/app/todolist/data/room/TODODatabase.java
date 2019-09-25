package com.app.todolist.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.app.todolist.data.model.TODOItem;


@Database(entities = {TODOItem.class}, version = 1, exportSchema = false)
public abstract class TODODatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
