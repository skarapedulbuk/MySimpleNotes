package com.skarapedulbuk.mysimplenotes.domain;


import android.os.Handler;
import android.os.Looper;

import com.skarapedulbuk.mysimplenotes.R;

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

        executor.execute(new Runnable() {
            @Override
            public void run() {

                result.add(new MyTask(R.string.task1, R.string.description1, R.bool.task_1_is_done));
                result.add(new MyTask(R.string.task2, R.string.description2, R.bool.task_2_is_done));
                result.add(new MyTask(R.string.task3, R.string.description3, R.bool.task_3_is_done));
                result.add(new MyTask(R.string.task4, R.string.description4, R.bool.task_4_is_done));


                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }
        });
    }


    @Override
    public void clear(Callback<Void> callback) {
        executor.execute(() -> {
            result.clear();
            mainThreadHandler.post(()->{
               callback.onSuccess(null);
            });
        });
    }
}
