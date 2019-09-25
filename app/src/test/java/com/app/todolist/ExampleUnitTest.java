package com.app.todolist;

import com.app.todolist.data.model.TODOItem;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void markAsComplete() {
        TODOItem todoItem = new TODOItem();
        todoItem.setName("Name");
        todoItem.setDate("25-5-2019");
        todoItem.setCompleted(false);

        todoItem.setCompleted(true);

        assertTrue(todoItem.isCompleted());
    }
}