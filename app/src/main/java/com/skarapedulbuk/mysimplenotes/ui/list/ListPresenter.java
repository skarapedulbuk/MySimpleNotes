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
}
