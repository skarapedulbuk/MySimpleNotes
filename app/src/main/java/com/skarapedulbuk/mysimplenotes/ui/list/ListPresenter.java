package com.skarapedulbuk.mysimplenotes.ui.list;


import com.skarapedulbuk.mysimplenotes.domain.Callback;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;
import com.skarapedulbuk.mysimplenotes.domain.TasksRepository;

import java.util.List;

public class ListPresenter {
    private final ListView view;
    private final TasksRepository repository;

    public ListPresenter(ListView view, TasksRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestTasks() {
        repository.getTasks(new Callback<List<MyTask>>() {
            @Override
            public void onSuccess(List<MyTask> result) {
                view.showTasks(result);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void removeAll() {
        repository.clear(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.clearTasks();
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void add(String title, String description, boolean b) {
        repository.add(title, description, b, new Callback<MyTask>() {
            @Override
            public void onSuccess(MyTask result) {
                view.addTask(result);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void delete(MyTask selectedTask) {
        repository.delete(selectedTask, new Callback<MyTask>() {
            @Override
            public void onSuccess(MyTask result) {
                view.deleteTask(selectedTask);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void edit(String title, String description, Boolean isDone, MyTask selectedTask) {
        repository.edit(selectedTask.getId(), title, description, isDone, new Callback<MyTask>() {

            @Override
            public void onSuccess(MyTask result) {
                view.editTask(result);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
