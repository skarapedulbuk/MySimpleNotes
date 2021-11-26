package com.skarapedulbuk.mysimplenotes.domain;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class MyTask implements Parcelable {

    /*@StringRes
    private final int taskTitle;

    @StringRes
    private final int taskDescription;

    @BoolRes
    private final int taskIsDone;*/

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

    public MyTask(String taskTitle, String taskDescription, Boolean taskIsDone) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskIsDone = taskIsDone;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected MyTask(Parcel in) {
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
        dest.writeString(taskTitle);
        dest.writeString(taskDescription);
        dest.writeBoolean(taskIsDone);
    }
}
