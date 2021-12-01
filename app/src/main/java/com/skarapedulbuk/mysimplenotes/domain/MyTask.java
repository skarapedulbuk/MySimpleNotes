package com.skarapedulbuk.mysimplenotes.domain;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class MyTask implements Parcelable {

    private String id;
    private String taskTitle;
    private String taskDescription;
    private Boolean taskIsDone;

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Boolean getTaskIsDone() {
        return taskIsDone;
    }

    public String getId() {
        return id;
    }

    public MyTask(String id, String taskTitle, String taskDescription, Boolean taskIsDone) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskIsDone = taskIsDone;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected MyTask(Parcel in) {
        id = in.readString();
        taskTitle = in.readString();
        taskDescription = in.readString();
        taskIsDone = in.readBoolean();
    }

    public static final Creator<MyTask> CREATOR = new Creator<MyTask>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taskTitle);
        dest.writeString(taskDescription);
        dest.writeBoolean(taskIsDone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyTask task = (MyTask) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(taskTitle, task.taskTitle) &&
                Objects.equals(taskDescription, task.taskDescription) &&
                Objects.equals(taskIsDone, task.taskIsDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskTitle, taskDescription, taskIsDone);
    }
}
