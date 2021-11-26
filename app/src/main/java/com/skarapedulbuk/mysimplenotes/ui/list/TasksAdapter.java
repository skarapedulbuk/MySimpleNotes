package com.skarapedulbuk.mysimplenotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;

import java.util.ArrayList;
import java.util.Collection;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    interface OnTaskClicked {
        void onTaskClicked(MyTask task);
    }

    private ArrayList<MyTask> tasks = new ArrayList<>();

    public void setTaskClicked(OnTaskClicked taskClicked) {
        this.taskClicked = taskClicked;
    }

    private OnTaskClicked taskClicked;

    public OnTaskClicked getTaskClicked() {
        return taskClicked;
    }

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
        holder.descriptionTextView.setText(task.getTaskDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {

        CheckBox titleCheckBox;
        FloatingActionButton editButton;
        TextView descriptionTextView;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCheckBox = itemView.findViewById(R.id.checkbox_of_task);
            titleCheckBox.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Checkbox Click", Toast.LENGTH_SHORT).show();
            });

            descriptionTextView = itemView.findViewById(R.id.description_of_task);


            editButton = itemView.findViewById(R.id.edit_button);
            editButton.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Edit", Toast.LENGTH_SHORT).show();
                if (getTaskClicked() != null) {
                    getTaskClicked().onTaskClicked(tasks.get(getAdapterPosition()));
                }

            });
        }
    }
}
