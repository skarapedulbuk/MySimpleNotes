package com.skarapedulbuk.mysimplenotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;

import java.util.ArrayList;
import java.util.Collection;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private OnTaskClicked taskClicked;
    private Fragment fragment;
    private ArrayList<MyTask> tasks = new ArrayList<>();

    public TasksAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public int deleteTask(MyTask selectedTask) {
        int index = tasks.indexOf(selectedTask);
        tasks.remove(index);
        return index;
    }

    public int editTask(MyTask result) {
        int index = -1;

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(result.getId())) {
                index = i;
                break;
            }
        }

        tasks.set(index, result);
        return index;
    }

    public void addTask(MyTask result) {
        tasks.add(result);
    }

    interface OnTaskClicked {
        void onTaskClicked(View itemView, MyTask task);
    }

    public void setTaskClicked(OnTaskClicked taskClicked) {
        this.taskClicked = taskClicked;
    }

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
        holder.titleCheckBox.setChecked(task.getTaskIsDone());
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

            fragment.registerForContextMenu(itemView);

            editButton = itemView.findViewById(R.id.edit_button);
            editButton.setOnClickListener(v -> {
                //Toast.makeText(v.getContext(), "Context menu of edit button", Toast.LENGTH_SHORT).show();
                if (getTaskClicked() != null) {
                    getTaskClicked().onTaskClicked(v, tasks.get(getAdapterPosition()));
                }

            });
        }
    }
}
