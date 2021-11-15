package com.skarapedulbuk.mysimplenotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;

import java.util.ArrayList;
import java.util.Collection;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private ArrayList<MyTask> tasks = new ArrayList<>();

    public void setTasks(Collection<MyTask> toSet) {
        tasks.clear();
        tasks.addAll(toSet);
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.TasksViewHolder holder, int position) {
        MyTask task = tasks.get(position);
        holder.titleCheckBox.setText(task.getTaskTitle());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {

        CheckBox titleCheckBox;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCheckBox = itemView.findViewById(R.id.checkbox_of_task);
        }
    }
}
