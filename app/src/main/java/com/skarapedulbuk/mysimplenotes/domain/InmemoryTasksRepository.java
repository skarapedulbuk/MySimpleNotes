package com.skarapedulbuk.mysimplenotes.domain;


import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InmemoryTasksRepository implements TasksRepository {

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private final List<MyTask> result = new ArrayList<>();

    @Override
    public void getTasks(Callback<List<MyTask>> callback) {

        executor.execute(() -> {
            result.add(new MyTask("Название1", "Описание1", true));
            result.add(new MyTask("Название2", "Описание2 Описание2 Описание2 Описание2 Описание2", false));
            result.add(new MyTask("Название3", "Описание3", true));
            result.add(new MyTask("Название4", "Описание4", false));
            result.add(new MyTask("Название5", "Описание5", true));

            mainThreadHandler.post(() -> callback.onSuccess(result));
        });
    }


    @Override
    public void clear(Callback<Void> callback) {
        executor.execute(() -> {
            result.clear();
            mainThreadHandler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public void add(String title, String description, Boolean isDone, Callback<MyTask> callback) {

        MyTask task = new MyTask(title, description, isDone);

        result.add(task);

        mainThreadHandler.post(() -> callback.onSuccess(task));
    }
}
