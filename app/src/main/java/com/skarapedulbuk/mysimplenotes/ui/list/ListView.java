package com.skarapedulbuk.mysimplenotes.ui.list;


import com.skarapedulbuk.mysimplenotes.domain.MyTask;

import java.util.List;

public interface ListView {
    void showTasks(List<MyTask> tasks);

    void clearTasks();
}
