package com.skarapedulbuk.mysimplenotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.BoolRes;
import androidx.annotation.StringRes;

public class MyTask implements Parcelable {

    @StringRes
    private final int taskTitle;

    @StringRes
    private final int taskDescription;

    @BoolRes
    private final int taskIsDone;

    public int getTaskTitle() {
        return taskTitle;
    }

    public int getTaskDescription() {
        return taskDescription;
    }

    public int getTaskIsDone() {
        return taskIsDone;
    }

    public MyTask(int taskTitle, int taskDescription, int taskIsDone) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskIsDone = taskIsDone;
    }

    protected MyTask(Parcel in) {
        taskTitle = in.readInt();
        taskDescription = in.readInt();
        taskIsDone = in.readInt();
    }

    public static final Creator<MyTask> CREATOR = new Creator<MyTask>() {
        @Override
        public MyTask createFromParcel(Parcel in) {
            return new MyTask(in);
        }

        @Override
        public MyTask[] newArray(int size) {
            return new MyTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(taskTitle);
        dest.writeInt(taskDescription);
        dest.writeInt(taskIsDone);
    }
}
