package com.skarapedulbuk.mysimplenotes.ui.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;


public class DetailsFragment extends Fragment {

    public static final String ARG_TASK = "ARG_TASK";
    public static final String KEY_LIST_DETAILS = "KEY_LIST_DETAILS";

    // private final MyTask task;


    public DetailsFragment() {
        super(R.layout.fragment_details);

    }

    /*public DetailsFragment(MyTask task) {
        super(R.layout.fragment_details);
        this.task = task;
    }*/
    public static DetailsFragment newInstance(MyTask task) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckedTextView title = view.findViewById(R.id.task_title);
        TextView description = view.findViewById(R.id.task_description);

        if (getArguments() != null && getArguments().containsKey(ARG_TASK)) {

            MyTask task = getArguments().getParcelable(ARG_TASK);

            title.setText(task.getTaskTitle());
            description.setText(task.getTaskDescription());
            title.setChecked(getResources().getBoolean(task.getTaskIsDone()));

        }

        getParentFragmentManager().setFragmentResultListener(DetailsFragment.KEY_LIST_DETAILS,
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        MyTask task1 = result.getParcelable(ARG_TASK);

                        title.setText(task1.getTaskTitle());
                        description.setText(task1.getTaskDescription());
                        title.setChecked(getResources().getBoolean(task1.getTaskIsDone()));

                    }
                });
    }


}