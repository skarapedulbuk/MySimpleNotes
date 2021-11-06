package com.skarapedulbuk.mysimplenotes.domain;


import com.skarapedulbuk.mysimplenotes.R;

import java.util.ArrayList;
import java.util.List;

public class InmemoryTasksRepository implements TasksRepository {
    @Override
    public List<MyTask> getTasks() {

        ArrayList<MyTask> result = new ArrayList<>();

        result.add(new MyTask(R.string.task1, R.string.description1, R.bool.task_1_is_done));
        result.add(new MyTask(R.string.task2, R.string.description2, R.bool.task_2_is_done));
        result.add(new MyTask(R.string.task3, R.string.description3, R.bool.task_3_is_done));
        result.add(new MyTask(R.string.task4, R.string.description4, R.bool.task_4_is_done));


        return result;
    }
}
