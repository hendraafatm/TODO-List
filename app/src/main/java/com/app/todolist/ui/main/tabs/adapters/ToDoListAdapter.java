package com.app.todolist.ui.main.tabs.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.todolist.R;
import com.app.todolist.data.model.TODOItem;
import com.app.todolist.ui.main.tabs.todo.TODOCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.MyViewHolder> {

    List<TODOItem> listItems;
    TODOCallback todoCallback;

    public ToDoListAdapter(List<TODOItem> listItems, TODOCallback todoCallback) {
        this.listItems = listItems;
        this.todoCallback = todoCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        TODOItem todoItem = listItems.get(position);
        myViewHolder.tvTodoName.setText(todoItem.getName());
        myViewHolder.tvTodoDate.setText(todoItem.getDate());

        if (todoItem.isCompleted()) {
            myViewHolder.separator.setVisibility(View.GONE);
            myViewHolder.actionsView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_todo)
        public TextView tvTodoName;
        @BindView(R.id.tv_date)
        public TextView tvTodoDate;
        @BindView(R.id.separator)
        View separator;
        @BindView(R.id.actionsView)
        LinearLayout actionsView;
        @BindView(R.id.ic_complete)
        public ImageView ivComplete;
        @BindView(R.id.ic_edit)
        public ImageView ivEdit;
        @BindView(R.id.ic_delete)
        public ImageView ivDelete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            ivComplete.setOnClickListener(v -> {
                todoCallback.onItemComplete(getAdapterPosition());
            });

            ivEdit.setOnClickListener(v -> {
                todoCallback.onItemEdit(getAdapterPosition());
            });

            ivDelete.setOnClickListener(v -> {
                todoCallback.onItemDelete(getAdapterPosition());
            });
        }
    }
}
