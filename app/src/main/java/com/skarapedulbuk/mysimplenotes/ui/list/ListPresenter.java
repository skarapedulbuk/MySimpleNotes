package com.skarapedulbuk.mysimplenotes.ui.list;


import com.skarapedulbuk.mysimplenotes.domain.TasksRepository;

public class ListPresenter {
    private final ListView view;
    private final TasksRepository repository;

    public ListPresenter(ListView view, TasksRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestTasks() {
        view.showTasks(repository.getTasks());
    }
}
