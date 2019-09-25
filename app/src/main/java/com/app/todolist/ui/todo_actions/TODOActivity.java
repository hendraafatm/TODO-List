package com.app.todolist.ui.todo_actions;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.todolist.R;
import com.app.todolist.data.model.TODOItem;
import com.app.todolist.helper.AppConstants;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TODOActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.etTodoName)
    EditText etTodoName;
    @BindView(R.id.tvDate)
    TextView tvDate;

    private DatePickerDialog datePickerDialog;
    ToDoViewModel toDoViewModel;
    TODOItem todoItem = null;

    public static void launch(Context context, TODOItem todoItem) {
        Intent intent = new Intent(context, TODOActivity.class);
        intent.putExtra(AppConstants.TODO_ITEM, todoItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ButterKnife.bind(this);


        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);


        todoItem = (TODOItem) getIntent().getSerializableExtra(AppConstants.TODO_ITEM);
        if (todoItem != null) {
            onPreFillData();
        }
    }

    private void onPreFillData() {
        if (!todoItem.getName().isEmpty()) {
            etTodoName.setText(todoItem.getName());
        }
        if (!todoItem.getDate().isEmpty()) {
            tvDate.setText(todoItem.getDate());
        }
    }


    public void onSaveToDo(View view) {
        String name = etTodoName.getText().toString();
        String date = tvDate.getText().toString();

        if (name.isEmpty()) {
            etTodoName.setError(getString(R.string.empty_name));
            etTodoName.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            tvDate.setError(getString(R.string.empty_date));
            tvDate.requestFocus();
            return;
        }

        if (todoItem == null) {
            todoItem = new TODOItem();
            onAddToDo(name, date);

        } else {
            onUpdateToDo(name, date);
        }

        finish();
    }

    private void onUpdateToDo(String name, String date) {
        todoItem.setName(name);
        todoItem.setDate(date);
        todoItem.setCompleted(false);
        toDoViewModel.updateTODO(todoItem);

        EventBus.getDefault().post(todoItem);
    }

    private void onAddToDo(String name, String date) {
        todoItem.setName(name);
        todoItem.setDate(date);
        todoItem.setCompleted(false);
        toDoViewModel.addTODO(todoItem);

        EventBus.getDefault().post(todoItem);
    }

    public void onChooseDate(View view) {
        datePickerDialog = DatePickerDialog.newInstance(this);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setTitle("Date Picker");

        datePickerDialog.show(getFragmentManager(), "Date Picker");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + monthOfYear + "-" + dayOfMonth;
        tvDate.setText(date);
    }
}
