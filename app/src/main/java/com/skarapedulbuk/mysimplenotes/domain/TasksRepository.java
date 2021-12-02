package com.skarapedulbuk.mysimplenotes.domain;

import java.util.List;

public interface TasksRepository {
    void getTasks(Callback<List<MyTask>> callback);

    void clear(Callback<Void> callback);

    void add(String title, String description, Boolean isDone, Callback<MyTask> callback);

    void edit(String id, String title, String description, Boolean isDone, Callback<MyTask> callback);

    void delete(MyTask myTask, Callback<MyTask> callback);

}
