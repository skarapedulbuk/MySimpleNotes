package com.skarapedulbuk.mysimplenotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;

import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;
import com.skarapedulbuk.mysimplenotes.ui.details.DetailsFragment;
import com.skarapedulbuk.mysimplenotes.ui.list.ListFragment;

public class MainActivity extends AppCompatActivity implements ListFragment.OnTaskClicked {
    private static final String ARG_TASK = "ARG_TASK";

    private MyTask selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (!(fragmentManager.findFragmentById(R.id.fragment_list_container) instanceof ListFragment)) {
            fragmentManager.popBackStack();
        }
        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_TASK)) {
            selectedTask = savedInstanceState.getParcelable(ARG_TASK);

            if (isLandscape) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_TASK, selectedTask);

                fragmentManager.setFragmentResult(DetailsFragment.KEY_LIST_DETAILS, bundle);
            } else {


                DetailsFragment detailsFragment = DetailsFragment.newInstance(selectedTask);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_list_container, detailsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
        getSupportFragmentManager().setFragmentResultListener(ListFragment.KEY_LIST_ACTIVITY,
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                        selectedTask = result.getParcelable(ListFragment.ARG_TASK);

                        if (isLandscape) {
                            fragmentManager.setFragmentResult(DetailsFragment.KEY_LIST_DETAILS, result);
                        } else {
                            DetailsFragment detailsFragment = DetailsFragment.newInstance(selectedTask);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_list_container, detailsFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (selectedTask != null) {
            outState.putParcelable(ARG_TASK, selectedTask);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTaskClicked(MyTask task) {
        DetailsFragment detailsFragment = DetailsFragment.newInstance(task);

        FragmentManager fragmentManager = getSupportFragmentManager();

        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_details_container, detailsFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_list_container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }
}